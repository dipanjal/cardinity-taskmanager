package com.cardinity.assessment.service;

import com.cardinity.assessment.enums.SystemUserRole;
import com.cardinity.assessment.enums.TaskStatus;
import com.cardinity.assessment.model.auth.CurrentUser;
import com.cardinity.assessment.model.request.task.TaskCreationRequest;
import com.cardinity.assessment.model.response.TaskResponse;
import com.cardinity.assessment.service.task.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@SpringBootTest
public class TaskServiceTests {

    @Autowired
    private TaskService taskService;

    @Test
    @Disabled
    public void createTaskTest(){
        TaskCreationRequest request = new TaskCreationRequest();
        request.setName("Create Task Service");
        request.setDescription("Design Rest API for Task Creation");
        request.setExpiryHour(1);
        request.setStatus(TaskStatus.OPEN.getValue());
        request.setUserId(2);
        request.setProjectId(1);
        CurrentUser user = new CurrentUser(1, "user1", "",
                List.of(new SimpleGrantedAuthority(SystemUserRole.USER.getRole())));
        TaskResponse response = taskService.createTask(request, user);
        Assertions.assertNotNull(response);
    }
}
