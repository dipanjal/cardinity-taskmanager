package com.cardinity.assessment.repository;

import com.cardinity.assessment.entity.TaskEntity;
import com.cardinity.assessment.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public interface TaskRepository extends JpaRepository<TaskEntity, Long>, JpaSpecificationExecutor<TaskEntity> {

    List<TaskEntity> findAllByCreatedAtBefore(Date date);
//    List<TaskEntity> findAllByAssignedUsersAndAndCreatedAtBefore(UserEntity userEntity)
}
