package org.jason.fgedge.exception;

public class ConfigException extends Exception {
	
	public ConfigException() {
		super();
	}

	public ConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigException(Throwable cause) {
		super(cause);
	}

	public ConfigException(String message) {
		super(message);
	}

	private static final long serialVersionUID = -3685107604144790111L;

}
