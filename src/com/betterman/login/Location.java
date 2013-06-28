package com.betterman.login;

import com.baidu.location.*;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Process;
import android.os.Vibrator;

public class Location extends Application {

	public LocationClient mLocationClient = null;
	private String mData;  
	public MyLocationListener myListener = new MyLocationListener();
	public TextView mTv=null;
	public NotifyLister mNotifyer=null;
	public Vibrator mVibrator01;
	public double latitude=0;
	public double lontitude=0;
	//public Bundle bundle=new Bundle();//new
	@Override
	public void onCreate() {
		mLocationClient = new LocationClient( getApplicationContext() );
		mLocationClient.registerLocationListener( myListener );
		/**
		//如下3行代码为 位置提醒相关代码
		mNotifyer = new NotifyLister();
		mNotifyer.SetNotifyLocation(42.03249652949337,113.3129895882556,3000,"gps");//4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
		mLocationClient.registerNotify(mNotifyer);
		*/
		super.onCreate(); 
		Log.d("locSDK_Demo1", "... Application onCreate... pid=" + Process.myPid());
	}
	
	/**
	 * 显示字符串
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			mData = str;
			if ( mTv != null )
				mTv.setText(mData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return ;
			StringBuffer sb = new StringBuffer(256);
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			latitude=location.getLatitude();
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			lontitude=location.getLongitude();
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			
			/*bundle.putDouble("latitude", latitude);//new
			bundle.putDouble("lontitude", lontitude);//new*/
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\n省：");
				sb.append(location.getProvince());
				sb.append("\n市：");
				sb.append(location.getCity());
				sb.append("\n区/县：");
				sb.append(location.getDistrict());
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}
			sb.append("\nsdk version : ");
			sb.append(mLocationClient.getVersion());
			logMsg(sb.toString());
		}
		
		public double getlatitude()
		{
			return latitude;
		}
		
		public double getlonitude()
		{
			return lontitude;
		}
		
		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null){
				return ;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			} 
			if(poiLocation.hasPoi()){
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			}else{				
				sb.append("noPoi information");
			}
			logMsg(sb.toString());
		}
	}
	
	/**
	 * 位置提醒回调函数
	 */
	public class NotifyLister extends BDNotifyListener{
		public void onNotify(BDLocation mlocation, float distance){
			mVibrator01.vibrate(1000);
		}
	}
}