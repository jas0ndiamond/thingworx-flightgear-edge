package org.jason.fgedge.config;

import org.jason.fgcontrol.aircraft.config.ConfigDirectives;

public interface TWXConfigDirectives {
	public static final String PLATFORM_HOST_DIRECTIVE = "platformHost";
	public static final String PLATFORM_PORT_DIRECTIVE = "platformPort";
	public static final String APPKEY_DIRECTIVE = "appkey";
	
	public static final String THINGNAME_DIRECTIVE = ConfigDirectives.AIRCRAFT_NAME_DIRECTIVE;
	
	public static final String PROXY_HOST_DIRECTIVE = "proxyHost";
	public static final String PROXY_PORT_DIRECTIVE = "proxyPort";
	public static final String PROXY_USER_DIRECTIVE = "proxyUser";
	public static final String PROXY_PASS_DIRECTIVE = "proxyPass";
	
	public static final String SSH_PORT_DIRECTIVE = "sshPort";
	public static final String SSH_USER_DIRECTIVE = "sshUser";
	public static final String SSH_PASS_DIRECTIVE = "sshPass";
	public static final String SSH_HOME_DIR_DIRECTIVE = "sshHomeDir";
}
