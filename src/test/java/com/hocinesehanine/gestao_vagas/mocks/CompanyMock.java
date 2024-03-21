package com.hocinesehanine.gestao_vagas.mocks;

import com.hocinesehanine.gestao_vagas.modules.company.entities.CompanyEntity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class CompanyMock {

    public static CompanyEntity companyEntityMock() {
        return CompanyEntity.builder()
                .id(1L)
                .username("testUser")
                .email("test@example.com")
                .password("password")
                .website("example.com")
                .name("Test Company")
                .description("Description of Test Company")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
