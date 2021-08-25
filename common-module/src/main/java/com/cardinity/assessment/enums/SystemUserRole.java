package com.cardinity.assessment.enums;

import com.cardinity.assessment.exception.RecordNotFoundException;
import com.cardinity.assessment.utils.StreamUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@AllArgsConstructor
@Getter
public enum SystemUserRole {

    USER(1, "USER", "ROLE_USER"),
    ADMIN(2, "ADMIN", "ROLE_ADMIN");

    private final long code;
    private final String value;
    private final String role;

    private static SystemUserRole find(final Predicate<SystemUserRole> predicate) {
        return StreamUtils.find(Arrays.stream(SystemUserRole.values()), predicate)
                .orElseThrow(() -> new RecordNotFoundException("No Role Found for this argument"));
    }

    public static String getValueByCode(final long code){
        return find(x -> x.code == code)
                .getValue();
    }

    public static String getRoleByCode(final long code){
        return find(x -> x.code == code)
                .getRole();
    }

    public static boolean isValidRole(final long code){
        return Arrays
                .stream(SystemUserRole.values())
                .anyMatch(x -> x.getCode() == code);
    }

    public static boolean isInvalidRole(final long code){
        return !isValidRole(code);
    }
}
