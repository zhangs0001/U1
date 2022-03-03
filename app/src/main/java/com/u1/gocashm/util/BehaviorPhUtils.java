package com.u1.gocashm.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.ScreenUtils;
import com.google.gson.Gson;
import com.u1.gocashm.PhApplication;
import com.u1.gocashm.model.request.BehaviorPhReqModel;
import com.u1.gocashm.model.request.BehaviorRecord;
import com.u1.gocashm.model.request.BehaviorRecordReqModel;
import com.u1.gocashm.model.request.RecordPhModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.util.constant.PhConstants;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * 行为数据工具类
 * Created by Brain on 2018/9/20.
 */

public class BehaviorPhUtils {

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    /**
     * 清除行为数据缓存
     *
     * @param bfModel
     */
    @Deprecated
    public static void clearBfModel(BehaviorPhReqModel bfModel) {
        if (bfModel.getRecords() != null && bfModel.getRecords().size() > 0) {
            bfModel.getRecords().clear();
        }
    }

    /**
     * @param id
     * @param context
     * @deprecated use {@link #setEnterPageModifyV2(String, Context)}
     * 进入页面事件
     */
    @Deprecated
    public static BehaviorPhReqModel setEnterPageModify(String id, Context context) {
        long applyId = 0;
        try {
            applyId = SharedPreferencesPhUtil.getInstance(context).getLong(PhConstants.PhUser.APPLY_ID, 0L);
        } catch (Exception e) {
            Log.d("BehaviorPhReqModel", new Gson().toJson(e));
        }
        // 进入页面时创建行为数据model
        BehaviorPhReqModel bfModel = new BehaviorPhReqModel();
        bfModel.setApplyId(applyId + "");
        bfModel.setCookieId(AppPhUtils.getDeviceId());
        bfModel.setInternalIp(AppPhUtils.getHostIP());
        bfModel.setScreenHeight(ScreenUtils.getScreenHeight() + "");
        bfModel.setScreenWidth(ScreenUtils.getScreenWidth() + "");
        // 进入页面事件
        RecordPhModel recordPhModel = new RecordPhModel();
        recordPhModel.setId(id);
        recordPhModel.setStartTime(format.format(new Date()));
        bfModel.getRecords().add(recordPhModel);
        return bfModel;
    }


    /**
     * 进入页面事件
     *
     * @param id
     * @param context
     */
    public static BehaviorRecordReqModel setEnterPageModifyV2(String id, Context context) {
        // 进入页面时创建行为数据model
        BehaviorRecordReqModel bfModel = new BehaviorRecordReqModel();
        bfModel.setToken(TokenUtils.getToken(PhApplication.getContext()));
        // 进入页面事件
        BehaviorRecord recordPhModel = new BehaviorRecord();
        recordPhModel.setControlNo(id);
        recordPhModel.setStartTime(String.valueOf(System.currentTimeMillis()));
        bfModel.getRecords().add(recordPhModel);
        return bfModel;
    }


    /**
     * 输入框获取焦点
     *
     * @param
     */
    @Deprecated
    public static RecordPhModel setStartModify(String id, String value) {
        RecordPhModel recordPhModel = new RecordPhModel();
        recordPhModel.setId(id);
        recordPhModel.setOldValue(value);
        recordPhModel.setStartTime(format.format(new Date()));
        return recordPhModel;
    }

    /**
     * 输入框失去焦点
     *
     * @param recordPhModel
     * @param bfModel
     * @param id
     * @param value
     */
    @Deprecated
    public static void setEndModify(RecordPhModel recordPhModel, BehaviorPhReqModel bfModel, String id, String value) {
        recordPhModel.setEndTime(format.format(new Date()));
        recordPhModel.setId(id);
        recordPhModel.setNewValue(value);
        bfModel.getRecords().add(recordPhModel);
    }


    /**
     * @param bfModel
     * @param id
     * @param oldValue
     * @param newValue
     * @deprecated use {@link #setChangeModifyV2(BehaviorRecordReqModel, String, String, String)}
     * <p>
     * Select,Radio change事件
     */
    @Deprecated
    public static void setChangeModify(BehaviorPhReqModel bfModel, String id, String oldValue, String newValue) {
        RecordPhModel recordPhModel = new RecordPhModel();
        recordPhModel.setStartTime(format.format(new Date()));
        recordPhModel.setId(id);
        recordPhModel.setNewValue(newValue);
        recordPhModel.setOldValue(oldValue);
        bfModel.getRecords().add(recordPhModel);
    }

