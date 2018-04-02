package com.accenture.aris.common.batch.item;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.DataBinder;

public class BeanWrapperFieldSetMapper<T> extends org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper<T> {

    public static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    
    private String dateFormat = DEFAULT_DATE_FORMAT;
    
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
    
    @Override
    protected void initBinder(DataBinder binder) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(this.dateFormat);
        dateFormatter.setLenient(false);
        CustomDateEditor dateEditor = new CustomDateEditor(dateFormatter, true);
        binder.registerCustomEditor(Date.class, dateEditor);
        
        super.initBinder(binder);
    }
}
