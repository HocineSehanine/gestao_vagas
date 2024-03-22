package com.hocinesehanine.gestao_vagas.mocks;

import com.hocinesehanine.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import com.hocinesehanine.gestao_vagas.modules.candidate.entities.CandidateEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.hocinesehanine.gestao_vagas.mocks.JobMock.jobEntityMock;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CandidateMock {

    public static CandidateEntity candidateEntityMock() {
        return CandidateEntity.builder()
                .id(1L)
                .name("Jhon Doe")
                .username("jhonDoe_")
                .email("jhonDoe1@email.com")
                .password("pass321")
                .description("Fullstack developer")
                .curriculum("s3.com/file")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static ApplyJobEntity applyJobEntityMock() {
        return ApplyJobEntity.builder()
                .id(1L)
                .candidateEntity(candidateEntityMock())
                .jobEntity(jobEntityMock())
                .candidateId(29L)
                .jobId(29L)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
