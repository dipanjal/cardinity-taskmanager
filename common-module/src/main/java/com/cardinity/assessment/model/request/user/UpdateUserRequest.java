package com.cardinity.assessment.model.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Getter
@Setter
public class UpdateUserRequest {
    @Min(value = 1, message = "validation.constraints.userId.NotNull.message")
    private long id;
    @NotBlank(message = "validation.constraints.user.fullName.NotNull.message")
    @JsonProperty("full_name")
    private String fullName;
    @NotBlank(message = "validation.constraints.user.email.empty.message")
    @Email(message = "validation.constraints.user.email.Invalid.message")
    private String email;
}
