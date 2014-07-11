package com.packtpub.resteasy.example.oauth;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.skeleton.key.EnvUtil;
import org.jboss.resteasy.skeleton.key.representations.AccessTokenResponse;

public class OauthClientTest {

	public static void main(String[] args) throws Exception {

		String truststorePath = "C:/Users/Andres/jboss/2do_jboss/jboss-as-7.1.1.Final/standalone/configuration/client-truststore.ts";
		String truststorePassword = "changeit";
		truststorePath = EnvUtil.replace(truststorePath);

		KeyStore truststore = loadKeyStore(truststorePath, truststorePassword);

		ResteasyClient client = new ResteasyClientBuilder()
				.disableTrustManager().trustStore(truststore).build();

		Form form = new Form().param("grant_type", "client_credentials");
		ResteasyWebTarget target = client
				.target("https://localhost:8443/oauth-server/j_oauth_token_grant");
		target.register(new BasicAuthentication("andres", "andres"));

		AccessTokenResponse tokenResponse = target.request().post(
				Entity.form(form), AccessTokenResponse.class);
		Response response = client
				.target("https://localhost:8443/discstore/discs")
				.request()
				.header(HttpHeaders.AUTHORIZATION,
						"Bearer " + tokenResponse.getToken()).get();
		try {
			String xml = response.readEntity(String.class);
			System.out.println(xml);
		} finally {
			client.close();
		}

	}

	private static KeyStore loadKeyStore(String filename, String password)
			throws Exception {
		KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		File truststoreFile = new File(filename);
		FileInputStream trustStream = new FileInputStream(truststoreFile);
		trustStore.load(trustStream, password.toCharArray());
		trustStream.close();
		return trustStore;
	}

}
