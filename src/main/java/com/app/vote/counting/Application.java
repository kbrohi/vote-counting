package com.app.vote.counting;

import com.app.vote.counting.util.VotingOptionsFileLoader;

public class Application {
	
	public static void main(String[] args) {
		
		//loading the voting options from file options.txt
		VotingOptionsFileLoader votingOptionsFileLoader = new VotingOptionsFileLoader();
		String options = votingOptionsFileLoader.loadOptions();
		System.out.println(options);
		
	}
}