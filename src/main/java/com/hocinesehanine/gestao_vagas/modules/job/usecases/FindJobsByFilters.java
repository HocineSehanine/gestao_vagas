package com.hocinesehanine.gestao_vagas.modules.job.usecases;

import com.hocinesehanine.gestao_vagas.modules.job.entities.JobEntity;
import com.hocinesehanine.gestao_vagas.modules.job.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindJobsByFilters {

    private final JobRepository jobRepository;

    public FindJobsByFilters(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<JobEntity> execute(final String filter) {
        return jobRepository.findByDescriptionContainingIgnoreCase(filter);
    }
}
