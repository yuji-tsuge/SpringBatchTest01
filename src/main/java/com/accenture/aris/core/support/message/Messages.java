package com.accenture.aris.core.support.message;

public interface Messages {

	public String getMessage(String code);
	
	public String getMessage(String code, Object... args);
	
    public String getSimpleMessage(String code);
    
    public String getSimpleMessage(String code, Object... args);
    
}
