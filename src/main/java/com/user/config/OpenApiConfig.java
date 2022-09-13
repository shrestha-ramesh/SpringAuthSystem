package com.user.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfig {

    private String BEARER = "Bearer";

    @Bean
    public OpenAPI userInfo(){

        List<SecurityRequirement> securityRequirementsList = new ArrayList<>();
        securityRequirementsList.add(new SecurityRequirement().addList(BEARER));

        return new OpenAPI()
                .info(new Info().title("User"))
                .components(new Components()
                        .addSecuritySchemes(BEARER, createBearerAuthorizationSchema()))
                .security(securityRequirementsList);
    }
    private SecurityScheme createBearerAuthorizationSchema(){
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .description("Token");
    }
}
