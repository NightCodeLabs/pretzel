package com.github.cucumberlocust4j.pretzel.network;

import com.jcraft.jsch.UserInfo;

public class UserInfoImpl implements UserInfo {
	
	private String password;
    private String passPhrase;
    
    public UserInfoImpl (String password, String passPhrase) {
        this.password = password;
        this.passPhrase = passPhrase;
    }

	@Override
	public String getPassphrase() {
		return this.passPhrase;
	}

	@Override
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public boolean promptPassphrase(String message) {
        return true;
    }
	
	@Override
    public boolean promptPassword(String message) {
        return false;
    }
	
	@Override
    public boolean promptYesNo(String message) {
        return true;
    }
	
	@Override
    public void showMessage(String message) {
        System.out.println("UserInfoImp.showMessage()");
    }
	
}
