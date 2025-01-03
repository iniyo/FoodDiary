package com.service.fooddiary.interfaces.swagger;

import com.service.fooddiary.infrastructure.common.constants.basecode.ErrorCode;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(Components components) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            components.addResponses(errorCode.name(), SwaggerUtils.createApiResponse(errorCode));
        }
        return new OpenAPI().components(components);
    }

    @Bean
    public Components components() {
        return new Components();
    }

}

