package com.cardinity.assessment.service.project;

import com.cardinity.assessment.model.auth.CurrentUser;
import com.cardinity.assessment.model.request.project.ProjectCreationRequest;
import com.cardinity.assessment.model.response.ProjectResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public interface ProjectService {
    @Transactional
    ProjectResponse createUser(ProjectCreationRequest request);
    @Transactional(readOnly = true)
    List<ProjectResponse> getUserProjects(CurrentUser user);
}
