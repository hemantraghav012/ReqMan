package com.reqman.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil 
{
	
	public static byte[] readBytesFromFile(String filePath) {
		byte[] fileBytes = null;
		File inputFile = null;
		FileInputStream inputStream = null;
		try {
			inputFile = new File(filePath);
			inputStream = new FileInputStream(inputFile);
			fileBytes = new byte[(int) inputFile.length()];
			inputStream.read(fileBytes);
			inputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
			// throw new Exception(e);
		}
		return fileBytes;
	}

	public static void saveBytesToFile(String filePath, byte[] fileBytes) {
		FileOutputStream outputStream = null;

		try {
			outputStream = new FileOutputStream(filePath);
			outputStream.write(fileBytes);
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}