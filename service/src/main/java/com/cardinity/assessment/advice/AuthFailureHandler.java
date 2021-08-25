package com.cardinity.assessment.advice;

import com.cardinity.assessment.model.ApiResponse;
import com.cardinity.assessment.utils.ResponseBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dipanjal
 * @since 2/8/2021
 */
@Component
@RequiredArgsConstructor
public class AuthFailureHandler implements AuthenticationEntryPoint {

    private final ObjectMapper jacksonMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ApiResponse<Void> data = ResponseBuilder.buildResponse(HttpStatus.UNAUTHORIZED, "Authentication Failed");
        response.getOutputStream()
                .println(jacksonMapper.writeValueAsString(data));
    }
}
