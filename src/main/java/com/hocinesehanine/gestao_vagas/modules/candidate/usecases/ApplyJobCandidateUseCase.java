package com.hocinesehanine.gestao_vagas.modules.candidate.usecases;

import com.hocinesehanine.gestao_vagas.exceptions.JobNotFoundException;
import com.hocinesehanine.gestao_vagas.exceptions.CandidateNotFoundException;
import com.hocinesehanine.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import com.hocinesehanine.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import com.hocinesehanine.gestao_vagas.modules.candidate.repository.CandidateRepository;
import com.hocinesehanine.gestao_vagas.modules.job.repository.JobRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyJobCandidateUseCase {

    private final CandidateRepository candidateRepository;

    private final JobRepository jobRepository;
    private final ApplyJobRepository applyJobRepository;


    public ApplyJobCandidateUseCase(final CandidateRepository candidateRepository, final JobRepository jobRepository, final ApplyJobRepository applyJobRepository) {
        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
        this.applyJobRepository = applyJobRepository;
    }

    public ApplyJobEntity execute(final Long candidateId, final Long jobId) {
        final var candidateEntity= candidateRepository.findById(candidateId).orElseThrow(CandidateNotFoundException::new);
        final var jobEntity= jobRepository.findById(jobId).orElseThrow(JobNotFoundException::new);
        final var applyJobEntity = ApplyJobEntity.builder()
                .candidateId(candidateEntity.getId())
                .jobId(jobEntity.getId())
                .build();

        return applyJobRepository.save(applyJobEntity);
    }
}
