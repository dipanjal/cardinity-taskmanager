package com.cardinity.assessment.service;

import com.cardinity.assessment.entity.ProjectEntity;
import com.cardinity.assessment.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Service
public class ProjectEntityService extends BaseCRUDService<ProjectEntity, ProjectRepository> {

    public ProjectEntityService(ProjectRepository repository) {
        super(repository);
    }

    @Override
    public ProjectEntity delete(long id){
        UnaryOperator<ProjectEntity> unlinkUsers = projectEntity -> {
            projectEntity.getAssignedUsers().forEach( u -> u.removeProject(projectEntity));
            projectEntity.getAssignedUsers().clear();
            return projectEntity;
        };

        UnaryOperator<ProjectEntity> unlinkTasks = projectEntity -> {
            projectEntity.getTasks().forEach(taskEntity -> taskEntity.setProjectEntity(null));
            projectEntity.getTasks().clear();
            return projectEntity;
        };

        UnaryOperator<ProjectEntity> deleteProject = projectEntity -> {
            repository.delete(projectEntity);
            return projectEntity;
        };

        return repository.findById(id)
                .map(unlinkUsers)
                .map(unlinkTasks)
                .map(deleteProject)
                .orElse(null);
    }

    @Override
    public Optional<ProjectEntity> deleteOpt(long id) {
        return Optional.ofNullable(this.delete(id));
    }
}
