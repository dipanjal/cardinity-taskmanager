package com.cardinity.assessment.controller;

import com.cardinity.assessment.exception.BadRequestException;
import com.cardinity.assessment.model.auth.CurrentUser;
import com.cardinity.assessment.service.LocaleMessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public class BaseController {

    private LocaleMessageHelper messageHelper;

    @Autowired
    public void inject(LocaleMessageHelper messageHelper){
        this.messageHelper = messageHelper;
    }

    private String getJoinedErrorMessage(BindingResult bindingResult){
        return bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .map(messageHelper::getLocalMessage)
                .collect(Collectors.joining(", "));
    }

    protected void throwIfHasError(BindingResult result){
        if(result.hasErrors())
            throw new BadRequestException(this.getJoinedErrorMessage(result));
    }

    protected CurrentUser getCurrentUser() {
        return (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
