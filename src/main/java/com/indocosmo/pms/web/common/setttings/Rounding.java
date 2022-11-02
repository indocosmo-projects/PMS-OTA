package com.indocosmo.pms.web.common.setttings;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Rounding {
	public static double nRound(double value, double nearest) {
		double result = roundTo(value / nearest, 0) * nearest;
		NumberFormat formatter = new DecimalFormat("#0.00");
		return Double.parseDouble(formatter.format(result));

	}

	public static double nRound(double value) {
		double nearest = commonSettings.getRoundrule();
		return nRound(value, nearest);
	}

	public static double roundTo(double value, int prec) {
		String decimalFormat = getDecimalFormatString(prec);
		return Double.valueOf(formatNumber(value, decimalFormat));
	}

	private static String getDecimalFormatString(int prec) {
		String actualPrec = String.valueOf(prec + 2);
		String decimalFormat = String.format("%-" + actualPrec + "s", "0.").replace(' ', '0');
		return decimalFormat;
	}

	public static String formatNumber(String number, String format) {
		DecimalFormat mNumberFormat = new DecimalFormat(format);
		String formatValue = mNumberFormat.format(Double.parseDouble(number));
		return formatValue;
	}

	public static String formatNumber(double number, String format) {
		DecimalFormat mNumberFormat = new DecimalFormat(format);
		String formatValue = mNumberFormat.format(number);
		return formatValue;
	}

	public static String roundToTwo(double value) {
		NumberFormat formatter = new DecimalFormat("0.00");
		return formatter.format(value);

	}

	public static double roundOff(BigDecimal d, int scale, boolean roundUp) {
		int mode = (roundUp) ? BigDecimal.ROUND_HALF_UP : BigDecimal.ROUND_HALF_DOWN;
		return d.setScale(scale, mode).doubleValue();
	}

}
