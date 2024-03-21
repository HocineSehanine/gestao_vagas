package com.hocinesehanine.gestao_vagas.modules.candidate.controllers;

import com.hocinesehanine.gestao_vagas.modules.candidate.dto.AuthCandidateDto;
import com.hocinesehanine.gestao_vagas.modules.candidate.dto.ProfileCandidateResponse;
import com.hocinesehanine.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.hocinesehanine.gestao_vagas.modules.candidate.usecases.ApplyJobCandidateUseCase;
import com.hocinesehanine.gestao_vagas.modules.candidate.usecases.AuthCandidateUseCase;
import com.hocinesehanine.gestao_vagas.modules.candidate.usecases.CreateCandidateUseCase;
import com.hocinesehanine.gestao_vagas.modules.candidate.usecases.ProfileCandidateUseCase;
import com.hocinesehanine.gestao_vagas.modules.job.entities.JobEntity;
import com.hocinesehanine.gestao_vagas.modules.job.usecases.FindJobsByFilters;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {

    private final CreateCandidateUseCase candidateUseCase;
    private final ProfileCandidateUseCase profileCandidateUseCase;
    private final AuthCandidateUseCase authCandidateUseCase;
    private final FindJobsByFilters findJobsByFilters;

    private final ApplyJobCandidateUseCase applyJobCandidateUseCase;

    public CandidateController(final CreateCandidateUseCase candidateUseCase, final ProfileCandidateUseCase profileCandidateUseCase, final AuthCandidateUseCase authCandidateUseCase, final FindJobsByFilters findJobsByFilters, final ApplyJobCandidateUseCase applyJobCandidateUseCase) {
        this.candidateUseCase = candidateUseCase;
        this.profileCandidateUseCase = profileCandidateUseCase;
        this.authCandidateUseCase = authCandidateUseCase;
        this.findJobsByFilters = findJobsByFilters;
        this.applyJobCandidateUseCase = applyJobCandidateUseCase;
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> generateAccessToken(@RequestBody AuthCandidateDto authCandidateDto) {
        try {
            final var token = authCandidateUseCase.execute(authCandidateDto);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @Operation(summary = "Cadastro de candidato", description = "Esssa função é responável por cadastrar um candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = CandidateEntity.class))
                    )
            }),
            @ApiResponse(responseCode = "400", description = "usuário já existe")
    })
    @PostMapping("/")
    public ResponseEntity<Object> createCandidate(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            final var savedCandidate = candidateUseCase.execute(candidateEntity);

            return ResponseEntity.ok().body(savedCandidate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfile do candidato", description = "Esssa função é responável por buscar as informações do candidato")
    @SecurityRequirement(name = "jwt_auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = ProfileCandidateResponse.class))
                    )
            }),
            @ApiResponse(responseCode = "400", description = "user not found")
    })
    public ResponseEntity<Object> getCandidate(final HttpServletRequest request) {
        final var idCandidate = request.getAttribute("candidate_id").toString();
        try {
            final var profileCandidate = profileCandidateUseCase.execute(Long.parseLong(idCandidate));

            return ResponseEntity.ok().body(profileCandidate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de vagas disponíveis para o candidato", description = "Esssa função é responável por listar todas as vagas disponíveis, baseada no filtro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))
                    )
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> getAllJobs(@RequestParam final String filter) {
        return findJobsByFilters.execute(filter);
    }

    @PostMapping("/jpb/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Cadastro de vaga para o candidato", description = "Esssa função é responável por cadatrar um candidato numa vaga")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))
                    )
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> apply(final HttpServletRequest request, @RequestParam Long idJob ) {
        final var idCandidate = request.getAttribute("candidate_id").toString();
        try {
            final var applyJobEntity = applyJobCandidateUseCase.execute(Long.parseLong(idCandidate), idJob);

            return ResponseEntity.ok().body(applyJobEntity);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
