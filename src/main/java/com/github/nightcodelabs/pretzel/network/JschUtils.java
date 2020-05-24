package com.github.nightcodelabs.pretzel.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class JschUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(JschUtils.class);
	
	
	public Session createNewSession(String user, String host, int port) {
		
		JSch jsch = new JSch();
		Session session = null;
		
		try {
			session = jsch.getSession(user, host, port);
		} catch (JSchException e) {
			logger.error("Something went wrong creating a new remote session. Info: "+ e.getMessage());
		}
		
		return session;
	}
	
	public Session setUpUIBasicCredentials(Session session, String user, String password, String passPhrase) {		
		UserInfoImpl ui = new UserInfoImpl(user, passPhrase);		
		session.setUserInfo(ui);
		session.setPassword(password);
		return session;
	}
	
	/*public Session connectSession() {
		Session session = this.createNewSession(user, host, port);
		this.setUpUIBasicCredentials(session, user, password, passPhrase);
		session.connect();
		return session;
	}*/
	
	 
	
	
	
	

}
