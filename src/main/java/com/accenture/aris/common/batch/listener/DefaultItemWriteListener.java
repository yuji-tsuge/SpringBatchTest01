package com.accenture.aris.common.batch.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;

public class DefaultItemWriteListener<T> implements ItemWriteListener<T> {

    @Override
    public void beforeWrite(List<? extends T> list) {

    }

    @Override
    public void afterWrite(List<? extends T> list) {

    }

    @Override
    public void onWriteError(Exception paramException, List<? extends T> list) {

    }

}
