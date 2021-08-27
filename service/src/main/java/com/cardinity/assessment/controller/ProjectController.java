package com.cardinity.assessment.controller;

import com.cardinity.assessment.model.request.project.ProjectCreationRequest;
import com.cardinity.assessment.model.request.project.ProjectUpdateRequest;
import com.cardinity.assessment.model.response.ProjectResponse;
import com.cardinity.assessment.service.project.ProjectService;
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
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController extends BaseController {

    private final ProjectService service;

    @GetMapping("/get")
    public ResponseEntity<List<ProjectResponse>> getCurrentUserProjects(){
        return ResponseEntity.ok(service.findCurrentUserProjects());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<List<ProjectResponse>> getAllProjects(){
        return ResponseEntity.ok(service.findAllProjects());
    }

    @GetMapping("/get-by-user-id/{id}")
    public ResponseEntity<List<ProjectResponse>> getProjectById(@PathVariable long id){
        return ResponseEntity.ok(service.findProjectByUser(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ProjectResponse> createNewProject(@RequestBody
            @Validated(UserAction.CREATE.class) ProjectCreationRequest request, BindingResult result) {
        super.throwIfHasError(result);
        return ResponseEntity.ok(service.createProject(request));
    }

    @PostMapping("/update")
    public ResponseEntity<ProjectResponse> updateExistingProject(@RequestBody
            @Validated(UserAction.UPDATE.class) ProjectUpdateRequest request, BindingResult result) {
        super.throwIfHasError(result);
        return ResponseEntity.ok(service.updateProject(request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProjectResponse> deleteExistingProject(@PathVariable long id) {
        return ResponseEntity.ok(service.deleteProject(id));
    }
}
