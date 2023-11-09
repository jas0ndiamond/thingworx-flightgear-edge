package org.jason.fgedge.config;

public interface TWXConfigDirectives {
	public static final String PLATFORM_HOST_DIRECTIVE = "platformHost";
	public static final String PLATFORM_PORT_DIRECTIVE = "platformPort";
	public static final String APPKEY_DIRECTIVE = "appkey";
	
	public static final String THINGNAME_DIRECTIVE = "thingName";

	//caltrops really only usable in a proxy environment with a twx platform
	public static final String CALTROPS_HOST_DIRECTIVE = "caltropsHost";
	public static final String CALTROPS_PORT_DIRECTIVE = "caltropsPort";
	public static final String CALTROPS_PROXY_HOST_DIRECTIVE = "caltropsProxyHost";
	public static final String CALTROPS_PROXY_PORT_DIRECTIVE = "caltropsProxyPort";
	public static final String CALTROPS_PROXY_USER_DIRECTIVE = "caltropsProxyUser";
	public static final String CALTROPS_PROXY_PASS_DIRECTIVE = "caltropsProxyPass";
}
