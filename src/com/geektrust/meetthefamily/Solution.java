package com.geektrust.meetthefamily;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {

	public static void main(String ...args) {

		Family family = new Family();
		try {
			family.initTree(args[0]);
			File file = new File(args[1]);
			processInputFile(family, file);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Please enter file location!");
		} catch (FileNotFoundException e1) {
			System.out.println("File Not Found!! Please check the file and the location provided!");
		}

	}

	/**
	 * Process input file.
	 * 
	 * @param family - object on which the command to be processed
	 * @param file   - input file
	 */
	private static void processInputFile(Family family, File file) {
		try (Scanner sc = new Scanner(file)) {
			while (sc.hasNextLine()) {
				String command = sc.nextLine();
				String commandResult = processCommand(family, command);
				System.out.println(commandResult);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found!! Please check the file and the location provided!");
		}
	}

	/**
	 * Process a command and return the output string
	 * 
	 * @param family  - object on which the command to be processed
	 * @param command - input command string to be processed
	 * @return
	 */
	private static String processCommand(Family family, String command) {
		String[] commandParams = command.split(" ");
		String commandResult;
		switch (commandParams[0]) {
		case "ADD_CHILD":
			commandResult = family.addchild(commandParams[1], commandParams[2], commandParams[3]);
			break;

		case "GET_RELATIONSHIP":
			commandResult = family.getRelationship(commandParams[1], commandParams[2]);
			break;

		default:
			commandResult = "INVALID COMMAND!";
			break;
		}
		return commandResult;
	}
}
