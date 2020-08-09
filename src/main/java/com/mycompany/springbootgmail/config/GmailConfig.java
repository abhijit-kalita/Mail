package com.mycompany.springbootgmail.config;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.mycompany.springbootgmail.exception.GmailConfigException;
import com.mycompany.springbootgmail.properties.GmailProperties;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(GmailProperties.class)
public class GmailConfig {

    private final GmailProperties gmailProperties=null;
    
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_LABELS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    @Bean
    Gmail gmail() throws IOException {
        return new Gmail.Builder(httpTransport(), JacksonFactory.getDefaultInstance(), getCredentials(httpTransport()))
                .setApplicationName(gmailProperties.getApplicationName()).build();
    }

//    private Credential getCredentials() {
//        BasicAuthentication auth = new BasicAuthentication(gmailProperties.getClientId(),
//                gmailProperties.getClientSecret());
//
//        return new Credential.Builder(BearerToken.authorizationHeaderAccessMethod())
//                .setJsonFactory(JacksonFactory.getDefaultInstance())
//                .setTransport(httpTransport())
//                .setClientAuthentication(auth)
//                .setTokenServerEncodedUrl(gmailProperties.getTokenServerUrl())
//                .build()
//                .setRefreshToken(gmailProperties.getRefreshToken());
//    }
    
    
    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GmailConfig.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    	//InputStream in =null;
    	if (in == null) {
            throw new FileNotFoundException("Resource not found: " + "CREDENTIALS_FILE_PATH");
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load( JacksonFactory.getDefaultInstance(), new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT,  JacksonFactory.getDefaultInstance(), clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    @Bean
    NetHttpTransport httpTransport() {
        try {
            return GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            throw new GmailConfigException(e);
        }
    }

    public static final String USER_ID_ME = "me";

}
