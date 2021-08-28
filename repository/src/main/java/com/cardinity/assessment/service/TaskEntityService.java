package com.cardinity.assessment.service;

import com.cardinity.assessment.entity.TaskEntity;
import com.cardinity.assessment.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Service
public class TaskEntityService extends BaseCRUDService<TaskEntity, TaskRepository> {

    public TaskEntityService(TaskRepository repository) {
        super(repository);
    }

    @Override
    public TaskEntity delete(long id){
        UnaryOperator<TaskEntity> unlinkAssociations = taskEntity -> {
            taskEntity.getAssignedUsers().forEach( u -> u.removeTask(taskEntity));
            taskEntity.getAssignedUsers().clear();
            taskEntity.setProjectEntity(null);
            return taskEntity;
        };

        UnaryOperator<TaskEntity> deleteTask = taskEntity -> {
            repository.delete(taskEntity);
            return taskEntity;
        };

        return repository.findById(id)
                .map(unlinkAssociations)
                .map(deleteTask)
                .orElseThrow(() -> new RuntimeException("No Task Found with this ID associated"));
    }

    @Override
    public Optional<TaskEntity> deleteOpt(long id) {
        return Optional.ofNullable(this.delete(id));
    }

    public List<TaskEntity> findAllExpiredTasks() {
        return repository.findAllByCreatedAtBefore(new Date());
    }
}
