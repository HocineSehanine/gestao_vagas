package com.hocinesehanine.gestao_vagas.security;

import com.hocinesehanine.gestao_vagas.security.filters.SecurityCandidateFilter;
import com.hocinesehanine.gestao_vagas.security.filters.SecurityCompanyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final SecurityCompanyFilter securityCompanyFilter;
    private final SecurityCandidateFilter securityCandidateFilter;

    private static final String[] PERMITTED_ROUTES_LIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger/resources/**",
            "/actuator/**"
    };

    public SecurityConfig(final SecurityCompanyFilter securityCompanyFilter, final SecurityCandidateFilter securityCandidateFilter) {
        this.securityCompanyFilter = securityCompanyFilter;
        this.securityCandidateFilter = securityCandidateFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/candidate/").permitAll()
                            .requestMatchers("/company/").permitAll()
                            .requestMatchers("/company/auth").permitAll()
                            .requestMatchers("/candidate/job").permitAll()
                            .requestMatchers("/candidate/auth").permitAll()
                            .requestMatchers("/candidate/profile").permitAll()
                            .requestMatchers("/candidate/job/apply").permitAll()
                            .requestMatchers(PERMITTED_ROUTES_LIST).permitAll();
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(securityCompanyFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class)
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
