package com.hocinesehanine.gestao_vagas.modules.candidate.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(example = "Jhon doe", requiredMode = Schema.RequiredMode.REQUIRED, description = "nome do candidato")
    private String name;

    @NotBlank()
    @Pattern(regexp = "\\S+", message = "O Campo [username] não deve conter espaço")
    @Schema(example = "jhondoe", requiredMode = Schema.RequiredMode.REQUIRED, description = "nome do usuário do candidato")
    private String username;

    @Schema(example = "jhondoe@gmaill.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "email do candidato")
    @Email(message = "O Campo [email] deve conter um e-mail válido")
    private String email;

    @Schema(example = "password1233", minLength = 8, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED, description = "senha do candidato")
    @Length(min = 8, max = 100, message = "O Campo [password] não deve conter mais que 100 caracteres, e menos que 8 caracteres")
    private String password;

    @Schema(example = "dev java junior", requiredMode = Schema.RequiredMode.REQUIRED, description = "breve descrição do candidato")
    private String description;

    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
