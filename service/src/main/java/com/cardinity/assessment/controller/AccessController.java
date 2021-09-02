package com.cardinity.assessment.controller;

import com.cardinity.assessment.model.request.auth.AuthenticationRequest;
import com.cardinity.assessment.model.response.auth.TokenResponse;
import com.cardinity.assessment.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@RestController
@RequiredArgsConstructor
public class AccessController {

    private final AuthService authService;

    @PostMapping("/access/token")
    @Operation(tags = ApiTags.AUTH, description = "Get Access Token")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
