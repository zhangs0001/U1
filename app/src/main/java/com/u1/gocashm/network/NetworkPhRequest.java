package com.u1.gocashm.network;


import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.firstinvest.chlibrary.BuildConfig;
import com.u1.gocashm.model.InviteCodeModel;
import com.u1.gocashm.model.request.AddBankCardBody;
import com.u1.gocashm.model.request.AddBankCardModel;
import com.u1.gocashm.model.request.ApplyReqPhModel;
import com.u1.gocashm.model.request.AuthInfoPhReqModel;
import com.u1.gocashm.model.request.BankCardListModel;
import com.u1.gocashm.model.request.BehaviorPhReqModel;
import com.u1.gocashm.model.request.BehaviorRecordReqModel;
import com.u1.gocashm.model.request.ChangePwdPhReqModel;
import com.u1.gocashm.model.request.ContinueLoanPhReqModel;
import com.u1.gocashm.model.request.DdiReqModel;
import com.u1.gocashm.model.request.FacebookReqModel;
import com.u1.gocashm.model.request.FawryBean;
import com.u1.gocashm.model.request.ForgetPwdPhReqModel;
import com.u1.gocashm.model.request.ImagePhReqModel;
import com.u1.gocashm.model.request.JpushReqModel;
import com.u1.gocashm.model.request.LoginPhReqModel;
import com.u1.gocashm.model.request.ScheduleCalcPhReqModel;
import com.u1.gocashm.model.request.SessionPhReqModel;
import com.u1.gocashm.model.request.UpdateOrderReqModel;
import com.u1.gocashm.model.response.ApplyIndexPhModel;
import com.u1.gocashm.model.response.ApplyModel;
import com.u1.gocashm.model.response.AuthInfoPhModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.ChannelListModel;
import com.u1.gocashm.model.response.ContinueLoanPhModel;
import com.u1.gocashm.model.response.ContractDelayCalcPhModel;
import com.u1.gocashm.model.response.ContractDetailPhModel;
import com.u1.gocashm.model.response.ContractPhModel;
import com.u1.gocashm.model.response.CouponModel;
import com.u1.gocashm.model.response.DDIRespModel;
import com.u1.gocashm.model.response.DictionaryPhModel;
import com.u1.gocashm.model.response.GoogleGpsPhModel;
import com.u1.gocashm.model.response.InitUserModel;
import com.u1.gocashm.model.response.JpushRespModel;
import com.u1.gocashm.model.response.LoanCountPhModel;
import com.u1.gocashm.model.response.LoanResultPhModel;
import com.u1.gocashm.model.response.LocationModel;
import com.u1.gocashm.model.response.LoginModel;
import com.u1.gocashm.model.response.LotterModel;
import com.u1.gocashm.model.response.LotterResModel;
import com.u1.gocashm.model.response.NoticeListModel;
import com.u1.gocashm.model.response.NoticeModel;
import com.u1.gocashm.model.response.OrderDetailModel;
import com.u1.gocashm.model.response.OrderListModel;
import com.u1.gocashm.model.response.PaymentStatusModel;
import com.u1.gocashm.model.response.PhotoImagePhModel;
import com.u1.gocashm.model.response.ProductPhModel;
import com.u1.gocashm.model.response.RecommendModel;
import com.u1.gocashm.model.response.RenewaInfoPhModel;
import com.u1.gocashm.model.response.RenewalPhModel;
import com.u1.gocashm.model.response.RepayInfoPhModel;
import com.u1.gocashm.model.response.ScheduleCalcPhModel;
import com.u1.gocashm.model.response.StepModel;
import com.u1.gocashm.model.response.UserInfoDetailModel;
import com.u1.gocashm.model.response.UserInfoModel;
import com.u1.gocashm.model.response.UserSurveyModel;
import com.u1.gocashm.model.response.VersionPhModel;
import com.u1.gocashm.util.model.CallModel;
import com.u1.gocashm.util.model.MessageModel;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NetworkPhRequest {

    //登录
    public static void login(@NonNull LoginPhReqModel loginReq, Subscriber<LoginModel> subscriber) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("telephone", loginReq.getPhone())
                .addFormDataPart("loginType", loginReq.getLoginType())
                .addFormDataPart("deviceId", loginReq.getDeviceId())
                .addFormDataPart("gps", loginReq.getGps());
        if (!TextUtils.isEmpty(loginReq.getCode())) {
            builder.addFormDataPart("code", loginReq.getCode());
        }
        if (!TextUtils.isEmpty(loginReq.getPassword())) {
            builder.addFormDataPart("password", loginReq.getPassword());
        }
        NetworkPh.getHttpServer().login(builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    // 字典查询
    public static void dictionaryQuery(String code, Subscriber<DictionaryPhModel> subscriber) {
        NetworkPh.getHttpServer().dictionaryQuery(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 字典查询,查询分行信息
    public static void dictionaryQueryBranch(String bank, String city, String branchCode, Subscriber<DictionaryPhModel> subscriber) {
        NetworkPh.getHttpServer().dictionaryQueryBranch(bank, city, branchCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 获取短信验证码 type:枚举类型 LOGIN、REGISTER、MODIFY_PASSWORD
    public static void getSmsCode(String phoneNum, String type, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().getSmsCode(phoneNum, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //忘记密码
    public static void forgetPassword(ForgetPwdPhReqModel model, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().forgetPassword(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //是否续贷
    public static void getIsContinuedLoan(Subscriber<LoanCountPhModel> subscriber) {
        NetworkPh.getHttpServer().getIsContinuedLoan()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取贷款产品
    public static void getProduct(String token, Subscriber<ProductPhModel> subscriber) {
        NetworkPh.getHttpServer().getProduct(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    //还款计划试算
    public static void scheduleCalc(ScheduleCalcPhReqModel model, Subscriber<ScheduleCalcPhModel> subscriber) {
        NetworkPh.getHttpServer().scheduleCalc(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 保存工作信息
    public static void saveJobInfo(ApplyReqPhModel applyReqPhModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().saveJobInfo(applyReqPhModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 上传单张身份证照片
    public static void applyUploadOnePhoto(ImagePhReqModel imageModel, Callback<PhotoImagePhModel> callback) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("type", imageModel.getImageType())
                .addFormDataPart("deviceId", imageModel.getDeviceId())
                .addFormDataPart("applyId", String.valueOf(imageModel.getApplyId()));

        File imageFrontFile = new File(imageModel.getImagePath());//filePath 图片地址
        RequestBody imageFrontBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFrontFile);
        builder.addFormDataPart("imageFront", imageFrontFile.getName(), imageFrontBody);

        List<MultipartBody.Part> parts = builder.build().parts();
        NetworkPh.getHttpServer().applyUploadOnePhoto(parts, imageModel.getDeviceId()).enqueue(callback);
    }

    // 上传单张身份证照片
    public static void applyUploadOnePhoto(ImagePhReqModel imageModel, String cardType, Callback<PhotoImagePhModel> callback) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("type", imageModel.getImageType())
                .addFormDataPart("deviceId", imageModel.getDeviceId())
                .addFormDataPart("applyId", String.valueOf(imageModel.getApplyId()))
                .addFormDataPart("idcard", imageModel.getIdcard())
                .addFormDataPart("cardType", cardType);

        File imageFrontFile = new File(imageModel.getImagePath());//filePath 图片地址
        RequestBody imageFrontBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFrontFile);
        builder.addFormDataPart("imageFront", imageFrontFile.getName(), imageFrontBody);

        List<MultipartBody.Part> parts = builder.build().parts();
        NetworkPh.getHttpServer().applyUploadOnePhoto(parts, imageModel.getDeviceId()).enqueue(callback);
    }

    // 保存身份证照片
    public static void savePhoto(ApplyReqPhModel applyReqPhModel, Subscriber<BasePhModel> subscriber) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("card_type_code", applyReqPhModel.getCard_type_code())
                .addFormDataPart("card_num", applyReqPhModel.getCard_num())
                .addFormDataPart("token", applyReqPhModel.getToken());

        File imageFrontFile = new File(applyReqPhModel.getIdCardImagePath());//filePath 图片地址
        RequestBody imageFrontBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFrontFile);
        builder.addFormDataPart("file_card", imageFrontFile.getName(), imageFrontBody);

        File imageBackFile = new File(applyReqPhModel.getBackPhotoImagePath());//filePath 图片地址
        RequestBody imageBackBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageBackFile);
        builder.addFormDataPart("file_card_back", imageFrontFile.getName(), imageBackBody);

        File imageHoldFile = new File(applyReqPhModel.getHoldImagePath());//filePath 图片地址
        RequestBody imageHoldBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageHoldFile);
        builder.addFormDataPart("file_face", imageHoldFile.getName(), imageHoldBody);
        NetworkPh.getHttpServer().savePhoto(builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 提交申请信息
    public static void submitApplyInfo(ApplyReqPhModel applyReqPhModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().submitApplyInfo(applyReqPhModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 提交申请信息
    public static void submitApplyFileInfo(ImagePhReqModel imageModel, ApplyReqPhModel applyReqPhModel, Callback<BasePhModel> callback) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("deviceId", imageModel.getDeviceId())
                .addFormDataPart("applyId", String.valueOf(imageModel.getApplyId()))
                .addFormDataPart("institution_name", applyReqPhModel.getInstitution_name())
                .addFormDataPart("type", applyReqPhModel.getType())
                .addFormDataPart("token", applyReqPhModel.getToken())
                .addFormDataPart("step_name", applyReqPhModel.getStep_name());


        File imageFrontFile = new File(imageModel.getImagePath());//filePath 图片地址
        RequestBody imageFrontBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFrontFile);
        builder.addFormDataPart("company_id", imageFrontFile.getName(), imageFrontBody);


        List<MultipartBody.Part> parts = builder.build().parts();
        NetworkPh.getHttpServer().submitApplyFileInfo(parts)
                .enqueue(callback);
    }


    //保存首页信息
    public static void applyIndex(ApplyReqPhModel model, Subscriber<ApplyIndexPhModel> subscriber) {
        NetworkPh.getHttpServer().applyIndex(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //保存个人信息
    public static void applyPersonalinfo(ApplyReqPhModel model, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().applyPersonalinfo(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //地址信息
    public static void applyAddressinfo(ApplyReqPhModel model, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().applyAddressinfo(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取申请单状态
    public static void queryApplyStatus(Subscriber<LoanResultPhModel> subscriber) {
        NetworkPh.getHttpServer().queryApplyStatus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //查询合同列表
    public static void getContractlist(Subscriber<ContractPhModel> subscriber) {
        NetworkPh.getHttpServer().getContractlist()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //查询合同详情
    public static void getContractdetail(String contractNo, Subscriber<ContractDetailPhModel> subscriber) {
        NetworkPh.getHttpServer().getContractdetail(contractNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // App版本检查
    public static void versionCheck(Subscriber<VersionPhModel> subscriber) {
        NetworkPh.getHttpServer().appVersionCheck("android", com.u1.gocashm.BuildConfig.VERSION_NAME)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 判断accessToken有效性 PUT
    public static void sessionCheck(SessionPhReqModel sessionReqBean, Subscriber<LoginModel> subscriber) {
        NetworkPh.getHttpServer().sessionCheck(sessionReqBean.getUserId(), sessionReqBean.getDeviceId(), sessionReqBean.getGps(), sessionReqBean.getAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    //上传设备授权信息
    public static void uploadDeviceInfo(AuthInfoPhReqModel deviceInfo, Subscriber<AuthInfoPhModel> subscriber) {
        NetworkPh.getHttpServer().uploadDeviceInfo(deviceInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 合同延期
    public static void contractDelay(String contractNo, String accessToken, int delayDays, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().contractDelay(accessToken, contractNo, delayDays)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 合同延期试算
    public static void contractDelayCalc(String contractNo, Subscriber<ContractDelayCalcPhModel> subscriber) {
        NetworkPh.getHttpServer().contractDelayCalc(contractNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 获取合同预览
    public static void getContractPreview(String loanDay, String loanAmount, String token, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().getContractPreview(loanDay, loanAmount, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //上传续贷信息
    public static void continuedLoan(String accessToken, ContinueLoanPhReqModel continueLoanPhReqModel, Subscriber<ContinueLoanPhModel> subscriber) {
        NetworkPh.getHttpServer().continuedLoan(accessToken, continueLoanPhReqModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 续贷提交申请
    public static void continuedLoanSubmit(ApplyReqPhModel applyReqPhModel, Subscriber<ApplyModel> subscriber) {
        NetworkPh.getHttpServer().continuedLoanSubmit(applyReqPhModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //上传登录设备信息
    public static void loginDeviceInfo(RequestBody body, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().loginDeviceInfo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //上传联系人信息
    public static void contactInfo(RequestBody body, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().contactInfo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //上传短信
    public static void messageInfo(MessageModel messageModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().messageInfo(messageModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //上传通话记录
    public static void callRecordInfo(CallModel callModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().callRecordInfo(callModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //更新客户身份信息
    public static void updateUserBaseInfo(ApplyReqPhModel applyReqPhModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().updateUserBaseInfo(applyReqPhModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //更新联系人信息
    public static void updateContactInfo(ApplyReqPhModel applyReqPhModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().updateContactInfo(applyReqPhModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //更新公司信息
    public static void updateUseCompanyInfo(ApplyReqPhModel applyReqPhModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().updateUseCompanyInfo(applyReqPhModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //更新影相信息
    public static void updateImageInfo(ApplyReqPhModel applyReqPhModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().updateImageInfo(applyReqPhModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //更新银行卡信息
    public static void updateUseBankCardInfo(ApplyReqPhModel applyReqPhModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().updateUseBankCardInfo(applyReqPhModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取googleGps
    public static void getGoogleGps(Subscriber<GoogleGpsPhModel> subscriber) {
        NetworkPh.getHttpServer().getGoogleGps()
                .subscribeOn(Schedulers.io())
                .timeout(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //上传行为信息
    public static void uploadBehaviorInfo(BehaviorPhReqModel behaviorPhReqModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().uploadBehaviorInfo(behaviorPhReqModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //上传行为信息
    public static void uploadBehaviorInfoV2(BehaviorRecordReqModel behaviorPhReqModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().uploadBehaviorInfoV2(behaviorPhReqModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //上传行为信息
    public static void uploadBehaviorInfoNoTokenV2(BehaviorRecordReqModel behaviorPhReqModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().uploadBehaviorInfoNoTokenV2(behaviorPhReqModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 获取合同下载
    public static void getContractDownload(String contractNo, String accessToken, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().getContractDownload(contractNo, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //上传交易凭证
    public static void uploadRepaymentMark(ImagePhReqModel imagePhReqModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().uploadRepaymentMark(imagePhReqModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取用户信息
    public static void getUserInfoDetail(Subscriber<UserInfoDetailModel> subscriber) {
        NetworkPh.getHttpServer().getUserInfoDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 退出登录
    public static void logout(String accessToken, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().logout(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //取消再贷
    public static void applyByCancel(ContinueLoanPhReqModel continueLoanPhReqModel, Subscriber<ContinueLoanPhModel> subscriber) {
        NetworkPh.getHttpServer().applyByCancel(continueLoanPhReqModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //拒绝单再贷
    public static void applyByReject(ApplyReqPhModel applyReqPhModel, Subscriber<ApplyModel> subscriber) {
        NetworkPh.getHttpServer().applyByReject(applyReqPhModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //上传所有APP信息
    public static void uploadAppInfo(RequestBody body, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().uploadAppInfo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //查看还款账户
    public static void getRepaymentAccount(Subscriber<RepayInfoPhModel> subscriber) {
        NetworkPh.getHttpServer().getRepaymentAccount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取推广链接
    public static void getRecommendUrl(Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().getRecommendUrl()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取推广链接
    public static void getRecommend(String token, Subscriber<InviteCodeModel> subscriber) {
        NetworkPh.getHttpServer().getRecommend(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取推广列表
    public static void getMyRecommendList(String token, Subscriber<RecommendModel> subscriber) {
        NetworkPh.getHttpServer().getMyRecommend(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取优惠券列表
    public static void getCouponList(ApplyReqPhModel reqPhModel, Subscriber<CouponModel> subscriber) {
        NetworkPh.getHttpServer().getCoupon(reqPhModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //使用优惠券
    public static void useCoupon(List<Integer> couponIdList, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().useCoupon(couponIdList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //使用优惠券
    public static void useCoupon(List<Integer> couponIdList, String confirm, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().useCoupon(couponIdList, confirm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //使用优惠券
    public static void getAgencyFee(String payOrgName, long applyId, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().getAgencyFee(payOrgName, applyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 保存补件身份证照片
    public static void saveSupplementPhoto(ApplyReqPhModel applyReqPhModel, Subscriber<BasePhModel> subscriber) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("token", applyReqPhModel.getToken());
        if (!TextUtils.isEmpty(applyReqPhModel.getIdCardImagePath())) {
            File imageFrontFile = new File(applyReqPhModel.getIdCardImagePath());//filePath 图片地址
            RequestBody imageFrontBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFrontFile);
            builder.addFormDataPart("file_card", imageFrontFile.getName(), imageFrontBody);
        }
        if (!TextUtils.isEmpty(applyReqPhModel.getHoldImagePath())) {
            File imageHoldFile = new File(applyReqPhModel.getHoldImagePath());//filePath 图片地址
            RequestBody imageHoldBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageHoldFile);
            builder.addFormDataPart("file_face", imageHoldFile.getName(), imageHoldBody);
        }
        NetworkPh.getHttpServer().saveSupplementPhoto(builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 上传补件身份证照片
    public static void uploadSupplementPhoto(ImagePhReqModel imageModel, Callback<PhotoImagePhModel> callback) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("type", imageModel.getImageType())
                .addFormDataPart("deviceId", imageModel.getDeviceId())
                .addFormDataPart("applyId", String.valueOf(imageModel.getApplyId()));
        if (!TextUtils.isEmpty(imageModel.getIdcard())) {
            builder.addFormDataPart("idcard", imageModel.getIdcard());
        }
        if (!TextUtils.isEmpty(imageModel.getCardType())) {
            builder.addFormDataPart("cardType", imageModel.getCardType());
        }

        File imageFrontFile = new File(imageModel.getImagePath());//filePath 图片地址
        RequestBody imageFrontBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFrontFile);
        builder.addFormDataPart("imageFront", imageFrontFile.getName(), imageFrontBody);

        List<MultipartBody.Part> parts = builder.build().parts();
        NetworkPh.getHttpServer().uploadSupplementPhoto(parts, imageModel.getDeviceId()).enqueue(callback);
    }

    //获取是否展示线下取款
    public static void getPayType(Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().getPayType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //首页
    public static void appIndex(Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().appIndex()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //短信验证码获取方式
    public static void getSmsCodeType(String type, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().getSmsCodeType(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //更新额度
    public static void updateAmount(ApplyReqPhModel applyReqU2Model, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().updateAmount(applyReqU2Model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 上传极光推送用户信息
    public static void uploadJpushUserInfo(JpushReqModel jpushReqModel, Subscriber<JpushRespModel> subscriber) {
        NetworkPh.getHttpServer2(BuildConfig.JPUSH_URL).uploadJpushUserInfo(jpushReqModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 数盟接口
    public static void getDDIinfo(DdiReqModel reqModel, Subscriber<DDIRespModel> subscriber) {
        NetworkPh.getHttpServer2(BuildConfig.DDI_URL).getDDIinfo(reqModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取用户等级
    public static void getUserLevel(String token, Subscriber<InitUserModel> subscriber) {
        NetworkPh.getHttpServer().getUserLevel(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    //获取是否展示活体检测
    public static void getTakeHoldType(Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().getTakeHoldType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 用户注册
     *
     * @param applyReqPhModel
     * @param subscriber
     * @deprecated
     */
    public static void register(ApplyReqPhModel applyReqPhModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().register(applyReqPhModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //用户注册
    public static void registerV2(ApplyReqPhModel applyReqPhModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().registerV2(applyReqPhModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //修改密码验证
    public static void resetPassword(ForgetPwdPhReqModel applyReqModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().resetPassword(applyReqModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //修改密码确认
    public static void resetPasswordSubmit(ForgetPwdPhReqModel applyReqU2Model, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().resetPasswordSubmit(applyReqU2Model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //修改密码
    public static void changePassword(ChangePwdPhReqModel applyReqU2Model, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().changePassword(applyReqU2Model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //添加联系人
    public static void addContact(RequestBody body, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().addContact(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //提交添加联系人
    public static void addContactSubmit(Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().addContactSubmit()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取通知内容
    public static void getNotice(Subscriber<NoticeModel> subscriber) {
        NetworkPh.getHttpServer().getNotice()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取站内信
    public static void getMessageList(int page, int pageSizeString, String token, Subscriber<NoticeListModel> subscriber) {
        NetworkPh.getHttpServer().getMessageList(token, "notice", page, pageSizeString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //设置公告状态已读
    public static void setMessageRead(String token, List<Integer> ids, Subscriber<BasePhModel> subscriber) {
        if (ids == null || ids.size() == 0) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(BuildConfig.END_POINT + NetApi.MESSAGE_SET_READ);
        sb.append("?");
        for (int id : ids) {
            sb.append("id[]=" + id);
            sb.append("&");
        }
        sb.append("token=" + token);
        sb.append("&");
        sb.append("type=notice");

        NetworkPh.getHttpServer()
                .setMessageRead(sb.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //查询订单列表
    public static void getOrderList(String token, String status, Subscriber<OrderListModel> subscriber) {
        NetworkPh.getHttpServer().getOrderList(token, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //查询订单详情
    public static void getOrderDetail(String token, String orderId, Subscriber<OrderDetailModel> subscriber) {
        NetworkPh.getHttpServer().getOrderDetail(token, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 创建订单
    public static void createOrder(ApplyReqPhModel reqModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().createOrder(reqModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 更新订单
     */
    public static void sign(UpdateOrderReqModel model, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().sign(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 频道列表
     */
    public static void getChannelList(Subscriber<ChannelListModel> subscriber) {
        NetworkPh.getHttpServer().getChannelList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 获取位置信息
    public static void getLocation(ApplyReqPhModel reqModel, Subscriber<LocationModel> subscriber) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("position", reqModel.getPosition())
                .addFormDataPart("token", reqModel.getToken());

        List<MultipartBody.Part> parts = builder.build().parts();
        NetworkPh.getHttpServer().getLocation(parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 更新订单
     */
    public static void updateOrder(UpdateOrderReqModel model, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().updateOrder(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 用户调查列表
    public static void getUserSurveyList(String token, String step, Subscriber<UserSurveyModel> subscriber) {
        NetworkPh.getHttpServer().getUserSurveyList(token, step)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 提交用户调查
    public static void submitUserSurvey(String token, String step, String option, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().submitUserSurvey(token, step, option)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 保存facebook 授权信息
    public static void saveFacebookInfo(FacebookReqModel model, Subscriber<BasePhModel> subscriber) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("token", model.getToken())
                .addFormDataPart("type", model.getType())
                .addFormDataPart("email", model.getEmail())
                .addFormDataPart("first_name", model.getFirst_name())
                .addFormDataPart("id", model.getId())
                .addFormDataPart("last_name", model.getLast_name())
                .addFormDataPart("name", model.getName())
                .addFormDataPart("name_format", model.getName_format())
                .addFormDataPart("short_name", model.getShort_name());

        RequestBody imageFrontBody = RequestBody.create(MediaType.parse("multipart/form-data"), model.getPicture());
        builder.addFormDataPart("picture", model.getPicture().getName(), imageFrontBody);
        NetworkPh.getHttpServer().saveFacebookInfo(builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 获取步骤
    public static void getUserStep(ApplyReqPhModel reqModel, Subscriber<StepModel> subscriber) {
        NetworkPh.getHttpServer().getUserStep(reqModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 机构列表
     */
    public static void getInstitutionList(Subscriber<ChannelListModel> subscriber) {
        NetworkPh.getHttpServer().getInstitutionList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取个人信息
     */
    public static void getUserInfo(String token, Subscriber<UserInfoModel> subscriber) {
        NetworkPh.getHttpServer().getUserInfo(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    //上传位置信息
    public static void uploadLocation(RequestBody body, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().uploadLocation(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //上传相册信息
    public static void uploadPhotoInfo(RequestBody body, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().uploadPhotoInfo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取条形码
    public static void getBarCode(ApplyReqPhModel reqPhModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().getBarCode(reqPhModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取申请单状态
    public static void getLastOrder(ApplyReqPhModel reqModel, Subscriber<LoanResultPhModel> subscriber) {
        NetworkPh.getHttpServer().getLastOrder(reqModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //上传通讯录总数
    public static void uploadContactCount(RequestBody body, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().uploadContactCount(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //验券
    public static void checkingCoupon(String token, String couponId, String orderId, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().checkingCoupon(token, couponId, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //验券
    public static void checkingCouponOrder(String token, String couponId, String orderId, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().checkingCouponOrder(token, couponId, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    //获取优惠券列表
    public static void getMyEffectiveCoupon(ApplyReqPhModel reqPhModel, Subscriber<CouponModel> subscriber) {
        NetworkPh.getHttpServer().getMyEffectiveCoupon(reqPhModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void getBoundsActivity(String token, Subscriber<LotterModel> subscriber) {
        NetworkPh.getHttpServer().getBoundsActivity(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void addBounds(String token, String activity_id, Subscriber<LotterResModel> subscriber) {
        NetworkPh.getHttpServer().addBounds(token, activity_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void getRenewalInfo(String token, String orderId, Subscriber<RenewaInfoPhModel> subscriber) {
        NetworkPh.getHttpServer().getRenewalInfo(token, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void getRenewal(String token, String orderId, Subscriber<RenewalPhModel> subscriber) {
        NetworkPh.getHttpServer().getRenewal(token, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 还款Fawry
     *
     * @param token
     * @param orderId
     * @param channel
     * @param method
     * @param repayAmount
     * @param subscriber
     */
    public static void putFawry(String token, String orderId, String channel, String method, String repayAmount, Subscriber<FawryBean> subscriber) {
        NetworkPh.getHttpServer().getFawry(token, orderId, channel, method, repayAmount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 还款列表
     *
     * @param token
     * @param subscriber
     */
    public static void paymentList(String token, Subscriber<PaymentStatusModel> subscriber) {
        NetworkPh.getHttpServer().getPaymentList(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 还款列表
     *
     * @param token
     * @param subscriber
     */
    public static void repayQuery(String token, String transaction_no, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().getRepayQuery(token, transaction_no)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 绑定银行卡
     * //     * @param token
     * //     * @param card_number   银行卡号
     * //     * @param holder_name   名字
     * //     * @param expiry_year   年份
     * //     * @param expiry_month  月份
     * //     * @param cvv           cvv
     * //     * @param subscriber
     */
    public static void addBankCard(@NonNull AddBankCardBody addBankCardBody, Subscriber<AddBankCardModel> subscriber) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("token", addBankCardBody.getToken())
                .addFormDataPart("card_number", addBankCardBody.getCard_number())
                .addFormDataPart("expiry_year", addBankCardBody.getExpiry_year())
                .addFormDataPart("expiry_month", addBankCardBody.getExpiry_month())
                .addFormDataPart("cvv", addBankCardBody.getCvv())
                .addFormDataPart("holder_name", addBankCardBody.getHolder_name());
        NetworkPh.getHttpServer().addBankCard(builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 银行卡列表
     *
     * @param token
     * @param subscriber
     */
    public static void getBankCardList(String token, Subscriber<BankCardListModel> subscriber) {
        NetworkPh.getHttpServer().getbankCardList(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 删除银行卡
     *
     * @param token
     * @param subscriber
     */
    public static void deleteBankCard(String token, int id, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().deleteBankCard(token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 使用银行卡还款
     *
     * @param token
     * @param subscriber
     */
    public static void useBankCardRepayMent(String token, int id, String amount, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().useBankCardRepayMent(token, id, amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 绑定银行卡
     * <p>
     * //     * @param token
     *
     * @param subscriber
     */
    public static void BindingPayMent(ApplyReqPhModel applyReqPhModel, Subscriber<BasePhModel> subscriber) {
        NetworkPh.getHttpServer().BindingPayMent(applyReqPhModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
