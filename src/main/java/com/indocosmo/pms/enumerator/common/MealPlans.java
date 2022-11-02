package com.indocosmo.pms.enumerator.common;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.indocosmo.pms.enumerator.AccMst;

public enum MealPlans {
	EP(1,"EP"), CP(2,"CP"), MAP(3,"MAP"), AP(4,"AP");

	private int planId;
	private String plan;

	private MealPlans(int planId,String name) {
		this.plan = name;
		this.planId=planId;
	}

	private static final Map<Integer, MealPlans> MENUPLANS = new HashMap<Integer, MealPlans>();

	static {
		
		for (MealPlans mealPlans : EnumSet.allOf(MealPlans.class))
			MENUPLANS.put(mealPlans.getPlanId(), mealPlans);
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	
	public int getPlanId() {
		return planId;
	}

	public static Map<Integer, MealPlans> getMenuplans() {
		return MENUPLANS;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}
	
}
