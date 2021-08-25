package com.cardinity.assessment.repository;

import com.cardinity.assessment.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findByIdIn(List<Long> ids);
}
