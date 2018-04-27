package com.randr.webdw.startup;

import java.sql.Timestamp;

public class RegisteredUser {

	protected String name;
	protected String email;
	protected String sendEmails;
	protected String country;
	protected String portalRegistered;
	protected Timestamp dateRegistered;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Timestamp getDateRegistered() {
		return dateRegistered;
	}
	public void setDateRegistered(Timestamp dateRegistered) {
		this.dateRegistered = dateRegistered;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPortalRegistered() {
		return portalRegistered;
	}
	public void setPortalRegistered(String portalRegistered) {
		this.portalRegistered = portalRegistered;
	}
	public String getSendEmails() {
		return sendEmails;
	}
	public void setSendEmails(String sendEmails) {
		this.sendEmails = sendEmails;
	}
}
