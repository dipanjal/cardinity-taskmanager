package com.cardinity.assessment.config;

import com.cardinity.assessment.controller.ApiTags;
import com.cardinity.assessment.props.AppProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@Configuration
@RequiredArgsConstructor
public class OpenApiConfiguration {

    private final AppProperties properties;

    private final String SECURITY_TYPE = "BearerAuth";

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("Task Manager REST Api")
                .pathsToExclude("/dummy/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_TYPE))
                .components(getSecurityComponents())
                .addTagsItem(new Tag().name(ApiTags.AUTH))
                .addTagsItem(new Tag().name(ApiTags.PROJECT))
                .addTagsItem(new Tag().name(ApiTags.TASK))
                .addTagsItem(new Tag().name(ApiTags.USER))
                .info(getApiInfo());
    }

    private Components getSecurityComponents() {
        return new Components()
                .addSecuritySchemes(SECURITY_TYPE, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));
    }

    private Info getApiInfo() {
        return new Info()
                .title("Task Manager REST Api")
                .version(properties.getApiVersion())
                .description("A RESTful API Service for Cardinity Project Management Application")
                .contact(getContactInfo())
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));
    }

    private Contact getContactInfo() {
        return new Contact()
                .name("Dipanjal Maitra")
                .email("dipanjalmaitra@gmail.com")
                .url("https://github.com/dipanjal");
    }
}
