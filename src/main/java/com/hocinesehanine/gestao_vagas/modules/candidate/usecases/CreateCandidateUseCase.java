package com.hocinesehanine.gestao_vagas.modules.candidate.usecases;

import com.hocinesehanine.gestao_vagas.exceptions.UserAlreadyExistException;
import com.hocinesehanine.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.hocinesehanine.gestao_vagas.modules.candidate.repository.CandidateRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
public class CreateCandidateUseCase {

    private final CandidateRepository candidateRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateCandidateUseCase(final CandidateRepository candidateRepository, final PasswordEncoder passwordEncoder) {
        this.candidateRepository = candidateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CandidateEntity execute(final CandidateEntity candidateEntity) {
        final var existentCandidate = candidateRepository
                .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail()).orElse(null);

        if (nonNull(existentCandidate)) {
            throw new UserAlreadyExistException();
        }


        final var password = passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(password);
        return candidateRepository.save(candidateEntity);
    }
}
