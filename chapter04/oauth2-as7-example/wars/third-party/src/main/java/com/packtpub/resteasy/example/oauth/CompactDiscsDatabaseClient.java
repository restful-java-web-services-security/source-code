package com.packtpub.resteasy.example.oauth;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.skeleton.key.servlet.ServletOAuthClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

/**
 * 
 * @author Andres
 * 
 */
public class CompactDiscsDatabaseClient {
	public static void redirect(HttpServletRequest request, HttpServletResponse response) {
		ServletOAuthClient oAuthClient = (ServletOAuthClient) request.getServletContext().getAttribute(
				ServletOAuthClient.class.getName());
		try {
			oAuthClient.redirectRelative("discList.jsp", request, response);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<String> getCompactDiscs(HttpServletRequest request) {

		ServletOAuthClient oAuthClient = (ServletOAuthClient) request.getServletContext().getAttribute(
				ServletOAuthClient.class.getName());
		ResteasyClient rsClient = new ResteasyClientBuilder().trustStore(oAuthClient.getTruststore())
				.hostnameVerification(ResteasyClientBuilder.HostnameVerificationPolicy.ANY).build();
		String urlDiscs = "https://localhost:8443/store/discs";
		try {
			String bearerToken = "Bearer " + oAuthClient.getBearerToken(request);
			Response response = rsClient.target(urlDiscs).request().header(HttpHeaders.AUTHORIZATION, bearerToken)
					.get();
			return response.readEntity(new GenericType<List<String>>() {
			});
		} finally {
			rsClient.close();
		}
	}
}
