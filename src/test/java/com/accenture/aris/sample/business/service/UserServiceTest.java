package com.accenture.aris.sample.business.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.accenture.aris.core.support.utils.AssertUtils;
import com.accenture.aris.core.support.utils.DatabaseUtils;
import com.accenture.aris.sample.business.entity.UserEntity;
import com.accenture.aris.sample.business.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/batch-config-test.xml")
public class UserServiceTest {
    
    private static final Logger LOGGER =LoggerFactory.getLogger(UserServiceTest.class);
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository repository ;
    
    @Before
    public void init() throws DataSetException, IOException, SQLException, DatabaseUnitException {
        DatabaseUtils.execute(DatabaseOperation.DELETE_ALL, "user/data/testSaveUserService_01_Database.xls", new MySqlDataTypeFactory());
    }
    
    @Test
    public void testSaveUserService() throws Exception {
        Map<String, List<?>> map = AssertUtils.getData("user/data/testSaveUserService_01_Entity.xml", "user/data/testSaveUserService_01_Entity.xls");
        List<UserEntity> list = (List<UserEntity>)map.get("userEntities");
        
        LOGGER.debug("expect={}", list);
        
        for (UserEntity entity: list) {
            userService.saveUserService(entity);
        }
        List<UserEntity> result = repository.selectAll();

        LOGGER.debug("actual={}", result);
        
        AssertUtils.assertEquals(list, result);
    }
    
    @After
    public void destroy() throws DataSetException, IOException, SQLException, DatabaseUnitException {
        //DatabaseUtils.execute(DatabaseOperation.DELETE, "/user/data/testSaveUserService_01_Database.xls", new MySqlDataTypeFactory());
    }
}
