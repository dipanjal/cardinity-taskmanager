package com.cardinity.assessment.entity;

import com.cardinity.assessment.entity.base.BaseUpdatableEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * @author dipanjal
 * @since version 0.0.1
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
    @Column(name = "status")
    private int status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private ProjectEntity projectEntity;

    @ManyToMany(mappedBy = "tasks")
    private Collection<UserEntity> assignedUsers = new HashSet<>();
    public void addUser(UserEntity userEntity){
        assignedUsers.add(userEntity);
    }
    public void removeUser(UserEntity userEntity){
        assignedUsers.remove(userEntity);
    }
}
