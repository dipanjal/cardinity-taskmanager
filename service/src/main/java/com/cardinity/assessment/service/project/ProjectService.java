package com.cardinity.assessment.service.project;

import com.cardinity.assessment.model.request.project.ProjectCreationRequest;
import com.cardinity.assessment.model.request.project.ProjectUpdateRequest;
import com.cardinity.assessment.model.response.ProjectResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public interface ProjectService {

    @Transactional(readOnly = true)
    List<ProjectResponse> findAllProjects();

    @Transactional(readOnly = true)
    List<ProjectResponse> findCurrentUserProjects();

    @Transactional(readOnly = true)
    List<ProjectResponse> findProjectByUser(long userId);

    @Transactional
    ProjectResponse createProject(ProjectCreationRequest request);

    @Transactional
    ProjectResponse updateProject(ProjectUpdateRequest request);

    @Transactional
    ProjectResponse deleteProject(long projectId);
}
