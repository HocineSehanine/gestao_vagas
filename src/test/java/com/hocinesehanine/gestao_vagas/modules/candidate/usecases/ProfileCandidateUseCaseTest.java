package com.hocinesehanine.gestao_vagas.modules.candidate.usecases;

import com.hocinesehanine.gestao_vagas.exceptions.CandidateNotFoundException;
import com.hocinesehanine.gestao_vagas.modules.candidate.repository.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.hocinesehanine.gestao_vagas.mocks.CandidateMock.candidateEntityMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileCandidateUseCaseTest {

    @Mock
    private CandidateRepository candidateRepository;

    @InjectMocks
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Test
    void shouldGetProfile() {
        final var candidateEntityMock = candidateEntityMock();

        when(candidateRepository.findById(anyLong())).thenReturn(Optional.of(candidateEntityMock));
        final var profile = profileCandidateUseCase.execute(1L);

        assertEquals(candidateEntityMock.getDescription(), profile.getDescription());
        assertEquals(candidateEntityMock.getUsername(), profile.getUsername());
        assertEquals(candidateEntityMock.getEmail(), profile.getEmail());
        assertEquals(candidateEntityMock.getId(), profile.getId());
        assertEquals(candidateEntityMock.getName(), profile.getName());
    }

    @Test
    void shouldThrowCandidateNotFoundException() {
        when(candidateRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CandidateNotFoundException.class, () -> profileCandidateUseCase.execute(1L));
    }
}