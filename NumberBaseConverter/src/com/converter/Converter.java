package com.converter;

import java.math.BigInteger;
import java.util.Scanner;

public class Converter {

	static Scanner scan;

	public static void beginConversion(Integer sourceBase, Integer targetBase) {

		scan = new Scanner(System.in);
		String conversionResult = "";
		boolean keepRunning = true;
		String decimal = "";
		String fraction = "";
		String value = "";
		do {
			System.out.println("Enter number in base " + sourceBase + " to convert to base " + targetBase
					+ " (To go back type /back)");
			value = scan.nextLine().toLowerCase().trim();

			if (value.equals("/back")) {
				keepRunning = false;
				break;
			}

			if (sourceBase == targetBase) {
				conversionResult = value;
			} else {
				if (value.contains(".")) {
					String[] input = value.split("\\.");
					decimal = input[0];
					fraction = input[1];
					conversionResult = conversionResult(decimal, fraction, sourceBase, targetBase);
				} else {
					decimal = value;
					fraction = "";
					conversionResult = conversionResult(decimal, fraction, sourceBase, targetBase);
				}
			}
			System.out.println("Conversion result: " + conversionResult);
		} while (keepRunning);
	}

	private static String conversionResult(String decimal, String fraction, Integer sourceBase, Integer targetBase) {
		String result = "";
		int power = 5;
		result = new BigInteger(decimal, sourceBase).toString(targetBase);

		if (fraction.isEmpty()) {
			return result.toUpperCase();
		}

		double fractionResult = fractionToDecimal(fraction, sourceBase);

		if (fractionResult <= 0.5) {
			fractionResult = Math.floor((fractionResult * Math.pow(targetBase, power)));
		} else {
			fractionResult = Math.ceil((fractionResult * Math.pow(targetBase, power)));
		}

		String finalFractionalResult = fractionDecimalResultToTargetBase(fractionResult, sourceBase, targetBase);

		result += "." + finalFractionalResult;
		return result;
	}

	private static double fractionToDecimal(String fraction, Integer sourceBase) {
		double fractionResult = 0.0;
		char[] fractionElements = fraction.toCharArray();

		for (int i = 0; i < fraction.length(); i++) {
			double convertedValue = 0.0;
			if (Character.isDigit(fractionElements[i])) {
				convertedValue = Integer.parseInt(String.valueOf(fractionElements[i]));
			} else {
				convertedValue = Character.getNumericValue(fractionElements[i]);
			}
			fractionResult += convertedValue * Math.pow(sourceBase, -(i + 1));
		}
		return fractionResult;
	}

	public static String fractionDecimalResultToTargetBase(double fractionResult, Integer sourceBase,
			Integer targetBase) {
		BigInteger bigDecimalFraction = new BigInteger(String.valueOf((int)fractionResult));
		BigInteger target = new BigInteger(String.valueOf(targetBase));
		String remainderStacker = "";

		while (true) {
			if (bigDecimalFraction.compareTo(BigInteger.ZERO) == 0) {
				break;
			} else {
				BigInteger remainder = bigDecimalFraction.remainder(target);
				if (remainder.intValueExact() >= 10) {
					remainderStacker += (Character.forDigit(remainder.intValueExact(), 36));
				} else {
					remainderStacker += remainder.toString();
				}
				bigDecimalFraction = bigDecimalFraction.divide(target);
			}

		}
		if (remainderStacker.length() < 5) {
			for (int i = remainderStacker.length(); i < 5; i++) {
				remainderStacker += "0";
			}
		} else {
			remainderStacker = remainderStacker.substring(0, 5);
		}
		return new StringBuilder(remainderStacker).reverse().toString().toUpperCase();
	}

}
