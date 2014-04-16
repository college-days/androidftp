只要使用ftp那个package就ok了，另一个包里是一个例子，可以直接运行，连得是163ftp

## requirements

* commons-net-3.0.1.jar

## quick start

* should config ftpservername ftpport ftpusername ftppassword in ```FTPConfig.java```

以163为例

```java
public static final String FTPSERVER= "10.214.55.163";
public static final int FTPPORT = 21;
public static final String FTPUSERNAME = "lin";
public static final String FTPPASSWORD = "501";
```

## simple useage in activity

* upload file

```java
Intent uploadIntent = new Intent(this, FTPUploadService.class);
uploadIntent.putExtra("localfilename", "ftp/test.jpg");
uploadIntent.putExtra("remotefilename", "test.jpg");
this.startService(uploadIntent);
```

* download file

```java
Intent downloadIntent = new Intent(this, FTPDownloadService.class);
downloadIntent.putExtra("localfilename", "333.jpg");
downloadIntent.putExtra("localfilepath", "ftp/");
downloadIntent.putExtra("remotefilename", "test.jpg");
this.startService(downloadIntent);
```

## manifest

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>

<service android:name="ftp.FTPUploadService"></service>
<service android:name="ftp.FTPDownloadService"></service>
```

## common bugs

* 默认路径是在```/storage/sdcard```(_/sdcard_)，如果下载的时候```localfilename```一样的话会有异常，所以最好在保存的filename中加有唯一标识(_时间戳之类的_)