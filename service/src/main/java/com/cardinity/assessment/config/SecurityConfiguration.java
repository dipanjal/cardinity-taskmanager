package com.cardinity.assessment.config;

import com.cardinity.assessment.advice.AuthFailureHandler;
import com.cardinity.assessment.filter.JWTRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author dipanjal
 * @since 2/7/2021
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("cardinityUserDetailsService")
    private UserDetailsService userDetailsService;
    private final JWTRequestFilter jwtFilter;
    private final AuthFailureHandler authFailureHandler;

    @Override
    protected void configure(final AuthenticationManagerBuilder authBuilder) throws Exception {
        authBuilder.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
/*        http.csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/access/token")
                .permitAll()
                .anyRequest().authenticated();
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().exceptionHandling().authenticationEntryPoint(authFailureHandler);

//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private String[] getAllowedUrls(){
        return new String[]{
                "/access/token",
                "/v3/api-docs/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-resources/**",
                "/null/**",
                "/webjars/**",
        };
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }
}
