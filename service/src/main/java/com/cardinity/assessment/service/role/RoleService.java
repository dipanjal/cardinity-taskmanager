package com.cardinity.assessment.service.role;

import com.cardinity.assessment.entity.RoleEntity;
import com.cardinity.assessment.enums.SystemUserRole;
import com.cardinity.assessment.exception.RecordNotFoundException;
import com.cardinity.assessment.service.RoleEntityService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleEntityService roleEntityService;

    public List<RoleEntity> findAllRoles(){
        return roleEntityService.findAll();
    }

    public List<RoleEntity> createRoles(List<SystemUserRole> roles) {
        Function<SystemUserRole, RoleEntity> mapFunction = r -> new RoleEntity(r.getCode(), r.getRole());

        List<RoleEntity> roleEntities = roles.stream()
                .map(mapFunction)
                .collect(Collectors.toList());
        return roleEntityService.save(roleEntities);
    }

    public RoleEntity findRoleById(long id) {
        SystemUserRole.getValueByCode(id);
        return roleEntityService
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No role found for this id associated"));
    }

    public RoleEntity findRoleByType(SystemUserRole role) {
        return findRoleById(role.getCode());
    }

    public List<RoleEntity> findRolesIn(List<Long> ids){
        List<RoleEntity> roles = roleEntityService.findRolesIn(ids);
        if(CollectionUtils.isEmpty(roles))
            throw new RecordNotFoundException("No Role found");
        return roles;
    }
}
