package com.cardinity.assessment.service.task;

import com.cardinity.assessment.model.auth.CurrentUser;
import com.cardinity.assessment.model.request.task.TaskCreationRequest;
import com.cardinity.assessment.model.request.task.TaskUpdateRequest;
import com.cardinity.assessment.model.response.TaskResponse;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public interface TaskService {

    @Transactional(readOnly = true)
    List<TaskResponse> findTasksByUserId(long userId);
    @Transactional(readOnly = true)
    List<TaskResponse> findAllTasks();
    @Transactional(readOnly = true)
    List<TaskResponse> findAllExpiredTasks();

    @Transactional(readOnly = true)
    List<TaskResponse> findCurrentUserTasks(@NonNull CurrentUser currentUser);
    @Transactional(readOnly = true)
    List<TaskResponse> findCurrentUserExpiredTasks(@NonNull CurrentUser currentUser);
    @Transactional(readOnly = true)
    List<TaskResponse> findCurrentUserTasksByProjectId(@NonNull CurrentUser currentUser, long projectId);
    @Transactional(readOnly = true)
    List<TaskResponse> findCurrentUserTasksByStatus(@NonNull CurrentUser currentUser, String status);

    @Transactional
    TaskResponse createTask(TaskCreationRequest request, CurrentUser currentUser);
    @Transactional
    TaskResponse updateTask(TaskUpdateRequest request, CurrentUser currentUser);
    @Transactional
    TaskResponse deleteTask(long projectId);
}
