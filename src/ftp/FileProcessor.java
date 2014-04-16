package ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Path.Direction;
import android.os.Environment;

public class FileProcessor {
	
	private String SDcardPath = null;

	public String getSDcardPath() {
		return SDcardPath;
	}
	
	public FileProcessor(){
		this.SDcardPath = Environment.getExternalStorageDirectory() + "/";
	}
	
	public File createDictionary(String dirString){
		File dir = new File(this.SDcardPath + dirString);
		dir.mkdir();
		return dir;
	}
	
	public File createFile(String fileString){
		File file = new File(this.SDcardPath + fileString);
		try {
			file.createNewFile();
			return file;
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("can not create file");
			return null;
			
		}
			
	}
	
	public boolean isFileExist(String fileName){
		File file = new File(this.SDcardPath + fileName);
		return file.exists();
	}
	
	public File writeFile2SDcardFromInputStream(String pathStr, String filenameStr, InputStream inputStream){
		File dir = null;
		File file = null;
		OutputStream outputStream = null;
		
		try {
			dir = createDictionary(pathStr);		
		} catch (NullPointerException e) {
			// TODO: handle exception
			System.out.println("dictionary is null");
		}try {
			file = createFile(pathStr + filenameStr);
		} catch (NullPointerException e1) {
			// TODO Auto-generated catch block
			System.out.println("file is null");
		}
		try {
			outputStream = new FileOutputStream(file);
			byte[] buffer = new byte[4*1024];
			
			/*
			 * 这样写貌似会导致下载下来资源的大小会比服务器实际资源大小要大
			 * 什么原因
			while((inputStream.read(buffer)) != -1){
				outputStream.write(buffer);
			}*/
			
			int temp;
			while((temp = (inputStream.read(buffer))) != -1){
				outputStream.write(buffer, 0, temp);
			}
			outputStream.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return file;
	}
}
