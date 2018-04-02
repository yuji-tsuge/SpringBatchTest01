package com.accenture.aris.common.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

public class ValidationWrapper<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationWrapper.class);

    @Autowired
    private Validator validator;
    
    private boolean isThrow = false;
    private boolean isLoggingError = false;
    
    public void setThrow(boolean isThrow) {
        this.isThrow = isThrow;
    }

    public void setLoggingError(boolean isLoggingError) {
        this.isLoggingError = isLoggingError;
    }

    /**
     * バリデーションを実行します.<p>
     * ARIS Batchでは原則としてBeanValidationを行う想定です。
     * @param target バリデーションの対象Object
     * @param objectName 対象Objectの名称
     * @return BindinResultオブジェクト
     */
    public BindingResult validate(T target, String objectName) {
        assert(target != null);
        if (objectName == null) {
            objectName = target.getClass().getSimpleName();
            objectName = objectName.substring(0, 1).toLowerCase() + objectName.substring(1);
        }
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(target, objectName);
        validator.validate(target, bindingResult);
        if (bindingResult.hasErrors()) {
            if (isLoggingError == true) {
                for(ObjectError error: bindingResult.getAllErrors()) {
                    String objectNameStr = error.getObjectName();
                    if (error instanceof FieldError) {
                        FieldError fe = (FieldError)error;
                        LOGGER.error("-=============-");
                        LOGGER.error("| INPUT ERROR: ObjectName=" + objectNameStr + " propertyName=" + fe.getField() + " value=" + fe.getRejectedValue() + " message=" + fe.getDefaultMessage());
                        LOGGER.error("-=============-");
                    }
                }
            }
            if (isThrow) {
                throw new ValidationErrorException("input check error.", bindingResult);
            }
        }
        return bindingResult;
    }
    
    /**
     * バリデーションを実行します.<p>
     * {@link ValidationWrapper#validate(Object, String, boolean)}と機能は同じですが、オブジェクトの名称を自動で作成し、エラーが存在する場合にはExceptionをThrowします。 
     * @param target バリデーションの対象Object
     */
    public BindingResult validate(T target) {
        return validate(target, null);
    }
}
