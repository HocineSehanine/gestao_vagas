package com.hocinesehanine.gestao_vagas.mocks;

import com.hocinesehanine.gestao_vagas.modules.job.entities.JobEntity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.hocinesehanine.gestao_vagas.mocks.CompanyMock.companyEntityMock;

@NoArgsConstructor
public class JobMock {

    public static JobEntity jobEntityMock() {
        return JobEntity.builder()
                .id(2L)
                .description("Fullstack developer opportunity")
                .benefits("Gym pass, medical plan, day off when it´s your birthday")
                .level("JUNIOR")
                .companyEntity(companyEntityMock())
                .companyId(2L)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
