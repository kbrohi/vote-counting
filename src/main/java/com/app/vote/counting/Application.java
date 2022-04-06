package com.app.vote.counting;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.app.vote.counting.util.Utils;
import com.app.vote.counting.util.VotingOptionsFileLoader;

public class Application {
	
	public static void main(String[] args) {
		
		//loading the voting options from file options.txt
		VotingOptionsFileLoader votingOptionsFileLoader = new VotingOptionsFileLoader();
		String options = votingOptionsFileLoader.loadOptions();
		System.out.println(options);
		
		//display options with prefix
		System.out.println(Utils.getOptionsWithPrefix(options));
		
		Map<String, Map<String, Integer>> ballots = new HashMap<String, Map<String,Integer>>();
		Map<Character, String> places = Utils.options(options);
		
		int count = 1;
		Scanner scan = new Scanner(System.in);
		String input;
		while(!(input = scan.next()).equalsIgnoreCase("tally")) {
			Map<String, Integer> values = new HashMap<String, Integer>();
			for(int i = 0; i < input.length(); i++) {
				Character ch  = input.charAt(i);
				values.put(places.get(ch), i + 1);
			}
			
			ballots.put("Candidate " + count, values);
			
			count++;
		}
		

		String winner = Utils.findWinner(ballots, places);
		
		System.out.println("*******************************************************************************\n\n The Winner Is: " + winner + " \n************************************");
		scan.close();
	}
}