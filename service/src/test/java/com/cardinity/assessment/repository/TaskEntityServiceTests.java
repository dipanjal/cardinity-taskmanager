package com.cardinity.assessment.repository;

import com.cardinity.assessment.entity.TaskEntity;
import com.cardinity.assessment.service.TaskEntityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@SpringBootTest
public class TaskEntityServiceTests {

    @Autowired
    private TaskEntityService entityService;

    @Test
    @Disabled
    public void findUserTaskByInAProject() {
        Optional<TaskEntity> taskEntities = entityService
                .findTaskByUserIdAndTaskId(1, 1);
        Assertions.assertFalse(taskEntities.isEmpty());
    }
}
