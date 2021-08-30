package com.cardinity.assessment.model.request.task;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@Getter
@Setter
public class AssignUserTaskRequest {
    @Min(value = 1, message = "validation.constraints.projectId.Invalid.message")
    private long taskId;
    @Min(value = 1, message = "validation.constraints.userId.Invalid.message")
    private long userId;
}
