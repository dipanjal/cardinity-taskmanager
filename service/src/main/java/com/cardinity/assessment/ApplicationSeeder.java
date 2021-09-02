package com.cardinity.assessment;

import com.cardinity.assessment.enums.SystemUserRole;
import com.cardinity.assessment.enums.TaskStatus;
import com.cardinity.assessment.model.auth.CurrentUser;
import com.cardinity.assessment.model.request.project.ProjectCreationRequest;
import com.cardinity.assessment.model.request.task.TaskCreationRequest;
import com.cardinity.assessment.model.request.user.CreateUserRequest;
import com.cardinity.assessment.props.AppProperties;
import com.cardinity.assessment.service.ProjectEntityService;
import com.cardinity.assessment.service.TaskEntityService;
import com.cardinity.assessment.service.project.ProjectService;
import com.cardinity.assessment.service.role.RoleService;
import com.cardinity.assessment.service.task.TaskService;
import com.cardinity.assessment.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final UserService userService;
    private final RoleService roleService;

    private final ProjectEntityService projectEntityService;
    private final ProjectService projectService;

    private final TaskEntityService taskEntityService;
    private final TaskService taskService;

    private final AppProperties props;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(props.isSeederEnabled()){
            populateRoles();
            populateUsers();
//            populateProjects();
//            populateTasks();
        }
    }

    private CurrentUser getDummyCurrentUser() {
        return new CurrentUser(
                1, "user1", "user",
                List.of(new SimpleGrantedAuthority(SystemUserRole.USER.getRole()))
        );
    }


    private void populateRoles() {
        if(CollectionUtils.isEmpty(roleService.findAllRoles()))
            roleService.createRoles(List.of(
                    SystemUserRole.USER,
                    SystemUserRole.ADMIN
            ));
    }

    private List<CreateUserRequest> getDummyUsers(){
        return List.of(
                CreateUserRequest
                        .builder()
                        .fullName("Dummy User 1").userName("user1").email("user1@test.com")
                        .password("user").role(SystemUserRole.USER.getCode())
                        .build(),
                CreateUserRequest
                        .builder()
                        .fullName("Dummy User 2").userName("user2").email("user2@test.com")
                        .password("user").role(SystemUserRole.USER.getCode())
                        .build(),
                CreateUserRequest
                        .builder()
                        .fullName("Dummy Admin 1").userName("admin1").email("admin1@test.com")
                        .password("admin").role(SystemUserRole.ADMIN.getCode())
                        .build(),
                CreateUserRequest
                        .builder()
                        .fullName("Admin User").userName("admin_user").email("admin_user@test.com")
                        .password("admin")
                        .role(SystemUserRole.ADMIN.getCode())
                        .role(SystemUserRole.USER.getCode())
                        .build()
        );
    }

    private void populateUsers() {
        if(CollectionUtils.isEmpty(userService.findAllUsers())){
            getDummyUsers()
                    .forEach(userService::createUser);

        }
    }

    private void populateProjects() {
        if(CollectionUtils.isEmpty(projectEntityService.findAll())){

            ProjectCreationRequest p1 = new ProjectCreationRequest();
            p1.setName("Task Manager Rest Api");
            p1.setUserId(1);

            ProjectCreationRequest p2 = new ProjectCreationRequest();
            p2.setName("Task Manager Web");
            p2.setUserId(2);

            projectService.createProject(p1, getDummyCurrentUser());
            projectService.createProject(p2, getDummyCurrentUser());
        }
    }

    private void populateTasks() {

        if(CollectionUtils.isEmpty(taskEntityService.findAll())){

            TaskCreationRequest t1 = new TaskCreationRequest();
            t1.setName("Initiate a Spring Boot Project");
            t1.setDescription("A Very basic Spring boot Project Structure");
            t1.setProjectId(1);
            t1.setExpiryHour(1);
            t1.setStatus(TaskStatus.OPEN.getValue());
            t1.setUserId(1);

            TaskCreationRequest t2 = new TaskCreationRequest();
            t2.setName("Add Security");
            t2.setDescription("Configure Spring Security");
            t2.setProjectId(1);
            t2.setExpiryHour(4);
            t2.setStatus(TaskStatus.OPEN.getValue());
            t2.setUserId(1);

            TaskCreationRequest t3 = new TaskCreationRequest();
            t3.setName("Design Dashboard");
            t3.setDescription("A dashboard design for Task Manager");
            t3.setProjectId(2);
            t3.setExpiryHour(5);
            t3.setStatus(TaskStatus.OPEN.getValue());
            t3.setUserId(2);

            taskService.createTask(t1, getDummyCurrentUser());
            taskService.createTask(t2, getDummyCurrentUser());
            taskService.createTask(t3, getDummyCurrentUser());
        }

    }
}
