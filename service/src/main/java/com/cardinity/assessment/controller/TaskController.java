package com.cardinity.assessment.controller;

import com.cardinity.assessment.model.request.task.TaskCreationRequest;
import com.cardinity.assessment.model.request.task.TaskUpdateRequest;
import com.cardinity.assessment.model.response.TaskResponse;
import com.cardinity.assessment.service.task.TaskService;
import com.cardinity.assessment.validation.UserAction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController extends BaseController {

    private final TaskService service;

    @GetMapping("/get")
    public ResponseEntity<List<TaskResponse>> getCurrentUserTasks(){
        return ResponseEntity.ok(service.findCurrentUserTasks(super.getCurrentUser()));
    }

    @GetMapping("/get/expired")
    public ResponseEntity<List<TaskResponse>> getCurrentUserExpiredTasks(){
        return ResponseEntity.ok(service.findCurrentUserExpiredTasks(super.getCurrentUser()));
    }

    @GetMapping("/get-by-status/{status}")
    public ResponseEntity<List<TaskResponse>> getCurrentUserTasksByStatus(@PathVariable String status){
        return ResponseEntity.ok(service.findCurrentUserTasksByStatus(super.getCurrentUser(), status));
    }

    @GetMapping("/get-by-project/{id}")
    public ResponseEntity<List<TaskResponse>> getCurrentUserTasksByProjectId(@PathVariable long id){
        return ResponseEntity.ok(service.findCurrentUserTasksByProjectId(super.getCurrentUser(), id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<TaskResponse>> getAllTasks(){
        return ResponseEntity.ok(service.findAllTasks());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-by-user-id/{id}")
    public ResponseEntity<List<TaskResponse>> getTaskByUserId(@PathVariable long id){
        return ResponseEntity.ok(service.findTasksByUserId(id));
    }

    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createNewTask(@RequestBody
            @Validated(UserAction.CREATE.class) TaskCreationRequest request, BindingResult result) {
        super.throwIfHasError(result);
        return ResponseEntity.ok(service.createTask(request, super.getCurrentUser()));
    }

    @PostMapping("/update")
    public ResponseEntity<TaskResponse> updateExistingTask(@RequestBody
            @Validated(UserAction.UPDATE.class) TaskUpdateRequest request, BindingResult result) {
        super.throwIfHasError(result);
        return ResponseEntity.ok(service.updateTask(request, super.getCurrentUser()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TaskResponse> deleteExistingTask(@PathVariable long id) {
        return ResponseEntity.ok(service.deleteTask(id));
    }
}
