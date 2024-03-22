package com.hocinesehanine.gestao_vagas.modules.candidate.usecases;

import com.hocinesehanine.gestao_vagas.exceptions.UserAlreadyExistException;
import com.hocinesehanine.gestao_vagas.modules.candidate.repository.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.hocinesehanine.gestao_vagas.mocks.CandidateMock.candidateEntityMock;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCandidateUseCaseTest {

    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateCandidateUseCase candidateUseCase;

    @Test
    void shouldCreateCandidate() {
        final var candidateToBeCreated = candidateEntityMock();
        when(candidateRepository
                .findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.empty());

        candidateUseCase.execute(candidateToBeCreated);

        verify(candidateRepository, times(1)).save(any());
        verify(passwordEncoder, times(1)).encode(any());
    }

    @Test
    void shouldThrowUserAlreadyExistException() {
        final var candidateToBeCreated = candidateEntityMock();
        when(candidateRepository
                .findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.of(candidateToBeCreated));

        assertThrows(UserAlreadyExistException.class, () -> candidateUseCase.execute(candidateToBeCreated));
    }
}