package com.geektrust.meetthefamily;

import java.util.ArrayList;
import java.util.List;

enum Gender {
	Male, Female
}

public class Member {

	String name;
	Gender gender;

	Member mother;
	Member father;

	Member spouse;

	List<Member> children;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param gender
	 * @param father
	 * @param mother
	 */
	public Member(String name, Gender gender, Member father, Member mother) {
		this.name = name;
		this.gender = gender;
		this.mother = mother;
		this.father = father;
		this.spouse = null;
		this.children = new ArrayList<Member>();
	}

	/**
	 * Add child.
	 * 
	 * @param child
	 */
	public void addChild(Member child) {
		this.children.add(child);
	}

	/**
	 * Add spouse.
	 * 
	 * @param spouse
	 */
	public void addSpouse(Member spouse) {
		this.spouse = spouse;
	}

	/**
	 * Return list of daughters or sons.
	 * 
	 * @param gender
	 * @return
	 */
	public String searchChild(Gender gender) {
		StringBuilder sb = new StringBuilder("");
		for (Member m : this.children) {
			if (m.gender == gender) {
				sb.append(m.name).append(" ");
			}
		}
		return sb.toString().trim();
	}
	
	/**
	 * Return list of all Siblings.
	 * 
	 * @param personName
	 * @return
	 */
	public String searchSiblings() {
		StringBuilder sb = new StringBuilder("");
		if (this.mother != null) {
			for (Member m : this.mother.children) {
				if (!this.name.equals(m.name) ) {
					sb.append(m.name).append(" ");
				}
			}	
		}
		
		return sb.toString().trim();
	}

	/**
	 * Return list of children - daughters or son , name other than personName. 
	 * 
	 * @param gender
	 * @param personName
	 * @return
	 */
	public String searchChildren(Gender gender, String personName) {
		StringBuilder sb = new StringBuilder("");

		for (Member m : this.children) {
			if (!personName.equals(m.name) && m.gender == gender) {
				sb.append(m.name).append(" ");
			}
		}
		
		return sb.toString().trim();
	}
	
	/**
	 * Search Aunt/Uncle based on gender and Maternal/Paternal based on member being
	 * father or mother. Returns list of names (as string) of brothers/sisters of
	 * {@link Member}.
	 * 
	 * @param gender
	 * @return
	 */
	public String searchAuntOrUncle(Gender gender) {

		StringBuilder sb = new StringBuilder("");

		if (this.mother != null) {
			for (Member m : this.mother.children) {
				if (!this.name.equals(m.name) && m.gender == gender) {
					sb.append(m.name).append(" ");
				}
			}
		}

		return sb.toString().trim();
	}

}