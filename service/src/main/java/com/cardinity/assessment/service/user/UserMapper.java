package com.cardinity.assessment.service.user;

import com.cardinity.assessment.entity.RoleEntity;
import com.cardinity.assessment.entity.UserEntity;
import com.cardinity.assessment.model.auth.CurrentUser;
import com.cardinity.assessment.model.request.user.CreateUserRequest;
import com.cardinity.assessment.model.request.user.UpdateUserRequest;
import com.cardinity.assessment.model.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    /** Spring Security Model Mapping */
    public static User mapToUserDetails(UserEntity entity) {
        return new CurrentUser (
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                getGrantedAuthorities(entity.getRoles())
        );
    }

    private static List<GrantedAuthority> getGrantedAuthorities(Collection<RoleEntity> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    /** ENTITY MAPPING */
    public UserEntity mapToNewUserEntity(CreateUserRequest dto, Collection<RoleEntity> roleEntities){
        return new UserEntity(
                dto.getUserName(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getFullName(),
                dto.getEmail(),
                roleEntities
        );
    }

    public void fillUpdatableEntity(UserEntity entity, UpdateUserRequest dto){
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
    }

    /** DTO MAPPING */
    public UserResponse mapToDTO(UserEntity entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .userName(entity.getUsername())
                .email(entity.getEmail())
                .roles(getRoleNames(entity.getRoles()))
                .build();
    }

    public List<UserResponse> mapToDTO(List<UserEntity> entityList) {
        return entityList
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /** HELPERS */
    private List<String> getRoleNames(Collection<RoleEntity> roleEntities){
        return roleEntities
                .stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toList());
    }
}
