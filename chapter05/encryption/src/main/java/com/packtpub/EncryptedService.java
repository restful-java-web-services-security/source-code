package com.packtpub;

import java.io.InputStream;
import java.security.cert.X509Certificate;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.security.PemUtils;
import org.jboss.resteasy.security.smime.EnvelopedOutput;

@Path("/encrypted")
public class EncryptedService {

	@GET
	public EnvelopedOutput gretting() throws Exception {
		InputStream certPem = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("democert.pem");
		X509Certificate myX509Certificate = PemUtils.decodeCertificate(certPem);
		EnvelopedOutput output = new EnvelopedOutput("Hello world",
				MediaType.TEXT_PLAIN);
		output.setCertificate(myX509Certificate);
		return output;
	}

}