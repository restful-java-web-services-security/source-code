package com.howtodoinjava.demo.rest.service;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import com.howtodoinjava.demo.rest.data.CompactDiscDatabase;

@Path("/compactDisc-service")
public class CompactDiscService {
	@PermitAll
	@GET
	@Path("/compactDiscs/{name}")
	public Response getCompactDiscByName(@PathParam("name") String name, @Context Request request) {
		Response.ResponseBuilder rb = Response.ok(CompactDiscDatabase.getCompactDiscByName(name));
		return rb.build();
	}

	@RolesAllowed("ADMIN")
	@PUT
	@Path("/compactDiscs/{name}")
	public Response updatePriceByDiscName(@PathParam("name") String name) {
		// Update the User resource
		CompactDiscDatabase.updateCompactDisc(name, 10.5);
		return Response.status(200).build();
	}
}
