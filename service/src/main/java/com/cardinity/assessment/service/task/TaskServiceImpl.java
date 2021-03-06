package com.cardinity.assessment.service.task;

import com.cardinity.assessment.annotation.EnableLogging;
import com.cardinity.assessment.entity.ProjectEntity;
import com.cardinity.assessment.entity.TaskEntity;
import com.cardinity.assessment.entity.UserEntity;
import com.cardinity.assessment.enums.TaskStatus;
import com.cardinity.assessment.exception.BadRequestException;
import com.cardinity.assessment.exception.RecordNotFoundException;
import com.cardinity.assessment.model.auth.CurrentUser;
import com.cardinity.assessment.model.request.task.AssignUserTaskRequest;
import com.cardinity.assessment.model.request.task.TaskCreationRequest;
import com.cardinity.assessment.model.request.task.TaskUpdateRequest;
import com.cardinity.assessment.model.response.TaskResponse;
import com.cardinity.assessment.service.BaseService;
import com.cardinity.assessment.service.ProjectEntityService;
import com.cardinity.assessment.service.TaskEntityService;
import com.cardinity.assessment.service.UserEntityService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
@EnableLogging
public class TaskServiceImpl extends BaseService implements TaskService {
    private final TaskEntityService taskEntityService;
    private final UserEntityService userEntityService;
    private final ProjectEntityService projectEntityService;
    private final TaskMapper mapper;

    @Override
    public List<TaskResponse> findTasksByUserId(long userId) {
        List<TaskResponse> taskResponseList = userEntityService.findById(userId)
                .map(UserEntity::getTasks)
                .map(mapper::mapToDTO)
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.userId.NotFound.message"));

        if(CollectionUtils.isEmpty(taskResponseList))
            throw new RecordNotFoundException(getMessage("validation.constraints.task.NotFound.message"));
        return taskResponseList;
    }

    @Override
    public List<TaskResponse> findAllTasks() {
        List<TaskResponse> taskResponseList = taskEntityService.findAll()
                .stream()
                .map(mapper::mapToDTO)
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(taskResponseList))
            throw new RecordNotFoundException(getMessage("validation.constraints.task.NotFound.message"));
        return taskResponseList;
    }

    @Override
    public List<TaskResponse> findAllExpiredTasks() {
        List<TaskResponse> taskResponseList = taskEntityService.findAllExpiredTasks()
                .stream()
                .map(mapper::mapToDTO)
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(taskResponseList))
            throw new RecordNotFoundException(getMessage("validation.constraints.task.NotFound.message"));
        return taskResponseList;
    }

    @Override
    public List<TaskResponse> findCurrentUserTasks(@NonNull CurrentUser currentUser) {
        return this.findTasksByUserId(super.getCurrentUser().getId());
    }

    @Override
    public List<TaskResponse> findCurrentUserExpiredTasks(@NonNull CurrentUser currentUser) {
        Collection<TaskEntity> taskEntities = userEntityService.findById(currentUser.getId())
                .map(UserEntity::getTasks)
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.userId.NotFound.message"));

        List<TaskResponse> taskResponses = taskEntities.stream()
                .filter(task -> task.getExpireAt().before(new Date()))
                .map(mapper::mapToDTO)
                .collect(Collectors.toList());

        if(CollectionUtils.isEmpty(taskResponses))
            throw new RecordNotFoundException(getMessage("validation.constraints.task.NotFound.message"));
        return taskResponses;
    }

    @Override
    public List<TaskResponse> findCurrentUserTasksByProjectId(@NonNull CurrentUser currentUser,
                                                              long projectId) {
        List<TaskResponse> taskResponses = taskEntityService
                .findTaskByUserIdAndProjectId(currentUser.getId(), projectId)
                .stream()
                .map(mapper::mapToDTO)
                .collect(Collectors.toList());

        if(CollectionUtils.isEmpty(taskResponses))
            throw new RecordNotFoundException(getMessage("validation.constraints.task.NotFound.message"));
        return taskResponses;
    }

    @Override
    public List<TaskResponse> findCurrentUserTasksByStatus(@NonNull CurrentUser currentUser,
                                                           String status) {
        if(TaskStatus.isInvalidStatus(status))
            throw new BadRequestException(getMessage("validation.constraints.taskStatus.Invalid.message"));

        List<TaskResponse> taskResponses = taskEntityService
                .findTaskByUserIdAndTaskStatus(currentUser.getId(), TaskStatus.getCodeByValue(status))
                .stream()
                .filter(t -> t.getStatus() == TaskStatus.getCodeByValue(status))
                .map(mapper::mapToDTO)
                .collect(Collectors.toList());

        if(CollectionUtils.isEmpty(taskResponses))
            throw new RecordNotFoundException(getMessage("validation.constraints.task.NotFound.message"));
        return taskResponses;
    }

    @Override
    public TaskResponse createTask(TaskCreationRequest request, CurrentUser currentUser) {
        ProjectEntity projectEntity = projectEntityService.findById(request.getProjectId())
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.project.NotFound.message"));

        TaskEntity entity = mapper.mapToEntity(
                request, projectEntity, currentUser,
                userEntityService.findById(request.getUserId())
        );

        return mapper.mapToDTO(taskEntityService.save(entity));
    }

    @Override
    public TaskResponse updateTask(TaskUpdateRequest request, CurrentUser currentUser) {

        ProjectEntity projectEntity = projectEntityService.findById(request.getProjectId())
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.project.NotFound.message"));

        TaskEntity taskEntityToUpdate = taskEntityService
                .findTaskByUserIdAndTaskId(request.getUserId(), request.getTaskId())
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.task.NotFound.message"));

        if(TaskStatus.isClosed(taskEntityToUpdate.getStatus()))
            throw new BadRequestException(getMessage("validation.constraints.taskClosed.ImmutableError.message"));

        mapper.mapToEntity(
                request, taskEntityToUpdate, projectEntity, currentUser,
                userEntityService.findById(request.getUserId()));

        return mapper.mapToDTO(taskEntityToUpdate);
    }

    @Override
    public TaskResponse deleteTask(long projectId) {
        return taskEntityService.deleteOpt(projectId)
                .map(mapper::mapToDTO)
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.task.NotFound.message"));
    }

    @Override
    public TaskResponse assignTaskToUser(AssignUserTaskRequest request, CurrentUser currentUser) {
        TaskEntity taskEntity = taskEntityService.findById(request.getTaskId())
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.task.NotFound.message"));

        if(TaskStatus.isClosed(taskEntity.getStatus()))
            throw new BadRequestException(getMessage("validation.constraints.taskClosed.ImmutableError.message"));

        UserEntity userEntity = userEntityService.findById(request.getUserId())
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.userId.NotFound.message"));

        mapper.assignTaskToUser(userEntity, taskEntity);
        taskEntity.setUpdatedById(currentUser.getId());

        return mapper.mapToDTO(taskEntityService.save(taskEntity));
    }
}
