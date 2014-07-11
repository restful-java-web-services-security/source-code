package com.packtpub.resteasy.example.oauth;

import java.io.IOException;
import java.security.KeyStore;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.skeleton.key.servlet.ServletOAuthClient;
import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.InternalServerErrorException;

/**
 * 
 * @author Andres
 * 
 */
public class CompactDiscsDatabaseClient {
	public static void redirect(HttpServletRequest request,
			HttpServletResponse response) {
		ServletOAuthClient oAuthClient = (ServletOAuthClient) request
				.getServletContext().getAttribute(
						ServletOAuthClient.class.getName());
		try {
			oAuthClient.redirectRelative("discsList.jsp", request, response);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<String> getCompactDiscs(HttpServletRequest request) {

		ServletOAuthClient oAuthClient = (ServletOAuthClient) request
				.getServletContext().getAttribute(
						ServletOAuthClient.class.getName());
		KeyStore truststore = oAuthClient.getTruststore();
		ResteasyClient rsClient = new ResteasyClientBuilder()
				.disableTrustManager().trustStore(truststore).build();

		String urlDiscs = "https://localhost:8443/discstore/discs";
		try {
			String bearerToken = "Bearer "
					+ oAuthClient.getBearerToken(request);
			Response response = rsClient.target(urlDiscs).request()
					.header(HttpHeaders.AUTHORIZATION, bearerToken).get();
			return response.readEntity(new GenericType<List<String>>() {
			});
		} catch (BadRequestException bre) {
			bre.printStackTrace();
		} catch (InternalServerErrorException isee) {
			isee.printStackTrace();
		} finally {
			rsClient.close();
		}
		return null;
	}
}
