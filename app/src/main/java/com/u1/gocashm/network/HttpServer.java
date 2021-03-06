package com.u1.gocashm.network;


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
import com.u1.gocashm.model.request.FawryBean;
import com.u1.gocashm.model.request.ForgetPwdPhReqModel;
import com.u1.gocashm.model.request.ImagePhReqModel;
import com.u1.gocashm.model.request.JpushReqModel;
import com.u1.gocashm.model.request.ScheduleCalcPhReqModel;
import com.u1.gocashm.model.request.UpdateOrderReqModel;
import com.u1.gocashm.model.response.ApplyIndexPhModel;
import com.u1.gocashm.model.response.ApplyModel;
import com.u1.gocashm.model.response.AuthInfoPhModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.ChannelListModel;
import com.u1.gocashm.model.response.ContinueLoanPhModel;
import com.u1.gocashm.model.response.ContractDetailPhModel;
import com.u1.gocashm.model.response.ContractPhModel;
import com.u1.gocashm.model.response.ContractDelayCalcPhModel;
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

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

public interface HttpServer {

    //??????
    @POST(NetApi.LOGIN)
    Observable<LoginModel> login(@Body MultipartBody multipartBody);

    //??????
    @GET(NetApi.LOGOUT)
    Observable<BasePhModel> logout(@Query("token") String token);

    //******** ?????? **********
    // ????????????
    @GET(NetApi.DICTIONARY)
    Observable<DictionaryPhModel> dictionaryQuery(@Query("parent_code") String code);

    // ????????????
    @GET(NetApi.DICTIONARY_BRANCH)
    Observable<DictionaryPhModel> dictionaryQueryBranch(@Query("attr1") String bank, @Query("attr2") String city, @Query("parentCode") String branchCode);

    // ????????????????????? type:???????????? LOGIN???REGISTER
    @POST(NetApi.GET_SMS_CODE)
    Observable<BasePhModel> getSmsCode(@Query("telephone") String phone, @Query("use") String type);

    // ??????????????????
    @POST(NetApi.SAVE_JOB_INFO)
    Observable<BasePhModel> saveJobInfo(@Body ApplyReqPhModel applyReqPhModel);

    // ???????????????????????????
    @Multipart
    @POST(NetApi.APPLY_UPLOAD_ONE_PHOTO)
    Call<PhotoImagePhModel> applyUploadOnePhoto(@Part List<MultipartBody.Part> photos, @Query("deviceId") String deviceId);

    // ?????????????????????
    @POST(NetApi.SAVE_PHOTO)
    Observable<BasePhModel> savePhoto(@Body MultipartBody multipartBody);

    // ??????????????????
    @POST(NetApi.SUBMIT_APPLY_INFO)
    Observable<BasePhModel> submitApplyInfo(@Body ApplyReqPhModel applyReqPhModel);

    @Multipart
    @POST(NetApi.SUBMIT_APPLY_INFO)
    Call<BasePhModel> submitApplyFileInfo(@Part List<MultipartBody.Part> parts);

    // ????????????
    @POST(NetApi.FORGET_PASSWORD)
    Observable<BasePhModel> forgetPassword(@Body ForgetPwdPhReqModel forgetPwdPhReqModel);

    //????????????
    @GET(NetApi.GET_ISCONTINUEDLOAN)
    Observable<LoanCountPhModel> getIsContinuedLoan();

    //??????????????????
    @POST(NetApi.GET_PRODUCT)
    Observable<ProductPhModel> getProduct(@Query("token") String token);

    //??????????????????
    @POST(NetApi.SCHEDULECALC)
    Observable<ScheduleCalcPhModel> scheduleCalc(@Body ScheduleCalcPhReqModel scheduleCalcPhReqModel);

    //??????????????????
    @POST(NetApi.APPLY_INDEX)
    Observable<ApplyIndexPhModel> applyIndex(@Body ApplyReqPhModel applyReqPhModel);

    //??????????????????
    @POST(NetApi.APPLY_PERSONALINFO)
    Observable<BasePhModel> applyPersonalinfo(@Body ApplyReqPhModel applyReqPhModel);

