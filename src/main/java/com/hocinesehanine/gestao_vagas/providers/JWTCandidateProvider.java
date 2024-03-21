package com.hocinesehanine.gestao_vagas.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTCandidateProvider {

    @Value("${jwtCandidate.secret}")
    private String secret;

    public DecodedJWT validateToken(String token) {
        token = token.replace("Bearer ", "");

        try {
            final Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm).build().verify(token);
        } catch (final JWTVerificationException e) {
            return null;
        }
    }
}
