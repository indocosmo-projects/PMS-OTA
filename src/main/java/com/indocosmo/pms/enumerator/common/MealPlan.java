package com.indocosmo.pms.enumerator.common;

import java.util.ArrayList;
import java.util.EnumSet;

public enum MealPlan {
	EP("EP"), CP("CP"), MAP("MAP"), AP("AP");

	private String plan;

	private MealPlan(String name) {
		this.plan = name;
	}

	private static final ArrayList<String> MEALPLANS = new ArrayList<String>();

	static {
		for (MealPlan mealPlans : EnumSet.allOf(MealPlan.class))
			MEALPLANS.add(mealPlans.getPlan());
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public static ArrayList<String> getMealplans() {
		return MEALPLANS;
	}
}