package com.accenture.aris.common.batch.item;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;

public class BeanWrapperFieldExtractor<T> extends org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor<T> {

    public static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public static final String DEFAULT_NUMBER_FORMAT = "################0.0###";

    private String dateFormat = DEFAULT_DATE_FORMAT;
    private String numberFormat = DEFAULT_NUMBER_FORMAT;
    private String[] names;
    
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
    
    public void setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
    }
    
    public void setNames(String[] names) {
        Assert.notNull(names, "Names must be non-null");
        this.names = ((String[])Arrays.asList(names).toArray(new String[names.length]));
        super.setNames(names);
    }
    
    @Override
    public Object[] extract(T item) {
        List<Object> values = new ArrayList<Object>();
        BeanWrapper bw = new BeanWrapperImpl(item);
        SimpleDateFormat dateFormatter = null;
        DecimalFormat numberFormatter = null;
        for (String propertyName : this.names) {
            Object value = bw.getPropertyValue(propertyName);
            if (value instanceof Date) {
                if (dateFormatter == null) {
                    dateFormatter = new SimpleDateFormat(dateFormat);
                    dateFormatter.setLenient(false);
                }
                values.add(dateFormatter.format(value));
            } else if (value instanceof Number){
                if (numberFormatter == null) {
                    numberFormatter = new DecimalFormat(this.numberFormat);
                }
                values.add(numberFormatter.format(value));
            } else {
                values.add(value);
            }
        }
        return values.toArray();
    }

}
