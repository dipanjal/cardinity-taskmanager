package com.cardinity.assessment.entity.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@Setter
@Getter
@MappedSuperclass
public class BaseUpdatableEntity extends BaseEntity {

    @Column(name = "created_by")
    private long createdById;

    @Column(name = "updated_by")
    private long updatedById;
}
