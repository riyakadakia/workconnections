package com.wrkconn.common.utils;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
        
public class StringUtil {

	static Logger _log = LogManager.getLogger(StringUtil.class);   

	/*
	 * Check to see if the string is null or empty
	 */
    public static boolean isNullOrEmpty(String s) {
    	if (s == null || s.length() == 0) {
	    	return true;
        }
        return false;
    }
    
    public static String trimChar(String s, char c) {
    	if (s == null || s.length() == 0) {
    		return s;
    	}
    	
		String s2 = null;
		
		// trim the original string first
		s.trim();
		
		// remove leading and trailing 'c' characters
		int fidx = s.indexOf(c);
		if (fidx == 0)
			s2 = s.substring(1);
		else
			s2 = s;
		
		int lidx = s2.lastIndexOf('|');
		if (lidx == s2.length() - 1) 
			s2 = s2.substring(0, lidx);
	
		_log.trace("StringUtil::trim() original string: " + s + ", new string: " + s2);
		
		return s2;
    }

    
	public static boolean isValidCharacter(char ch) {
		// must be between '0' and '9', 'A' and 'Z', 'a' & 'z'
	    return ((ch >= 48 && ch <= 57) || (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122));
	}
	
	public static List<String> getWords(String phrase) {		
		List<String> retWords = new ArrayList<String>();
		
		// String phrase = "Ambri, -- a+ Inc. FinancialForce.com a, ";
		String words[] = phrase.split(" ");
		
		for (int i = 0; i < words.length; i++) {
			// if word is an empty string, ignore it..
			if (words[i] == "") continue;	

			// Remove any trailing invalid character... (e.g. , or .)
			char c = words[i].charAt(words[i].length()-1);
			if (c == ',' || c == '.') {
				words[i] = words[i].substring(0, words[i].length()-1);
			}
			
			// Remove any trailing .com (e.g. FinancialForce.com, Salesforce.com)
			String word = words[i].toLowerCase();
			int idx = word.lastIndexOf(".com");
			if (idx != -1) {
				words[i] = words[i].substring(0, idx);
			}
			
			// if word is less than 2 characters, ignore it..
			if (words[i].length() < 2) continue;

			// if word has not a single valid characters (e.g. --), ignore it..
			int sz = words[i].length();
			int j = 0;
			for (j = 0; j < sz; j++) {
				if (isValidCharacter(words[i].charAt(j)) == true) {
					break;
				}
			}
			if (j == sz) continue;

			retWords.add(words[i]);
		}
		
		return retWords;
	}
	
}
