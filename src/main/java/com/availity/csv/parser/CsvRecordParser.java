package com.availity.csv.parser;

import java.util.ArrayList;
import java.util.List;

import com.availity.csv.lexical.CsvLexicalAnalyzer;
import com.availity.csv.lexical.enumer.CsvColumnTypeEnum;
import com.availity.csv.lexical.enumer.LexicalActionEnum;
import com.availity.csv.lexical.enumer.LexicalTokenEnum;

public class CsvRecordParser {
	
    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    @SuppressWarnings("unused")
	public List<String>parseRecord(String line, CsvLexicalAnalyzer lex) throws Exception{
    	
    	int currPos = 0;
    	List<String>parserResult = new ArrayList<String>();
    	StringBuilder parsedColumn = new StringBuilder();
    	for(char currChar : line.toCharArray()) {
//     		if(CsvColumnTypeEnum.Quoted.equals(lex.getCOLUMN_TYPE()) && LexicalActionEnum.ADDCOLUMN.equals(lex.getLEXICAL_ACTION())) {
//     				if(line.charAt(currPos-1) != ',') {
//     					continue;
//     				}
//     		}
    		lex.analyze(currPos, line);
    		if(LexicalActionEnum.ADDCHAR.equals(lex.getLEXICAL_ACTION())) {
    			parsedColumn.append(currChar);
    		}
    		else if(LexicalActionEnum.ADDCOLUMN.equals(lex.getLEXICAL_ACTION())) {
    			if(CsvColumnTypeEnum.Quoted.equals(lex.getCOLUMN_TYPE())) {
    					parsedColumn.append("\"");
    			}
    			if(LexicalTokenEnum.EOC.equals(lex.getCURR_LEXICAL_TOKEN()) && !CsvColumnTypeEnum.Quoted.equals(lex.getCOLUMN_TYPE())) {
    				parsedColumn.append(line.charAt(line.length()-1));
    			}
    			String trimedColumn = parsedColumn.toString().trim();
    			parserResult.add(trimedColumn);
    			parsedColumn = new StringBuilder();
    		}
    		currPos++;
    	}
    	return parserResult;
    	
    }
    
//    public List<String> parseRecord(String cvsLine, char separators, char customQuote) {
//
//	        List<String> result = new ArrayList<String>();
//	        
//	        //if empty, return!
//	        if (cvsLine == null && cvsLine.isEmpty()) {
//	            return result;
//	        }
//
//	        if (customQuote == ' ' ) {
//	            customQuote = DEFAULT_QUOTE;
//	        }
//
//	        if (separators == ' ') {
//	            separators = DEFAULT_SEPARATOR;
//	        }
//
//	        StringBuffer curVal = new StringBuffer();
//	        boolean inQuotes = false;
//	        boolean startCollectChar = false;
//	        boolean doubleQuotesInColumn = false;
//
//	        char[] chars = cvsLine.toCharArray();
//
//	        for (char ch : chars) {
//
//	            if (inQuotes) {
//	                startCollectChar = true;
//	                if (ch == customQuote) {
//	                    inQuotes = false;
//	                    doubleQuotesInColumn = false;
//	                } else {
//
//	                    //Fixed : allow "" in custom quote enclosed
//	                    if (ch == '\"') {
//	                        if (!doubleQuotesInColumn) {
//	                            curVal.append(ch);
//	                            doubleQuotesInColumn = true;
//	                        }
//	                    } else {
//	                        curVal.append(ch);
//	                    }
//
//	                }
//	            } else {
//	                if (ch == customQuote) {
//
//	                    inQuotes = true;
//
//	                    //Fixed : allow "" in empty quote enclosed
//	                    if (chars[0] != '"' && customQuote == '\"') {
//	                        curVal.append('"');
//	                    }
//
//	                    //double quotes in column will hit this!
//	                    if (startCollectChar) {
//	                        curVal.append('"');
//	                    }
//
//	                } else if (ch == separators) {
//
//	                    result.add(curVal.toString());
//
//	                    curVal = new StringBuffer();
//	                    startCollectChar = false;
//
//	                } else if (ch == '\r') {
//	                    //ignore LF characters
//	                    continue;
//	                } else if (ch == '\n') {
//	                    //the end, break!
//	                    break;
//	                } else {
//	                    curVal.append(ch);
//	                }
//	            }
//
//	        }
//
//	        result.add(curVal.toString());
//
//	        return result;
//	    }



}
