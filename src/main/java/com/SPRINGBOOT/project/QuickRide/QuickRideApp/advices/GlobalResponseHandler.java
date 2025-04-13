package com.SPRINGBOOT.project.QuickRide.QuickRideApp.advices;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //both route return the body
        List<String> allowedRoutes = List.of("/v3/api-docs", "/actuator", "/swagger-ui", "/swagger-ui.html"
                                                ,"/swagger-ui/**",        // Allow Swagger UI
                                                "/v3/api-docs/**",       // Allow API docs
                                                "/swagger-ui.html",      // Swagger HTML page
                                                "/actuator/**",          // Actuator endpoints if used
                                                "/**"                    // Optionally allow all localhost requests
                                                );

        boolean isAllowed = allowedRoutes
                .stream()
                .anyMatch(route -> request.getURI().getPath().contains(route));

        if(body instanceof ApiResponse<?> || isAllowed) {
            return body;
        }

        return new ApiResponse<>(body);
    }
}