package com.u1.gocashm.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hewei
 * @date 2018/8/27 0027 下午 6:29
 */
public class VerifyUtil {

    public static boolean isValidPassword(String password) {
        if (!TextUtils.isEmpty(password) && password.length() >= 6 && password.length() <= 16 && okPassword(password)) {
            return true;
        }
        return false;
    }

    // 密码验证的正则表达式:由数字和字母组成，并且要同时含有数字和字母，且长度要在6-16位之间
    private static boolean okPassword(String password) {
        Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
        Matcher match = pattern.matcher(password);
        return match.matches();
    }

    /**
     * 验证名字
     * 检验1，字符：拉丁文
     * 检验2，长度：<40
     * 检验3，特殊字符：除了存在（'）的特殊字符; 无其他特殊字符
     *
     * @param name 名字
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkName(String name) {
        if (TextUtils.isEmpty(name.trim())) {
            return false;
        }
        String regex = "[aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ ']{1,40}";
        return Pattern.matches(regex, name);
    }

    public static boolean checkNameSize(String name) {
        if (!TextUtils.isEmpty(name) && name.contains(" ")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证身份证地址
     * 检验1，字符：拉丁文
     * 检验2，长度：<40
     * 检验3，特殊字符：除了存在（'）的特殊字符; 无其他特殊字符
     *
     * @param address 名字
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCardAddress(String address) {
        if (TextUtils.isEmpty(address.trim())) {
            return false;
        }
        String regex = "[1234567890aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ '.]{1,100}";
        return Pattern.matches(regex, address);
    }

    /**
     * 验证短信验证码
     *
     * @param code
     * @return
     */
    public static boolean checkSmsCode(String code) {
        if (TextUtils.isEmpty(code)) {
            return false;
        }
        String regex = "[0-9]{4}";
        return Pattern.matches(regex, code);
    }

    /**
     * 验证公司
     * 检验1，字符：拉丁文、数字
     *
     * @param company 公司
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkCompany(String company) {
        if (TextUtils.isEmpty(company.trim())) {
            return false;
        }
        String regex = "[aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ 0-9&]+";
        return Pattern.matches(regex, company);
    }

    /**
     * 验证手机
     * 字长：10位或11位
     *
     * @param phone 电话/手机
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPhoneLength(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        String regex = "[0-9]{5,11}";
        return Pattern.matches(regex, phone);
    }

    /**
     * 匹配菲律宾Driver’s License类型证件号
     *
     * @param card
     * @return
     */
    public static boolean checkIdCardForDriver(String card) {
        String regex = "[A-Za-z][0-9]{10}";
        return Pattern.matches(regex, card);
    }

    /**
     * 匹配菲律宾sss card类型证件号
     *
     * @param card
     * @return
     */
    public static boolean checkIdCardForSSS(String card) {
        String regex = "[0-9]{10}";
        return Pattern.matches(regex, card);
    }

    /**
     * 匹配菲律宾umid card类型证件号
     *
     * @param card
     * @return
     */
    public static boolean checkIdCardForUMID(String card) {
        String regex = "[0-9]{12}";
        return Pattern.matches(regex, card);
    }

    /**
     * 匹配菲律宾PRC类型证件号
     *
     * @param card
     * @return
     */
    public static boolean checkIdCardForPRC(String card) {
        String regex = "[0-9]{7}";
        return Pattern.matches(regex, card);
    }

