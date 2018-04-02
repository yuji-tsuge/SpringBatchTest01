package com.accenture.aris.common.batch.parameters;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class DefaultJobParametersValidator implements JobParametersValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultJobParametersValidator.class);
    
    @Override
    public void validate(JobParameters params) throws JobParametersInvalidException {
        Map<String, JobParameter> paramsMap = params.getParameters();
        Iterator<Map.Entry<String, JobParameter>> it = paramsMap.entrySet().iterator();
        for(int i=0; it.hasNext(); i++) {
            Map.Entry<String, JobParameter> entry = it.next();
            LOGGER.debug("job-param[" + i + "]:" +" key=" +  entry.getKey() +", parameter=" + entry.getValue());
            
            // 必要に応じてここにValidationを記述する。
            
        }
    }
}