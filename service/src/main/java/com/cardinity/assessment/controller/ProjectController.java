package com.cardinity.assessment.controller;

import com.cardinity.assessment.model.request.project.ProjectCreationRequest;
import com.cardinity.assessment.model.request.project.ProjectUpdateRequest;
import com.cardinity.assessment.model.response.ProjectResponse;
import com.cardinity.assessment.model.response.TaskResponse;
import com.cardinity.assessment.service.project.ProjectService;
import com.cardinity.assessment.service.task.TaskService;
import com.cardinity.assessment.validation.UserAction;
import io.swagger.v3.oas.annotations.Operation;
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
@RequiredArgsConstructor
public class ProjectController extends BaseController {

    private final ProjectService service;
    private final TaskService taskService;

    @GetMapping("/projects")
    @Operation(tags = ApiTags.PROJECT, description = "Fetch your projects")
    public ResponseEntity<List<ProjectResponse>> getCurrentUserProjects(){
        return ResponseEntity.ok(service.findCurrentUserProjects(getCurrentUser()));
    }

/*    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/projects/all")
    @Operation(tags = ApiTags.PROJECT, description = "Fetch all projects, Permitted to Admin only")
    public ResponseEntity<List<ProjectResponse>> getAllProjects(){
        return ResponseEntity.ok(service.findAllProjects());
    }*/

    @PostMapping("/projects")
    @Operation(tags = ApiTags.PROJECT, description = "Create new project")
    public ResponseEntity<ProjectResponse> createNewProject(@RequestBody
            @Validated(UserAction.CREATE.class) ProjectCreationRequest request, BindingResult result) {
        super.throwIfHasError(result);
        return ResponseEntity.ok(service.createProject(request, getCurrentUser()));
    }

    @PutMapping("/projects")
    @Operation(tags = ApiTags.PROJECT, description = "Update existing project")
    public ResponseEntity<ProjectResponse> updateExistingProject(@RequestBody
            @Validated(UserAction.UPDATE.class) ProjectUpdateRequest request, BindingResult result) {
        super.throwIfHasError(result);
        return ResponseEntity.ok(service.updateProject(request, getCurrentUser()));
    }

    @DeleteMapping("/projects/{id}")
    @Operation(tags = ApiTags.PROJECT, description = "Delete existing project")
    public ResponseEntity<ProjectResponse> deleteExistingProject(@PathVariable long id) {
        return ResponseEntity.ok(service.deleteProject(id));
    }

    @GetMapping("/projects/{id}/tasks")
    @Operation(tags = ApiTags.PROJECT, description = "Fetch your tasks by status in a project")
    public ResponseEntity<List<TaskResponse>> getCurrentUserTasksByProjectId(@PathVariable long id){
        return ResponseEntity.ok(taskService.findCurrentUserTasksByProjectId(super.getCurrentUser(), id));
    }
}
