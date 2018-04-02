package com.accenture.aris.sample.business.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.accenture.aris.core.support.ServiceResult;
import com.accenture.aris.core.support.excel.ExcelUtils;
import com.accenture.aris.core.support.mail.JavaMailSenderWrapper;
import com.accenture.aris.sample.business.entity.UserEntity;
import com.accenture.aris.sample.business.handler.UserHandler;
import com.accenture.aris.sample.business.repository.UserRepository;
import com.accenture.aris.sample.business.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private static final long serialVersionUID = 1L;
    
    private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
	private JavaMailSenderWrapper javaMailSender;
    
    @Override
    public ServiceResult<UserEntity> searchUserService(String id) {
        if (id == null || id.equals("")) {
            return new ServiceResult<UserEntity>();
        }
        return new ServiceResult<UserEntity>(userRepository.selectByPrimaryKey(id));
    }
    
    @Override
    public ServiceResult<List<UserEntity>> searchUserListService(UserEntity entity) {
        if (entity == null) {
            return new ServiceResult<List<UserEntity>>();
        }
        return new ServiceResult<List<UserEntity>>(userRepository.selectByEntity(entity));
    }

    @Override
    public ServiceResult<Boolean> saveUserService(UserEntity entity) {
        if (entity == null) {
            return new ServiceResult<Boolean>(false);
        }
        userRepository.insert(entity);
        return new ServiceResult<Boolean>(true);
    }

    @Override
    public ServiceResult<Boolean> deleteUserService(String id) {
        if (id == null) {
            return new ServiceResult<Boolean>(false);
        }
        userRepository.deleteByPrimaryKey(id);
        return new ServiceResult<Boolean>(true);
    }

    @Override
    public ServiceResult<Boolean> updateUserService(UserEntity entity) {
        if (entity == null) {
            return new ServiceResult<Boolean>(false);
        }
        userRepository.updateByPrimaryKey(entity);
        return new ServiceResult<Boolean>(true);
    }
    
    @Override
    public ServiceResult<Boolean> sendMail(Map<String, Object> model) throws IOException, GeneralSecurityException {
    	
    	InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("template/userList.xls");
    	DataSource dataSource = new ByteArrayDataSource(ExcelUtils.transform(inputStream, model), "application/xls");
		javaMailSender.sendWithAttachement("yuya.miura@accenture.com", "winclue56@gmail.com", "This is a test Message.", dataSource, "userList.xls", "template/userList.vm", model);
		
		return new ServiceResult<Boolean>(true);
    }
    
    @Override
    public ServiceResult<Boolean> downloadWithHandlerService(UserEntity entity, OutputStream outputStream) throws IOException {
        File downloadFile = null;
        boolean isSuccess = false;
        try{
            UserHandler handler = new UserHandler();
            handler.init();
            userRepository.selectByEntityWithHandler(entity, handler);
            downloadFile = handler.download();
            FileCopyUtils.copy(new FileSystemResource(downloadFile).getInputStream(), outputStream);
            isSuccess = true;
        } finally {
            if (isSuccess == true) {
                try {
                    if (downloadFile != null && downloadFile.exists() == true) {
                        downloadFile.delete();
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            } else {
                LOGGER.error("Download failure.");
            }
        }
        return new ServiceResult<Boolean>(true);
    }
}