package com.accenture.aris.common.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.SkipListenerSupport;

public class DefaultSkipListener<T, S> extends SkipListenerSupport<T, S> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSkipListener.class);
    
    @Override
    public void onSkipInRead(Throwable t) {
        LOGGER.info("Skip Read. exception = {}", t.getClass().getName());
    }

    @Override
    public void onSkipInWrite(S item, Throwable t) {
        LOGGER.info("Skip Write. Item = {}, exception = {}", item, t.getClass().getName());
    }

    @Override
    public void onSkipInProcess(T item, Throwable t) {
        LOGGER.info("Skip Process. Item = {}, exception = {}", item, t.getClass().getName());
    }
}
