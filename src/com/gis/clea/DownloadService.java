package com.gis.clea;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service{

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("service is on!!!");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		DownloadFileThread downloadMp3Thread = new DownloadFileThread();
		new Thread(downloadMp3Thread).start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println("service destroy");
	}
	
	class DownloadFileThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String downloadURL = "http://www.seuknower.com/Uploads/Images/Event/Poster/Thumb/t_5343db3456704.jpg";
			System.out.println(downloadURL);
			HttpDownloader httpDownloader = new HttpDownloader();
			int result = httpDownloader.downloadFileToSDcard(downloadURL, "ftp/", "test.jpg");			
			System.out.println("download mp3 file result is : " + result);
			System.out.println("finish downloading");
		}
		
	}
	
}
