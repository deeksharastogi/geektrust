package com.geektrust.meetthefamily;

import java.util.ArrayList;
import java.util.List;

enum Gender{
	Male, 
	Female
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
		this.name= name;
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
}