package com.packtpub.resteasy.example.oauth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
@Path("discs")
public class CompactDiscService {
	@GET
	@Produces("application/json")
	public List<String> getCompactDiscs() {
		ArrayList<String> compactDiscList = new ArrayList<String>();
		compactDiscList.add("The Ramones");
		compactDiscList.add("The Clash");
		compactDiscList.add("Nirvana");
		return compactDiscList;
	}
}
