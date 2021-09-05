package org.jason.flightgear.c172p.client.callback;

import com.thingworx.communications.client.IPasswordCallback;

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
