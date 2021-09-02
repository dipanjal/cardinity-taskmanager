package com.cardinity.assessment.controller;

import com.cardinity.assessment.model.response.ProjectResponse;
import com.cardinity.assessment.model.response.TaskResponse;
import com.cardinity.assessment.service.project.ProjectService;
import com.cardinity.assessment.service.task.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@RestController
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final ProjectService projectService;
    private final TaskService taskService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}/tasks")
    @Operation(tags = ApiTags.USER, description = "Fetch tasks of a user, Admin access only")
    public ResponseEntity<List<TaskResponse>> getTaskByUserId(@PathVariable long id){
        return ResponseEntity.ok(taskService.findTasksByUserId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}/projects")
    @Operation(tags = ApiTags.USER, description = "Fetch projects of a user, Admin access only")
    public ResponseEntity<List<ProjectResponse>> getProjectById(@PathVariable long id){
        return ResponseEntity.ok(projectService.findProjectByUser(id));
    }
}
