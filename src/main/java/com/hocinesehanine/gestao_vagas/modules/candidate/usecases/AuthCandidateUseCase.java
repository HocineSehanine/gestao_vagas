package com.hocinesehanine.gestao_vagas.modules.candidate.usecases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hocinesehanine.gestao_vagas.modules.candidate.dto.AuthCandidateDto;
import com.hocinesehanine.gestao_vagas.modules.candidate.dto.AuthCandidateResponse;
import com.hocinesehanine.gestao_vagas.modules.candidate.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthCandidateUseCase {

    private final CandidateRepository candidateRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${jwtCandidate.secret}")
    private String secret;

    public AuthCandidateUseCase(final CandidateRepository candidateRepository, final PasswordEncoder passwordEncoder) {
        this.candidateRepository = candidateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthCandidateResponse execute(AuthCandidateDto authCandidateDto) throws AuthenticationException {
        var candidate = candidateRepository.findByUsername(authCandidateDto.username()).orElseThrow(() -> new UsernameNotFoundException("Campo candidate ou password esta incorreto"));

        final var passwordMatches = passwordEncoder.matches(authCandidateDto.password(), candidate.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        final Algorithm algorithm = Algorithm.HMAC256(secret);
        final var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
        final var token = JWT.create().withIssuer("javagas")
                .withExpiresAt(expiresIn)
                .withClaim("roles", List.of("CANDIDATE"))
                .withSubject(candidate.getId().toString())
                .sign(algorithm);

        return AuthCandidateResponse.builder().access_token(token).expires_in(expiresIn.toEpochMilli()).build();
    }
}
