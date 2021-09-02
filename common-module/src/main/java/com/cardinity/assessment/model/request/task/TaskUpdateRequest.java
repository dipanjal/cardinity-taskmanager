package com.cardinity.assessment.model.request.task;

import com.cardinity.assessment.validation.UserAction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@NoArgsConstructor
@Getter
@Setter
public class TaskUpdateRequest extends BaseTaskRequest {
    @NotNull(message = "validation.constraints.taskId.NotNull.message", groups = UserAction.UPDATE.class)
    @Min(value = 1, message = "validation.constraints.taskId.Invalid.message", groups = UserAction.UPDATE.class)
    private long taskId;
}
