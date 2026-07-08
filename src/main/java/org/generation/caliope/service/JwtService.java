package org.generation.caliope.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.generation.caliope.model.Users;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey key =
            Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(Users user){

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .claim("username", user.getUser_name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
    }

    // Obtener el correo del token
    public String extractEmail(String token){

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    // Validar si el token es correcto
    public boolean isTokenValid(String token){

        try{

            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;

        }catch (Exception e){

            return false;
        }
    }
}