    //????????????
    @POST(NetApi.APPLY_ADDRESSINFO)
    Observable<BasePhModel> applyAddressinfo(@Body ApplyReqPhModel applyReqPhModel);

    //?????????????????????
    @GET(NetApi.QUERY_APPLY_STATUS)
    Observable<LoanResultPhModel> queryApplyStatus();

    //??????????????????
    @GET(NetApi.GET_CONTRACTLIST)
    Observable<ContractPhModel> getContractlist();

    //??????????????????
    @GET(NetApi.GET_CONTRACTDETAIL)
    Observable<ContractDetailPhModel> getContractdetail(@Query("contractNo") String contractNo);

    // App????????????
    @GET(NetApi.APP_VERSION)
    Observable<VersionPhModel> appVersionCheck(@Query("platform") String platform, @Query("app_version") String app_version);

    // ??????accessToken??????????????????????????????????????????????????? PUT
    @FormUrlEncoded
    @PUT(NetApi.LOGIN)
    Observable<LoginModel> sessionCheck(@Field("userId") int userId, @Field("deviceId") String deviceId, @Field("gps") String gps, @Query("accessToken") String accessToken);

    //????????????????????????
    @POST(NetApi.AGREE_DEVICE_INFO)
    Observable<AuthInfoPhModel> uploadDeviceInfo(@Body AuthInfoPhReqModel deviceInfo);

    // ??????
    @PUT(NetApi.CONTRACT_DELAY)
    Observable<BasePhModel> contractDelay(@Query("accessToken") String accessToken, @Query("contractNo") String contractNo, @Query("delayDays") int delayDays);

    //????????????
    @PUT(NetApi.CONTRACT_DELAY_CALC)
    Observable<ContractDelayCalcPhModel> contractDelayCalc(@Query("contractNo") String contractNo);

    // ??????????????????
    @GET(NetApi.GET_CONTRACT_PREVIEW)
    Observable<BasePhModel> getContractPreview(@Query("loan_days") String loanDay, @Query("loan_amount") String loan_amount, @Query("token") String token);

    //????????????
    @POST(NetApi.CONTINUED_LOAN)
    Observable<ContinueLoanPhModel> continuedLoan(@Query("accessToken") String accessToken, @Body ContinueLoanPhReqModel continueLoanPhReqModel);

    //??????????????????
    @POST(NetApi.CONTINUED_LOAN_SUBMIT)
    Observable<ApplyModel> continuedLoanSubmit(@Body ApplyReqPhModel applyReqPhModel);


    //????????????????????????
    @POST(NetApi.LOGIN_DEVICE_INFO)
    Observable<BasePhModel> loginDeviceInfo(@Body RequestBody body);

    //?????????????????????
    @POST(NetApi.CONTACT_INFO)
    Observable<BasePhModel> contactInfo(@Body RequestBody body);

    //????????????
    @POST(NetApi.MESSAGE_INFO)
    Observable<BasePhModel> messageInfo(@Body MessageModel messageModel);

    //??????????????????
    @POST(NetApi.CALL_RECORD_INFO)
    Observable<BasePhModel> callRecordInfo(@Body CallModel callModel);

    //????????????????????????
    @POST(NetApi.UPDATE_USERBASEINFO)
    Observable<BasePhModel> updateUserBaseInfo(@Body ApplyReqPhModel applyReqPhModel);

    //?????????????????????
    @POST(NetApi.UPDATE_CONTACTINFO)
    Observable<BasePhModel> updateContactInfo(@Body ApplyReqPhModel applyReqPhModel);

    //??????????????????
    @POST(NetApi.UPDATE_USECOMPANYINFO)
    Observable<BasePhModel> updateUseCompanyInfo(@Body ApplyReqPhModel applyReqPhModel);

    //??????????????????
    @POST(NetApi.UPDATE_IMAGEINFO)
    Observable<BasePhModel> updateImageInfo(@Body ApplyReqPhModel applyReqPhModel);

    //?????????????????????
    @POST(NetApi.UPDATE_USEBANKCARDINFO)
    Observable<BasePhModel> updateUseBankCardInfo(@Body ApplyReqPhModel applyReqPhModel);

