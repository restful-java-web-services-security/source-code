package org.jboss.resteasy.tests.smime;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.security.smime.EnvelopedOutput;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
@Path("/smime")
public class SMIMEResource {

	private PrivateKey privateKey;
	private X509Certificate certificate;

	public SMIMEResource(PrivateKey privateKey, X509Certificate certificate) {
		this.privateKey = privateKey;
		this.certificate = certificate;
	}

	@GET
	public EnvelopedOutput getEncrypted() {
		System.out.println("HERE!!!!!");
		EnvelopedOutput output = new EnvelopedOutput("<a><b>hola</b></a>",
				MediaType.APPLICATION_XML_TYPE);
		output.setCertificate(certificate);
		return output;
	}

}
