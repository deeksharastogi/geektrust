package com.geektrust.meetthefamily;

import java.util.ArrayList;
import java.util.List;

public class Family {

	private static final String FEMALE = "Female";
	
	private Member familyHead;
	
	private void setFamilyHead(Member familyHead) {
		this.familyHead = familyHead;
	}

	/**
	 * Add head of the family.
	 * 
	 * @param name - name of the member
	 * @param gender - gender
	 */
	public void addFamilyHead(String name, String gender) {
		Gender g = (FEMALE.equals(gender)) ? Gender.Female : Gender.Male;
		setFamilyHead(new Member(name, g, null, null));
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
			result = "PERSON_NOT_FOUND";
		} else if (childName == null || gender == null) {
			result = "CHILD_ADDITION_FAILED";
		} else if (member.gender == Gender.Female) {
			Gender g = (FEMALE.equals(gender)) ? Gender.Female : Gender.Male;
			Member child = new Member(childName, g, member.spouse, member);
			member.addChild(child);
			result = "CHILD_ADDITION_SUCCEEDED";
		} else {
			result = "CHILD_ADDITION_FAILED";
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
			relations = "PERSON_NOT_FOUND";
		} else if (relationship == null) {
			relations = "PROVIDE VALID RELATION";
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
		String relations;
		switch (relationship) {

		case Relationship.DAUGHTER:
			relations = searchChild(member, Gender.Female);
			break;
		case Relationship.SON:
			relations = searchChild(member, Gender.Male);
			break;

		case Relationship.SIBLINGS:
			relations = searchSiblings(member);
			break;

		case Relationship.SISTER_IN_LAW:
			relations = searchInLaws(member, Gender.Female);
			break;
		case Relationship.BROTHER_IN_LAW:
			relations = searchInLaws(member, Gender.Male);
			break;

		case Relationship.MATERNAL_AUNT:
			relations = searchAuntUncle(member.mother, Gender.Female);
			break;
		case Relationship.PATERNAL_AUNT:
			relations = searchAuntUncle(member.father, Gender.Female);
			break;
		case Relationship.MATERNAL_UNCLE:
			relations = searchAuntUncle(member.mother, Gender.Male);
			break;
		case Relationship.PATERNAL_UNCLE:
			relations = searchAuntUncle(member.father, Gender.Male);
			break;
		default:
			relations = "NOT YET IMPLEMENTED";
			break;
		}

		return ("".equals(relations)) ? "NONE" : relations;
	}

	/**
	 * Search Aunt/Uncle based on gender and Maternal/Paternal based on member being
	 * father or mother. Returns list of names (as string) of brothers/sisters of
	 * {@link Member}.
	 * 
	 * @param member
	 * @param gender
	 * @return
	 */
	private String searchAuntUncle(Member member, Gender gender) {

		StringBuilder sb = new StringBuilder("");

		if (member != null && member.mother != null) {
			String personName = member.name;
			member = member.mother;
			for (Member m : member.children) {
				if (!personName.equals(m.name) && m.gender == gender) {
					sb.append(m.name).append(" ");
				}
			}
		}

		return sb.toString().trim();
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

		if (member.spouse != null) {
			String spouse = member.spouse.name;
			Member temp = member.spouse.mother;
			if (temp != null) {
				for (Member m : temp.children) {
					if (!spouse.equals(m.name) && m.gender == gender) {
						sb.append(m.name).append(" ");
					}
				}
			}
		}

		searchInMotherChildren(member, gender, personName, sb);

		return sb.toString().trim();
	}

	/**
	 * 
	 * @param member
	 * @param gender
	 * @param personName
	 * @param sb
	 */
	private void searchInMotherChildren(Member member, Gender gender, String personName, StringBuilder sb) {
		if (member.mother != null) {
			member = member.mother;
			for (Member m : member.children) {
				if (!personName.equals(m.name) && m.gender == gender) {
					sb.append(m.name).append(" ");
				}
			}
		}
	}

	/**
	 * Search {@link Member} siblings
	 * 
	 * @param {@link Member}
	 * @return
	 */
	private String searchSiblings(Member member) {
		String personName = member.name;
		StringBuilder sb = new StringBuilder("");
		if (member.mother != null) {
			member = member.mother;
			for (Member m : member.children) {
				if (!personName.equals(m.name)) {
					sb.append(m.name).append(" ");
				}
			}
		}
		return sb.toString().trim();
	}

	/**
	 * Search child of {@link Member} - daughter or son.
	 * 
	 * @param {@link Member}
	 * @param gender
	 * @return
	 */
	private String searchChild(Member member, Gender gender) {
		StringBuilder sb = new StringBuilder("");
		for (Member m : member.children) {
			if (m.gender == gender) {
				sb.append(m.name).append(" ");
			}
		}
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