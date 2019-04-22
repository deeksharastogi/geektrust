package com.geektrust.thetwoplanets;

public class PrepareBattalion {

	/**
	 * @return - total army strength of King Shah
	 */
	Battalion getKingShahArmyStrength() {
		// hard-coded - as provided in problem statement
		int[] army = { 100, 50, 10, 5 };
		return new Battalion(army);
	}

	/**
	 * 
	 * @param enemy
	 * @param kingShahBattalion
	 * @return
	 */
	public Battalion prepareKingShahforBattle(Battalion battalion, Battalion enemy) {

		int count;
		int countEunit;
		int countBunit;

		// RULE #1: The Power Rule
		// RULE #2: The Like-to-Like Rule

		for (int i = 0; i < Battalion.TOTAL_UNIT_TYPES; i++) {
			countEunit = enemy.getArmyUnit(i);
			countBunit = battalion.getArmyUnit(i);
			count = getCountByRule(countEunit, countBunit, Battalion.BATTALION_POWER);
			battalion.setArmyUnit(i, countBunit - count);
			enemy.setArmyUnit(i, countEunit - count * Battalion.BATTALION_POWER);
		}

		// RULE #3 and #4 : Substitution

		// check for lower ranked battalion choice
		// power => 1 lower = 1 enemy unit
		for (int i = Battalion.TOTAL_UNIT_TYPES - 1; i > 0; i--) {
			countEunit = enemy.getArmyUnit(i);
			if (countEunit > 0) {
				countBunit = battalion.getArmyUnit(i - 1);
				count = (countBunit < countEunit) ? countBunit : countEunit;
				battalion.setArmyUnit(i - 1, countBunit - count);
				enemy.setArmyUnit(i, countEunit - count);
			}
		}

		// check for upper ranked battalion choice
		// power => 1 upper = 1 own unit = 4 enemy unit
		for (int i = 0; i < Battalion.TOTAL_UNIT_TYPES - 1; i++) {
			countEunit = enemy.getArmyUnit(i);
			if (countEunit > 0) {
				countBunit = battalion.getArmyUnit(i + 1);
				count = getCountByRule(countEunit, countBunit, Battalion.UPPER_BATTALION_POWER);
				battalion.setArmyUnit(i + 1, countBunit - count);
				enemy.setArmyUnit(i, countEunit - count * Battalion.UPPER_BATTALION_POWER);
			}
		}

		return battalion;
	}

	private int getCountByRule(int count, int total, int power) {
		if (count == 0 || total == 0)
			return 0;

		if (count < power)
			return count;

		int c = count / power;
		if (count % power != 0) {
			c++;
		}
		return (total < c) ? total : c;
	}
	
}
