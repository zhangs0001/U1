package com.u1.gocashm.network;


public final class NetApi {

    // 登录
    public static final String LOGIN = "app/pwd-login";

    public static final String LOGOUT = "app/logout";

    // 字典查询
    public static final String DICTIONARY = "app/dict";

    // 分行字典查询
    public static final String DICTIONARY_BRANCH = "dictionary/attr";

    // 获取短信验证码
    public static final String GET_SMS_CODE = "app/captcha/send-sms";

    // 忘记密码
    public static final String FORGET_PASSWORD = "modify/password";

    // 是否续贷
    public static final String GET_ISCONTINUEDLOAN = "user/isContinuedLoan";

    // 保存工作信息
    public static final String SAVE_JOB_INFO = "app/user-init/step-three";

    // 上传单张身份证照片
    public static final String APPLY_UPLOAD_ONE_PHOTO = "ph/app/apply/oneImage";

    // 获取贷款产品
    public static final String GET_PRODUCT = "app/user-init/index";

    // 还款计划试算
    public static final String SCHEDULECALC = "app/user-init/trial";

    // 保存首页信息
    public static final String APPLY_INDEX = "ph/app/apply/index";

    // 保存身份证照片
    public static final String SAVE_PHOTO = "app/upload/card";

    // 提交申请信息
    public static final String SUBMIT_APPLY_INFO = "app/payment/create";

    // 保存个人信息
    public static final String APPLY_PERSONALINFO = "app/user-init/step-one";

    // 地址信息
    public static final String APPLY_ADDRESSINFO = "app/user-init/step-two";

    // 查询申请单状态
    public static final String QUERY_APPLY_STATUS = "ph/app/apply/queryApplyStatus";

    // 查询合同列表
    public static final String GET_CONTRACTLIST = "contract/contractList";

    // 查询合同详情
    public static final String GET_CONTRACTDETAIL = "contract/contractDetail";


    //版本更新检查
    public static final String APP_VERSION = "app/version";

    // 上传授权信息
    public static final String AGREE_DEVICE_INFO = "app/auth/deviceInfo";

    // 延期
    public static final String CONTRACT_DELAY = "contract/repayment/delay";

    // 延期试算
    public static final String CONTRACT_DELAY_CALC = "contract/repayment/delayCalc";

    // 获取合同预览
    public static final String GET_CONTRACT_PREVIEW = "app/order/agreement";

    // 续贷申请
    public static final String CONTINUED_LOAN = "ph/app/apply/continuedLoan";

    // 续贷提交申请
    public static final String CONTINUED_LOAN_SUBMIT = "ph/app/apply/continuedLoanSubmit";

    // 上传设备信息
    public static final String LOGIN_DEVICE_INFO = "app/data/user-phone-hardware";

    // 上传行为信息
    public static final String LOGIN_BEHAVIOR_INFO = "app/action/create";

    // 上传联系人
    public static final String CONTACT_INFO = "app/data/user-contact";

    // 上传短信
    public static final String MESSAGE_INFO = "app/customern/message";

    // 上传通话记录
    public static final String CALL_RECORD_INFO = "app/customer/callRecord";

    // 更新客户身份信息
    public static final String UPDATE_USERBASEINFO = "user/updateUserBaseInfo";

    // 更新联系人信息
    public static final String UPDATE_CONTACTINFO = "user/updateContactInfo";

    // 更新公司信息
    public static final String UPDATE_USECOMPANYINFO = "user/updateUseCompanyInfo";

    // 更新影相信息
    public static final String UPDATE_IMAGEINFO = "user/updateImageInfo";

    // 更新银行卡信息
    public static final String UPDATE_USEBANKCARDINFO = "user/updateUseBankCardInfo";

    // 上传谷歌获取的GPS
    public static final String GOOGLE_GPS = "https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyAaqOJLxpSEM--kc4EhB59s_r0TtVo_6gg";

    // 上传行为信息
    public static final String UPLOAD_BEHAVIOR = "app/action/create";
    public static final String UPLOAD_BEHAVIOR_V2 = "/app/data/user-behavior";
    public static final String UPLOAD_BEHAVIOR_NOTOKEN_V2 = "app/data/user-behavior-notoken";

    // 获取合同下载
    public static final String GET_CONTRACT_DOWNLOAD = "contract/download";

    // 上传还款凭证
    public static final String REPAYMENT_MARK = "user/transactionImage";

    // 获取用户信息
    public static final String USER_INFODETAIL = "user/getUserInfoDetail";

    // 取消再申请
    public static final String APPLY_BY_CANCEL = "ph/app/apply/applyByCancel";

    // 拒绝单再申请
    public static final String APPLY_BY_REJECT = "ph/app/apply/applyByReject";

    // 上传手机app列表
    public static final String UPLOAD_APP_INFO = "app/data/user-app-list";

    // 查看还款账户
    public static final String GET_REPAYMENT_ACCOUNT = "user/getRepaymentAccount";

    // 我的推广链接
    public static final String GET_RECOMMEND_URL = "recommend/getRecommendUrl";

    // 我的推广
    public static final String GET_MY_RECOMMEND = "recommend/myRecommend";

    // 我的优惠券
    public static final String GET_COUPON = "app/coupon/my-coupon";

    // 使用优惠券
    public static final String USE_MYCOUPON = "coupon/useMyCoupon";

    // 查询机构服务费
    public static final String GET_AGENCY_FEE = "payFee/getPayFee";

