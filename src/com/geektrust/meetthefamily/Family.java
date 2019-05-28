package com.geektrust.meetthefamily;

import static com.geektrust.meetthefamily.Constants.Relationship.*;
import static com.geektrust.meetthefamily.Constants.Message.*;

import java.util.ArrayList;
import java.util.List;

public class Family {

	private static final String FEMALE = "Female";

	private Member familyHead;

	/**
	 * Add head of the family.
	 * 
	 * @param name   - name of the member
	 * @param gender - gender
	 */
	public void addFamilyHead(String name, String gender) {
		Gender g = (FEMALE.equals(gender)) ? Gender.Female : Gender.Male;
		this.familyHead = new Member(name, g, null, null);
	}

	/**
	 * Add spouse to a member iff {@link Member} is not null and do not have spouse
	 * already.
	 * 
	 * @param memberName - member whose spouse to be added
	 * @param spouseName
	 * @param gender
	 */
	public void addSpouse(String memberName, String spouseName, String gender) {
		Member member = searchMember(familyHead, memberName);
		if (member != null && member.spouse == null) {
			Gender g = (FEMALE.equals(gender)) ? Gender.Female : Gender.Male;
			Member sp = new Member(spouseName, g, null, null);
			sp.addSpouse(member);
			member.addSpouse(sp);
		}
	}

	/**
	 * Add child to a member iff {@link Member} is not null and member is female.
	 * 
	 * @param motherName - member whose child to be added
	 * @param childName
	 * @param gender
	 * @return
	 */
	public String addchild(String motherName, String childName, String gender) {
		String result;
		Member member = searchMember(familyHead, motherName);
		if (member == null) {
			result = PERSON_NOT_FOUND;
		} else if (childName == null || gender == null) {
			result = CHILD_ADDITION_FAILED;
		} else if (member.gender == Gender.Female) {
			Gender g = (FEMALE.equals(gender)) ? Gender.Female : Gender.Male;
			Member child = new Member(childName, g, member.spouse, member);
			member.addChild(child);
			result = CHILD_ADDITION_SUCCEEDED;
		} else {
			result = CHILD_ADDITION_FAILED;
		}
		return result;
	}

	/**
	 * Search a {@link Member} with name as person. Find member's name corresponding
	 * to given relationship
	 * 
	 * @param person
	 * @param relationship
	 * @return
	 */
	public String getRelationship(String person, String relationship) {

		String relations;
		Member member = searchMember(familyHead, person);
		if (member == null) {
			relations = PERSON_NOT_FOUND;
		} else if (relationship == null) {
			relations = PROVIDE_VALID_RELATION;
		} else {
			relations = getRelationship(member, relationship);
		}

		return relations;

	}

	/**
	 * Find members name corresponding to given relationship to given {@link Member}
	 * 
	 * @param member       : whose relatives to be found
	 * @param relationship : relation to be foind
	 * @return list of names of relatives
	 */
	private String getRelationship(Member member, String relationship) {
		String relations = "";
		switch (relationship) {

		case DAUGHTER:
			relations = member.searchChild(Gender.Female);
			break;

		case SON:
			relations = member.searchChild(Gender.Male);
			break;

		case SIBLINGS:
			relations = member.searchSiblings();
			break;

		case SISTER_IN_LAW:
			relations = searchInLaws(member, Gender.Female);
			break;

		case BROTHER_IN_LAW:
			relations = searchInLaws(member, Gender.Male);
			break;

		case MATERNAL_AUNT:
			if (member.mother != null)
				relations = member.mother.searchAuntOrUncle(Gender.Female);
			break;

		case PATERNAL_AUNT:
			if (member.father != null)
				relations = member.father.searchAuntOrUncle(Gender.Female);
			break;

		case MATERNAL_UNCLE:
			if (member.mother != null)
				relations = member.mother.searchAuntOrUncle(Gender.Male);
			break;

		case PATERNAL_UNCLE:
			if (member.father != null)
				relations = member.father.searchAuntOrUncle(Gender.Male);
			break;

		default:
			relations = NOT_YET_IMPLEMENTED;
			break;
		}

		return ("".equals(relations)) ? NONE : relations;

	}

	/**
	 * Search {@link Member} sister-in law or brother -in law.
	 * 
	 * @param member
	 * @param gender
	 * @return
	 */
	private String searchInLaws(Member member, Gender gender) {
		String personName = member.name;
		StringBuilder sb = new StringBuilder("");
		String res = "";

		// search spouse mother children
		if (member.spouse != null && member.spouse.mother != null) {
			res = member.spouse.mother.searchChildren(gender, member.spouse.name);
		}
		sb.append(res);

		// search mother children
		res = "";
		if (member.mother != null) {
			res = member.mother.searchChildren(gender, personName);
		}
		sb.append(res);

		return sb.toString().trim();
	}

	/**
	 * Search {@link Member} object with name as memberName. Returns null in case
	 * not found.
	 * 
	 * @param head
	 * @param memberName
	 * @return {@link Member} object
	 */
	private Member searchMember(Member head, String memberName) {
		if (memberName == null || head == null) {
			return null;
		}

		Member member = null;
		if (memberName.equals(head.name)) {
			return head;
		} else if (head.spouse != null && memberName.equals(head.spouse.name)) {
			return head.spouse;
		}

		List<Member> childlist = new ArrayList<>();
		if (head.gender == Gender.Female) {
			childlist = head.children;
		} else if (head.spouse != null) {
			childlist = head.spouse.children;
		}

		for (Member m : childlist) {
			member = searchMember(m, memberName);
			if (member != null) {
				break;
			}
		}
		return member;
	}

}