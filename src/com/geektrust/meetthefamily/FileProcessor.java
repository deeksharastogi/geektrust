package com.geektrust.meetthefamily;

import static com.geektrust.meetthefamily.Constants.Commands.ADD_CHILD;
import static com.geektrust.meetthefamily.Constants.Commands.ADD_FAMILY_HEAD;
import static com.geektrust.meetthefamily.Constants.Commands.ADD_SPOUSE;
import static com.geektrust.meetthefamily.Constants.Commands.GET_RELATIONSHIP;
import static com.geektrust.meetthefamily.Constants.Message.INVALID_COMMAND;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileProcessor {

	/**
	 * Process file.
	 * 
	 * @param family      - object on which the command to be processed
	 * @param file        - file to be processed
	 * @param isInputFile - flag to check if file being processed is input or init
	 *                    file.
	 * 
	 */
	public void processInputFile(Family family, File file, boolean isInputFile) {
		try (Scanner sc = new Scanner(file)) {
			while (sc.hasNextLine()) {
				String command = sc.nextLine();

				if (isInputFile) {
					processInputCommand(family, command);
				} else {
					processInitCommand(family, command);
				}
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
	private void processInputCommand(Family family, String command) {
		String[] commandParams = command.split(" ");
		String commandResult;
		switch (commandParams[0]) {
		case ADD_CHILD:
			commandResult = family.addchild(commandParams[1], commandParams[2], commandParams[3]);
			break;

		case GET_RELATIONSHIP:
			commandResult = family.getRelationship(commandParams[1], commandParams[2]);
			break;

		default:
			commandResult = INVALID_COMMAND;
			break;
		}

		System.out.println(commandResult);
	}

	/**
	 * Process command to initialize family tree.
	 * 
	 * @param family
	 * 
	 * @param command
	 */
	private void processInitCommand(Family family, String command) {
		String[] commandParams = command.split(";");
		switch (commandParams[0]) {

		case ADD_FAMILY_HEAD:
			family.addFamilyHead(commandParams[1], commandParams[2]);
			break;

		case ADD_CHILD:
			family.addchild(commandParams[1], commandParams[2], commandParams[3]);
			break;

		case ADD_SPOUSE:
			family.addSpouse(commandParams[1], commandParams[2], commandParams[3]);
			break;

		default:
			System.out.println("INVALID INIT COMMAND!");
			break;
		}
	}
}
