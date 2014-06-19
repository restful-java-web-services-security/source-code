package com.packtpub.resteasy.services.test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.security.doseta.DosetaKeyRepository;
import org.jboss.resteasy.security.doseta.Verification;
import org.jboss.resteasy.security.doseta.Verifier;
import org.junit.Test;

public class SignedServiceTest {

	@Test
	public void testVerification() {
		// Keys repository
		DosetaKeyRepository repository = new DosetaKeyRepository();
		repository.setKeyStorePath("demo.jks");
		repository.setKeyStorePassword("changeit");
		repository.start();
		// Building the client
		ResteasyClient client = new ResteasyClientBuilder().build();
		Verifier verifier = new Verifier();
		Verification verification = verifier.addNew();
		verification.setRepository(repository);
		WebTarget target = client
				.target("http://localhost:8080/signatures-1.0/signed");
		Invocation.Builder request = target.request();
		request.property(Verifier.class.getName(), verifier);
		// Invocation to Restful web service
		Response response = request.post(Entity.text("Rene"));
		// Status 200 OK
		Assert.assertEquals(200, response.getStatus());
		System.out.println(response.readEntity(String.class));
		response.close();
		client.close();
	}
}
