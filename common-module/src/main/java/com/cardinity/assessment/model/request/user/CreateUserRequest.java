package com.cardinity.assessment.model.request.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Getter
@Builder(toBuilder = true)
public class CreateUserRequest {
    @NotBlank(message = "validation.constraints.user.fullName.NotNull.message")
    private final String fullName;
    @NotBlank(message = "validation.constraints.username.NotNull.message")
    private final String userName;
    @NotBlank(message = "validation.constraints.user.email.empty.message")
    private final String password;
    @NotBlank(message = "validation.constraints.user.password.empty.message")
    @Email(message = "validation.constraints.user.email.Invalid.message")
    private final String email;
    @NotNull(message = "validation.constraints.user.role.empty.message")
    @Singular
    private final List<Long> roles;
}

