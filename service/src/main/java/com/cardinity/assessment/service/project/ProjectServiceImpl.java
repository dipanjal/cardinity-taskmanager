package com.cardinity.assessment.service.project;

import com.cardinity.assessment.entity.ProjectEntity;
import com.cardinity.assessment.entity.UserEntity;
import com.cardinity.assessment.exception.RecordNotFoundException;
import com.cardinity.assessment.model.auth.CurrentUser;
import com.cardinity.assessment.model.request.project.ProjectCreationRequest;
import com.cardinity.assessment.model.request.project.ProjectUpdateRequest;
import com.cardinity.assessment.model.response.ProjectResponse;
import com.cardinity.assessment.service.BaseService;
import com.cardinity.assessment.service.ProjectEntityService;
import com.cardinity.assessment.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl extends BaseService implements ProjectService {

    private final ProjectEntityService projectEntityService;
    private final UserEntityService userEntityService;
    private final ProjectMapper mapper;

    @Override
    public List<ProjectResponse> findAllProjects() {
        List<ProjectEntity> entityList = projectEntityService.findAll();
        return mapper.mapToDTO(entityList);
    }

    @Override
    public List<ProjectResponse> findCurrentUserProjects(CurrentUser currentUser) {
        return this.findProjectByUser(currentUser.getId());
    }

    @Override
    public List<ProjectResponse> findProjectByUser(long userId) {
        List<ProjectResponse> projectResponses =
                userEntityService.findById(userId)
                        .map(UserEntity::getProjects)
                        .map(mapper::mapToDTO)
                        .orElseThrow(supplyRecordNotFoundException("validation.constraints.userId.NotFound.message"));

        if(CollectionUtils.isEmpty(projectResponses))
            throw new RecordNotFoundException(getMessage("validation.constraints.project.NotFound.message"));

        return projectResponses;
    }

    @Override
    public ProjectResponse createProject(ProjectCreationRequest request, CurrentUser currentUser) {
        ProjectEntity entity = mapper.mapToEntity(
                request, currentUser,
                userEntityService.findById(request.getUserId())
        );
        return mapper.mapToDTO(projectEntityService.save(entity));
    }

    @Override
    public ProjectResponse updateProject(ProjectUpdateRequest request, CurrentUser currentUser) {
        return projectEntityService.findById(request.getProjectId())
                .map(project ->
                        mapper.mapToEntity(request, project, currentUser,
                                userEntityService.findById(request.getUserId())))
                .map(projectEntityService::save)
                .map(mapper::mapToDTO)
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.project.NotFound.message"));
    }

    @Override
    public ProjectResponse deleteProject(long projectId) {
        return projectEntityService.deleteOpt(projectId)
                .map(mapper::mapToDTO)
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.project.NotFound.message"));
    }
}
