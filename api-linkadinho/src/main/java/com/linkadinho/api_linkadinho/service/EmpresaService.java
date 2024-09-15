package com.linkadinho.api_linkadinho.service;

import com.amazonaws.services.s3.AmazonS3;
import com.linkadinho.api_linkadinho.domain.empresa.DadosAtualizarEmpresa;
import com.linkadinho.api_linkadinho.domain.empresa.DadosCadastroEmpresa;
import com.linkadinho.api_linkadinho.domain.empresa.Empresa;
import com.linkadinho.api_linkadinho.domain.usuario.UserRole;
import com.linkadinho.api_linkadinho.domain.usuario.Usuario;
import com.linkadinho.api_linkadinho.repositories.EmpresaRepository;
import com.linkadinho.api_linkadinho.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AmazonS3 clienteS3;

    @Value("${aws.bucket}")
    private String bucketAWS;

    public Empresa cadastrarEmpresa(DadosCadastroEmpresa dados, String emailUsuario) {
        String imgUrl = null;

        if(dados.image() != null) {
            imgUrl = this.uploadImagem(dados.image());
        }

        Empresa empresa = new Empresa(dados);
        empresa.setCodigo(gerarCodigo());
        empresa.setImgUrl(imgUrl);

        Usuario usuario = (Usuario) usuarioRepository.findByEmail(emailUsuario);
        usuario.setRole(UserRole.ADMIN);
        usuario.setEmpresa(empresa);
        usuarioRepository.save(usuario);

        empresa.setAdmin(usuario);

        return repository.save(empresa);
    }

    public Empresa atualizarEmpresa(DadosAtualizarEmpresa dados){
        var empresa = repository.getReferenceById(dados.id());
        String newimgUrl = null;

        if(dados.image() != null) {

            if(this.deletarImagem(empresa.getImgUrl())) {
                newimgUrl = this.uploadImagem(dados.image());
                empresa.setImgUrl(newimgUrl);
            }
            else {
                System.out.println("Não foi possível atualizar a imagem");
            }

        }

        if(dados.nome() != null) {
            empresa.setNome(dados.nome());
        }

        return repository.save(empresa);
    }

    private String uploadImagem(MultipartFile file) {
        String nome_imagem = UUID.randomUUID() + "-" + file.getOriginalFilename();

        try {
            File arquivo = this.convertMultipartToFile(file);
            clienteS3.putObject(bucketAWS,nome_imagem, arquivo);
            arquivo.delete();
            return clienteS3.getUrl(bucketAWS, nome_imagem).toString();
        } catch (Exception e){
            System.out.println("Erro no upload de arquivo: " + e);
            return null;
        }

    }

    private boolean deletarImagem(String imgUrl) {
        try {
            clienteS3.deleteObject(bucketAWS, imgUrl);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao deletar imagem");
            return false;
        }
    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();

        return convFile;
    }

    private String gerarCodigo(){
        String codigo;
        do {
            codigo = gerarCodigoAleatorio(6);
        } while (repository.existsByCodigo(codigo));

        return codigo;
    }

    private String gerarCodigoAleatorio(int tamanho) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder codigo = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            codigo.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }

        return codigo.toString();
    }
}
