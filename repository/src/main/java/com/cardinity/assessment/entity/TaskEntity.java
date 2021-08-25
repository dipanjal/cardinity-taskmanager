package com.cardinity.assessment.entity;

import com.cardinity.assessment.entity.base.BaseUpdatableEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author dipanjal
 * @since 2/6/2021
 */
@Entity
@Table(name = "task")
@Getter
@Setter
public class TaskEntity extends BaseUpdatableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "expire_at")
    private Date expireAt;
    @Column(name = "assigned_to")
    private long assignedUserId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            optional = false)
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private ProjectEntity projectEntity;
}
