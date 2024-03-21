package com.hocinesehanine.gestao_vagas.modules.candidate.usecases;

import com.hocinesehanine.gestao_vagas.exceptions.JobNotFoundException;
import com.hocinesehanine.gestao_vagas.exceptions.CandidateNotFoundException;
import com.hocinesehanine.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import com.hocinesehanine.gestao_vagas.modules.candidate.repository.CandidateRepository;
import com.hocinesehanine.gestao_vagas.modules.job.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.hocinesehanine.gestao_vagas.mocks.CandidateMock.applyJobEntityMock;
import static com.hocinesehanine.gestao_vagas.mocks.CandidateMock.candidateEntityMock;
import static com.hocinesehanine.gestao_vagas.mocks.JobMock.jobEntityMock;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplyJobCandidateUseCaseTest {

    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private JobRepository jobRepository;
    @Mock
    private ApplyJobRepository applyJobRepository;

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Test
    void shouldExecute() {
        final var candidateEntityMock = candidateEntityMock();
        final var jobEntityMock = jobEntityMock();
        final var applyJobEntity = applyJobEntityMock();

        when(candidateRepository.findById(anyLong())).thenReturn(Optional.of(candidateEntityMock));
        when(jobRepository.findById(anyLong())).thenReturn(Optional.of(jobEntityMock));
        when(applyJobRepository.save(any())).thenReturn(applyJobEntity);

        final var applyJob = applyJobCandidateUseCase.execute(29L, 47L);

        assertNotNull(applyJob);
        verify(applyJobRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowUserNotFoundException() {
        when(candidateRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CandidateNotFoundException.class, () -> applyJobCandidateUseCase.execute(29L, 47L));
    }

    @Test
    void shouldThrowJobNotFoundException() {
        final var candidateEntityMock = candidateEntityMock();

        when(candidateRepository.findById(anyLong())).thenReturn(Optional.of(candidateEntityMock));
        when(jobRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(JobNotFoundException.class, () -> applyJobCandidateUseCase.execute(29L, 47L));
    }
}