package com.intune.exception;

public class DeviceNotFound extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DeviceNotFound(String msg) {
		super(msg);
	}
}