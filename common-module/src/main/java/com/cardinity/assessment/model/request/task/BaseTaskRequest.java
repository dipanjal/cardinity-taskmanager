package com.cardinity.assessment.model.request.task;

import com.cardinity.assessment.validation.CommonRegex;
import com.cardinity.assessment.validation.UserAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
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

    @NotNull(message = "validation.constraints.projectId.NotNull.message", groups = {UserAction.CREATE.class, UserAction.UPDATE.class})
    @Range(min = 1, message = "validation.constraints.projectId.Invalid.message", groups = {UserAction.CREATE.class, UserAction.UPDATE.class})
    private long projectId;

    @NotEmpty(message = "validation.constraints.taskName.NotNull.message", groups = {UserAction.CREATE.class, UserAction.UPDATE.class})
    private String name;

    @Length(min = 5, max = 300, message = "validation.constraints.task-description-length.Invalid.message",
            groups = {UserAction.CREATE.class, UserAction.UPDATE.class})
    private String description;

    private int expiryHour;
    @Pattern(regexp = CommonRegex.STATUS_VALIDATION_REGEX, flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "validation.constraints.taskStatus.Invalid.message",
            groups = {UserAction.CREATE.class, UserAction.UPDATE.class})

    private String status;
    private long userId;
}
