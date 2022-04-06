package com.app.vote.counting.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

	public static String getOptionsWithPrefix(String options) {
		
		List<String> optionsList = Arrays.asList(options.split("\n"));
		
		String result = "";
		int prefix = 65;
		
		for(String option : optionsList) {
			result += (char)prefix + ". " + option+"\n";
			prefix++;
		}
		return result;
	}
	
	public static Map<Character, String> options(String options) {
		Map<Character, String> places = new HashMap<Character, String>();
		List<String> optionsList = Arrays.asList(options.split("\n"));
		
		int prefix = 65;
		
		for(String option : optionsList) {
			places.put((char)prefix, option);
			prefix++;
		}
		
		return places;
	}

	public static String findWinner(Map<String, Map<String, Integer>> ballots) {
		for (Map.Entry<String, Map<String, Integer>> ballot : ballots.entrySet()) {
			
			System.out.println(ballot.getKey());
			for(Map.Entry<String, Integer> candidate : ballot.getValue().entrySet()) {
				System.out.println(candidate.getKey() + " : " + candidate.getValue());
			}
			
			System.out.println("**********************************************************");
		}

		return null;
	}
	
}