    /**
     * 匹配菲律宾TIN类型证件号
     *
     * @param card
     * @return
     */
    public static boolean checkIdCardForTIN(String card) {
        if (TextUtils.isEmpty(card)) {
            return false;
        }
        if (card.length() == 12) {
            String str = card.substring(card.length() - 3);
            if ("000".equals(str)) {
                String regex = "[0-9]{12}";
                return Pattern.matches(regex, card);
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    /**
     * 匹配菲律宾Passport类型证件号
     *
     * @param card
     * @return
     */
    public static boolean checkIdCardForPassport(String card) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{9}$";
        return Pattern.matches(regex, card);
    }

    /**
     * 匹配菲律宾10位银行卡
     *
     * @param card
     * @return
     */
    public static boolean checkBankCardWith10(String card) {
        String regex = "[0-9]{10}";
        return Pattern.matches(regex, card);
    }

    /**
     * 匹配菲律宾China Bank银行卡
     *
     * @param card
     * @return
     */
    public static boolean checkBankCardForChinaBank(String card) {
        String regex = "[0-9]{10,12}";
        return Pattern.matches(regex, card);
    }


    /**
     * 匹配菲律宾10位银行卡，首位位5-7
     *
     * @param card
     * @return
     */
    public static boolean checkBankCardWith102(String card) {
        String regex = "[5-7][0-9]{9}";
        return Pattern.matches(regex, card);
    }

    /**
     * 匹配菲律宾12位银行卡
     *
     * @param card
     * @return
     */
    public static boolean checkBankCardWith12(String card) {
        String regex = "[0-9]{12}";
        return Pattern.matches(regex, card);
    }

    /**
     * 匹配菲律宾13位银行卡
     *
     * @param card
     * @return
     */
    public static boolean checkBankCardWith13(String card) {
        String regex = "[0-9]{13}";
        return Pattern.matches(regex, card);
    }

    /**
     * 匹配菲律宾16位银行卡
     *
     * @param card
     * @return
     */
    public static boolean checkBankCardWith16(String card) {
        String regex = "[0-9]{16}";
        return Pattern.matches(regex, card);
    }

    public static boolean isBankError(String bank, String bankNum) {
        switch (bank) {
            case "BPI":
            case "Land Bank":
                if (!checkBankCardWith10(bankNum)) {
                    return true;
                }
                break;
            case "UCPB":
            case "Union Bank":
            case "Eastwest Bank":
            case "PNB":
            case "Asia United Bank":
                if (!checkBankCardWith12(bankNum)) {
                    return true;
                }
                break;
            case "Metrobank":
            case "Security Bank":
                if (!checkBankCardWith13(bankNum)) {
                    return true;
                }
                break;
            case "BPI Family Savings Bank":
                if (!checkBankCardWith102(bankNum)) {
                    return true;
                }
                break;
            case "RCBC":
                if (!(checkBankCardWith10(bankNum) || checkBankCardWith16(bankNum))) {
                    return true;
                }
                break;
            case "China Bank":
                if (!checkBankCardForChinaBank(bankNum)) {
                    return true;
                }
                break;
            case "BDO(Banco De Oro)":
                if (!(checkBankCardWith10(bankNum) || checkBankCardWith12(bankNum))) {
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }

    /**
     * 验证菲律宾联系人
     * todo
     */
    public static boolean checkPhoneForPh(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        String regex = "^0[0-9]{10}$";
        return Pattern.matches(regex, phone);
    }

    /**
     * 验证菲律宾手机
     * 字长：10位,首位9
     * todo
     *
     * @param phone 电话/手机
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        String regex = "^0[0-9]{10}$";
        return Pattern.matches(regex, phone);
    }

    // 判断一个字符串是否含有数字
    public static boolean hasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 匹配菲律宾证件号
     *
     * @param card
     * @return
     */
    public static boolean checkIdCard(String cardType, String card) {
        switch (cardType) {
            case "Driver’s License ":
                if (checkIdCardForDriver(card)) {
                    return true;
                }
                break;
            case "SSS card ":
                if (checkIdCardForSSS(card)) {
                    return true;
                }
                break;
            case "UMID ":
                if (checkIdCardForUMID(card)) {
                    return true;
                }
                break;
            case "TIN":
                if (checkIdCardForTIN(card)) {
                    return true;
                }
                break;
            case "PRC ID ":
                if (checkIdCardForPRC(card)) {
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }

}