    //??????googleGps
    @POST(NetApi.GOOGLE_GPS)
    Observable<GoogleGpsPhModel> getGoogleGps();

    //??????????????????
    @POST(NetApi.UPLOAD_BEHAVIOR)
    Observable<BasePhModel> uploadBehaviorInfo(@Body BehaviorPhReqModel behaviorPhReqModel);

    //??????????????????
    @POST(NetApi.UPLOAD_BEHAVIOR_V2)
    Observable<BasePhModel> uploadBehaviorInfoV2(@Body BehaviorRecordReqModel behaviorPhReqModel);

    //??????????????????
    @POST(NetApi.UPLOAD_BEHAVIOR_NOTOKEN_V2)
    Observable<BasePhModel> uploadBehaviorInfoNoTokenV2(@Body BehaviorRecordReqModel behaviorPhReqModel);

    // ??????????????????
    @POST(NetApi.GET_CONTRACT_DOWNLOAD)
    Observable<BasePhModel> getContractDownload(@Query("contractNo") String contractNo, @Query("accessToken") String accessToken);

    //??????????????????
    @POST(NetApi.REPAYMENT_MARK)
    Observable<BasePhModel> uploadRepaymentMark(@Body ImagePhReqModel imagePhReqModel);

    //??????????????????
    @GET(NetApi.USER_INFODETAIL)
    Observable<UserInfoDetailModel> getUserInfoDetail();

    //????????????
    @POST(NetApi.APPLY_BY_CANCEL)
    Observable<ContinueLoanPhModel> applyByCancel(@Body ContinueLoanPhReqModel continueLoanPhReqModel);

    //???????????????
    @POST(NetApi.APPLY_BY_REJECT)
    Observable<ApplyModel> applyByReject(@Body ApplyReqPhModel applyReqPhModel);

    //??????APP??????
    @POST(NetApi.UPLOAD_APP_INFO)
    Observable<BasePhModel> uploadAppInfo(@Body RequestBody body);

    //??????????????????
    @GET(NetApi.GET_REPAYMENT_ACCOUNT)
    Observable<RepayInfoPhModel> getRepaymentAccount();

    //????????????????????????
    @GET(NetApi.GET_RECOMMEND_URL)
    Observable<BasePhModel> getRecommendUrl();

    //??????????????????
    @GET(NetApi.URI_INVITE_FRIEND_CODE)
    Observable<InviteCodeModel> getRecommend(@Query("token") String token);

    //????????????????????????
    @GET(NetApi.URI_INVITE_USERS)
    Observable<RecommendModel> getMyRecommend(@Query("token") String token);

    //?????????????????????
    @POST(NetApi.GET_COUPON)
    Observable<CouponModel> getCoupon(@Body ApplyReqPhModel reqPhModel);

    //???????????????
    @POST(NetApi.USE_MYCOUPON)
    Observable<BasePhModel> useCoupon(@Query("couponId") List<Integer> couponIdList);

    //???????????????
    @POST(NetApi.USE_MYCOUPON)
    Observable<BasePhModel> useCoupon(@Query("couponId") List<Integer> couponIdList, @Query("confirm") String confirm);

    //?????????????????????
    @GET(NetApi.GET_AGENCY_FEE)
    Observable<BasePhModel> getAgencyFee(@Query("payOrg") String payOrgName, @Query("applyId") long applyId);

    // ???????????????????????????
    @POST(NetApi.SAVE_SUPPLEMENT_PHOTO)
    Observable<BasePhModel> saveSupplementPhoto(@Body MultipartBody multipartBody);

    // ???????????????????????????
    @Multipart
    @POST(NetApi.UPLOAD_SUPPLEMENT_IMAGE)
    Call<PhotoImagePhModel> uploadSupplementPhoto(@Part List<MultipartBody.Part> photos, @Query("deviceId") String deviceId);

    // ??????????????????????????????
    @GET(NetApi.GET_PAY_TYPE)
    Observable<BasePhModel> getPayType();

    //??????
    @GET(NetApi.APP_INDEX)
    Observable<BasePhModel> appIndex();

