package com.accenture.aris.core.support.excel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelUtils {
	
	public static byte[] transform(InputStream inputStream, Map<String, Object> map) throws FileNotFoundException, IOException, GeneralSecurityException {
		
		HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(inputStream));
		XLSTransformer transformer = new XLSTransformer();
		transformer.transformWorkbook(workbook, map);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		workbook.write(out);
		byte[] data = out.toByteArray();
		out.close();
		
		return data;
		
	}
	
}