    /**
     * Select,Radio change事件
     *
     * @param bfModel
     * @param id
     * @param oldValue
     * @param newValue
     */
    public static void setChangeModifyV2(BehaviorRecordReqModel bfModel, String id, String oldValue, String newValue) {
        BehaviorRecord recordPhModel = new BehaviorRecord();
        recordPhModel.setStartTime(String.valueOf(System.currentTimeMillis()));
        recordPhModel.setControlNo(id);
        recordPhModel.setNewValue(newValue);
        recordPhModel.setOldValue(oldValue);
        bfModel.getRecords().add(recordPhModel);
    }

    /**
     * @param bfModel
     * @param id
     * @deprecated use {@link #setClickModifyV2(BehaviorRecordReqModel, String)}
     * 按钮事件
     */
    @Deprecated
    public static void setClickModify(BehaviorPhReqModel bfModel, String id) {
        RecordPhModel recordPhModel = new RecordPhModel();
        recordPhModel.setStartTime(format.format(new Date()));
        recordPhModel.setId(id);
        bfModel.getRecords().add(recordPhModel);
    }

    /**
     * 按钮事件
     *
     * @param bfModel
     * @param id
     */
    public static BehaviorRecord setClickModifyV2(BehaviorRecordReqModel bfModel, String id) {
        return setClickModifyV2(bfModel, id, false);
    }

    /**
     * 按钮事件
     *
     * @param bfModel
     * @param id
     */
    public static BehaviorRecord setClickModifyV2(BehaviorRecordReqModel bfModel, String id, boolean flag) {
        if (bfModel == null) return null;
        if (flag) {
            for (BehaviorRecord record : bfModel.getRecords()) {
                if (record.getControlNo().equals(id)) {
                    bfModel.getRecords().remove(record);
                    break;
                }
            }
        }
        BehaviorRecord recordPhModel = new BehaviorRecord();
        recordPhModel.setStartTime(String.valueOf(System.currentTimeMillis()));
        recordPhModel.setControlNo(id);
        bfModel.getRecords().add(recordPhModel);
        return recordPhModel;
    }


    /**
     * @param bfModel
     * @param id
     * @deprecated use {@link #setDestroyModifyV2(BehaviorRecordReqModel, String)}
     * 页面销毁事件
     */
    public static void setDestroyModify(BehaviorPhReqModel bfModel, String id) {
        RecordPhModel recordPhModel = new RecordPhModel();
        recordPhModel.setStartTime(format.format(new Date()));
        recordPhModel.setId(id);
        bfModel.getRecords().add(recordPhModel);
    }


    /**
     * 页面销毁事件
     *
     * @param bfModel
     * @param id
     */
    public static BehaviorRecord setDestroyModifyV2(BehaviorRecordReqModel bfModel, String id) {
        BehaviorRecord recordPhModel = new BehaviorRecord();
        recordPhModel.setStartTime(String.valueOf(System.currentTimeMillis()));
        recordPhModel.setControlNo(id);
        bfModel.getRecords().add(recordPhModel);
        return recordPhModel;
    }


    /**
     * 传感器 事件
     *
     * @param bfModel
     * @param id
     */
    @Deprecated
    public static void setSensorValue(BehaviorPhReqModel bfModel, String id, String x, String y, String z) {
        RecordPhModel recordPfModel = new RecordPhModel();
        recordPfModel.setStartTime(format.format(new Date()));
        recordPfModel.setId(id);
        recordPfModel.setNewValue("X:" + x + " Y:" + y + " Z:" + z);
        bfModel.getRecords().add(recordPfModel);
    }

    /**
     * @param bfModel
     * @deprecated use {@link #saveBehaviorReqModelV2(BehaviorRecordReqModel)}
     * 保存行为数据
     */
    @Deprecated
    public static void saveBehaviorReqModel(final BehaviorPhReqModel bfModel) {
        // 调用后台保存数据
        /*NetworkPhRequest.uploadBehaviorInfo(bfModel, new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                if (basePhModel.getCode().equals(CODE_SUCCESS)) {
                    //清除缓存数据
                    clearBfModel(bfModel);
                }
            }
        });*/
    }

    /**
     * 保存行为数据
     *
     * @param bfModel
     */
    public static void saveBehaviorReqModelV2(final BehaviorRecordReqModel bfModel) {
        LogUtil.w("upload behavior records:" + bfModel.toString());
        PhSubscriber<BasePhModel> subscriber = new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                if (basePhModel.getCode().equals(CODE_SUCCESS)) {
                    if (bfModel.getRecords() != null && bfModel.getRecords().size() > 0) {
                        bfModel.getRecords().clear();
                    }
                }
            }
        };
        if (TextUtils.isEmpty(bfModel.getToken())) {
            NetworkPhRequest.uploadBehaviorInfoNoTokenV2(bfModel, subscriber);
        } else {
            NetworkPhRequest.uploadBehaviorInfoV2(bfModel, subscriber);
        }

    }

}
