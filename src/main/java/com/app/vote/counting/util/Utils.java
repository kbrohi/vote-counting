package com.app.vote.counting.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	public static String findWinner(Map<String, Map<String, Integer>> ballots, Map<Character, String> places) {
		Map<String, List<Map<String, Integer>>> data = roundOneDataByPreference(ballots, places);
		int winnerRange = (ballots.size() / 2) + 1;
		
		Map<String, List<Map<String, Integer>>> filteredData = data.entrySet().stream().filter(place -> place.getValue().size() >= winnerRange).collect(Collectors.toMap(e -> e.getKey(), g -> g.getValue()));
		System.out.println("Filter Data Size: " + filteredData.size());
		for(Map.Entry<String, List<Map<String, Integer>>> entry: data.entrySet()) {
			System.out.println("****************" + entry.getKey() + "*************** Size: " + entry.getValue().size());
			
			entry.getValue().stream().forEach(en -> en.entrySet().forEach(e -> System.out.println(e.getKey() + " : " + e.getValue())));
			
		}
		
		
		return filteredData.entrySet().size() == 1 ? filteredData.entrySet().stream().findFirst().get().getKey() : roundTwoDataByVoting(data, winnerRange);
	}

	private static Map<String, List<Map<String, Integer>>> roundOneDataByPreference(Map<String, Map<String, Integer>> ballots,
			Map<Character, String> places) {
		Map<String, List<Map<String, Integer>>> data = new HashMap<String, List<Map<String,Integer>>>();
		for(Map.Entry<Character, String> place : places.entrySet()) {
			
			List<Map<String, Integer>> placeData = getDatabyKey(place.getValue(), ballots);
			if(!placeData.isEmpty()) 
				data.put(place.getValue(), placeData);
		}
		
		return data;
	}

	private static List<Map<String, Integer>> getDatabyKey(String value, Map<String, Map<String, Integer>> ballots) {
		List<Map<String, Integer>> list = ballots.values().stream().filter(ballot -> (ballot.get(value) != null && ballot.get(value) == 1)).collect(Collectors.toList());
		
		return list;
	}
	
	private static String roundTwoDataByVoting(Map<String, List<Map<String, Integer>>> data, int winnerRange) {
		Map.Entry<String, List<Map<String, Integer>>> leastVotes = Collections.min(data.entrySet(), Comparator.comparing(e -> e.getValue().size()));
		
		Map<String, Integer> secondPreference = leastVotes.getValue().stream().filter( l -> l.values().stream().filter(v -> v.intValue() == 2).findFirst().isPresent()).findFirst().get();
		String key = secondPreference.entrySet().stream().filter(e -> e.getValue().intValue() == 2).map(Map.Entry::getKey).findFirst().get();
		
		List<Map<String, Integer>> newData = data.get(key);
		newData.add(secondPreference);
		data.put(key, newData);
		data.remove(leastVotes.getKey());

		for(Map.Entry<String, List<Map<String, Integer>>> entry: data.entrySet()) {
			System.out.println("****************" + entry.getKey() + "*************** Size: " + entry.getValue().size());
			
			entry.getValue().stream().forEach(en -> en.entrySet().forEach(e -> System.out.println(e.getKey() + " : " + e.getValue())));
			
			
		}
		
		Map<String, List<Map<String, Integer>>> filteredData = data.entrySet().stream().filter(place -> place.getValue().size() >= winnerRange).collect(Collectors.toMap(e -> e.getKey(), g -> g.getValue()));
		
		System.out.println("Filtered: " + filteredData.entrySet().size());
		return filteredData.entrySet().size() == 1 ? filteredData.entrySet().stream().findFirst().get().getKey() : roundThreeCountVotes(data, winnerRange);
	}

	private static String roundThreeCountVotes(Map<String, List<Map<String, Integer>>> data, int winnerRange) {
		
		return null;
	}
	
	
	
}
