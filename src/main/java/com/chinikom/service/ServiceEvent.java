package com.chinikom.service;

import org.springframework.context.ApplicationEvent;

import com.chinikom.domain.AddressDetails;

/**
 * This is an optional class used in publishing application events. This can be
 * used to inject events into the Spring Boot audit management endpoint.
 */
public class ServiceEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	AddressDetails eventAddress;
	String eventType;

	public ServiceEvent(Object source) {
		super(source);
	}

	@Override
	public String toString() {
		return "My CustomerService Event";
	}

	public AddressDetails getEventAddress() {
		return eventAddress;
	}

	public void setEventAddress(AddressDetails eventAddress) {
		this.eventAddress = eventAddress;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public ServiceEvent(Object source, AddressDetails eventAddress,
			String eventType) {
		super(source);
		this.eventAddress = eventAddress;
		this.eventType = eventType;
	}

}
