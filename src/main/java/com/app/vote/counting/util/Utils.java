package com.app.vote.counting.util;

import java.util.Arrays;
import java.util.List;

public class Utils {

	public static String getOptionsWithPrefix(String options) {
		
		List<String> optionsList = Arrays.asList(options.split("\n"));
		
		String result = "";
		int prefix = 65;
		
		for(String option : optionsList) {
			result += (char)prefix + option+"\n";
			prefix++;
		}
		return result;
	}
	
}
