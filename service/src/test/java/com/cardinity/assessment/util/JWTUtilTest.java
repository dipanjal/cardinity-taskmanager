package com.cardinity.assessment.util;

import com.cardinity.assessment.utils.JWTUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@SpringBootTest
public class JWTUtilTest {

    @Test
    public void generateTokenTest() {
        String username = "dipanjal";
        String token = JWTUtils.generateToken(username);
        boolean isValid = JWTUtils.isTokenValid(token, username);
        Assertions.assertTrue(isValid);
    }
}
