package com.accenture.aris.core.support.zip;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipUtils {
	
	public static byte[] compress(byte[] data, String fileName, String password) throws FileNotFoundException, IOException, ZipException {
		
		ZipModel model = new ZipModel();
		ZipParameters params = new ZipParameters();
		params.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		params.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		params.setEncryptFiles(true);
		params.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
		params.setPassword(password);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(bos, model);
		params.setSourceExternalStream(true);
		params.setFileNameInZip(fileName);
		zos.putNextEntry(null, params);
		zos.write(data);
		zos.closeEntry();
		zos.finish();
		zos.close();
		
		return bos.toByteArray();
	}
}
