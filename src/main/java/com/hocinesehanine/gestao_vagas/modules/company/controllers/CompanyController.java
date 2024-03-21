package com.hocinesehanine.gestao_vagas.modules.company.controllers;

import com.hocinesehanine.gestao_vagas.modules.company.dto.AuthCompanyDto;
import com.hocinesehanine.gestao_vagas.modules.company.entities.CompanyEntity;
import com.hocinesehanine.gestao_vagas.modules.company.usecases.AuthCompanyUseCase;
import com.hocinesehanine.gestao_vagas.modules.company.usecases.CreateCompanyUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CreateCompanyUseCase createCompanyUseCase;
    private final AuthCompanyUseCase authCompanyUseCase;

    public CompanyController(final CreateCompanyUseCase createCompanyUseCase, final AuthCompanyUseCase authCompanyUseCase) {
        this.createCompanyUseCase = createCompanyUseCase;
        this.authCompanyUseCase = authCompanyUseCase;
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> generateToken(@RequestBody AuthCompanyDto authCompanyDto) {
        try {
            final var token = authCompanyUseCase.execute(authCompanyDto);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> createCompany(@Valid @RequestBody final CompanyEntity companyEntity) {
        try {
            final var savedCompany = this.createCompanyUseCase.execute(companyEntity);

            return ResponseEntity.ok().body(savedCompany);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
