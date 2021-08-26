package com.cardinity.assessment.advice;

import com.cardinity.assessment.utils.ResponseBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class CardinityAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper jacksonMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream()
                .write(jacksonMapper.writeValueAsBytes(
                        ResponseBuilder.buildResponse(
                                HttpStatus.FORBIDDEN, "Forbidden! You are not allowed to access this resource")));
    }
}
