package com.geektrust.thetwoplanets;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
	
	public static void main(String ...f) {
		
		Scanner sc = new Scanner(System.in);
		String command = sc.nextLine();
		sc.close();
		
		Solution sol = new Solution();
		
		// Get enemy details
		Battalion enemyBattalion = sol.prepareForBattle(command);
		
		if(enemyBattalion != null) {
			Battle battle = new Battle();
			String result = battle.getBattleResult(enemyBattalion);
			System.out.println(result);
		}
	}

	/**
	 * Search the command for the four numbers and identifying the no of unit being deployed.
	 * 4 types of units-
	 * 		*0 : horse
	 * 		*1 : elephant
	 * 		*2 : armoured tanks
	 * 		*3 : sling guns
	 * Ex. Falicornia attacks with {0} H, {1} E, {2} AT, {3} SG 
	 * 
	 * @param command - input from command line
	 * @return 
	 */
	private Battalion prepareForBattle(String command) {
		Pattern pattern = Pattern.compile("([0-9]+)");
		Matcher matcher = pattern.matcher(command);
		int count = 0;
		int[] battalion = new int[Battalion.TOTAL_UNIT_TYPES]; // 4 types - 0 : horse, 1 : elephant, 2 : artanks, 3 : sling guns
        while (matcher.find()) {    
        	battalion[count] = Integer.parseInt(matcher.group());
            count++;
        }    
        
        if(count == Battalion.TOTAL_UNIT_TYPES){    
        	return new Battalion(battalion);
        } 
        
        System.out.println("INVALID INPUT");
		return null;
	}

}
