package com.hocinesehanine.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponse {

    @Schema(example = "Candidato vip")
    private String description;

    @Schema(example = "oPoderosoChefao")
    private String username;

    @Schema(example = "oPoderosoChefao@email.com.it")
    private String email;

    @Schema(example = "911")
    private Long id;

    @Schema(example = "Alcaponi")
    private String name;
}
