package com.packtpub.resteasy.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.security.doseta.Signed;
import org.jboss.resteasy.annotations.security.doseta.Verifications;
import org.jboss.resteasy.annotations.security.doseta.Verify;

@Path("/signed")
public class SignedService {

	@POST
	@Produces("text/plain")
	@Signed(selector = "demo", domain = "packtpub.com")
	public String sign(String input) {
		System.out.println("Aplyng signature " + input);
		return "signed " + input;
	}

	@POST
	@Path("ram")
	@Signed(selector = "demo", domain = "packtpub.com")
	@Consumes("text/plain")
	public String requestRam(int numberOfGB) {
		return numberOfGB + "-GB";
	}

	@Verifications({ 
			@Verify(identifierName = "s", identifierValue = "demo"),
			@Verify(identifierName = "d", identifierValue = "itpacktpub.com") })
	@POST
	@Path("verifier")
	@Produces("text/plain")
	public String processRequestRam(String input) {
		int numberOfGbRequested = Integer.valueOf(input.split("-")[0]);
		if (numberOfGbRequested > 4) {
			return "deny";
		} else {
			return "accepted";
		}
	}
}
