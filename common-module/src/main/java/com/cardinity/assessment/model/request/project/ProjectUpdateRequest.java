package com.cardinity.assessment.model.request.project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author dipanjal
 * @since 2/6/2021
 */

@Getter
@Setter
@NoArgsConstructor
public class ProjectUpdateRequest extends BaseProjectRequest {
    @NotNull(message = "validation.constraints.projectId.NotNull.message")
    @Range(min = 1, message = "validation.constraints.projectId.Invalid.message")
    private long projectId;
}
