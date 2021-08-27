package com.cardinity.assessment.service.project;

import com.cardinity.assessment.entity.ProjectEntity;
import com.cardinity.assessment.entity.UserEntity;
import com.cardinity.assessment.model.auth.CurrentUser;
import com.cardinity.assessment.model.request.project.ProjectCreationRequest;
import com.cardinity.assessment.model.request.project.ProjectUpdateRequest;
import com.cardinity.assessment.model.response.ProjectResponse;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.cardinity.assessment.utils.DateTimeUtils.toAPIDateFormat;
/**
 * @author dipanjal
 * @since 0.0.1
 */
@Component
public class ProjectMapper {

    /** DTO to Entity */
    public ProjectEntity mapToEntity(ProjectCreationRequest request, CurrentUser createdBy,
                                     Optional<UserEntity> assignedUserOpt){
        var entity = new ProjectEntity();
        entity.setName(request.getName());
        entity.setCreatedById(createdBy.getId());
        entity.setUpdatedById(createdBy.getId());
//        assignedUserOpt.ifPresent(entity::addUser);
        assignedUserOpt.ifPresent(userEntity -> {
            userEntity.addProject(entity);
            entity.addUser(userEntity);
        });
        return entity;
    }

    public ProjectEntity mapToEntity(ProjectUpdateRequest request, ProjectEntity entity, CurrentUser createdBy,
                                     Optional<UserEntity> assignedUserOpt){
        entity.setName(request.getName());
        entity.setUpdatedById(createdBy.getId());
        assignedUserOpt.ifPresent(entity::addUser);
        return entity;
    }

    /** Entity to DTO */
    public ProjectResponse mapToDTO(ProjectEntity entity){
        return new ProjectResponse(
                entity.getId(),
                entity.getName(),
                toAPIDateFormat(entity.getCreatedAt()),
                toAPIDateFormat(entity.getUpdatedAt()),
                entity.getCreatedById(),
                entity.getUpdatedById()
        );
    }

    public List<ProjectResponse> mapToDTO(Collection<ProjectEntity> entityList){
        return entityList
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
