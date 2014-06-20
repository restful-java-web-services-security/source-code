package com.howtodoinjava.demo.rest.data;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.howtodoinjava.demo.rest.model.CompactDisc;

public class CompactDiscDatabase {
	public static HashMap<String, CompactDisc> compactDiscs = new HashMap<String, CompactDisc>();

	static {
		CompactDisc ramonesCD = new CompactDisc();
		ramonesCD.setDiscName("Ramones Anthology");
		ramonesCD.setBandName("The Ramones");
		ramonesCD.setPrice(15.0);

		Calendar calendar = Calendar.getInstance();
		calendar.set(1980, 10, 22);
		Date realeaseDate = calendar.getTime();
		ramonesCD.setReleaseDate(realeaseDate);
		compactDiscs.put("Ramones Anthology", ramonesCD);

	}

	public static CompactDisc getCompactDiscByName(String name) {
		return compactDiscs.get(name);
	}

	public static void updateCompactDisc(String name, double newPrice) {
		CompactDisc cd = compactDiscs.get(name);
		cd.setPrice(newPrice);
	}
}