    // 保存补件身份证照片
    public static final String SAVE_SUPPLEMENT_PHOTO = "app/upload-replenish";

    // 上传补件身份证照片
    public static final String UPLOAD_SUPPLEMENT_IMAGE = "ph/app/apply/supplementImage";

    //获取是否展示线下取款
    public static final String GET_PAY_TYPE = "payFee/getPayType";

    //首页
    public static final String APP_INDEX = "app/index";

    // 短信验证码获取方式
    public static final String GET_SMS_CODE_TYPE = "validate/getcodeType";

    // 更新额度
    public static final String UPDATE_AMOUNT = "apply/updateAmount";

    //上传极光信息
    public static final String SAVE_NOTICE_INFO = "saveCustomerJpushInfo";

    //获取用户等级
    public static final String GET_USER_LEVEL = "user/getLevel";

    public static final String URI_INIT_USER = "app/user-init/index";

    //获取是否展示活体检测
    public static final String GET_TAKE_HOLD_TYPE = "config/getConfigValue/LIVENESS_DETECTION";

    // 用户注册
    /**
     * @deprecated
     */
    public static final String REGISTER = "app/register";
    //用户注册 使用验证码注册登录
    public static final String REGISTER_V2 = "app/login";

    //修改密码登录
    public static final String RESET_PASSWORD = "app/forgot-pwd";

    //修改密码提交
    public static final String RESET_PASSWORD_SUBMIT = "app/forgot-pwd";
    //修改密码
    public static final String CHANGE_PASSWORD = "app/change-pwd";

    //添加联系人
    public static final String ADD_CONTACT = "apply/supplementContact";

    //提交添加联系人
    public static final String ADD_CONTACT_SUBMIT = "apply/submitSupplementContact";

    //获取通知内容
    public static final String GET_NOTICE = "config/getNotification";

    //获取站内信
    public static final String GET_MESSAGE_LIST = "app/inbox/index";
    public static final String MESSAGE_SET_READ = "app/inbox/set-read";

    // 查询合同列表
    public static final String ORDER_LIST = "app/order/index";

    // 查询合同详情
    public static final String ORDER_DETAIL = "app/order/detail";

    // 创建订单
    public static final String CREATE_ORDER = "app/order/create";

    //签约
    public static final String SIGN = "app/order/sign";

    //频道列表
    public static final String GET_CHANNEL_LIST = "app/bank/channel_list";

    // 获取位置信息
    public static final String GET_LOCATION = "app/city/position-address";

    //更新订单
    public static final String UPDATE_ORDER = "app/order/update";

    //用户调查列表
    public static final String USER_SURVEY_LIST = "app/user-init/detention";

    //用户调查提交
    public static final String SUBMIT_USER_SURVEY = "app/user-init/surveys-submit";

    // 保存facebook 授权信息
    public static final String SAVE_FACEBOOK_INFO = "app/data/facebook";

    // 获取步骤
    public static final String GET_USER_STEP = "app/user-init/get-step";

    //获取机构列表
    public static final String GET_INSTITUTION_LIST = "app/bank/institution_list";

    //获取个人信息
    public static final String GET_USER_INFO = "app/user-info/get-user-detail";

    // 上传位置信息
    public static final String UPLOAD_LOCATION = "app/data/user-position";

    //上传相册信息
    public static final String UPLOAD_PHOTO_INFO = "app/data/user-photo-exif";

    //获取条形码
    public static final String GET_BAR_CODE = "app/payment/create-barcode";

    // 查询申请单状态
    public static final String GET_LAST_ORDER = "app/user-init/last-loan";

    // 上传通讯录总数
    public static final String UPLOAD_CONTACT_COUNT = "app/data/user-contact-count";

    //验证优惠券
    public static final String CHECKING_COUPON = "/app/coupon/checking";

    public static final String URI_MY_EFFECTIVE_COUPON = "/app/coupon/my-effective-coupon";
    //邀请好友
    public static final String URI_INVITE_FRIEND_CODE = "/app/user/invite-code";
    public static final String URI_INVITE_USERS = "/app/user/invited-users";
    //签约页验券
    public static final String URI_CHECKING_COUPON_ORDER = "/app/coupon/checking-after-ordersign";

    public static final String URI_BOUNS_ACTIVITY = "/app/user/bonus-activity";
    public static final String URI_BOUNS_ADD = "/app/user/bonus-add";


    public static final String URI_RENEWAL_INFO = "app/renewal/get-renewal-info";
    public static final String URI_APPLY_RENEWAL = "/app/renewal/apply-renewal";

    //银行还款
    public static final String URI_REPAY_BANK = "/app/repay/bank";

    //FawryPay支付
    public static final String URI_REPAY_REPAY = "/app/repay/repay";

    //还款列表
    public static final String URI_REPAY_LIST = "/app/repay/repay-list";

    //还款查询
    public static final String URI_REPAY_QUERY = "/app/repay/online-check";

    //银行卡列表
    public static final String URI_BANKCARD_LIST = "/app/repay/get-bank";

    //绑定银行卡
    public static final String URI_BIND_BANKCARD = "/app/repay/add-bank";

    //删除银行卡
    public static final String URI_DELETE_BANKCARD = "/app/repay/remove-bank";

    //使用银行卡还款
    public static final String URI_USE_BANKCARD_REPAYMENT = "/app/repay/use-bank";


}
