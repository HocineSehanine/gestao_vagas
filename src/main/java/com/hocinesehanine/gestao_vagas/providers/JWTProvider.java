package com.hocinesehanine.gestao_vagas.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

@Service
public class JWTProvider {

    public DecodedJWT validateToken(String token) {
        token = token.replace("Bearer ", "");

        final String secret = "JAVAGAS_@321#";

        try {
            final Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm).build().verify(token);
        } catch (final JWTVerificationException e) {
            return null;
        }
    }
}
