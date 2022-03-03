package com.u1.gocashm.util.http;

import android.util.Log;

import com.u1.gocashm.model.request.JpushReqModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.JpushRespModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.GsonPhHelper;
import com.u1.gocashm.util.model.CallModel;
import com.u1.gocashm.util.model.ContactModel;
import com.u1.gocashm.util.model.DeviceNewInfo;
import com.u1.gocashm.util.model.MessageModel;

import org.json.JSONObject;

import java.util.HashMap;

import java.util.Map;

import okhttp3.RequestBody;

public class UploadInfoUtils {
    private static final String TAG = UploadInfoUtils.class.getSimpleName();

    /**
     * 上传通讯录
     *
     * @param contactModel
     */
    public static void updateContact(ContactModel contactModel) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("data_json", contactModel.getData());
        maps.put("token", contactModel.getToken());
        NetworkPhRequest.contactInfo(getRequestBody(maps), new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                Log.d(TAG, basePhModel.toString());
            }
        });
    }

    /**
     * 上传通讯录总数
     */
    public static void updateContactCount(String token, String count) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("count", count);
        maps.put("token", token);
        NetworkPhRequest.uploadContactCount(getRequestBody(maps), new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                Log.d(TAG, basePhModel.toString());
            }
        });
    }

    /**
     * 上传通话记录
     *
     * @param callModel
     */
    public static void updateCall(CallModel callModel) {
        NetworkPhRequest.callRecordInfo(callModel, new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                Log.d(TAG, basePhModel.toString());
            }
        });
    }

    /**
     * 上传信息记录
     *
     * @param messageModel
     */
    public static void updateMessage(MessageModel messageModel) {
        NetworkPhRequest.messageInfo(messageModel, new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                Log.d(TAG, basePhModel.toString());
            }
        });
    }

    /**
     * 上传设备信息
     *
     * @param deviceInfo
     */
    public static void updateDevice(String token, DeviceNewInfo deviceInfo) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("data_json", GsonPhHelper.getGson().toJson(deviceInfo));
        maps.put("token", token);
        NetworkPhRequest.loginDeviceInfo(getRequestBody(maps), new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                Log.d(TAG, basePhModel.toString());
            }
        });
    }

    public static void saveJpushInfo(JpushReqModel model) {
        NetworkPhRequest.uploadJpushUserInfo(model, new PhSubscriber<JpushRespModel>() {
            @Override
            public void onNext(JpushRespModel jpushRespModel) {
                super.onNext(jpushRespModel);
                if ("0".equals(jpushRespModel.getCode())) {
                    Log.d(TAG, jpushRespModel.getMessage());
                }
            }
        });
    }

    /**
     * 上传位置信息
     */
    public static void uploadLocation(String token, String json) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("data_json", json);
        maps.put("token", token);
        NetworkPhRequest.uploadLocation(getRequestBody(maps), new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel baseRcModel) {
                super.onNext(baseRcModel);
                Log.d(TAG, baseRcModel.toString());
            }
        });
    }

    /**
     * 上传相册信息
     */
    public static void uploadPhotoInfo(String token, String json) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("data_json", json);
        maps.put("token", token);
        NetworkPhRequest.uploadPhotoInfo(getRequestBody(maps), new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel baseRcModel) {
                super.onNext(baseRcModel);
                Log.d(TAG, baseRcModel.toString());
            }
        });
    }

    public static RequestBody getRequestBody(Map<String, Object> mapParams) {
        JSONObject json = new JSONObject(mapParams);//JSONUtil.mapToJson(mapParams);
        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json.toString());
    }
}
