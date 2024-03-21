package com.hocinesehanine.gestao_vagas.modules.candidate.controllers;

import com.hocinesehanine.gestao_vagas.modules.company.repository.CompanyRepository;
import com.hocinesehanine.gestao_vagas.modules.job.dto.CreateJobRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.hocinesehanine.gestao_vagas.mocks.CompanyMock.companyEntityMock;
import static com.hocinesehanine.gestao_vagas.utils.testUtils.generateToken;
import static com.hocinesehanine.gestao_vagas.utils.testUtils.objectToJson;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CandidateControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    void shouldCreateAJob() throws Exception {
        final var companyEntityMock = companyEntityMock();

        final var createdCompany = companyRepository.saveAndFlush(companyEntityMock);

        final var createdJob = CreateJobRequest.builder()
                .description("vaga test")
                .benefits("benefits test")
                .level("level test")
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createdJob))
                        .header("Authorization", generateToken(createdCompany.getId(), "JAVAGAS_@321#"))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldNotBeAbleToCreateAJobWhenDeclaredCompanyIdDoesNotExist() throws Exception {
        final var createdJob = CreateJobRequest.builder()
                .description("vaga test")
                .benefits("benefits test")
                .level("level test")
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createdJob))
                .header("Authorization", generateToken(24L, "JAVAGAS_@321#")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldReturnsUnauthorized() throws Exception {
        final var companyEntityMock = companyEntityMock();

        final var createdCompany = companyRepository.saveAndFlush(companyEntityMock);

        final var createdJob = CreateJobRequest.builder()
                .description("vaga test")
                .benefits("benefits test")
                .level("level test")
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createdJob))
                        .header("Authorization", generateToken(createdCompany.getId(), "WRONG_SECRET_KEY"))
                )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}