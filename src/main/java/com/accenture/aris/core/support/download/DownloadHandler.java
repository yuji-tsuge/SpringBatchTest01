package com.accenture.aris.core.support.download;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.ibatis.session.ResultHandler;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

public abstract class DownloadHandler implements ResultHandler {

    public static final String DEFAULT_OUTPUT_DIR = "/temp";
    
    public static final String DEFAULT_ENCORDING = "UTF-8";
    
	private CsvBeanWriter writer;
	
	private CsvPreference preference = CsvPreference.EXCEL_PREFERENCE;
	
	private File outDir = new File(DEFAULT_OUTPUT_DIR);
	
	private String encode = DEFAULT_ENCORDING;
	
	private File file;
	
	public void init() throws IOException {
		file = File.createTempFile("FILE_DOWNLOAD.", ".tmp", outDir);
		writer = new CsvBeanWriter(new FileWriterWithEncoding(file, encode), preference);
	}
	
	public CsvPreference getPreference() {
		return preference;
	}
	
	public void setPreference(CsvPreference preference) {
		this.preference = preference;
	}
	
	public File getoutDir() {
		return outDir;
	}
	
	public void setOutDir(File outDir) {
		this.outDir = outDir;
	}
	
	public String getEncode() {
		return encode;
	}
	
	public void setEncode(String encode) {
		this.encode = encode;
	}
	
	public void write(Object object, String[] header, CellProcessor[] cellProcessors) throws IOException {
		writer.write(object, header, cellProcessors);
	}
	
	public File download() throws IOException {
		writer.close();
		return file;
	}
}
