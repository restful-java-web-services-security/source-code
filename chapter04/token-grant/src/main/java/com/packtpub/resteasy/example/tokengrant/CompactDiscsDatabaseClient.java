package com.packtpub.resteasy.example.tokengrant;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.skeleton.key.representations.AccessTokenResponse;

/**
 * 
 * @author Andres
 * 
 */
public class CompactDiscsDatabaseClient {
	private static String urlDiscs = "https://localhost:8443/discstore/discs";
	private static String urlAuth = "https://localhost:8443/oauth-server/j_oauth_token_grant";

	public static List<String> getCompactDiscs() {

		ResteasyClient rsClient = new ResteasyClientBuilder().disableTrustManager().build();

		Form form = new Form().param("grant_type", "client_credentials");
		ResteasyWebTarget resourceTarget = rsClient.target(urlAuth);
		resourceTarget.register(new BasicAuthentication("andres", "andres"));
		AccessTokenResponse accessToken = resourceTarget.request().post(Entity.form(form),
				AccessTokenResponse.class);
		try {
			String bearerToken = "Bearer " + accessToken.getToken();
			Response response = rsClient.target(urlDiscs).request()
					.header(HttpHeaders.AUTHORIZATION, bearerToken).get();
			return response.readEntity(new GenericType<List<String>>() {
			});
		} finally {
			rsClient.close();
		}

	}
}
