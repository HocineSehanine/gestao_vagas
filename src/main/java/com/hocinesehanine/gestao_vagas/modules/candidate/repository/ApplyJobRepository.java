package com.hocinesehanine.gestao_vagas.modules.candidate.repository;

import com.hocinesehanine.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, Long> {
}
