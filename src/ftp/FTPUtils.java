package ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import android.os.Environment;

/**
 * 2013年09月20日21:38:04
 * 
 * 用于Android和FTP服务器进行交互的工具类
 * 
 * @author xiaoyaomeng
 *
 */
public class FTPUtils {
	private FTPClient ftpClient = null;
	private static FTPUtils ftpUtilsInstance = null;
	private String FTPUrl;
	private int FTPPort;
	private String UserName;
	private String UserPassword;
	
	private FTPUtils()
	{
		ftpClient = new FTPClient();
	}
	/*
	 * 得到类对象实例（因为只能有一个这样的类对象，所以用单例模式）
	 */
	public  static FTPUtils getInstance() {
		if (ftpUtilsInstance == null)
		{
			ftpUtilsInstance = new FTPUtils();
		}
		return ftpUtilsInstance;
	}
	
	/**
	 * 设置FTP服务器
	 * @param FTPUrl   FTP服务器ip地址
	 * @param FTPPort   FTP服务器端口号
	 * @param UserName    登陆FTP服务器的账号
	 * @param UserPassword    登陆FTP服务器的密码
	 * @return
	 */
	public boolean initFTPSetting(String FTPUrl, int FTPPort, String UserName, String UserPassword)
	{	
		this.FTPUrl = FTPUrl;
		this.FTPPort = FTPPort;
		this.UserName = UserName;
		this.UserPassword = UserPassword;
		
		int reply;
		
		try {
			//1.要连接的FTP服务器Url,Port
			ftpClient.connect(FTPUrl, FTPPort);
			
			//2.登陆FTP服务器
			ftpClient.login(UserName, UserPassword);
			
			//3.看返回的值是不是230，如果是，表示登陆成功
			reply = ftpClient.getReplyCode();
		
			if (!FTPReply.isPositiveCompletion(reply))
			{
				//断开
				ftpClient.disconnect();
				return false;
			}
			
			return true;
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 上传文件
	 * @param FilePath    要上传文件所在SDCard的路径
	 * @param FileName    要上传的文件的文件名(如：Sim唯一标识码)
	 * @return    true为成功，false为失败
	 */
	public boolean uploadFile(String FilePath, String FileName) {
		
		if (!ftpClient.isConnected())
		{
			if (!initFTPSetting(FTPUrl,  FTPPort,  UserName,  UserPassword))
			{
				return false;
			}
		}
		
		try {
			
			//设置存储路径
			ftpClient.makeDirectory("/data");
			ftpClient.changeWorkingDirectory("/data");
			
			//设置上传文件需要的一些基本信息
			ftpClient.setBufferSize(1024);  
	        ftpClient.setControlEncoding("UTF-8"); 
	        ftpClient.enterLocalPassiveMode();   
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	        
	        //文件上传吧～
	        FileInputStream fileInputStream = new FileInputStream(Environment.getExternalStorageDirectory()+ "/" + FilePath);
	        ftpClient.storeFile(FileName, fileInputStream);
	        
	        //关闭文件流
	        fileInputStream.close();
	        
	        //退出登陆FTP，关闭ftpCLient的连接
	        ftpClient.logout();
	        ftpClient.disconnect();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 下载文件
	 * @param FilePath  要存放的文件的路径
	 * @param FileName   远程FTP服务器上的那个文件的名字
	 * @return   true为成功，false为失败
	 */
	public boolean downLoadFile(String LocalFilePath, String LocalFileName, String FileName) {
		
		if (!ftpClient.isConnected())
		{
			if (!initFTPSetting(FTPUrl,  FTPPort,  UserName,  UserPassword))
			{
				return false;
			}
		}
		 
		try {
			ftpClient.enterLocalPassiveMode();
			// 转到指定下载目录
			ftpClient.changeWorkingDirectory("/data");
			InputStream inputStream = ftpClient.retrieveFileStream(FileName);
			FileProcessor fileProcessor = new FileProcessor();
	        fileProcessor.writeFile2SDcardFromInputStream(LocalFilePath, LocalFileName, inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
