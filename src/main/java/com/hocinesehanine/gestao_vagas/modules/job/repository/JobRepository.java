package com.hocinesehanine.gestao_vagas.modules.job.repository;

import com.hocinesehanine.gestao_vagas.modules.job.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<JobEntity, Long> {

    List<JobEntity> findByDescriptionContainingIgnoreCase(final String filter);
}
