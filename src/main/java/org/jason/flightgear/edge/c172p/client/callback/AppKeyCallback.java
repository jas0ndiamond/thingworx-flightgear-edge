package org.jason.flightgear.edge.c172p.client.callback;

import com.thingworx.communications.client.IPasswordCallback;

/**
 * Not a safe implementation of a secrets callback. Do not use for reference.
 * 
 * @author jason
 *
 */
public class AppKeyCallback implements IPasswordCallback {

	private String appKey = null;
	
	public AppKeyCallback(String appKey) {
		this.appKey = appKey;
	}
	
	@Override
	public char[] getSecret() {
		return this.appKey.toCharArray();
	}

}
