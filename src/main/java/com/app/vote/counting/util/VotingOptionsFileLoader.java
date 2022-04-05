package com.app.vote.counting.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class VotingOptionsFileLoader {
	
	public String loadOptions() {
		
		String options = null;
		
		try {
			byte[] optionsFileData = Files.readAllBytes(Paths.get("options.txt"));
			options = new String(optionsFileData);
		} catch (IOException e) {
			System.out.println("Voting options file not found");
			e.printStackTrace();
		}
		
		return options;
	}
	
}
