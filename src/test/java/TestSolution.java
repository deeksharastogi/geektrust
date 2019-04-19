package test.java;

import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.geektrust.meetthefamily.Solution;

class TestSolution {
	
	private static String pathInitFile;
	private static String pathInputFile;

	@BeforeAll
	static void setUp() {
		pathInitFile = Paths.get("input/initInputtest.txt").toAbsolutePath().toString();
		pathInputFile = Paths.get("input/inputtest.txt").toAbsolutePath().toString();
	}

	@Test
	public void mainSuccess() {
		Solution.main(pathInitFile, pathInputFile);
	}

	@Test
	public void mainWithZeroArgs() {
		Solution.main();
	}

	@Test
	public void mainWithOneArgs() {
		Solution.main(pathInitFile);
	}

	@Test
	public void mainWithInvalidInitFile() {
		Solution.main("input/inittest.txt", pathInputFile);
	}
	
	@Test
	public void mainWithInvalidInputFile() {
		Solution.main(pathInitFile, "input/input1test.txt");
	}	

}
