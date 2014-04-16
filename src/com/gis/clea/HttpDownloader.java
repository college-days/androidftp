package com.gis.clea;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpConnection;

import ftp.FileProcessor;
import android.R.integer;

public class HttpDownloader {
	
	private URL url;
	
	public String downloadFile(String urlStr){
		StringBuffer stringBuffer = new StringBuffer();
		BufferedReader bufferedReader = null;
		String line = new String("");
		try {
			this.url = new URL(urlStr); 
			HttpURLConnection httpConnection = (HttpURLConnection)this.url.openConnection();
			bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
			while((line = bufferedReader.readLine()) != null){
				stringBuffer.append(line);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return stringBuffer.toString();
		
	}
	
	public InputStream getInputStreamFromURL(String urlStr){
		try {
			this.url = new URL(urlStr);
			HttpURLConnection httpURLConnection = (HttpURLConnection)this.url.openConnection();
			return httpURLConnection.getInputStream();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public int downloadFileToSDcard(String urlStr, String pathStr, String fileNameStr){
		InputStream inputStream = null;
		FileProcessor fileProcessor = new FileProcessor();
		if(fileProcessor.isFileExist(pathStr + fileNameStr)){
			return 1;
		}else{
			inputStream = getInputStreamFromURL(urlStr);
			if(inputStream == null){
				return -1;
			}
			File resultFile = fileProcessor.writeFile2SDcardFromInputStream(pathStr, fileNameStr, inputStream);
			if(resultFile == null){
				return -1;
			}
		}
		if(inputStream != null){
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return 0;
		
	}

}
