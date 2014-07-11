package com.packtpub.resteasy.example.oauth;

import org.jboss.resteasy.skeleton.key.EnvUtil;
import org.jboss.resteasy.skeleton.key.servlet.ServletOAuthClient;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * 
 * @author Andres
 * 
 */
public class Loader implements ServletContextListener {

	private ServletOAuthClient oauthClient;

	private static KeyStore loadKeyStore(String filename, String password) throws Exception {
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		File keyStoreFile = new File(filename);
		FileInputStream keyStoreStream = new FileInputStream(keyStoreFile);
		keyStore.load(keyStoreStream, password.toCharArray());
		keyStoreStream.close();
		return keyStore;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String truststoreKSPath = "${jboss.server.config.dir}/client-truststore.ts";
		String truststoreKSPassword = "changeit";
		truststoreKSPath = EnvUtil.replace(truststoreKSPath);
		try {
			KeyStore truststoreKS = loadKeyStore(truststoreKSPath, truststoreKSPassword);
			oauthClient = new ServletOAuthClient();
			oauthClient.setTruststore(truststoreKS);
			oauthClient.setClientId("third-party");
			oauthClient.setPassword("changeit");
			oauthClient.setAuthUrl("https://localhost:8443/oauth-server/login.jsp");
			oauthClient
					.setCodeUrl("https://localhost:8443/oauth-server/j_oauth_resolve_access_code");
			oauthClient.start();
			sce.getServletContext().setAttribute(ServletOAuthClient.class.getName(), oauthClient);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		oauthClient.stop();
	}
}
