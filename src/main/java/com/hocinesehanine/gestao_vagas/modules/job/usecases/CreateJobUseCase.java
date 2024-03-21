package com.hocinesehanine.gestao_vagas.modules.job.usecases;

import com.hocinesehanine.gestao_vagas.exceptions.CompanyNotFoundException;
import com.hocinesehanine.gestao_vagas.modules.company.repository.CompanyRepository;
import com.hocinesehanine.gestao_vagas.modules.job.entities.JobEntity;
import com.hocinesehanine.gestao_vagas.modules.job.repository.JobRepository;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class CreateJobUseCase {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    public CreateJobUseCase(final JobRepository jobRepository, final CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    public JobEntity execute(final JobEntity jobEntity) {
        final var company = companyRepository.findById(jobEntity.getId()).orElse(null);

        if (isNull(company)) {
            throw new CompanyNotFoundException();
        }
        return jobRepository.save(jobEntity);
    }
}
