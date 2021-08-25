package com.cardinity.assessment.service.project;

import com.cardinity.assessment.model.auth.CurrentUser;
import com.cardinity.assessment.model.request.project.ProjectCreationRequest;
import com.cardinity.assessment.model.response.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {


    @Override
    public ProjectResponse createUser(ProjectCreationRequest request) {
        return null;
    }

    @Override
    public List<ProjectResponse> getUserProjects(CurrentUser user) {
        return null;
    }
}
