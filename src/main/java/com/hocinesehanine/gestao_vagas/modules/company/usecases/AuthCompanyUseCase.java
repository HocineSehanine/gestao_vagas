package com.hocinesehanine.gestao_vagas.modules.company.usecases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hocinesehanine.gestao_vagas.modules.company.dto.AuthCompanyDto;
import com.hocinesehanine.gestao_vagas.modules.company.dto.AuthCompanyResponse;
import com.hocinesehanine.gestao_vagas.modules.company.repository.CompanyRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCompanyUseCase {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthCompanyUseCase(final CompanyRepository companyRepository, final PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthCompanyResponse execute(final AuthCompanyDto authCompanyDto) throws AuthenticationException {
        final var company = companyRepository.findByUsername(authCompanyDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Campo company ou password esta incorreto"));

        final var passwordMatches = passwordEncoder.matches(authCompanyDto.getPassword(), company.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        final String secret = "JAVAGAS_@321#";

        final Algorithm algorithm = Algorithm.HMAC256(secret);
        final var expiresIn = Instant.now().plus(Duration.ofHours(2));
        final var token = JWT.create().withIssuer("javagas")
                .withExpiresAt(expiresIn)
                .withSubject(company.getId().toString())
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);

        return AuthCompanyResponse.builder().access_token(token).expires_in(expiresIn.toEpochMilli()).build();
    }
}
