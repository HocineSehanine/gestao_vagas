package com.hocinesehanine.gestao_vagas.modules.job.controllers;

import com.hocinesehanine.gestao_vagas.modules.job.dto.CreateJobRequest;
import com.hocinesehanine.gestao_vagas.modules.job.entities.JobEntity;
import com.hocinesehanine.gestao_vagas.modules.job.usecases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company/job")
public class JobController {

    private final CreateJobUseCase createJobUseCase;

    public JobController(CreateJobUseCase createJobUseCase) {
        this.createJobUseCase = createJobUseCase;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    @Tag(name = "Vagas", description = "Informações de vagas")
    @Operation(summary = "Cadastro de vagas", description = "Esssa função é responável por cadastrar vagas")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = JobEntity.class))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> createJob(@Valid @RequestBody final CreateJobRequest createJobRequest, final HttpServletRequest request) {
        try {
            final var companyId = request.getAttribute("company_id").toString();

            final var jobEntity = JobEntity.builder()
                    .id(Long.parseLong(companyId))
                    .description(createJobRequest.getDescription())
                    .benefits(createJobRequest.getBenefits())
                    .level(createJobRequest.getLevel())
                    .companyId(Long.parseLong(companyId))
                    .build();
            final var savedJob = this.createJobUseCase.execute(jobEntity);

            return ResponseEntity.ok().body(savedJob);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