    // ???????????????????????????
    @GET(NetApi.GET_SMS_CODE_TYPE)
    Observable<BasePhModel> getSmsCodeType(@Query("type") String type);

    // ????????????
    @POST(NetApi.UPDATE_AMOUNT)
    Observable<BasePhModel> updateAmount(@Body ApplyReqPhModel applyReqU2Model);

    //??????????????????
    @POST(NetApi.SAVE_NOTICE_INFO)
    Observable<JpushRespModel> uploadJpushUserInfo(@Body JpushReqModel jpushReqModel);

    //????????????
    @POST("/q")
    Observable<DDIRespModel> getDDIinfo(@Body DdiReqModel reqModel);

    // ??????????????????
    @POST(NetApi.URI_INIT_USER)
    Observable<InitUserModel> getUserLevel(@Query("token") String token);

    // ??????????????????????????????
    @GET(NetApi.GET_TAKE_HOLD_TYPE)
    Observable<BasePhModel> getTakeHoldType();

    //????????????
    @POST(NetApi.REGISTER)
    Observable<BasePhModel> register(@Body ApplyReqPhModel applyReqPhModel);

    //????????????
    @POST(NetApi.REGISTER_V2)
    Observable<BasePhModel> registerV2(@Body ApplyReqPhModel applyReqPhModel);

    //??????????????????
    @POST(NetApi.RESET_PASSWORD)
    Observable<BasePhModel> resetPassword(@Body ForgetPwdPhReqModel applyReqModel);

    //??????????????????
    @POST(NetApi.RESET_PASSWORD_SUBMIT)
    Observable<BasePhModel> resetPasswordSubmit(@Body ForgetPwdPhReqModel applyReqModel);

    //??????????????????
    @POST(NetApi.CHANGE_PASSWORD)
    Observable<BasePhModel> changePassword(@Body ChangePwdPhReqModel applyReqModel);

    //?????????????????????
    @POST(NetApi.ADD_CONTACT)
    Observable<BasePhModel> addContact(@Body RequestBody body);

    //?????????????????????
    @POST(NetApi.ADD_CONTACT_SUBMIT)
    Observable<BasePhModel> addContactSubmit();

    //??????????????????
    @GET(NetApi.GET_NOTICE)
    Observable<NoticeModel> getNotice();

    //???????????????
    @GET(NetApi.GET_MESSAGE_LIST)
    Observable<NoticeListModel> getMessageList(@Query("token") String token, @Query("type") String type, @Query("page") int page, @Query("size") int size);

    //??????????????????
    @GET
    Observable<BasePhModel> setMessageRead(@Url String url);


    //??????????????????
    @GET(NetApi.ORDER_LIST)
    Observable<OrderListModel> getOrderList(@Query("token") String token, @Query("status") String status);

    //??????????????????
    @GET(NetApi.ORDER_DETAIL)
    Observable<OrderDetailModel> getOrderDetail(@Query("token") String token, @Query("order_id") String orderId);

    //????????????
    @POST(NetApi.CREATE_ORDER)
    Observable<BasePhModel> createOrder(@Body ApplyReqPhModel reqModel);

    //??????
    @POST(NetApi.SIGN)
    Observable<BasePhModel> sign(@Body UpdateOrderReqModel reqModel);

    //????????????
    @GET(NetApi.GET_CHANNEL_LIST)
    Observable<ChannelListModel> getChannelList();

    // ??????????????????
    @Multipart
    @POST(NetApi.GET_LOCATION)
    Observable<LocationModel> getLocation(@Part List<MultipartBody.Part> list);

    //????????????
    @POST(NetApi.UPDATE_ORDER)
    Observable<BasePhModel> updateOrder(@Body UpdateOrderReqModel reqModel);

    // ??????????????????
    @POST(NetApi.USER_SURVEY_LIST)
    Observable<UserSurveyModel> getUserSurveyList(@Query("token") String token, @Query("step") String step);

    // ??????????????????
    @POST(NetApi.SUBMIT_USER_SURVEY)
    Observable<BasePhModel> submitUserSurvey(@Query("token") String token, @Query("step") String step, @Query("option") String option);

