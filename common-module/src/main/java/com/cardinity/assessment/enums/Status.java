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
@Getter
@AllArgsConstructor
public enum Status {

    ACTIVE(1, "Active"),
    CLOSED(2, "Closed"),
    SUSPENDED(3, "Suspended"),
    BLOCKED(4, "Blocked");

    private final int code;
    private final String value;

    public static Status find(final Predicate<Status> predicate) {
        return StreamUtils.find(Arrays.stream(Status.values()), predicate)
                .orElseThrow(() -> new RecordNotFoundException("No Status Found for this argument"));
    }

    public static String getValueByCode(int code){
        return find(x -> x.getCode() == code)
                .getValue();
    }

    public static boolean isActive(int code){
        return (Status.ACTIVE.getCode() == code);
    }

    public static boolean isInactive(int code){
        return !isActive(code);
    }
}
