package com.accenture.aris.sample.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.accenture.aris.common.validator.ValidationWrapper;
import com.accenture.aris.core.ForceSkipException;
import com.accenture.aris.sample.batch.record.UserRecord;
import com.accenture.aris.sample.business.entity.UserEntity;
import com.accenture.aris.sample.business.service.UserService;

public class UserRegistProcessor implements ItemProcessor<UserRecord, UserEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistProcessor.class);
    
    @Autowired
    private UserService service;
    
    @Autowired
    private ValidationWrapper<UserRecord> validator;
    
    @Override
    public UserEntity process(UserRecord input) throws Exception {

        // [step1 record validate]
        LOGGER.debug("input={}", input);
        validator.validate(input);

        // [step2 convert entity]
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(input, entity);
        
        LOGGER.debug("service={}", service);
        LOGGER.debug("entity={}", entity);
        
        // [step3 invoke service]
        boolean result = service.saveUserService(entity).getResult();
        if (result == false) {
            throw new ForceSkipException("UserService:saveUserService() failure. entity=" + entity);
        }
        return entity;
    }

}
