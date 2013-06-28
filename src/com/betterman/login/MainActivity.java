package com.betterman.login;

import com.baidu.location.*;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;

import com.betterman.login.Location;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.R;
import com.google.zxing.client.android.book.SearchBookContentsActivity;

public class MainActivity extends Activity 
{
	public Bundle bundl2;
	private Bundle bundl;
	public double latitude=0;
	public double lontitude=0;
	private Button scanning;
	private Button image;
	private Button ll;
	private TextView llsh=null;
	private LocationClient mLocClient;
	private Vibrator mVibrator01 =null;
	//StringBuffer sb = new StringBuffer(256);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
        scanning = (Button)findViewById(R.id.scanning_analytic);
        image=(Button)findViewById(R.id.image_analytic);
        ll=(Button)findViewById(R.id.button1);
        llsh=(TextView)findViewById(R.id.textView1);
    	mLocClient = ((Location)getApplication()).mLocationClient;
		((Location)getApplication()).mTv = llsh;
		mVibrator01 =(Vibrator)getApplication().getSystemService(Context.VIBRATOR_SERVICE);
		((Location)getApplication()).mVibrator01 = mVibrator01;
		
		scanning.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent();
				
				intent.setClass(MainActivity.this, CaptureActivity.class);
				/*bundl2.putDouble("latitude", latitude);
				bundl2.putDouble("lontitude",lontitude);
				intent.putExtras(bundl2);*/
				MainActivity.this.startActivity(intent);
			}
			
		});
		
		image.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ImageActivity.class);
				MainActivity.this.startActivity(intent);
			}
			
		});
		
		ll.setOnClickListener(new OnClickListener()
		{
			/* (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent();//new
			intent.setClass(MainActivity.this, Location.class);//new
				setLocationOption();
				mLocClient.start();
				if (mLocClient != null && mLocClient.isStarted()){
					mLocClient.requestLocation();
               /*bundl=intent.getBundleExtra("Bundle");//new
               latitude=bundl.getDouble("latitude");//new
               lontitude=bundl.getDouble("lontitude");//new
               sb.append(latitude);
               sb.append(lontitude);
               llsh.setText(sb.toString());*/
				}
	            
			}
			
		});
	}
	
	@Override
	public void onDestroy() {
		mLocClient.stop();
		((Location)getApplication()).mTv = null;
		super.onDestroy();
	}
	
	private void setLocationOption(){
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(false);				//打开gps
		option.setCoorType("bd09ll");		//设置坐标类型
		option.setAddrType("all");		//设置地址信息，仅设置为“all”时有地址信息，默认无地址信息
		option.setScanSpan(0);	//设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
		mLocClient.setLocOption(option);
	}

	
	}



