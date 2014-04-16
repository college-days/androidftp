package ftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;

public class FTPDownloadService extends Service{
	
	//FTP工具类  
    private FTPUtils ftpUtils = null;
    String localFileName = "";
    String localFilePath = "";
    String remoteFileName = "";
	
	@Override
	public IBinder onBind(Intent intent) {
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
		this.localFileName = intent.getStringExtra("localfilename");
		this.localFilePath = intent.getStringExtra("localfilepath");
		this.remoteFileName = intent.getStringExtra("remotefilename");
		System.out.println(localFilePath+localFileName);
		DownloadFileThread downloadFileThread = new DownloadFileThread();
		new Thread(downloadFileThread).start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	public void InitFTPServerSetting() {  
        // TODO Auto-generated method stub  
        ftpUtils = FTPUtils.getInstance();  
        boolean flag = ftpUtils.initFTPSetting(FTPConfig.FTPSERVER, FTPConfig.FTPPORT, FTPConfig.FTPUSERNAME, FTPConfig.FTPPASSWORD); 
        if (flag) {
			System.out.println("ftp登录成功");
		}else{
			System.out.println("ftp登录失败");
		}
    }  
	
	class DownloadFileThread implements Runnable{

		@Override
		public void run() {     
			InitFTPServerSetting();
			ftpUtils.downLoadFile(localFilePath, localFileName, remoteFileName);
			System.out.println("download finish");
		}
		
	}

}
