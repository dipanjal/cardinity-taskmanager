package com.cardinity.assessment.model.request.task;

import com.cardinity.assessment.validation.CommonRegex;
import com.cardinity.assessment.validation.UserAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author dipanjal
 * @since 2/6/2021
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseTaskRequest implements Serializable {
    @NotNull(message = "Project ID can not be empty")
    @Range(min = 1, message = "Invalid Project ID")
    private long projectId;
    @NotEmpty(message = "Task Name can't be empty", groups = UserAction.CREATE.class)
    private String name;
    @NotEmpty(message = "Task Description can't be empty")
    private String description;
    private int expiryHour;
    @Pattern(regexp = CommonRegex.STATUS_VALIDATION_REGEX, flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Invalid Task Status, Must be [open, in progress, closed]")
    private String status;
    private String assignedTo;
}
