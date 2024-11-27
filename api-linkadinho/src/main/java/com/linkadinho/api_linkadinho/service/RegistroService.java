package com.linkadinho.api_linkadinho.service;

import com.amazonaws.services.s3.AmazonS3;
import com.linkadinho.api_linkadinho.model.empresa.Empresa;
import com.linkadinho.api_linkadinho.model.usuario.Usuario;
import com.linkadinho.api_linkadinho.dto.AtualizarUsuarioDTO;
import com.linkadinho.api_linkadinho.dto.RegistrarUsuarioDTO;
import com.linkadinho.api_linkadinho.infra.exception.EmailJaExistenteException;
import com.linkadinho.api_linkadinho.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class RegistroService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AmazonS3 clienteS3;

    @Value("${aws.bucket}")
    private String bucketAWS;

    public Usuario registrar(RegistrarUsuarioDTO dados) {
        if (this.usuarioRepository.findByEmail(dados.email()) != null) {
            throw new EmailJaExistenteException("Email já está em uso");
        }

        String senhaCriptografada = bCryptPasswordEncoder.encode(dados.senha());
        Empresa empresa = null;
        if (dados.organizacao() != null) {
            empresa = empresaService.buscarEmpresa(dados.organizacao());
        }

        Usuario usuarioNovo = new Usuario(dados, senhaCriptografada, empresa);
        return usuarioRepository.save(usuarioNovo);
    }


    public Usuario atualizar(AtualizarUsuarioDTO atualizarUsuarioDTO) {
        var usuario = usuarioRepository.getReferenceById(atualizarUsuarioDTO.id());
        String url_foto = null;

        if(atualizarUsuarioDTO.fotoPerfil() != null) {
            if(usuario.getFoto_url() != null) {
                if (this.deletarImagem(usuario.getFoto_url())) {
                    url_foto = uploadImagem(atualizarUsuarioDTO.fotoPerfil());
                    usuario.setFoto_url(url_foto);

                } else {
                    System.out.println("Não foi possível deletar a foto");
                }
            } else {
                url_foto = uploadImagem(atualizarUsuarioDTO.fotoPerfil());
                usuario.setFoto_url(url_foto);
            }
        }

        if(atualizarUsuarioDTO.nome() != null) {
            usuario.setNome(atualizarUsuarioDTO.nome());
        }

        if(atualizarUsuarioDTO.sobrenome() != null) {
            usuario.setSobrenome(atualizarUsuarioDTO.sobrenome());
        }

        return usuarioRepository.save(usuario);
    }

    private String uploadImagem(MultipartFile file) {
        String nome_imagem = UUID.randomUUID() + "-" + file.getOriginalFilename();

        try {
            File arquivo = this.convertMultipartToFile(file);
            clienteS3.putObject(bucketAWS,nome_imagem, arquivo);
            arquivo.delete();
            return clienteS3.getUrl(bucketAWS, nome_imagem).toString();
        } catch (Exception e){
            return null;
        }

    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();

        return convFile;
    }

    private boolean deletarImagem(String url_foto) {
        try {
            clienteS3.deleteObject(bucketAWS, url_foto);
            return true;
        } catch (Exception e) {

            return false;
        }
    }
}
