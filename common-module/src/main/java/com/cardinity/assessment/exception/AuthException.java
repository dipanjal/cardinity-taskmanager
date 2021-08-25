package com.cardinity.assessment.exception;

import lombok.NoArgsConstructor;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@NoArgsConstructor
public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);
    }
    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
