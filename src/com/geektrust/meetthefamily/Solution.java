package com.geektrust.meetthefamily;

import java.io.File;

public class Solution {

	public static void main(String... args) {

		Family family = new Family();
		Solution sol = new Solution();

		try {
			sol.initFileToProcess(family, args[0], false);
			sol.initFileToProcess(family, args[1], true);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Please enter file location(s)!");
		}

	}

	/**
	 * Read file to process.
	 * 
	 * @param family
	 * @param filePath
	 * @param isInputFile
	 * @throws FileNotFoundException
	 */
	public void initFileToProcess(Family family, String filePath, boolean isInputFile) {
		File file = new File(filePath);
		FileProcessor processor = new FileProcessor();
		processor.processInputFile(family, file, isInputFile);
	}


}
