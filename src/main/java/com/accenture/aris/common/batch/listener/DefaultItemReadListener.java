package com.accenture.aris.common.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.item.file.FlatFileParseException;

public class DefaultItemReadListener<T> implements ItemReadListener<T> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultItemReadListener.class);

    @Override
    public void beforeRead() {
        
    }

    @Override
    public void afterRead(T bean) {
        
    }

    @Override
    public void onReadError(Exception ex) {
        if (ex instanceof FlatFileParseException) {
            FlatFileParseException ex2 = (FlatFileParseException)ex;
            LOGGER.error("-====================-");
            LOGGER.error("| RECORD PARSE ERROR: LINE:[{}] STRING:[{}]", ex2.getLineNumber(), ex2.getInput());
            LOGGER.error("-====================-");
        }
    }
}
