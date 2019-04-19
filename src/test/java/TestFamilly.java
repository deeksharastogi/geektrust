package test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import com.geektrust.meetthefamily.Family;

class TestFamilly {

	private static final String CHILD_ADDITION_FAILED = "CHILD_ADDITION_FAILED";
	private static final String PERSON_NOT_FOUND = "PERSON_NOT_FOUND";
	private static final String SATYA = "Satya";

	private static Family family;

	@BeforeAll
	static void setUpBeforeClass() throws FileNotFoundException {
		Path path = Paths.get("input/initInput.txt");
		family = new Family();
		family.initTree(path.toAbsolutePath().toString());
	}

	@Test
	public void initTreeWithNoInputFile() {
		Family familyObject = new Family();
		assertThrows(NullPointerException.class, () -> {
			familyObject.initTree(null);
		});
	}

	@Test
	public void initTreeWithInvalidCommandFile() throws FileNotFoundException {
		Path path = Paths.get("input/initInputtest.txt");
		Family familyObject = new Family();
		familyObject.initTree(path.toAbsolutePath().toString());
	}

	@Test
	public void initTreeWithAbsentCommandFile() {
		Family familyObject = new Family();
		assertThrows(FileNotFoundException.class, () -> {
			familyObject.initTree("input/inittest.txt");
		});
	}

	@Test
	public void addchildAllNullValues() {
		assertEquals(PERSON_NOT_FOUND, family.addchild(null, null, null));
	}

	@Test
	public void addchildNameNullValues() {
		assertEquals(CHILD_ADDITION_FAILED, family.addchild(SATYA, null, null));
	}

	@Test
	public void addchildGenderNullValues() {
		assertEquals(CHILD_ADDITION_FAILED, family.addchild(SATYA, "Ketu", null));
	}

	@Test
	public void addchildThroughFather() {
		assertEquals(CHILD_ADDITION_FAILED, family.addchild("Aras", "Ketu", "Male"));
	}

	@Test
	public void addchildThroughAbsentMember() {
		assertEquals(PERSON_NOT_FOUND, family.addchild("Aries", "Ketu", "Male"));
	}

	@Test
	public void addchildSuccess() {
		assertEquals("CHILD_ADDITION_SUCCEEDED", family.addchild(SATYA, "Ketu", "Male"));
	}

	@Test
	public void getRelationshipAllParamsNull() {
		assertEquals(PERSON_NOT_FOUND, family.getRelationship(null, null));
	}

	@Test
	public void getRelationshipRelationNull() {
		assertEquals("PROVIDE VALID RELATION", family.getRelationship(SATYA, null));
	}

	@ParameterizedTest
	@CsvFileSource(resources = "data.csv", numLinesToSkip = 1)
	public void getRelationshipValuesFromCsvFile(String memberName, String relation, String expected) {
		String actual = family.getRelationship(memberName, relation);
		assertEquals(expected, actual);
	}
}
