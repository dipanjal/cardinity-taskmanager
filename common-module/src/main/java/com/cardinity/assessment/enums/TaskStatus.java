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
public enum TaskStatus {

    OPEN(1, "Open"),
    IN_PROGRESS(2, "In Progress"),
    CLOSED(3, "Closed")
    ;

    private final int code;
    private final String value;



    public static TaskStatus find(Predicate<TaskStatus> predicate) {
        return StreamUtils.find(Arrays.stream(TaskStatus.values()), predicate)
                .orElseThrow(() -> new RecordNotFoundException("No Task Status found for this argument"));
    }

    public static String getValueByCode(final int code) {
        return find(task -> task.code == code)
                .getValue();
    }

    public static int getCodeByValue(final String value) {
        return find(task -> task.value.equalsIgnoreCase(value))
                .getCode();
    }

    public static boolean isValid(Predicate<TaskStatus> predicate){
        return Arrays.stream(TaskStatus.values())
                .anyMatch(predicate);
    }

    public static boolean isValidStatus(int code){
        return isValid(t -> t.getCode() == code);
    }

    public static boolean isInvalidStatus(int code){
        return !isValidStatus(code);
    }

    public static boolean isValidStatus(String value){
        return isValid(t -> t.getValue().equalsIgnoreCase(value));
    }

    public static boolean isInvalidStatus(String value){
        return !isValidStatus(value);
    }

    public static boolean isClosed(int code){
        return (TaskStatus.CLOSED.getCode() == code);
    }
}
