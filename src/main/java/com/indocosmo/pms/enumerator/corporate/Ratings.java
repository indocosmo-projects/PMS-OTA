package com.indocosmo.pms.enumerator.corporate;

public enum Ratings {
	A("A"), B("B"), C("C"), D("D"), E("E");

	private String rating;

	private Ratings(String rating) {
		this.rating = rating;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
}
