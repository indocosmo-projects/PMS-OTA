package com.indocosmo.pms.web.common.setttings;

import java.util.Date;

public class commonSettings {
	public static double roundrule;
	public static boolean isServiceChargeApplicable;
	public static boolean isServiceTaxIncluded;
	public static int serviceTaxId;
	public static int decimalPlaces;
	public static boolean mailNotifyReservation;
	public static boolean mailNotifyCutoffDate;
	public static boolean mailNotifyCancellation;
	public static boolean mailNotifyConfirmation;
	public static boolean mailNotifyNoshow;
	public static boolean mailNotifyCheckIn;
	public static boolean mailNotifyCheckOut;
	public static boolean smsNotifyReservation;
	public static boolean smsNotifyCutoffDate;
	public static boolean smsNotifyCancellation;
	public static boolean smsNotifyConfirmation;
	public static boolean smsNotifyNoshow;
	public static boolean smsNotifyCheckIn;
	public static boolean smsNotifyCheckOut;
	public static Date hotelDate;
	public static String loginId;
	public static String currencySymbol;
	// public static SystemSettings systmstngs;

	public static String getLoginId() {
		return loginId;
	}

	public static void setLoginId(String loginId) {
		commonSettings.loginId = loginId;
	}

	public static double getRoundrule() {
		return roundrule;
	}

	public static void setRoundrule(double roundrule) {
		commonSettings.roundrule = roundrule;
	}

	public static boolean isServiceChargeApplicable() {
		return isServiceChargeApplicable;
	}

	public static void setServiceChargeApplicable(boolean isServiceChargeApplicable) {
		commonSettings.isServiceChargeApplicable = isServiceChargeApplicable;
	}

	public static boolean isServiceTaxIncluded() {
		return isServiceTaxIncluded;
	}

	public static void setServiceTaxIncluded(boolean isServiceTaxIncluded) {
		commonSettings.isServiceTaxIncluded = isServiceTaxIncluded;
	}

	public static int getServiceTaxId() {
		return serviceTaxId;
	}

	public static void setServiceTaxId(int serviceTaxId) {
		commonSettings.serviceTaxId = serviceTaxId;
	}

	public static int getDecimalPlaces() {
		return decimalPlaces;
	}

	public static void setDecimalPlaces(int decimalPlaces) {
		commonSettings.decimalPlaces = decimalPlaces;
	}

	public static boolean isMailNotifyReservation() {
		return mailNotifyReservation;
	}

	public static void setMailNotifyReservation(boolean mailNotifyReservation) {
		commonSettings.mailNotifyReservation = mailNotifyReservation;
	}

	public static boolean isMailNotifyCutoffDate() {
		return mailNotifyCutoffDate;
	}

	public static void setMailNotifyCutoffDate(boolean mailNotifyCutoffDate) {
		commonSettings.mailNotifyCutoffDate = mailNotifyCutoffDate;
	}

	public static boolean isMailNotifyCancellation() {
		return mailNotifyCancellation;
	}

	public static void setMailNotifyCancellation(boolean mailNotifyCancellation) {
		commonSettings.mailNotifyCancellation = mailNotifyCancellation;
	}

	public static boolean isMailNotifyConfirmation() {
		return mailNotifyConfirmation;
	}

	public static void setMailNotifyConfirmation(boolean mailNotifyConfirmation) {
		commonSettings.mailNotifyConfirmation = mailNotifyConfirmation;
	}

	public static boolean isMailNotifyNoshow() {
		return mailNotifyNoshow;
	}

	public static void setMailNotifyNoshow(boolean mailNotifyNoshow) {
		commonSettings.mailNotifyNoshow = mailNotifyNoshow;
	}

	public static boolean isMailNotifyCheckIn() {
		return mailNotifyCheckIn;
	}

	public static void setMailNotifyCheckIn(boolean mailNotifyCheckIn) {
		commonSettings.mailNotifyCheckIn = mailNotifyCheckIn;
	}

	public static boolean isMailNotifyCheckOut() {
		return mailNotifyCheckOut;
	}

	public static void setMailNotifyCheckOut(boolean mailNotifyCheckOut) {
		commonSettings.mailNotifyCheckOut = mailNotifyCheckOut;
	}

	public static boolean isSmsNotifyReservation() {
		return smsNotifyReservation;
	}

	public static void setSmsNotifyReservation(boolean smsNotifyReservation) {
		commonSettings.smsNotifyReservation = smsNotifyReservation;
	}

	public static boolean isSmsNotifyCutoffDate() {
		return smsNotifyCutoffDate;
	}

	public static void setSmsNotifyCutoffDate(boolean smsNotifyCutoffDate) {
		commonSettings.smsNotifyCutoffDate = smsNotifyCutoffDate;
	}

	public static boolean isSmsNotifyCancellation() {
		return smsNotifyCancellation;
	}

	public static void setSmsNotifyCancellation(boolean smsNotifyCancellation) {
		commonSettings.smsNotifyCancellation = smsNotifyCancellation;
	}

	public static boolean isSmsNotifyConfirmation() {
		return smsNotifyConfirmation;
	}

	public static void setSmsNotifyConfirmation(boolean smsNotifyConfirmation) {
		commonSettings.smsNotifyConfirmation = smsNotifyConfirmation;
	}

	public static boolean isSmsNotifyNoshow() {
		return smsNotifyNoshow;
	}

	public static void setSmsNotifyNoshow(boolean smsNotifyNoshow) {
		commonSettings.smsNotifyNoshow = smsNotifyNoshow;
	}

	public static boolean isSmsNotifyCheckIn() {
		return smsNotifyCheckIn;
	}

	public static void setSmsNotifyCheckIn(boolean smsNotifyCheckIn) {
		commonSettings.smsNotifyCheckIn = smsNotifyCheckIn;
	}

	public static boolean isSmsNotifyCheckOut() {
		return smsNotifyCheckOut;
	}

	public static void setSmsNotifyCheckOut(boolean smsNotifyCheckOut) {
		commonSettings.smsNotifyCheckOut = smsNotifyCheckOut;
	}

	public static Date getHotelDate() {
		return hotelDate;
	}

	public static void setHotelDate(Date hotelDate) {
		commonSettings.hotelDate = hotelDate;
	}

	public static String getCurrencySymbol() {
		return currencySymbol;
	}

	public static void setCurrencySymbol(String currencySymbol) {
		commonSettings.currencySymbol = currencySymbol;
	}

}
