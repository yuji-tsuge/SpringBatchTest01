package com.accenture.aris.sample.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;


import com.accenture.aris.core.ForceSkipException;
import com.accenture.aris.core.support.ServiceResult;
import com.accenture.aris.sample.business.entity.UserEntity;
import com.accenture.aris.sample.business.service.UserService;

public class UserUpdateProcessor implements ItemProcessor<UserEntity, UserEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserUpdateProcessor.class);

    @Autowired
    private UserService userService;
    
    @Override
    public UserEntity process(UserEntity input) throws Exception {
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(input, entity);
        ServiceResult<Boolean> result = userService.updateUserService(entity);
        if (result.getResult() == true) {
            LOGGER.debug("update bean=" + entity);
            return entity;
        } else {
            throw new ForceSkipException();
        }
    }
}
