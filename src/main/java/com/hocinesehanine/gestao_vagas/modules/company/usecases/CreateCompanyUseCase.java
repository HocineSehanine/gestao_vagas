package com.hocinesehanine.gestao_vagas.modules.company.usecases;

import com.hocinesehanine.gestao_vagas.exceptions.UserAlreadyExistException;
import com.hocinesehanine.gestao_vagas.modules.company.entities.CompanyEntity;
import com.hocinesehanine.gestao_vagas.modules.company.repository.CompanyRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
public class CreateCompanyUseCase {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateCompanyUseCase(final CompanyRepository companyRepository, final PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CompanyEntity execute(final CompanyEntity companyEntity) {
        final var existentCompany = companyRepository
                .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail()).orElse(null);

        if (nonNull(existentCompany)) {
            throw new UserAlreadyExistException();
        }

        final var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);
        return companyRepository.save(companyEntity);
    }
}
