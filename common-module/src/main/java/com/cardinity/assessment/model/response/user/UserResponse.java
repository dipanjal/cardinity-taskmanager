package com.cardinity.assessment.model.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@Getter
@Setter
@Builder
public class UserResponse {
    private long id;
    private String fullName;
    private String userName;
    private String email;
    private List<String> roles;
}
