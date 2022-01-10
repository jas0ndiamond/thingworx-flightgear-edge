package org.jason.fgedge.sshd;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.AsyncAuthException;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.command.CommandFactory;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.shell.ProcessShellFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Launch an SSH server with set credentials and home directory. 
 *
 */
public class EdgeSSHDServer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(EdgeSSHDServer.class);

	private final static String DEFAULT_USER = "edge";
	private final static String DEFAULT_PASS = "twxedge";
	private final static String DEFAULT_HOME_DIR = "/tmp/thingworx-flightgear-edge";
	private final static String DEFAULT_BIND_ADDR = "0.0.0.0";
	private final static int DEFAULT_PORT = 9999;
	
	private String bindAddress;
	private int port;
	private String edgeUser;
	private String edgePass;
	private String homeDir;
	private boolean isRunning;
	private boolean isShutdown;
	
	public EdgeSSHDServer() {
		this(DEFAULT_USER, DEFAULT_PASS);
	}
	
	public EdgeSSHDServer(String user, String pass) {
		this.edgeUser = user;
		this.edgePass = pass;
		
		setBindAddress(DEFAULT_BIND_ADDR);
		setPort(DEFAULT_PORT);
		setHomeDir(DEFAULT_HOME_DIR);
	}
	
	public void setBindAddress(String address) {
		this.bindAddress = address;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public void setHomeDir(String homeDir) {
		this.homeDir = homeDir;
	}
		
	private SshServer buildSshServer() {
		SshServer sshdServer = SshServer.setUpDefaultServer();
		
		sshdServer.setHost(bindAddress);
		sshdServer.setPort(port);

		sshdServer.setPasswordAuthenticator(new PasswordAuthenticator() {
			@Override
			public boolean authenticate(String username, String password, ServerSession session)
					throws PasswordChangeRequiredException, AsyncAuthException {
				return edgeUser.equals(username) && edgePass.equals(password);
			}
		});
		sshdServer.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());
		
		//seems to use user.dir instead of HOME_DIR upon login
		Path homePath = Paths.get(homeDir);
		VirtualFileSystemFactory vfs = new VirtualFileSystemFactory(homePath);
		vfs.setUserHomeDir(edgeUser, homePath);
		
		sshdServer.setFileSystemFactory(vfs);
		
		//will see remote echo if you manually ssh into this
		sshdServer.setShellFactory(new ProcessShellFactory("bash", "/usr/bin/bash", "--norc", "-i", "-l" ));
		
		sshdServer.setCommandFactory(new CommandFactory() {

			@Override
			public Command createCommand(ChannelSession channel, String command) throws IOException {
				
				String[] commandFields = command.split(" ");
				
	            return new ProcessShellFactory( commandFields[0], commandFields ).createShell(channel);
			}
			
		});
		
		return sshdServer;
	}

	@Override
	public void run() {
		//start the sshd server
		SshServer sshdServer = buildSshServer();
		
		try {
			sshdServer.start();
			isRunning = true;
			isShutdown = false;
		} catch (IOException e) {
			LOGGER.error("Failed to launch SSHD server", e);
		}
			
		//sleep until shutdown is invoked
		while(isRunning) {
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				LOGGER.warn("SSHD server run time sleep interrupted", e);
			}
		}
		
		//exit the sshd server
		
		if (sshdServer != null) {
			try {
				sshdServer.close();
			} catch (IOException e) {
				LOGGER.error("Exception shutting down SSHD server", e);
			}
		}
		
		isShutdown = true;
	}
	
	public void shutdown() {
		isRunning = false;
		
		while(!isShutdown) {
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				LOGGER.warn("SSHD server shut down sleep interrupted", e);
			}
		}
	}
}
