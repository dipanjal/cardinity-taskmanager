package com.cardinity.assessment.service;

import com.cardinity.assessment.entity.RoleEntity;
import com.cardinity.assessment.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Service
public class RoleEntityService extends BaseCRUDService<RoleEntity, RoleRepository> {

    public RoleEntityService(RoleRepository repository) {
        super(repository);
    }

    public List<RoleEntity> findRolesIn(List<Long> ids){
        return repository.findByIdIn(ids);
    }
}
