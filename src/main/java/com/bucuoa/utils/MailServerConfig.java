package com.bucuoa.utils;

public class MailServerConfig {
	
	private boolean auth = true;
	private String smtphost;
	private String user;
	private String password;
	public boolean isAuth() {
		return auth;
	}
	public void setAuth(boolean auth) {
		this.auth = auth;
	}
	public String getSmtphost() {
		return smtphost;
	}
	public void setSmtphost(String smtphost) {
		this.smtphost = smtphost;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
