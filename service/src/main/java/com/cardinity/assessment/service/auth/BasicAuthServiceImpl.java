package com.cardinity.assessment.service.auth;

import com.cardinity.assessment.exception.AuthException;
import com.cardinity.assessment.exception.BadRequestException;
import com.cardinity.assessment.model.request.auth.AuthenticationRequest;
import com.cardinity.assessment.props.AppProperties;
import com.cardinity.assessment.service.BaseService;
import com.cardinity.assessment.utils.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Service
public class BasicAuthServiceImpl extends BaseService implements AuthService {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final AppProperties props;

    public BasicAuthServiceImpl(@Qualifier("cardinityUserDetailsService") UserDetailsService userDetailsService,
                                AuthenticationManager authenticationManager,
                                AppProperties props) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.props = props;
    }

    @Override
    public String authenticate(AuthenticationRequest request) {
        try{
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            );
            authenticationManager.authenticate(auth);
        }catch (BadCredentialsException e){
            throw new BadCredentialsException(
                    getMessage("validation.authentication.failed.message"), e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        return JWTUtils.generateToken(userDetails.getUsername());
    }

    @Override
    public UserDetails validateToken(String token) {
        if(StringUtils.isBlank(token) || !StringUtils.startsWith(token, props.getTokenPrefix()))
            throw new BadRequestException(getMessage("validation.token.format.invalid.message"));

        String jwtToken = JWTUtils.trimToken(token);
        String userName = JWTUtils.extractUserName(jwtToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        if(JWTUtils.isTokenInvalidOrExpired(jwtToken, userDetails.getUsername()))
            throw new AuthException(getMessage("validation.token.expired.or.invalid.message"));

        return userDetails;
    }
}
