package com.packtpub;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/services")
public class EncryptedApplication extends Application {

	private Set<Object> resources = new HashSet<Object>();

	public EncryptedApplication() throws Exception {
		resources.add(new EncryptedServiceTest());
	}

	@Override
	public Set<Object> getSingletons() {
		return resources;
	}
}
