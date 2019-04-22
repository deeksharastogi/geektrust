package com.geektrust.thetwoplanets;

public class Battle {

	Battalion enemy;

	/**
	 * Driver method to prepare army for deployment, checkVictory, and return
	 * result.
	 * 
	 * @param enemyBattalion
	 * @return battle result
	 */
	public String getBattleResult(Battalion enemyBattalion) {
		enemy = enemyBattalion;
		PrepareBattalion prepare = new PrepareBattalion();
		Battalion kingShahCompleteBattalion = prepare.getKingShahArmyStrength();
		Battalion deployedBattalion = getDeployedBattalion(kingShahCompleteBattalion);
		boolean isKingVictorious = checkForVictory();

		return getBattleResult(deployedBattalion, isKingVictorious);
	}

	/**
	 * 
	 * @param kingShahCompleteBattalion
	 * @return
	 */
	private Battalion getDeployedBattalion(Battalion kingShahCompleteBattalion) {
		PrepareBattalion prepare = new PrepareBattalion();
		Battalion remainingBattalion = prepare.prepareKingShahforBattle(kingShahCompleteBattalion, enemy);
		Battalion initialBattalion = prepare.getKingShahArmyStrength();

		int[] deployedBattalion = new int[4];

		for (int i = 0; i < Battalion.TOTAL_UNIT_TYPES; i++) {
			deployedBattalion[i] = initialBattalion.getArmyUnit(i) - remainingBattalion.getArmyUnit(i);
		}

		return new Battalion(deployedBattalion);
	}

	/**
	 * Generate battle result summary from deployed units and the boolean flag
	 * 
	 * @param deployedBattalion
	 * @param isKingVictorious
	 * @return
	 */
	private String getBattleResult(Battalion deployedBattalion, boolean isKingVictorious) {

		String resultString = "Lengaburu deploys {0} H, {1} E, {2} AT, {3} SG and {4}";

		int i;
		for (i = 0; i < Battalion.TOTAL_UNIT_TYPES; i++) {
			resultString = resultString.replace("{" + i + "}", "" + deployedBattalion.getArmyUnit(i));
		}

		if (isKingVictorious) {
			resultString = resultString.replace("{" + i + "}", "wins");
		} else {
			resultString = resultString.replace("{" + i + "}", "loses");
		}

		return resultString;
	}

	/**
	 * If all enemy forces counter prepared successfully - king victorious.
	 * 
	 * @return true - iff king victorious
	 */
	private boolean checkForVictory() {
		for (int i = 0; i < Battalion.TOTAL_UNIT_TYPES; i++) {
			if (enemy.getArmyUnit(i) != 0) {
				return false;
			}
		}
		return true;
	}

}
