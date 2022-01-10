package org.jason.fgedge.callback;

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
	
	/**
	 *	Seriously do not use this for reference.
	 */
	@Override
	public char[] getSecret() {
		return this.appKey.toCharArray();
	}

}
