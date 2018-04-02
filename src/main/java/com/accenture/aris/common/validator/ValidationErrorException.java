package com.accenture.aris.common.validator;

import org.springframework.validation.BindingResult;

public class ValidationErrorException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    private BindingResult bindingResult;
    
    public ValidationErrorException(String message, BindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
