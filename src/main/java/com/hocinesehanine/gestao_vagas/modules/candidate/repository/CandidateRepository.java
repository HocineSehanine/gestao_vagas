package com.hocinesehanine.gestao_vagas.modules.candidate.repository;

import com.hocinesehanine.gestao_vagas.modules.candidate.entities.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<CandidateEntity, Long> {
    Optional<CandidateEntity> findByUsernameOrEmail(final String username, final String email);

    Optional<CandidateEntity> findByUsername(final String username);
}
