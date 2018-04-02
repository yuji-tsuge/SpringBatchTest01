package com.accenture.aris.core.support;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServiceResult<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private T result;
	
	private String errorCode;
	
	private Map<String, Object> attributes;
	
	public ServiceResult() {
	}
	
	public ServiceResult(T result) {
		this.result = result;
	}
	
	public T getResult() {
		return result;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public boolean hasError() {
		return errorCode != null ? true : false;
	}
	
	public Object getAttribute(String name) {
		return this.attributes == null ? null : this.attributes.get(name);
	}
	
	public void setAttribute(String name, Object value) {
		if (this.attributes == null) {
			Map<String, Object> attributes = Collections.synchronizedMap(new HashMap<String, Object>());
			this.attributes = attributes;
		}
		this.attributes.put(name, value);
	}
	
	public boolean hasAttribute(String name) {
		return this.attributes == null ? false : this.attributes.containsKey(name);
	}
}
