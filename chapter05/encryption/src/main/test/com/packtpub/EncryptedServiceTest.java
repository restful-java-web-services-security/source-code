package com.packtpub;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import junit.framework.Assert;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.security.PemUtils;
import org.jboss.resteasy.security.smime.EnvelopedInput;
import org.junit.Test;

public class EncryptedServiceTest {

	@Test
	public void testEncryptedGet() throws Exception {
		// LOADING THE CERTIFICATE
		X509Certificate myX509Certificate = 							PemUtils.decodeCertificate(
				Thread
				.currentThread().getContextClassLoader()
				.getResourceAsStream("democert.pem"));
		// LOADING THE KEY
		PrivateKey myPrivateKey = PemUtils.decodePrivateKey(Thread
				.currentThread().getContextClassLoader()
				.getResourceAsStream("demokey.pem"));
		// CREATING A CLIENT FOR THE WEB SERVICE
		Client client = new ResteasyClientBuilder().build();
		WebTarget target = client								.target(
      		"http://localhost:8080/encryption-1.0/services/encrypted"
		);
		// RETRIEVING THE RESULT OF METHOD EXECUTION
		EnvelopedInput<?> input = 	target.request().
						get(EnvelopedInput.class);
		Assert.assertEquals("Hello world",
				input.getEntity(String.class, 
				myPrivateKey, myX509Certificate));
		client.close();

	}

}