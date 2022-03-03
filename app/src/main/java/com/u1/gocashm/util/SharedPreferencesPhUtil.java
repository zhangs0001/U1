package com.u1.gocashm.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.reflect.TypeToken;
import com.u1.gocashm.PhApplication;
import com.u1.gocashm.model.response.Apply;
import com.u1.gocashm.model.response.Customer;
import com.u1.gocashm.model.response.ScheduleCalcPhModel;
import com.u1.gocashm.model.response.UserInfoDetailModel;
import com.u1.gocashm.model.response.UserLevelModel;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.util.model.ContactInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class SharedPreferencesPhUtil {

    private static SharedPreferencesPhUtil instance;
    private SharedPreferences settings;

    private SharedPreferencesPhUtil(Context context) {
        settings = context.getSharedPreferences(PhConstants.SPFILENAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesPhUtil getInstance(Context context) {

        if (null == context)
            context = PhApplication.getContext();

        if (null == context)
            throw new NullPointerException("PhApplication 未初始化?");
        if (instance == null) {
            synchronized (SharedPreferencesPhUtil.class) {
                if (instance == null)
                    instance = new SharedPreferencesPhUtil(
                            context.getApplicationContext());
            }
        }

        return instance;

    }

    public boolean saveString(String key, String value) {
        return settings.edit().putString(key, value).commit();
    }

    public void applyString(String key, String value) {
        settings.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return settings.getString(key, "");
    }

    public String getGps(String key, String defaultValue) {
        return settings.getString(key, defaultValue);
    }

    public void saveInt(String key, int value) {
        settings.edit().putInt(key, value).apply();
    }

    public void applyInt(String key, int value) {
        settings.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defaultValue) {
        return settings.getInt(key, defaultValue);
    }

    public void saveLong(String key, long value) {
        settings.edit().putLong(key, value).apply();
    }

    public void applyLong(String key, long value) {
        settings.edit().putLong(key, value).apply();
    }

    public long getLong(String key, long defaultValue) {
        return settings.getLong(key, defaultValue);
    }

    public Double getOptDouble(String key) {
        String retStr = settings.getString(key, null);
        Double ret = null;
        try {
            ret = Double.parseDouble(retStr);
        } catch (Exception e) {
        }
        return ret;
    }

    public Boolean getOptBoolean(String key) {
        String retStr = settings.getString(key, null);
        Boolean ret = null;
        try {
            ret = Boolean.parseBoolean(retStr);
        } catch (Exception e) {
        }
        return ret;
    }

    public Double getDouble(String key) {
        String retStr = settings.getString(key, null);
        Double ret = null;
        try {
            if (retStr != null) {
                ret = Double.parseDouble(retStr);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        return ret;
    }

    public void saveHashMap(final String key, HashMap<String, String> map) {
        final JSONObject ret = new JSONObject(map);
        settings.edit().putString(key, ret.toString()).apply();
    }

    public HashMap<String, String> getHashMapByKey(String key) {
        HashMap<String, String> ret = new HashMap<String, String>();
        String mapStr = settings.getString(key, "{}");
        JSONObject mapJson = null;
        try {
            mapJson = new JSONObject(mapStr);
        } catch (Exception e) {
            return ret;
        }

        if (mapJson != null) {
            Iterator<String> it = mapJson.keys();
            while (it.hasNext()) {
                String theKey = it.next();
                String theValue = mapJson.optString(theKey);
                ret.put(theKey, theValue);
            }
        }
        return ret;
    }

    public void saveBoolean(String key, boolean bool) {
        settings.edit().putBoolean(key, bool).apply();
    }

    public void applyBoolean(String key, boolean bool) {
        settings.edit().putBoolean(key, bool).apply();
    }

    public boolean getBoolean(String key) {
        return settings.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean is) {
        return settings.getBoolean(key, is);
    }

    public void saveArrayList(String key, ArrayList<String> list) {
        settings.edit().putString(key, GsonPhHelper.getGson().toJson(list)).apply();
    }

    public ArrayList<String> getArrayList(String key) {
        ArrayList<String> ret = new ArrayList<String>();
        String listStr = settings.getString(key, "{}");
        JSONArray listJson = null;
        try {
            listJson = new JSONArray(listStr);
        } catch (Exception e) {
            return ret;
        }

        if (listJson != null) {
            for (int i = 0; i < listJson.length(); i++) {
                String temp = listJson.optString(i);
                ret.add(temp);
            }
        }
        return ret;
    }

    public void saveListInteger(String key, List<Integer> list) {
        settings.edit().putString(key, GsonPhHelper.getGson().toJson(list)).apply();
    }

    public List<Integer> getListInteger(String key) {
        List<Integer> ret = new ArrayList<>();
        String listStr = settings.getString(key, "{}");
        JSONArray listJson = null;
        try {
            listJson = new JSONArray(listStr);
        } catch (Exception e) {
            return ret;
        }

        if (listJson != null) {
            for (int i = 0; i < listJson.length(); i++) {
                Integer temp = listJson.optInt(i);
                ret.add(temp);
            }
        }
        return ret;
    }

    public void removeByKey(String key) {
        settings.edit().remove(key).apply();
    }

    public void clearApplyData() {
        removeByKey(PhConstants.LOANINFOVIEW_AMOUNT);
        removeByKey(PhConstants.LOANINFOVIEW_DATE);
        removeByKey(PhConstants.LOANINFOVIEW_PAYMENT);
        removeByKey(PhConstants.APPLYID);
        removeByKey(PhConstants.APPLYTERM);
        removeByKey(PhConstants.APPLYPRODUCTCODE);
        removeByKey(PhConstants.APPLYPHONE);

        removeByKey(PhConstants.PhIdentityInfo.IDENTITY_CARD);
        removeByKey(PhConstants.PhIdentityInfo.IDENTITY_SEX);
        removeByKey(PhConstants.PhIdentityInfo.IDENTITY_MARITAL);
        removeByKey(PhConstants.PhIdentityInfo.IDENTITY_CODE_MARITAL);
        removeByKey(PhConstants.PhIdentityInfo.IDENTITY_EMAIL);
        removeByKey(PhConstants.PhIdentityInfo.IDENTITY_APP);
        removeByKey(PhConstants.PhIdentityInfo.IDENTITY_ISTRUE);
        removeByKey(PhConstants.PhIdentityInfo.IDENTITY_APPPHONE);
        removeByKey(PhConstants.PhIdentityInfo.IDENTITY_LIST);
        removeByKey(PhConstants.PhIdentityInfo.IDENTITY_BIRTHDAY);
        removeByKey(PhConstants.PhIdentityInfo.IDENTITY_EDUCATION);
        removeByKey(PhConstants.PhIdentityInfo.IDENTITY_EDUCATION_CODE);
        removeByKey(PhConstants.PhIdentityInfo.IDENTITY_RELIGION);
        removeByKey(PhConstants.PhIdentityInfo.IDENTITY_RELIGION_CODE);
        removeByKey(PhConstants.PhIdentityInfo.CARD_TYPE);
        removeByKey(PhConstants.PhIdentityInfo.CARD_TYPE_CODE);
        removeByKey(PhConstants.PhIdentityInfo.BANK_ACCOUNT);
        removeByKey(PhConstants.PhIdentityInfo.FIRST_NAME);
        removeByKey(PhConstants.PhIdentityInfo.MIDDLE_NAME);
        removeByKey(PhConstants.PhIdentityInfo.LAST_NAME);


        removeByKey(PhConstants.PhAddressInfo.ADDRESS_PROVINCE);
        removeByKey(PhConstants.PhAddressInfo.ADDRESS_CITY);
        removeByKey(PhConstants.PhAddressInfo.ADDRESS_COUNTY);
        removeByKey(PhConstants.PhAddressInfo.ADDRESS_PROVINCE_CODE);
        removeByKey(PhConstants.PhAddressInfo.ADDRESS_CITY_CODE);
        removeByKey(PhConstants.PhAddressInfo.ADDRESS_COUNTY_CODE);
        removeByKey(PhConstants.PhAddressInfo.ADDRESS_DETAIL);
        removeByKey(PhConstants.PhAddressInfo.ADDRESS_DURATION);
        removeByKey(PhConstants.PhAddressInfo.ADDRESS_DURATION_CODE);


        removeByKey(PhConstants.PhJobInfo.LOAN_INTENT);
        removeByKey(PhConstants.PhJobInfo.PROFESSION);
        removeByKey(PhConstants.PhJobInfo.WORK_INDUSTRY);
        removeByKey(PhConstants.PhJobInfo.COMPANY_NAME);
        removeByKey(PhConstants.PhJobInfo.JOB_POSITION);
        removeByKey(PhConstants.PhJobInfo.MONTHLY_INCOME);
        removeByKey(PhConstants.PhJobInfo.WORK_TIME);
        removeByKey(PhConstants.PhJobInfo.OFFICE_PHONE);
        removeByKey(PhConstants.PhJobInfo.RELATIVES_NAME);
        removeByKey(PhConstants.PhJobInfo.RELATIVES_PHONE);
        removeByKey(PhConstants.PhJobInfo.RELATIONSHIP);
        removeByKey(PhConstants.PhJobInfo.INCOME_FREQUENCY);
        removeByKey(PhConstants.PhJobInfo.NEXT_INCOME_DATE);
        removeByKey(PhConstants.PhJobInfo.LOANINTENT_CODE);
        removeByKey(PhConstants.PhJobInfo.PROFESSION_CODE);
        removeByKey(PhConstants.PhJobInfo.WORKINDUSTRY_CODE);
        removeByKey(PhConstants.PhJobInfo.INCOMEFREQUENCY_CODE);
        removeByKey(PhConstants.PhJobInfo.WORKTIME_CODE);
        removeByKey(PhConstants.PhJobInfo.RELATIONSHIP_CODE);
        removeByKey(PhConstants.PhJobInfo.CONTACT_LIST);

//        removeByKey(PhConstants.PhUser.ACCESS_TOKEN);
        removeByKey(PhConstants.PhUser.USER_ID);
//        removeByKey(PhConstants.PhUser.USER_PHONE);
        removeByKey(PhConstants.PhUser.USER_NAME);

        removeByKey(PhConstants.PhPayInfo.BRANCH_CODE);
        removeByKey(PhConstants.PhPayInfo.BANK_CODE);
        removeByKey(PhConstants.PhPayInfo.BANK_CITY_CODE);
        removeByKey(PhConstants.PhPayInfo.BRANCH_NAME);
        removeByKey(PhConstants.PhPayInfo.BANK_NAME);
        removeByKey(PhConstants.PhPayInfo.BANK_NUM);
        removeByKey(PhConstants.PhPayInfo.BANK_CITY_NAME);

        removeByKey(PhConstants.PhPhoto.PHOTO_IMAGE_ID);
        removeByKey(PhConstants.PhPhoto.PHOTO_IMAGE_PATH);
        removeByKey(PhConstants.PhPhoto.PHOTO_HOLD_IMAGE_ID);
        removeByKey(PhConstants.PhPhoto.PHOTO_HOLD_IMAGE_PATH);
        removeByKey(PhConstants.PhPhoto.LIVENESS_COUNT);
        removeByKey(PhConstants.PhPhoto.LIVENESS_ID);

        removeByKey(PhConstants.AGREEMENT_END_TIME);
        removeByKey(PhConstants.AGREEMENT_INIT_TIME);

        removeByKey(PhConstants.FINISH_STATUS.BASE_INFO);
        removeByKey(PhConstants.FINISH_STATUS.ADDRESS_INFO);
        removeByKey(PhConstants.FINISH_STATUS.JOB_INFO);
        removeByKey(PhConstants.FINISH_STATUS.PAY_INFO);
        removeByKey(PhConstants.FINISH_STATUS.PHOTO_INFO);

    }

    public boolean contains(String alarmHour) {
        return settings.contains(alarmHour);
    }

    public void logout() {
        removeByKey(PhConstants.PhUser.ACCESS_TOKEN);
        removeByKey(PhConstants.PhUser.USER_ID);
        removeByKey(PhConstants.PhUser.USER_PHONE);
        removeByKey(PhConstants.PhUser.USER_NAME);
        removeByKey(PhConstants.PhUser.USER_ID_CARD);
        removeByKey(PhConstants.PhUser.APPLY_ID);
        removeByKey(PhConstants.PhUser.CUSTOMER);
        removeByKey(PhConstants.PhUser.APPLY);
        removeByKey(PhConstants.USER_INFO);
        removeByKey(PhConstants.PhUser.FIRST_NAME);
        removeByKey(PhConstants.USER_LEVEL);
    }

    public void login(String token, int userId, String userPhone) {
        saveString(PhConstants.PhUser.ACCESS_TOKEN, token);
        saveInt(PhConstants.PhUser.USER_ID, userId);
        saveString(PhConstants.PhUser.USER_PHONE, userPhone);
//        saveString(PhConstants.PhUser.USER_NAME, userName);
//        saveString(PhConstants.PhUser.USER_ID_CARD, idCardNo);
//        saveLong(PhConstants.PhUser.APPLY_ID, applyId);
//        saveString(PhConstants.PhUser.FIRST_NAME, firstName);
    }

    public void saveLoanInfo(int amount, String date, String payment, long applyId, int term, String productCode, String phone) {
        settings.edit().putInt(PhConstants.LOANINFOVIEW_AMOUNT, amount).apply();
        settings.edit().putString(PhConstants.LOANINFOVIEW_DATE, date).apply();
        settings.edit().putString(PhConstants.LOANINFOVIEW_PAYMENT, payment).apply();
        settings.edit().putLong(PhConstants.PhUser.APPLY_ID, applyId).apply();
        settings.edit().putInt(PhConstants.APPLYTERM, term).apply();
        settings.edit().putString(PhConstants.APPLYPRODUCTCODE, productCode).apply();
        settings.edit().putString(PhConstants.APPLYPHONE, phone).apply();
    }

    public void setCustomer(Customer customer) {
        settings.edit().putString(PhConstants.PhUser.CUSTOMER, GsonPhHelper.getGson().toJson(customer)).apply();
    }

    public Customer getCustomer() {
        return GsonPhHelper.getGson().fromJson(settings.getString(PhConstants.PhUser.CUSTOMER, ""), Customer.class);
    }

    public void setApply(Apply apply) {
        settings.edit().putString(PhConstants.PhUser.APPLY, GsonPhHelper.getGson().toJson(apply)).apply();
    }

    public Apply getApply() {
        return GsonPhHelper.getGson().fromJson(settings.getString(PhConstants.PhUser.APPLY, ""), Apply.class);
    }

    public void setUserInfo(UserInfoDetailModel.UserInfoDetail userInfo) {
        settings.edit().putString(PhConstants.USER_INFO, GsonPhHelper.getGson().toJson(userInfo)).apply();
    }

    public UserInfoDetailModel.UserInfoDetail getUserInfo() {
        return GsonPhHelper.getGson().fromJson(settings.getString(PhConstants.USER_INFO, ""), UserInfoDetailModel.UserInfoDetail.class);
    }

    public void setCalcModel(ScheduleCalcPhModel.ScheduleCalc scheduleCalc) {
        settings.edit().putString(PhConstants.CALC_MODEL, GsonPhHelper.getGson().toJson(scheduleCalc)).apply();
    }

    public ScheduleCalcPhModel.ScheduleCalc getCalcModel() {
        return GsonPhHelper.getGson().fromJson(settings.getString(PhConstants.CALC_MODEL, ""), ScheduleCalcPhModel.ScheduleCalc.class);
    }

    public void setUserLevel(UserLevelModel.UserLevel userLevel) {
        settings.edit().putString(PhConstants.USER_LEVEL, GsonPhHelper.getGson().toJson(userLevel)).apply();
    }

    public UserLevelModel.UserLevel getUserLevel() {
        return GsonPhHelper.getGson().fromJson(settings.getString(PhConstants.USER_LEVEL, null), UserLevelModel.UserLevel.class);
    }

    public void setContactList(List<ContactInfo> contactList) {
        settings.edit().putString(PhConstants.PhJobInfo.CONTACT_LIST, GsonPhHelper.getGson().toJson(contactList)).apply();
    }

    public List<ContactInfo> getContactList() {
        return GsonPhHelper.getGson().fromJson(settings.getString(PhConstants.PhJobInfo.CONTACT_LIST, null), new TypeToken<List<ContactInfo>>() {
        }.getType());
    }

}
