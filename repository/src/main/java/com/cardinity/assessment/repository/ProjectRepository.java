package com.cardinity.assessment.repository;

import com.cardinity.assessment.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}
