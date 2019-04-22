package com.geektrust.thetwoplanets;

public class Battalion {

	public static final int HORSE = 0;
	
	public static final int ELEPHANT = 1;
	
	public static final int ARMOURED_TANK = 2;
	
	public static final int SLING_GUN = 3;
	
	public static final int TOTAL_UNIT_TYPES = 4;
	
	public static final int BATTALION_POWER = 2;
	
	public static final int UPPER_BATTALION_POWER = 2 * BATTALION_POWER;
		
	private int[] army;

	public Battalion(int[] army) {
		this.army = army;
	}

	public int[] getArmy() {
		return army;
	}

	public void setArmyUnit(int index, int value) {
		this.army[index] = value;
	}

	public int getArmyUnit(int index) {
		return this.army[index];
	}

}
