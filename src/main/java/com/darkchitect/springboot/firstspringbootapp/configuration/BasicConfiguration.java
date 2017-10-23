package com.darkchitect.springboot.firstspringbootapp.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("basic")
public class BasicConfiguration {

	private boolean value;
	private String message;
	private int number;
	
	public synchronized boolean isValue() {
		return value;
	}
	public synchronized void setValue(boolean value) {
		this.value = value;
	}
	public synchronized String getMessage() {
		return message;
	}
	public synchronized void setMessage(String message) {
		this.message = message;
	}
	public synchronized int getNumber() {
		return number;
	}
	public synchronized void setNumber(int number) {
		this.number = number;
	}
	
	
}
