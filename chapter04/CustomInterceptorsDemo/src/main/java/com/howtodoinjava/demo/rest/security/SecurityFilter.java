package com.howtodoinjava.demo.rest.security;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.apache.commons.codec.binary.Base64;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;

/**
 * This interceptor verify the access permissions for a user based on username
 * and passowrd provided in request
 * */
@Provider
public class SecurityFilter implements javax.ws.rs.container.ContainerRequestFilter {

	private static final String ADMIN = "ADMIN";
	private static final String RESOURCE_METHOD_INVOKER = "org.jboss.resteasy.core.ResourceMethodInvoker";
	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401,
			new Headers<Object>());
	private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403,
			new Headers<Object>());

	@Override
	public void filter(ContainerRequestContext requestContext) {
		ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext
				.getProperty(RESOURCE_METHOD_INVOKER);
		Method method = methodInvoker.getMethod();
		// Access allowed for all
		if (!method.isAnnotationPresent(PermitAll.class)) {
			// Access denied for all
			if (method.isAnnotationPresent(DenyAll.class)) {
				requestContext.abortWith(ACCESS_FORBIDDEN);
				return;
			}

			// Get request headers
			final MultivaluedMap<String, String> headersMap = requestContext.getHeaders();

			// Fetch authorization header
			final List<String> authorizationList = headersMap.get(AUTHORIZATION_PROPERTY);

			// If no authorization information present; block access
			if (authorizationList == null || authorizationList.isEmpty()) {
				requestContext.abortWith(ACCESS_DENIED);
				return;
			}

			// Get encoded username and password
			final String encodedUserPassword = authorizationList.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

			// Decode username and password
			String usernameAndPassword = new String(Base64.decodeBase64(encodedUserPassword));

			// Split username and password tokens
			final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
			final String userName = tokenizer.nextToken();
			final String password = tokenizer.nextToken();

			// Verify user access
			if (method.isAnnotationPresent(RolesAllowed.class)) {
				RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
				Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

				// Is user valid?
				if (!isUserAllowed(userName, password, rolesSet)) {
					requestContext.abortWith(ACCESS_DENIED);
					return;
				}
			}
		}
	}

	private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet) {
		boolean isAllowed = false;

		if (rolesSet.contains(ADMIN)) {
			isAllowed = true;
		}
		return isAllowed;
	}

}
