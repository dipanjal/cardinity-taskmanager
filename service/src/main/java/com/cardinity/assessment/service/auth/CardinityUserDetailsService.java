package com.cardinity.assessment.service.auth;

import com.cardinity.assessment.entity.UserEntity;
import com.cardinity.assessment.service.BaseService;
import com.cardinity.assessment.service.UserEntityService;
import com.cardinity.assessment.service.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class CardinityUserDetailsService extends BaseService implements UserDetailsService {

    private final UserEntityService userEntityService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = userEntityService
                .findUserByUsername(username)
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.username.NotFound.message"));
        return UserMapper.mapToUserDetails(entity);
    }

}
