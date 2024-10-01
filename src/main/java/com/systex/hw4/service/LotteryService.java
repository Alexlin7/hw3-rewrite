package com.systex.hw4.service;

import java.util.ArrayList;
import java.util.HashSet;


public class LotteryService {
	
	public static ArrayList<Integer> generateLotteryNum(HashSet<Integer> excludeNumberSet) {

		ArrayList<Integer> lottery = new ArrayList<>();

		while (lottery.size() < 6) {
			int luckNumber = (int) (Math.random() * 49 + 1);
			if (!excludeNumberSet.contains(luckNumber)) {
				lottery.add(luckNumber);
			}
		}
		return lottery;
	}

	public static void parseExcludeNum(String excludeNumString, HashSet<Integer> excludeNumberSet) {
		String[] numberStrings = excludeNumString.split(" ");
		for (String num : numberStrings) {
			try {
				if (Integer.parseInt(num.trim()) < 0) {
					throw new IllegalArgumentException("您所輸入的排除數字有負數");
				}
				excludeNumberSet.add(Integer.parseInt(num.trim()));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("您所輸入的排除數字似乎有非數字的值");
			}
			
		}
	}

}
