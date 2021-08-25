package com.cardinity.assessment;

import com.cardinity.assessment.enums.SystemUserRole;
import com.cardinity.assessment.model.request.user.CreateUserRequest;
import com.cardinity.assessment.service.role.RoleService;
import com.cardinity.assessment.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
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

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        populateRoles();
//        populateUsers();
    }
}
