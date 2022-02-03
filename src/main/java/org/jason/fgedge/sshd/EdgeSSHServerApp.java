package org.jason.fgedge.sshd;

/**
 * Quick client to test the embedded ssh server for edge aircraft.
 * 
 * Seems to hang eclipse so run it from the shell.
 * 
 * @author jason
 *
 */
public class EdgeSSHServerApp {
	public static void main(String[] args) {
		
		EdgeSSHDServer sshdServer = null;
		try {
			sshdServer = new EdgeSSHDServer();
		
			new Thread(sshdServer).start();
		
			Thread.sleep(60 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if(sshdServer != null) {
				sshdServer.shutdown();
			}
		}
	}
}
