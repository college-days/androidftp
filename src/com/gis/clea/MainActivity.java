package com.gis.clea;

import com.example.com.gis.clea.R;

import ftp.FTPDownloadService;
import ftp.FTPUploadService;
import ftp.FTPUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends ActionBarActivity implements OnClickListener{
    private Button buttonUpLoad = null;  
    private Button buttonDownLoad = null;
    private Button buttonHttpDownload = null;
    private Button buttonStopHttp = null;
    private TextView showTextView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		this.buttonDownLoad = (Button)this.findViewById(R.id.button_download);
		this.buttonUpLoad = (Button)this.findViewById(R.id.button_upload);
		this.buttonHttpDownload = (Button)this.findViewById(R.id.http_download);
		this.buttonStopHttp = (Button)this.findViewById(R.id.stop_http);
		this.buttonDownLoad.setOnClickListener(this);
		this.buttonUpLoad.setOnClickListener(this);
		this.buttonHttpDownload.setOnClickListener(this);
		this.buttonStopHttp.setOnClickListener(this);
		this.showTextView = (TextView)this.findViewById(R.id.show_view);
		
		this.showTextView.setText(Environment.getExternalStorageDirectory().getAbsolutePath());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_upload:
				Log.d("debug", "upload");
				this.showTextView.setText("upload");
				Intent uploadIntent = new Intent(this, FTPUploadService.class);
				this.startService(uploadIntent);
				break;
			case R.id.button_download:
				Log.d("debug", "download");
				this.showTextView.setText("download");
				Intent downloadIntent = new Intent(this, FTPDownloadService.class);
				this.startService(downloadIntent);
				break;
			case R.id.http_download:
				Log.d("debug", "http_download");
				this.showTextView.setText("httpdownload");
				Intent httpDownloadIntent = new Intent(this, DownloadService.class);
				this.startService(httpDownloadIntent);
				break;
			case R.id.stop_http:
				this.showTextView.setText("stophttp");
				Intent stopHttpIntent = new Intent(this, DownloadService.class);
				this.stopService(stopHttpIntent);
			default:
				break;
		}
	}
	
}
