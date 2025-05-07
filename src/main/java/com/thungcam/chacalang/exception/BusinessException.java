package com.thungcam.chacalang.exception;

import org.springframework.security.authorization.AuthorizationDeniedException;

public class BusinessException extends AuthorizationDeniedException {
    public BusinessException(String message) {
        super(message);
    }
}
