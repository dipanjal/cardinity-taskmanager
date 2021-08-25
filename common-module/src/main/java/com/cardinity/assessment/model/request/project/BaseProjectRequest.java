package com.cardinity.assessment.model.request.project;

import com.cardinity.assessment.validation.UserAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author dipanjal
 * @since 2/6/2021
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseProjectRequest implements Serializable {
    @NotEmpty(message = "Project Name can't be empty", groups = UserAction.CREATE.class)
    private String name;
    private String description;
    private long userId;
}
