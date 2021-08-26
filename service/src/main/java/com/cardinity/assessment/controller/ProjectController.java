package com.cardinity.assessment.controller;

import com.cardinity.assessment.model.response.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@RestController
@RequiredArgsConstructor
public class ProjectController {

    @GetMapping("/project/get-all")
    public ResponseEntity<List<ProjectResponse>> getAllProjects(){
        return ResponseEntity.ok(List.of(new ProjectResponse()));
    }
}
