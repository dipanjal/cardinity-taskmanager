package com.cardinity.assessment.controller;

import com.cardinity.assessment.model.request.task.AssignUserTaskRequest;
import com.cardinity.assessment.model.request.task.TaskCreationRequest;
import com.cardinity.assessment.model.request.task.TaskUpdateRequest;
import com.cardinity.assessment.model.response.TaskResponse;
import com.cardinity.assessment.service.task.TaskService;
import com.cardinity.assessment.validation.UserAction;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@RestController
@RequiredArgsConstructor
public class TaskController extends BaseController {

    private final TaskService service;

    @GetMapping("/tasks")
    @Operation(tags = ApiTags.TASK, description = "Fetch your tasks")
    public ResponseEntity<List<TaskResponse>> getCurrentUserTasks(){
        return ResponseEntity.ok(service.findCurrentUserTasks(super.getCurrentUser()));
    }

    @GetMapping("/tasks/expired")
    @Operation(tags = ApiTags.TASK, description = "Fetch your expired tasks")
    public ResponseEntity<List<TaskResponse>> getCurrentUserExpiredTasks(){
        return ResponseEntity.ok(service.findCurrentUserExpiredTasks(super.getCurrentUser()));
    }

    @GetMapping("/task/status/{status}")
    @Operation(tags = ApiTags.TASK, description = "Fetch your tasks by status")
    public ResponseEntity<List<TaskResponse>> getCurrentUserTasksByStatus(@PathVariable String status){
        return ResponseEntity.ok(service.findCurrentUserTasksByStatus(super.getCurrentUser(), status));
    }

    @PostMapping("/tasks")
    @Operation(tags = ApiTags.TASK, description = "Create new task")
    public ResponseEntity<TaskResponse> createNewTask(@RequestBody
            @Validated(UserAction.CREATE.class) TaskCreationRequest request, BindingResult result) {
        super.throwIfHasError(result);
        return ResponseEntity.ok(service.createTask(request, super.getCurrentUser()));
    }

    @PutMapping("/tasks")
    @Operation(tags = ApiTags.TASK, description = "Update existing task")
    public ResponseEntity<TaskResponse> updateExistingTask(@RequestBody
            @Validated(UserAction.UPDATE.class) TaskUpdateRequest request, BindingResult result) {
        super.throwIfHasError(result);
        return ResponseEntity.ok(service.updateTask(request, super.getCurrentUser()));
    }

    @PutMapping("/tasks/assign/user")
    @Operation(tags = ApiTags.TASK, description = "Assign use into a task")
    public ResponseEntity<TaskResponse> assignTaskToUser(@RequestBody
            @Valid AssignUserTaskRequest request, BindingResult result){
        super.throwIfHasError(result);
        return ResponseEntity.ok(service.assignTaskToUser(request, getCurrentUser()));
    }

    @DeleteMapping("/tasks/{id}")
    @Operation(tags = ApiTags.TASK, description = "Delete existing task")
    public ResponseEntity<TaskResponse> deleteExistingTask(@PathVariable long id) {
        return ResponseEntity.ok(service.deleteTask(id));
    }
}
