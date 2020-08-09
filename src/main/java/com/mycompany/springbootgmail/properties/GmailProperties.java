package com.mycompany.springbootgmail.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@ConfigurationProperties(prefix = "gmail")
public class GmailProperties {

    @NotBlank
    private String applicationName;

    @NotBlank
    private String tokenServerUrl;

    public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getTokenServerUrl() {
		return tokenServerUrl;
	}
	public void setTokenServerUrl(String tokenServerUrl) {
		this.tokenServerUrl = tokenServerUrl;
	}
	private String clientId;
    private String clientSecret;
    private String refreshToken;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
