package org.workconnections.backend.service;

//import java.util.ArrayList;
import java.util.List;
//import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.workconnections.backend.entity.Session;

public class ProgramsService {

	/*
	 * Process all programs for this session. Return a list of eligible program_ids.
	 */
	Integer[] processAllProgramsForThisSession(Session session, Integer[] processedConditionsArr) {
	
		Integer[] eligibleProgramsArr = null;
		
		/* XXX */
		
		return eligibleProgramsArr;
	}
	
	/*
	public static void main() {
		
		String inputStr = "1 & (2 || 3 || 4) & ( 5 || 6 || 7 || 8 || (9 & 10) ) & 11";
		List<String> matches = new ArrayList<>();
		Pattern p = Pattern.compile("\\([^()]*(?:\\([^()]*\\)[^()]*)*\\)");
		Matcher m = p.matcher(inputStr);
		while (m.find()) {
		    String fullMatch = m.group();
		    matches.add(fullMatch.substring(1, fullMatch.length() - 1));
		}
	}
	*/

}
