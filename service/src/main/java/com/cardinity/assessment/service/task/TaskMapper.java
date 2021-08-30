package com.cardinity.assessment.service.task;

import com.cardinity.assessment.entity.ProjectEntity;
import com.cardinity.assessment.entity.TaskEntity;
import com.cardinity.assessment.entity.UserEntity;
import com.cardinity.assessment.model.auth.CurrentUser;
import com.cardinity.assessment.model.request.task.TaskCreationRequest;
import com.cardinity.assessment.model.request.task.TaskUpdateRequest;
import com.cardinity.assessment.model.response.ProjectResponse;
import com.cardinity.assessment.model.response.TaskResponse;
import com.cardinity.assessment.service.project.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.cardinity.assessment.enums.TaskStatus.getCodeByValue;
import static com.cardinity.assessment.enums.TaskStatus.getValueByCode;
import static com.cardinity.assessment.utils.DateTimeUtils.expireAtHour;
import static com.cardinity.assessment.utils.DateTimeUtils.toAPIDateFormat;
import static com.cardinity.assessment.utils.DateTimeUtils.toAPIDateTimeFormat;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final ProjectMapper projectMapper;

    /** Entity to DTO*/
    public TaskResponse mapToDTO(TaskEntity entity){
        return new TaskResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                getValueByCode(entity.getStatus()),
                toAPIDateFormat(entity.getCreatedAt()),
                toAPIDateFormat(entity.getUpdatedAt()),
                toAPIDateTimeFormat(entity.getExpireAt()),
                entity.getCreatedById(),
                entity.getUpdatedById(),
                Optional.ofNullable(entity.getProjectEntity())
                .map(projectMapper::mapToDTO)
                .orElse(new ProjectResponse())
        );
    }

    public List<TaskResponse> mapToDTO(Collection<TaskEntity> taskEntities){
        return taskEntities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /** DTO to Entity*/

    public TaskEntity mapToEntity(TaskCreationRequest request, ProjectEntity projectEntity,
                                  CurrentUser currentUser, Optional<UserEntity> assignedUser){
        var entity = new TaskEntity();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setExpireAt(expireAtHour(request.getExpiryHour()));
        entity.setStatus(getCodeByValue(request.getStatus()));
        entity.setProjectEntity(projectEntity);
        entity.setCreatedById(currentUser.getId());
        entity.setUpdatedById(currentUser.getId());
        assignedUser.ifPresent(userEntity -> {
            userEntity.addTask(entity);
            entity.addUser(userEntity);
        });
        return entity;
    }

    public TaskEntity mapToEntity(TaskUpdateRequest request, TaskEntity taskEntity,
                                  ProjectEntity projectEntity,
                                  CurrentUser currentUser, Optional<UserEntity> assignedUser){
        taskEntity.setName(request.getName());
        taskEntity.setDescription(request.getDescription());
        taskEntity.setExpireAt(expireAtHour(request.getExpiryHour()));
        taskEntity.setStatus(getCodeByValue(request.getStatus()));
        taskEntity.setProjectEntity(projectEntity);
        taskEntity.setCreatedById(currentUser.getId());
        taskEntity.setUpdatedById(currentUser.getId());
        this.assignTaskToUser(assignedUser, taskEntity);
        return taskEntity;
    }

    public void assignTaskToUser(Optional<UserEntity> assignedUser, final TaskEntity taskEntity) {

        Predicate<UserEntity> userExistingTask =  userEntity -> userEntity.getTasks().contains(taskEntity);
        Predicate<UserEntity> userExistingProject =  userEntity -> userEntity.getProjects().contains(taskEntity.getProjectEntity());

        Consumer<UserEntity> setUserTaskAssociation = userEntity -> {
            userEntity.addTask(taskEntity);
            taskEntity.addUser(userEntity);
        };

        Consumer<UserEntity> setUserProjectAssociation = userEntity -> {
            userEntity.addProject(taskEntity.getProjectEntity());
            taskEntity.getProjectEntity().addUser(userEntity);
        };

        assignedUser
                .filter(Predicate.not(userExistingTask))
                .ifPresent(setUserTaskAssociation);

        assignedUser
                .filter(Predicate.not(userExistingProject))
                .ifPresent(setUserProjectAssociation);
    }

    public void assignTaskToUser(UserEntity assignedUser, final TaskEntity taskEntity) {
        assignTaskToUser(Optional.ofNullable(assignedUser), taskEntity);
    }
}
