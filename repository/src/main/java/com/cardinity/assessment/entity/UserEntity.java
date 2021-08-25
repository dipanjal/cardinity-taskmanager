package com.cardinity.assessment.entity;

import com.cardinity.assessment.entity.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_name", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "designation")
    private String designation;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinTable(name = "User_Role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    Collection<RoleEntity> roles = new HashSet<>();
    public void addRole(RoleEntity bookEntity){
        roles.add(bookEntity);
    }
    public void removeRole(RoleEntity bookEntity){
        roles.remove(bookEntity);
    }

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinTable(name = "user_projects",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "project_id") }
    )
    Collection<ProjectEntity> projects = new HashSet<>();
    public void addProject(ProjectEntity projectEntity){
        projects.add(projectEntity);
    }
    public void removeProject(ProjectEntity projectEntity){
        projects.remove(projectEntity);
    }

    public UserEntity(String username, String password, String fullName,
                      String email, Collection<RoleEntity> roles) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.roles.addAll(roles);
    }
}
