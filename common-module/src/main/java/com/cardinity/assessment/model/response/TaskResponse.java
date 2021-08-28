package com.cardinity.assessment.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author dipanjal
 * @since version 0.0.1
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskResponse {
    private long taskId;
    private String name;
    private String description;
    private String status;
    private String createdAt;
    private String updatedAt;
    private String expireAt;
    private long createdBy;
    private long updatedBy;
    private ProjectResponse project;
}
