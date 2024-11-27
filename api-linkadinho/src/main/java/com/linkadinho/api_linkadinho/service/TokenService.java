package com.linkadinho.api_linkadinho.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.linkadinho.api_linkadinho.model.usuario.Usuario;
import com.linkadinho.api_linkadinho.dto.TokenInfo;
import com.linkadinho.api_linkadinho.infra.exception.TokenInvalidoException;
import com.linkadinho.api_linkadinho.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.chave.secreta}")
    private String chave;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(chave);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(usuario.getEmail())
                    .withClaim("role", usuario.getRole().name())
                    .withClaim("organization", usuario.getEmpresa() != null ? usuario.getEmpresa().getNome() : "")
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(gerarExpiracao())
                    .sign(algoritmo);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro na geração do token jwt", exception);
        }
    }

    public TokenInfo validarToken(String token) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(chave);
            DecodedJWT decodedJWT = JWT.require(algoritmo)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token);

            String subject = decodedJWT.getSubject();
            String role = decodedJWT.getClaim("role").asString();
            String organization = decodedJWT.getClaim("organization").asString();
            Long id = decodedJWT.getClaim("id").asLong();
            Long expirationTime = decodedJWT.getExpiresAt().toInstant().toEpochMilli();

            if (organization == null || organization.isEmpty()) {
                Usuario usuario = (Usuario) usuarioRepository.findByEmail(subject);

                if (usuario != null && usuario.getEmpresa() != null) {

                    organization = usuario.getEmpresa().getNome();
                }
            }

            return new TokenInfo(subject, role, organization, id, expirationTime);
        } catch (JWTVerificationException exception) {
            throw new TokenInvalidoException("Token inválido ou expirado");
        }
    }

    public boolean isTokenExpiringSoon(String token) {
        TokenInfo tokenInfo = validarToken(token);
        long tempoExpiracao = tokenInfo.expirationTime(); // Tempo de expiração em milissegundos
        return System.currentTimeMillis() > tempoExpiracao - 5 * 60 * 1000; // Menos de 5 minutos para expirar
    }

    public String gerarNovoToken(String token) {
        TokenInfo tokenInfo = validarToken(token);
        return gerarToken(new Usuario(tokenInfo.subject(), tokenInfo.role(), tokenInfo.organization(), tokenInfo.id()));
    }

    private Instant gerarExpiracao(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
