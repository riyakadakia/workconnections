package org.workconnections.backend.service;

import org.springframework.stereotype.Service;
import org.workconnections.backend.entity.Program;
import org.workconnections.backend.entity.Session;

@Service
public class ProgramsService {

	/*
	 * Returns program details from programIds
	 */
	public Program[] getProgramFromIds(Integer[] programIds) {
		Program[] programArr = null;
	
		/* XXX */
				
		return programArr;
	}
	
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
