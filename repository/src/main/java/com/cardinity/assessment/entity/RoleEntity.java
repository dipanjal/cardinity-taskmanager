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
 * @since 2/8/2021
 */

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
public class RoleEntity extends BaseEntity {
    @Id
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users = new HashSet<>();

    public RoleEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
