package com.epam.bookstoreservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * the configuration for swagger
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();

        ticketPar.name("Authorization").description("token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        pars.add(ticketPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo())
//                Set the Global Token
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Bookstore Service")
                .description("Bookstore Service Task")
                .version("1.0")
                .build();
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> result = new ArrayList<>();

        ApiKey apiKey = new
                ApiKey("Authorization", "Authorization", "header");
        result.add(apiKey);

        return result;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> result = new ArrayList<>();

        result.add(getContextByPath("/demo/.*"));

        return result;
    }

    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();

        AuthorizationScope authorizationScope = new AuthorizationScope("global", "acessEverything");

        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];

        authorizationScopes[0] = authorizationScope;

        result.add(new SecurityReference("Authorization", authorizationScopes));

        return result;
    }

}