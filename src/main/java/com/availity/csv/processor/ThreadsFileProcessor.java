package com.availity.csv.processor;

import java.util.List;
import java.util.concurrent.Callable;

import com.availity.csv.utils.CsvUtils;

public class ThreadsFileProcessor implements Callable<Object>{
	
	private String file;
	private String filePath;
	private int bufferSize;
	

	@Override
	public Object call() throws Exception {
		CsvLoader loader = new CsvLoader();
		loader.loadcsv(file, filePath, 100);
		return null;
	}
	
	public ThreadsFileProcessor(String file, String filePath, int bufferSize) {
		super();
		this.file = file;
		this.filePath = filePath;
		this.bufferSize = bufferSize;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	
	
}
