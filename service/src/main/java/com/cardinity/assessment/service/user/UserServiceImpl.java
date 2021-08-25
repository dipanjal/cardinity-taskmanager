package com.cardinity.assessment.service.user;

import com.cardinity.assessment.annotation.EnableLogging;
import com.cardinity.assessment.entity.RoleEntity;
import com.cardinity.assessment.entity.UserEntity;
import com.cardinity.assessment.exception.NotUniqueException;
import com.cardinity.assessment.model.request.user.CreateUserRequest;
import com.cardinity.assessment.model.request.user.UpdateUserRequest;
import com.cardinity.assessment.model.response.user.UserResponse;
import com.cardinity.assessment.service.BaseService;
import com.cardinity.assessment.service.UserEntityService;
import com.cardinity.assessment.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@Service
@RequiredArgsConstructor
@EnableLogging
public class UserServiceImpl extends BaseService implements UserService {

    private final UserEntityService userEntityService;
    private final RoleService roleService;
    private final UserMapper mapper;

    @Override
    public List<UserResponse> findAllUsers() {
        List<UserEntity> userEntities = userEntityService.findAll();
        return mapper.mapToDTO(userEntities);
    }

    @Override
    public UserResponse findUserById(long id) {
        UserEntity userEntity = userEntityService
                .findById(id)
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.userId.NotFound.message"));
        return mapper.mapToDTO(userEntity);
    }

    @Override
    public UserResponse findUserByUsername(String userName) {
        UserEntity userEntity = userEntityService
                .findUserByUsername(userName)
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.username.NotFound.message"));
        return mapper.mapToDTO(userEntity);
    }

    @Override
    public UserResponse createUser(CreateUserRequest dto) {
        List<RoleEntity> roles = roleService.findRolesIn(dto.getRoles());

        userEntityService.findUserByUsername(dto.getUserName())
                .ifPresent(u -> {
                    throw new NotUniqueException(
                            messageHelper.getLocalMessage("validation.constraints.username.exists.message"));
                });

        UserEntity entity = mapper.mapToNewUserEntity(dto, roles);
        userEntityService.save(entity);
        return mapper.mapToDTO(entity);
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest dto) {
        UserEntity updatableEntity = userEntityService
                .findById(dto.getId())
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.userId.NotFound.message"));

        mapper.fillUpdatableEntity(updatableEntity, dto);
        userEntityService.save(updatableEntity);
        return mapper.mapToDTO(updatableEntity);
    }

    @Override
    public UserResponse deleteUser(long id) {
        return mapper.mapToDTO(userEntityService.delete(id));
    }
}
