package com.indocosmo.pms.util;

public class NumberToWordsConverter {

	private static String mNumberToConvert;;

	private static String[] units = { "", " One", " Two", " Three", " Four", " Five", " Six", " Seven", " Eight",
			" Nine" };
	private static String[] teen = { " Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen", " Sixteen",
			" Seventeen", " Eighteen", " Nineteen" };
	private static String[] tens = { " Twenty", " Thirty", " Fourty", " Fifty", " Sixty", " Seventy", " Eighty",
			" Ninety" };
	private static String[] maxs = { "", "", " Hundred", " Thousand", " Lakh", " Crore" };

	public NumberToWordsConverter() {
		mNumberToConvert = "";
	}

	public String convertToWords(long n) {
		mNumberToConvert = numToString(n);
		String converted = "";
		int pos = 1;
		boolean hun = false;
		while (mNumberToConvert.length() > 0) {
			if (pos == 1) // TENS AND UNIT POSITION
			{
				if (mNumberToConvert.length() >= 2) // 2DIGIT NUMBERS
				{
					String C = mNumberToConvert.substring(mNumberToConvert.length() - 2, mNumberToConvert.length());
					mNumberToConvert = mNumberToConvert.substring(0, mNumberToConvert.length() - 2);
					converted += digits(C);
				} else if (mNumberToConvert.length() == 1) // 1 DIGIT NUMBER
				{
					converted += digits(mNumberToConvert);
					mNumberToConvert = "";
				}
				pos++; // INCREASING POSITION COUNTER
			} else if (pos == 2) // HUNDRED POSITION
			{
				String C = mNumberToConvert.substring(mNumberToConvert.length() - 1, mNumberToConvert.length());
				mNumberToConvert = mNumberToConvert.substring(0, mNumberToConvert.length() - 1);
				if (converted.length() > 0 && digits(C) != "") {
					converted = (digits(C) + maxs[pos] + " and") + converted;
					hun = true;
				} else {
					if (digits(C) == "")
						;
					else
						converted = (digits(C) + maxs[pos]) + converted;
					hun = true;
				}
				pos++; // INCREASING POSITION COUNTER
			} else if (pos > 2) // REMAINING NUMBERS PAIRED BY TWO
			{
				if (mNumberToConvert.length() >= 2) // EXTRACT 2 DIGITS
				{
					String C = mNumberToConvert.substring(mNumberToConvert.length() - 2, mNumberToConvert.length());
					mNumberToConvert = mNumberToConvert.substring(0, mNumberToConvert.length() - 2);
					if (!hun && converted.length() > 0)
						converted = digits(C) + maxs[pos] + " and" + converted;
					else {
						if (digits(C) == "")
							;
						else
							converted = digits(C) + maxs[pos] + converted;
					}
				} else if (mNumberToConvert.length() == 1) // EXTRACT 1 DIGIT
				{
					if (!hun && converted.length() > 0)
						converted = digits(mNumberToConvert) + maxs[pos] + " and" + converted;
					else {
						if (digits(mNumberToConvert) == "")
							;
						else
							converted = digits(mNumberToConvert) + maxs[pos] + converted;
						mNumberToConvert = "";
					}
				}
				pos++; // INCREASING POSITION COUNTER
			}
		}
		return converted;
	}

	private String digits(String C) // TO RETURN SELECTED NUMBERS IN WORDS
	{
		String converted = "";
		for (int i = C.length() - 1; i >= 0; i--) {
			int ch = C.charAt(i) - 48;
			if (i == 0 && ch > 1 && C.length() > 1)
				converted = tens[ch - 2] + converted; // IF TENS DIGIT STARTS
														// WITH 2 OR MORE IT
														// FALLS UNDER TENS
			else if (i == 0 && ch == 1 && C.length() == 2) // IF TENS DIGIT
															// STARTS WITH 1 IT
															// FALLS UNDER TEENS
			{
				int sum = 0;
				for (int j = 0; j < 2; j++)
					sum = (sum * 10) + (C.charAt(j) - 48);
				return teen[sum - 10];
			} else {
				if (ch > 0)
					converted = units[ch] + converted;
			} // IF SINGLE DIGIT PROVIDED
		}
		return converted;
	}

	private String numToString(long n) // CONVERT THE NUMBER TO STRING
	{
		String num = "";
		while (n != 0) {
			num = ((char) ((n % 10) + 48)) + num;
			n /= 10;
		}
		return num;
	}

	public static String toWords(long number) {
		NumberToWordsConverter obj = new NumberToWordsConverter();
		return obj.convertToWords(number);

	}
}