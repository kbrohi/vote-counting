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

		List<String> optionsList = Arrays.asList(options.split(Constants.NEXT_LINE_CHAR));

		String result = "";
		int prefix = Constants.PREFIX_A_ASCII;

		for (String option : optionsList) {
			result += (char) prefix + ". " + option + Constants.NEXT_LINE_CHAR;
			prefix++;
		}
		return result;
	}

	public static Map<Character, String> options(String options) {
		Map<Character, String> places = new HashMap<Character, String>();
		List<String> optionsList = Arrays.asList(options.split(Constants.NEXT_LINE_CHAR));

		int prefix = 65;

		for (String option : optionsList) {
			places.put((char) prefix, option);
			prefix++;
		}

		return places;
	}

	public static String findWinner(Map<String, Map<String, Integer>> ballots, Map<Character, String> places) {
		Map<String, List<Map<String, Integer>>> data = roundOneDataByPreference(ballots, places);

		int winRange = (ballots.size() / 2) + 1;
		String winner = checkWinner(data, winRange);

		return winner != null ? winner : tally(data, winRange);
	}

	private static Map<String, List<Map<String, Integer>>> roundOneDataByPreference(
			Map<String, Map<String, Integer>> ballots, Map<Character, String> places) {
		Map<String, List<Map<String, Integer>>> data = new HashMap<String, List<Map<String, Integer>>>();
		for (Map.Entry<Character, String> place : places.entrySet()) {
			List<Map<String, Integer>> placeData = getDataByKey(place.getValue(), ballots);
			if (!placeData.isEmpty())
				data.put(place.getValue(), placeData);
		}

		return data;
	}

	private static List<Map<String, Integer>> getDataByKey(String value, Map<String, Map<String, Integer>> ballots) {
		List<Map<String, Integer>> list = ballots.values().stream()
				.filter(ballot -> (ballot.get(value) != null && ballot.get(value) == 1)).collect(Collectors.toList());

		return list;
	}

	private static String tally(Map<String, List<Map<String, Integer>>> data, int winRange) {
		Map.Entry<String, List<Map<String, Integer>>> leastVotes = Collections.min(data.entrySet(),
				Comparator.comparing(e -> e.getValue().size()));

		Map<String, Integer> secondPreference = leastVotes.getValue().stream()
				.filter(l -> l.values().stream().filter(v -> v.intValue() == 2).findFirst().isPresent()).findFirst()
				.get();
		String key = secondPreference.entrySet().stream().filter(e -> e.getValue().intValue() == 2)
				.map(Map.Entry::getKey).findFirst().get();

		List<Map<String, Integer>> newData = data.get(key);
		if(newData != null && !newData.isEmpty()) {
			newData.add(secondPreference);
			data.put(key, newData);
		}
		List<Map<String, Integer>> leastVoteData = data.get(leastVotes.getKey());
		if(leastVoteData.size() == 1) {
			data.remove(leastVotes.getKey());
			winRange = winRange - 1;
		} else {
			leastVoteData.removeIf( k -> k.entrySet()
					.stream()
					.anyMatch(e -> !secondPreference.containsKey(e.getKey()) || secondPreference.get(e.getKey()) < e.getValue()));

			data.put(leastVotes.getKey(), leastVoteData);
		}


		for (Map.Entry<String, List<Map<String, Integer>>> entry : data.entrySet()) {
			System.out
					.println("****************" + entry.getKey() + "*************** Size: " + entry.getValue().size());

			entry.getValue().stream()
					.forEach(en -> en.entrySet().forEach(e -> System.out.println(e.getKey() + " : " + e.getValue())));

		}

		String winner = checkWinner(data, winRange);

		return winner != null ? winner : tally(data, winRange);
	}

	private static String checkWinner(Map<String, List<Map<String, Integer>>> data, int winRange) {

		Map<String, List<Map<String, Integer>>> filteredData = data.entrySet().stream()
				.filter(place -> place.getValue().size() >= winRange)
				.collect(Collectors.toMap(e -> e.getKey(), g -> g.getValue()));

		if(filteredData.size() > 1) {
			Map.Entry<String, List<Map<String, Integer>>> maxVote =  Collections.max(data.entrySet(),
					Comparator.comparing(e -> e.getValue().size()));

			if(maxVote != null && !maxVote.getValue().isEmpty()) {
				return maxVote.getKey();
			}

			return filteredData.entrySet().stream().findAny().get().getKey();
		}

		System.out.println("Filtered Size: " + filteredData.size());
		return filteredData.size() == 1 ? filteredData.entrySet().stream().findFirst().get().getKey() : null;
	}

}