    // ??????facebook ????????????
    @POST(NetApi.SAVE_FACEBOOK_INFO)
    Observable<BasePhModel> saveFacebookInfo(@Body MultipartBody multipartBody);

    //????????????
    @POST(NetApi.GET_USER_STEP)
    Observable<StepModel> getUserStep(@Body ApplyReqPhModel reqModel);

    //????????????
    @GET(NetApi.GET_INSTITUTION_LIST)
    Observable<ChannelListModel> getInstitutionList();

    //??????????????????
    @GET(NetApi.GET_USER_INFO)
    Observable<UserInfoModel> getUserInfo(@Query("token") String token);

    //??????????????????
    @POST(NetApi.UPLOAD_LOCATION)
    Observable<BasePhModel> uploadLocation(@Body RequestBody body);

    //??????????????????
    @POST(NetApi.UPLOAD_PHOTO_INFO)
    Observable<BasePhModel> uploadPhotoInfo(@Body RequestBody body);

    //???????????????
    @POST(NetApi.GET_BAR_CODE)
    Observable<BasePhModel> getBarCode(@Body ApplyReqPhModel reqModel);

    //?????????????????????
    @POST(NetApi.GET_LAST_ORDER)
    Observable<LoanResultPhModel> getLastOrder(@Body ApplyReqPhModel reqModel);

    //?????????????????????
    @POST(NetApi.UPLOAD_CONTACT_COUNT)
    Observable<BasePhModel> uploadContactCount(@Body RequestBody body);


    @POST(NetApi.CHECKING_COUPON)
    Observable<BasePhModel> checkingCoupon(@Query("token") String token, @Query("coupon_id") String coupon_id, @Query("order_id") String order_id);

    @POST(NetApi.URI_CHECKING_COUPON_ORDER)
    Observable<BasePhModel> checkingCouponOrder(@Query("token") String token, @Query("coupon_id") String coupon_id, @Query("order_id") String order_id);

    //?????????????????????
    @POST(NetApi.URI_MY_EFFECTIVE_COUPON)
    Observable<CouponModel> getMyEffectiveCoupon(@Body ApplyReqPhModel reqPhModel);

    @GET(NetApi.URI_BOUNS_ACTIVITY)
    Observable<LotterModel> getBoundsActivity(@Query("token") String token);

    @GET(NetApi.URI_BOUNS_ADD)
    Observable<LotterResModel> addBounds(@Query("token") String token, @Query("activity_id") String activity_id);

    @GET(NetApi.URI_RENEWAL_INFO)
    Observable<RenewaInfoPhModel> getRenewalInfo(@Query("token") String token, @Query("order_id") String order_id);

    @POST(NetApi.URI_APPLY_RENEWAL)
    Observable<RenewalPhModel> getRenewal(@Query("token") String token, @Query("order_id") String order_id);

    @POST(NetApi.URI_REPAY_REPAY)
    Observable<FawryBean> getFawry(@Query("token") String token, @Query("orderId") String orderId, @Query("channel") String channel, @Query("method") String method, @Query("repayAmount") String repayAmount);

    @POST(NetApi.URI_REPAY_LIST)
    Observable<PaymentStatusModel> getPaymentList(@Query("token") String token);

    @POST(NetApi.URI_REPAY_QUERY)
    Observable<BasePhModel> getRepayQuery(@Query("token") String token, @Query("transaction_no") String transaction_no);

    @POST(NetApi.URI_BIND_BANKCARD)
    Observable<AddBankCardModel> addBankCard(@Body MultipartBody multipartBody);

    @POST(NetApi.URI_BANKCARD_LIST)
    Observable<BankCardListModel> getbankCardList(@Query("token") String token);

    @POST(NetApi.URI_DELETE_BANKCARD)
    Observable<BasePhModel> deleteBankCard(@Query("token") String token, @Query("id") int id);

    @POST(NetApi.URI_USE_BANKCARD_REPAYMENT)
    Observable<BasePhModel> useBankCardRepayMent(@Query("token") String token, @Query("id") int id, @Query("amount") String amount);

    @POST(NetApi.URI_BINDING_PAYMENT)
    Observable<BasePhModel> BindingPayMent(@Body ApplyReqPhModel applyReqPhModel);
}