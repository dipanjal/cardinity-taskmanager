package com.cardinity.assessment.service.auth;

import com.cardinity.assessment.model.request.auth.AuthenticationRequest;
import com.cardinity.assessment.model.response.auth.TokenResponse;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public interface AuthService {
    TokenResponse authenticate(AuthenticationRequest request);
    UserDetails validateToken(String token);
}
