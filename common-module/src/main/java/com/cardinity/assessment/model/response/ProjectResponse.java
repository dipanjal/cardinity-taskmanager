package com.cardinity.assessment.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author dipanjal
 * @since 2/6/2021
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectResponse implements Serializable {
    private long id;
    private String name;
    private String status;
    private String createdAt;
    private String updatedAt;
    private long createdBy;
    private long updatedBy;
}
