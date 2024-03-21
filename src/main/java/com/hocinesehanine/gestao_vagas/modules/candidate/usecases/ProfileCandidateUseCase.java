package com.hocinesehanine.gestao_vagas.modules.candidate.usecases;

import com.hocinesehanine.gestao_vagas.exceptions.CandidateNotFoundException;
import com.hocinesehanine.gestao_vagas.modules.candidate.dto.ProfileCandidateResponse;
import com.hocinesehanine.gestao_vagas.modules.candidate.repository.CandidateRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProfileCandidateUseCase {

    private final CandidateRepository candidateRepository;

    public ProfileCandidateUseCase(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public ProfileCandidateResponse execute(final Long candidateId) {
        final var candidate = candidateRepository.findById(candidateId).orElseThrow(CandidateNotFoundException::new);
        return ProfileCandidateResponse.builder()
                .description(candidate.getDescription())
                .username(candidate.getUsername())
                .email(candidate.getEmail())
                .id(candidate.getId())
                .name(candidate.getName())
                .build();
    }
}
