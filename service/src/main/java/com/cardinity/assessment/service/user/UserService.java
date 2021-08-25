package com.cardinity.assessment.service.user;

import com.cardinity.assessment.model.request.user.CreateUserRequest;
import com.cardinity.assessment.model.request.user.UpdateUserRequest;
import com.cardinity.assessment.model.response.user.UserResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */

public interface UserService {

    @Transactional(readOnly = true)
    List<UserResponse> findAllUsers();
    @Transactional(readOnly = true)
    UserResponse findUserById(long id);
    @Transactional(readOnly = true)
    UserResponse findUserByUsername(String userName);
    @Transactional
    UserResponse createUser(CreateUserRequest dto);
    @Transactional
    UserResponse updateUser(UpdateUserRequest dto);
    @Transactional
    UserResponse deleteUser(long id);
}
