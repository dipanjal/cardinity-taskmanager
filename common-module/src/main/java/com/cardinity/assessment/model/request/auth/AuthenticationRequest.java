package com.cardinity.assessment.model.request.auth;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Getter
@Setter
public class AuthenticationRequest implements Serializable {
    private String username;
    private String password;
}
