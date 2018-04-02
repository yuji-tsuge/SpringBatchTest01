package com.accenture.aris.core.jdbc;

import java.util.Properties;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.accenture.aris.tools.encrypt.EncryptTool;

public class DecryptDriverManagerDataSource extends DriverManagerDataSource {

	private EncryptTool encryptTool;
	
	public DecryptDriverManagerDataSource() {
	}

	public DecryptDriverManagerDataSource(String url) {
		super(url);
	}

	public DecryptDriverManagerDataSource(String url, Properties conProps) {
		super(url, conProps);
	}

	public DecryptDriverManagerDataSource(String url, String username,
			String password) {
		super(url, username, password);
	}

	public void setEncryptTool(EncryptTool encryptTool) {
		this.encryptTool = encryptTool;
	}

	@Override
	public void setPassword(String password) {
		if (this.encryptTool == null) {
			this.encryptTool = new EncryptTool();
		}
		super.setPassword(encryptTool.decrypt(password));
	}
}
