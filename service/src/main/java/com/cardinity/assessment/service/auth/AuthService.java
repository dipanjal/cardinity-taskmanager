package com.cardinity.assessment.service.auth;

import com.cardinity.assessment.model.request.auth.AuthenticationRequest;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public interface AuthService {
    String authenticate(AuthenticationRequest request);
    UserDetails validateToken(String token);
}
