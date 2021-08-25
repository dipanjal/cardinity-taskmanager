package com.cardinity.assessment.service;

import com.cardinity.assessment.repository.UserRepository;
import com.cardinity.assessment.entity.UserEntity;
import com.cardinity.assessment.exception.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Service
public class UserEntityService extends BaseCRUDService<UserEntity, UserRepository> {
    public UserEntityService(UserRepository repository) {
        super(repository);
    }

    public Optional<UserEntity> findUserByUsername(String userName){
        return repository.findDistinctByUsername(userName);
    }

    @Override
    public UserEntity delete(long id){
        return repository.findById(id)
                .map(userEntity -> {
                    userEntity.getProjects().forEach( p -> p.removeUser(userEntity));
                    userEntity.getProjects().clear();
                    repository.delete(userEntity);
                    return userEntity;
                }).orElseThrow(() -> new RecordNotFoundException("validation.constraints.userId.NotFound.message"));
    }
}
