package com.u1.gocashm.util;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import org.json.JSONException;
import org.json.JSONObject;

public class GaodeLocation {

    private AMapLocationClient mLocationClient;


    public interface LocationResultListener {
        void onLocationResult(String json, JSONObject jsonObject);
    }

    private LocationResultListener mLocationResultListener;

    public void setLocationResultListener(LocationResultListener l) {
        this.mLocationResultListener = l;
    }

    public void init(Context context) {
        mLocationClient = new AMapLocationClient(context);
        AMapLocationClientOption option = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        option.setInterval(1000);
        //设置是否返回地址信息（默认返回地址信息）
        option.setNeedAddress(true);
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        mLocationClient.setLocationOption(option);

    }

    public void startLocation() {
        AMapLocationListener mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (null == amapLocation)
                    return;
                Bundle bundle = amapLocation.getExtras();
                int errorCode = amapLocation.getErrorCode();
                String errorInfo = amapLocation.getErrorInfo();
                Log.d("GaodeLocation", "startLocation==errorCode is " + errorCode + ",errorInfo is " + errorInfo + ",bundle is " + bundle);

                double latitude = amapLocation.getLatitude();
                double longitude = amapLocation.getLongitude();
                String country = amapLocation.getCountry();
                String province = amapLocation.getProvince();
                String city = amapLocation.getCity();
                String district = amapLocation.getDistrict();
                String address = amapLocation.getAddress();
                String road = amapLocation.getRoad();
                String street = amapLocation.getStreet();
                String streetNum = amapLocation.getStreetNum();
                String cityCode = amapLocation.getCityCode();
                String adCode = amapLocation.getAdCode();
                String poiName = amapLocation.getPoiName();
                String aoiName = amapLocation.getAoiName();
                //String type = amapLocation.getT
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("latitude", latitude);
                    jsonObject.put("longitude", longitude);
                    jsonObject.put("province", province);
                    jsonObject.put("country", country);
                    jsonObject.put("city", city);
                    jsonObject.put("district", district);
                    jsonObject.put("address", address);
                    jsonObject.put("road", road);
                    jsonObject.put("street", street);
                    jsonObject.put("streetNum", streetNum);
                    jsonObject.put("cityCode", cityCode);
                    jsonObject.put("adCode", adCode);
                    jsonObject.put("poiName", poiName);
                    jsonObject.put("aoiName", aoiName);
                    jsonObject.put("poiName", poiName);

                    if (null != mLocationResultListener) {
                        mLocationResultListener.onLocationResult(jsonObject.toString(), jsonObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
        mLocationClient.stopLocation();
        mLocationClient.startLocation();
    }

    public void stopLocation() {
        if (null != mLocationClient) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        }
    }
}
