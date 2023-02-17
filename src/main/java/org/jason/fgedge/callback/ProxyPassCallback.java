package org.jason.fgedge.callback;

import com.thingworx.communications.client.IPasswordCallback;

/**
 * Not a safe implementation of a secrets callback. Do not use for reference.
 * 
 * @author jason
 *
 */
public class ProxyPassCallback implements IPasswordCallback {

	private String password = null;
	
	public ProxyPassCallback(String password) {
		this.password = password;
	}
	
	/**
	 *	Seriously do not use this for reference.
	 */
	@Override
	public char[] getSecret() {
		return this.password.toCharArray();
	}

}
