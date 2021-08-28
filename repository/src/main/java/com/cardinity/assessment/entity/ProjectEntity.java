package com.cardinity.assessment.entity;

import com.cardinity.assessment.entity.base.BaseUpdatableEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author dipanjal
 * @since version 0.0.1
 */
@Entity
@Table(name = "project")
@Getter
@Setter
public class ProjectEntity extends BaseUpdatableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "projects")
    private Collection<UserEntity> assignedUsers = new HashSet<>();
    public void addUser(UserEntity userEntity){
        assignedUsers.add(userEntity);
    }
    public void removeUser(UserEntity userEntity){
        assignedUsers.remove(userEntity);
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "projectEntity", orphanRemoval = true)
    private Collection<TaskEntity> tasks = new HashSet<>();
    public void addTask(TaskEntity taskEntity){
        tasks.add(taskEntity);
    }
    public void removeTask(TaskEntity taskEntity){
        tasks.remove(taskEntity);
    }
}
