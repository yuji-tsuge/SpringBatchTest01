package com.accenture.aris.core.support.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUtils {

	private static DriverAdapterCPDS driverAdapterCPDS;
	
    public static void execute(DatabaseOperation operation, String xlsPath, IDataTypeFactory factory, String schema)
            throws DatabaseUnitException, IOException, SQLException {
        
        IDatabaseConnection dbConn = null;
        Connection conn = driverAdapterCPDS.getPooledConnection().getConnection();
        if (schema == null) {
            dbConn = new DatabaseConnection(conn);
        } else {
            dbConn = new DatabaseConnection(conn, schema);
        }
        
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        IDataSet data = new XlsDataSet(loader.getResourceAsStream(xlsPath));
        
        if (factory != null) {
            dbConn.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, factory);
        }
        
        operation.execute(dbConn, data);
    }
    
    public static void execute(DatabaseOperation operation, String xlsPath, String schema)
        throws DatabaseUnitException, IOException, SQLException {
        execute(operation, xlsPath, null, schema);
    }
    
    public static void execute(DatabaseOperation operation, String xlsPath, IDataTypeFactory factory)
            throws DatabaseUnitException, IOException, SQLException {
            execute(operation, xlsPath, factory, null);
    }
    
    public static void execute(DatabaseOperation operation, String xlsPath)
            throws DatabaseUnitException, IOException, SQLException {
            execute(operation, xlsPath, null, null);
    }
    
    @Autowired
    public void setDriverAdapterCPDS(@Qualifier("dsAdapter") DriverAdapterCPDS driverAdapterCPDS) {
        DatabaseUtils.driverAdapterCPDS = driverAdapterCPDS;
    }
}
