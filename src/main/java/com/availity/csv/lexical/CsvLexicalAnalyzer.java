package com.availity.csv.lexical;

import com.availity.csv.lexical.enumer.CsvColumnTypeEnum;
import com.availity.csv.lexical.enumer.LexicalActionEnum;
import com.availity.csv.lexical.enumer.LexicalTokenEnum;
import com.availity.csv.lexical.enumer.LineStatusEnum;

public class CsvLexicalAnalyzer {

    private final char delimiter;
    private final char quoteChar;
    private CsvColumnTypeEnum COLUMN_TYPE;
    private LexicalActionEnum LEXICAL_ACTION;
    private LexicalTokenEnum CURR_LEXICAL_TOKEN;
    private LexicalTokenEnum PREV_LEXICAL_TOKEN;
    private LineStatusEnum LINE_STATUS;
    private int quotesInQuotedColumn;
    private int columnNum;
    private boolean isNewColumn = true;

    public CsvLexicalAnalyzer(final CsvFormat format) {
        this.delimiter = format.getDelimiter();
        this.quoteChar = format.getQuoteCharacter();
    }
    
    public void resetAnalyzer() {
    	COLUMN_TYPE = null;
    	LEXICAL_ACTION = null;
    	CURR_LEXICAL_TOKEN = null;
    	PREV_LEXICAL_TOKEN = null;
    	LINE_STATUS = null;
    	quotesInQuotedColumn = 0;
    	isNewColumn = true;
    }
    
 	public void analyze(int currPos, String line) throws Exception{
 		if(currPos == 0) resetAnalyzer();
 		int lineLength = line.length();
 		char currChar = line.charAt(currPos);
 		char prevChar = '\u0000';
 		if(currPos > 0) prevChar = line.charAt(currPos-1);
 		if(currChar == delimiter) {
 			PREV_LEXICAL_TOKEN = CURR_LEXICAL_TOKEN;
 			CURR_LEXICAL_TOKEN = LexicalTokenEnum.COMMA;
 		}else if(currChar == quoteChar) {
 			PREV_LEXICAL_TOKEN = CURR_LEXICAL_TOKEN;
 			CURR_LEXICAL_TOKEN = LexicalTokenEnum.QUOTE;
 			quotesInQuotedColumn++;
 		}
 		if(currPos+1 == lineLength) {
 			PREV_LEXICAL_TOKEN = CURR_LEXICAL_TOKEN;
 			CURR_LEXICAL_TOKEN = LexicalTokenEnum.EOL;
 		}
 		
		//initialize parsor indicators for a new column
		if(currPos == 0 || isNewColumn) {
			isNewColumn = false;
			LEXICAL_ACTION = LexicalActionEnum.STARTCOLUMN;
			if(currChar == quoteChar) {
				LEXICAL_ACTION = LexicalActionEnum.ADDCHAR;
				COLUMN_TYPE = CsvColumnTypeEnum.Quoted;
			}
			else {
				LEXICAL_ACTION = LexicalActionEnum.ADDCHAR;
				COLUMN_TYPE = CsvColumnTypeEnum.UNQUOTED;
			}
			if(currChar == delimiter) {
				LINE_STATUS = LineStatusEnum.FAIL;
				throw new Exception("The record can't start with delimiter - " + delimiter);
			}
		}
		else {
			if(CsvColumnTypeEnum.Quoted.equals(COLUMN_TYPE)) {
				
				//skip all characcters after column is added
				if(currChar != delimiter && LEXICAL_ACTION == LexicalActionEnum.ADDCOLUMN) return;
				else if(currChar == delimiter && LEXICAL_ACTION == LexicalActionEnum.ADDCOLUMN) resetAnalyzer();
				
				//condition for EOC - end of column case quoted column
				if(currChar == quoteChar) {
					if(currPos!=line.length()-1 && line.charAt(currPos+1) != quoteChar && line.charAt(currPos-1) != quoteChar) {
						LEXICAL_ACTION = LexicalActionEnum.ADDCOLUMN;
						if(!(quotesInQuotedColumn == 2 || (quotesInQuotedColumn-1) % 4 == 0)) {
							throw new Exception("Line is not properly closed with quotes");
						}
					}
					else {
						LEXICAL_ACTION = LexicalActionEnum.ADDCHAR;
					}
				}
			}
			else {
				//condition for EOC - end of column case unquoted column
				if(currChar == delimiter) {
					LEXICAL_ACTION = LexicalActionEnum.ADDCOLUMN;
					quotesInQuotedColumn = 0;
				}else {
					LEXICAL_ACTION = LexicalActionEnum.ADDCHAR;
				}
			}
	 		if(currPos+1 == lineLength) {
	 			CURR_LEXICAL_TOKEN = LexicalTokenEnum.EOC;
	 			LEXICAL_ACTION = LexicalActionEnum.ADDCOLUMN;
	 		}

		}
		
	}
 	
 	
    public char getQuoteChar() {
 		return quoteChar;
 	}

 	public char getDelimiter() {
 		return delimiter;
 	}

 	public CsvColumnTypeEnum getCOLUMN_TYPE() {
 		return COLUMN_TYPE;
 	}

 	public void setCOLUMN_TYPE(CsvColumnTypeEnum cOLUMN_TYPE) {
 		COLUMN_TYPE = cOLUMN_TYPE;
 	}

 	public LexicalActionEnum getLEXICAL_ACTION() {
 		return LEXICAL_ACTION;
 	}

 	public void setLEXICAL_ACTION(LexicalActionEnum lEXICAL_ACTION) {
 		LEXICAL_ACTION = lEXICAL_ACTION;
 	}

 	
 	public LineStatusEnum getLINE_STATUS() {
 		return LINE_STATUS;
 	}

 	public void setLINE_STATUS(LineStatusEnum lINE_STATUS) {
 		LINE_STATUS = lINE_STATUS;
 	}

 	public int getQuotesInQuotedColumn() {
 		return quotesInQuotedColumn;
 	}

 	public void setQuotesInQuotedColumn(int quotesInQuotedColumn) {
 		this.quotesInQuotedColumn = quotesInQuotedColumn;
 	}
 	
 	public LexicalTokenEnum getCURR_LEXICAL_TOKEN() {
 		return CURR_LEXICAL_TOKEN;
 	}

 	public void setCURR_LEXICAL_TOKEN(LexicalTokenEnum cURR_LEXICAL_TOKEN) {
 		CURR_LEXICAL_TOKEN = cURR_LEXICAL_TOKEN;
 	}

 	public LexicalTokenEnum getPREV_LEXICAL_TOKEN() {
 		return PREV_LEXICAL_TOKEN;
 	}

 	public void setPREV_LEXICAL_TOKEN(LexicalTokenEnum pREV_LEXICAL_TOKEN) {
 		PREV_LEXICAL_TOKEN = pREV_LEXICAL_TOKEN;
 	}

	public int getColumnNum() {
		return columnNum;
	}

	public void setColumnNum(int columnNum) {
		this.columnNum = columnNum;
	}

	public boolean isNewColumn() {
		return isNewColumn;
	}

	public void setNewColumn(boolean isNewColumn) {
		this.isNewColumn = isNewColumn;
	}
	
}
