package com.accenture.aris.core.jdbc;

import org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS;

import com.accenture.aris.tools.encrypt.EncryptTool;

public class DecryptDriverAdapterCPDS extends DriverAdapterCPDS {

	private static final long serialVersionUID = 1L;

	private EncryptTool tool;
	
    public void setEncryptTool(EncryptTool tool) {
        this.tool = tool;
    }
    
	@Override
	public void setPassword(String pass) {
		if (this.tool == null) {
			this.tool = new EncryptTool();
		}
		super.setPassword(tool.decrypt(pass));
	}
}
