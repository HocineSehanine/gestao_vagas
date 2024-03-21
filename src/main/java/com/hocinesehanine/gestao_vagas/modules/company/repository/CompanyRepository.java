package com.hocinesehanine.gestao_vagas.modules.company.repository;

import com.hocinesehanine.gestao_vagas.modules.company.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    Optional<CompanyEntity> findByUsernameOrEmail(final String username, final String email);

    Optional<CompanyEntity> findByUsername(final String username);
}
