package org.jason.fgedge.config;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.IPasswordCallback;

/**
 * TWX extended configuration - thingname, caltrops operational info.
 * 
 * @author jason
 *
 */
public class EdgeConfig extends ClientConfigurator {
	
	//toString append superclass config
	//+ ", [" + super.toString() + "]]";
	
	private String thingName;
	
	//fields to affect network comms
	private String caltropsHost;
	private int caltropsPort;
	private String caltropsProxyHost;
	private int caltropsProxyPort;
	private String caltropsProxyUser;
	private IPasswordCallback caltropsProxyPassCallback;

	private final static int UNCONFIG_PORT = -1;
	
	public EdgeConfig() {
		super();
		
		caltropsHost = null;
		caltropsPort = UNCONFIG_PORT;
		caltropsProxyHost = null;
		caltropsProxyPort = UNCONFIG_PORT;
		caltropsProxyUser = null;
		caltropsProxyPassCallback = null;
	}

	public String getCaltropsHost() {
		return caltropsHost;
	}

	public void setCaltropsHost(String caltropsHost) {
		this.caltropsHost = caltropsHost;
	}

	public int getCaltropsPort() {
		return caltropsPort;
	}

	public void setCaltropsPort(int caltropsPort) {
		this.caltropsPort = caltropsPort;
	}

	public String getCaltropsProxyHost() {
		return caltropsProxyHost;
	}

	public void setCaltropsProxyHost(String caltropsProxyHost) {
		this.caltropsProxyHost = caltropsProxyHost;
	}

	public int getCaltropsProxyPort() {
		return caltropsProxyPort;
	}

	public void setCaltropsProxyPort(int caltropsProxyPort) {
		this.caltropsProxyPort = caltropsProxyPort;
	}

	public String getCaltropsProxyUser() {
		return caltropsProxyUser;
	}

	public void setCaltropsProxyUser(String caltropsProxyUser) {
		this.caltropsProxyUser = caltropsProxyUser;
	}

	public IPasswordCallback getCaltropsProxyPassCallback() {
		return caltropsProxyPassCallback;
	}

	public void setCaltropsProxyPassCallback(IPasswordCallback caltropsProxyCallback) {
		this.caltropsProxyPassCallback = caltropsProxyCallback;
	}
	
	public boolean isCaltropsEnabled() {
		return (
			caltropsHost != null &&
			caltropsPort != UNCONFIG_PORT &&
			caltropsProxyHost != null &&
			caltropsProxyPort != UNCONFIG_PORT
		);
	}
	
	public String getThingName() {
		return thingName;
	}

	public void setThingName(String thingName) {
		this.thingName = thingName;
	}

	@Override
	public String toString() {
		return "EdgeConfig [thingName=" + thingName + ", caltropsHost=" + caltropsHost + ", caltropsPort="
				+ caltropsPort + ", caltropsProxyHost=" + caltropsProxyHost + ", caltropsProxyPort=" + caltropsProxyPort
				+ ", caltropsProxyUser=" + caltropsProxyUser + ", caltropsProxyPassCallback=" + caltropsProxyPassCallback  
				+ ", [" + super.toString() + "]]";
	}
}
