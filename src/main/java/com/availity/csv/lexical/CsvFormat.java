package com.availity.csv.lexical;

import static com.availity.csv.lexical.Constants.COMMA;
import static com.availity.csv.lexical.Constants.CRLF;
import static com.availity.csv.lexical.Constants.DOUBLE_QUOTE_CHAR;

import java.io.Serializable;


public class CsvFormat implements Serializable{

	private static final long serialVersionUID = 1L;
	
    public static final CsvFormat DEFAULT = new CsvFormat(COMMA, DOUBLE_QUOTE_CHAR, CRLF, null);	
    
    private CsvFormat(final char delimiter, final char quoteChar, final String eol, final String[] header) {
        this.delimiter = delimiter;
        this.quoteCharacter = quoteChar;
        this.header = header == null ? null : header.clone();
    }
    

    private final String[] header; // array of header column names
    private char delimiter;
    private char quoteCharacter;
    private String eol;

	public char getDelimiter() {
		return delimiter;
	}


	public void setDelimiter(char delimiter) {
		this.delimiter = delimiter;
	}


	public char getQuoteCharacter() {
		return quoteCharacter;
	}


	public void setQuoteCharacter(char quoteCharacter) {
		this.quoteCharacter = quoteCharacter;
	}


	public String getEol() {
		return eol;
	}


	public void setEol(String eol) {
		this.eol = eol;
	}


	public String[] getHeader() {
		return header;
	}
   
    
    
}
