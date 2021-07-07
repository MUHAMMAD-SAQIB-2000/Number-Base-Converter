package com.main;

import java.util.Scanner;

import com.converter.Converter;

public class Main {

	static Scanner scan;
	static Converter convertor;

	public static void main(String[] args) {
		start();
	}

	public static void start() {
		boolean keepRunning = true;
		scan = new Scanner(System.in);
		Integer sourceBase = 0;
		Integer targetBase = 0;
		do {

			System.out.println("\nEnter two numbers in format: {source base} {target base} (To quit type /exit)");
			String choice = scan.nextLine().toLowerCase();
			String[] input = choice.split(" ");

			if (choice.equals("/exit")) {
				System.exit(0);
			} else {
				sourceBase = Integer.parseInt(input[0]);
				targetBase = Integer.parseInt(input[1]);
				if (sourceBase <= 1 || targetBase <= 1) {
					System.out.println("Invalid Input! Source Or Target Base Cannot be <=1");
					keepRunning = true;
				} else {
					Converter.beginConversion(sourceBase, targetBase);
				}
			}

		} while (keepRunning);
	}

}
