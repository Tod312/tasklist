package com.example.tasklist.service.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

	private String secret;
	private long access;
	private long refresh;
	
	
	public JwtProperties() {}

	public JwtProperties(String secret, long access, long refresh) {
		this.secret = secret;
		this.access = access;
		this.refresh = refresh;
	}

	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public long getAccess() {
		return access;
	}
	public void setAccess(long access) {
		this.access = access;
	}
	public long getRefresh() {
		return refresh;
	}
	public void setRefresh(long refresh) {
		this.refresh = refresh;
	}
	
}
