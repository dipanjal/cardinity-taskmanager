package com.cardinity.assessment.validation;

/**
 * @author dipanjal
 * @since version 1
 */
public class UserAction {
    public interface CREATE{}
    public interface UPDATE{}
    public interface MODIFY extends CREATE, UPDATE{}
}
