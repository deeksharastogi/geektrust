package test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.geektrust.meetthefamily.Family;
import com.geektrust.meetthefamily.Solution;

class TestFamilly {

	private static final String CHILD_ADDITION_FAILED = "CHILD_ADDITION_FAILED";
	private static final String PERSON_NOT_FOUND = "PERSON_NOT_FOUND";
	private static final String SATYA = "Satya";

	private static Family family;

	@BeforeAll
	static void setUpBeforeClass()  {
		family = new Family();
		Solution sol = new Solution();
		
		String pathInitFile = Paths.get("input/initInput.txt").toAbsolutePath().toString();
		sol.initFileToProcess(family, pathInitFile, false);
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
	@CsvSource({
		"Satya,WIFE,NOT YET IMPLEMENTED",
		"Satya,Paternal-Uncle,NONE",
		"Kriya,Paternal-Uncle,Asva",
		"Asva,Maternal-Uncle,Chit Ish Vich Aras",
		"Tritha,Paternal-Aunt,Satya",
		"Yodhan,Maternal-Aunt,Tritha",
		"Satvy,Sister-In-Law,Atya",
		"Vyas,Brother-In-Law,Asva",
		"Queen Anga,Son,Chit Ish Vich Aras",
		"Queen Anga,Daughter,Satya",
		"Chit,Siblings,Ish Vich Aras Satya"
	})
	public void getRelationshipValuesFromCsvFile(String memberName, String relation, String expected) {
		String actual = family.getRelationship(memberName, relation);
		assertEquals(expected, actual);
	}
}
