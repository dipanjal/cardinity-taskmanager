package com.cardinity.assessment.specs;

import com.cardinity.assessment.entity.TaskEntity;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

/**
 * @author dipanjal
 * @since 0.0.1
 */

public abstract class TaskEntitySpecifications {

    public static Specification<TaskEntity> byUserId(long userId){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.join("assignedUsers").get("id"), userId)
        );
    }

    public static Specification<TaskEntity> byTaskId(long taskId){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("id"), taskId)
        );
    }

    public static Specification<TaskEntity> byProjectId(long projectId){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.join("projectEntity").get("id"), projectId)
        );
    }

    public static Specification<TaskEntity> byTaskStatus(int statusCode){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("status"), statusCode)
        );
    }
}
