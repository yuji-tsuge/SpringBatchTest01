package com.accenture.aris.common.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;

import com.accenture.aris.common.validator.ValidationErrorException;

public class DefaultItemProcessListener<T, S> implements ItemProcessListener<T, S> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultItemProcessListener.class);
    
    @Override
    public void beforeProcess(T bean) {

    }

    @Override
    public void afterProcess(T input, S output) {

    }

    @Override
    public void onProcessError(T input, Exception ex) {
        if (ex instanceof ValidationErrorException) {
            LOGGER.warn("Inout Error. error = {}", ((ValidationErrorException) ex).getBindingResult());
        }
    }

}
