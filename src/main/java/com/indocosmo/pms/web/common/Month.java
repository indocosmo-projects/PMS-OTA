package com.indocosmo.pms.web.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class Month {
	Map<Integer, String> daysOfMonths = new LinkedHashMap<Integer, String>();

	public Map<Integer, String> dates(String s) {
		if (s == "february") {
			for (int i = 1; i < 28; i++) {
				String daysToShow = String.valueOf(i);
				daysOfMonths.put(i, daysToShow);
			}

			daysOfMonths.put(28, "EOM");
		}

		return daysOfMonths;
	}

	public Map<Integer, String> month() {
		final Map<Integer, String> monthmap = new LinkedHashMap<Integer, String>();
		monthmap.put(4, "April");
		monthmap.put(5, "May");
		monthmap.put(6, "June");
		monthmap.put(7, "July");
		monthmap.put(8, "August");
		monthmap.put(9, "September");
		monthmap.put(10, "October");
		monthmap.put(11, "November");
		monthmap.put(12, "December");
		monthmap.put(1, "January");
		monthmap.put(2, "February");
		monthmap.put(3, "March");

		return monthmap;
	}
}