package com.u1.gocashm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * date，time，datetime的辅助类
 */
public class DateTimePhUtil {
	
	private static Date minDate = DateTimePhUtil.parseDateTime("1970-01-01 08:00:01");
	
	/**
	 * 根据当前日期，获取月初日期
	 * @param sourceDate 当前时间
	 * @author jianhua.huang
	 * */
	public static Date getBeginMonth(Date sourceDate)
	{
		if(null == sourceDate)
		{
			return null;
		}
		Calendar calendar = getBeginDay(sourceDate);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		
		return calendar.getTime();
	}
	
	/**
	 * 根据当前日期获取月末日期
	 * @author jianhua.huang
	 * @param sourceDate 当前日期
	 * */
	public static Date getEndMonth(Date sourceDate)
	{
		if(null == sourceDate)
		{
			return null;
		}
		Calendar calendar = getEndDay(sourceDate);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		return calendar.getTime();
	}
	
	/**
	 * 根据当前时间获取年初时间
	 * @author jianhua.huang
	 * @param sourceDate 给定时间
	 * */
	public static Date getBeginYear(Date sourceDate)
	{
		if(null == sourceDate)
		{
			return null;
		}
		Calendar calendar = getBeginDay(sourceDate);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
		
		return calendar.getTime();
	}
	
	/**
	 * 根据当前时间获取年末时间
	 * @author jianhua.huang
	 * @param sourceDate 给定时间
	 * */
	public static Date getEndYear(Date sourceDate)
	{
		if(null == sourceDate)
		{
			return null;
		}
		Calendar calendar = getEndDay(sourceDate);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
		
		return calendar.getTime();
	}
	
	/**
	 * 获取一天当中的结束时间
	 * @author jianhua.huang
	 * @param sourceDate 给定时间
	 * */
	public static Calendar getEndDay(Date sourceDate)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sourceDate);
		calendar.set(Calendar.HOUR,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		calendar.set(Calendar.MILLISECOND,999);
		
		return calendar;
	}
	
	/**
	 * 获取一天当中的开始时间
	 * @author jianhua.huang
	 * @param sourceDate 给定时间
	 * */
	public static Calendar getBeginDay(Date sourceDate)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sourceDate);
		calendar.set(Calendar.HOUR,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		
		return calendar;
	}
	
	/**
	 * 获取制定月的月半时间
	 * @param sourceDate 给定日期
	 * @param middleDay 这个参数可以指定哪天是给定日期的月半日期，如果不传则默认15号为月半日期
	 * @return 月半日期
	 * */
	public static Date getMiddleDay(Date sourceDate, Integer middleDay)
	{
		int temp = 0;
		if(null == middleDay)
		{
			temp = 14;
		}
		else
		{
			temp = middleDay.intValue() - 1;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sourceDate);
		calendar.set(Calendar.DAY_OF_MONTH, temp);
		calendar.set(Calendar.HOUR,12);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		
		return calendar.getTime();
	}
	
	/**
	 * 取得当前的Timestamp
	 * 
	 * @return 当前的Timestamp
	 */
	public static java.sql.Timestamp nowTimestamp() {
		return getTimestamp(System.currentTimeMillis());
	}

	/**
	 * 毫秒数转化成Tiemstamp时间类型
	 * 
	 * @param time
	 * @return
	 */
	public static java.sql.Timestamp getTimestamp(long time) {
		return new java.sql.Timestamp(time);
	}

	/**
	 * 抽取日期的年
	 */
	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
	public static int getHour(Date date){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}
	/**
	 * 日期格式化成字符串，默认采用格式为"yyyy-MM-dd"
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式字符串(null采用默认格式)
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		if (pattern == null) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return (sdf.format(date));
	}

	/**
	 * 日期格式化成字符串，采用格式为"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static String formatDateTime(Date date) {
		return (formatDate(date, "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 当前日期格式化成字符串，采用格式为"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return
	 */
	public static String formatDateTime() {
		return (formatDate(now(), "yyyy-MM-dd HH:mm:ss"));
	}

    /**
     * 当前日期格式化成字符串，采用格式为"yyyyMMddHHmmss"
     *
     * @return
     */
    public static String formatDateTimeNormal() {
        return (formatDate(now(), "yyyyMMddHHmmss"));
    }

	/**
	 * 日期格式化成字符串，采用格式为"yyyy-MM-dd"
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static String formatDate(Date date) {
		return (formatDate(date, "yyyy-MM-dd"));
	}

	/**
	 * 日期格式化成字符串，采用格式为"yyyyMMdd"
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static String formatDate(Date date, boolean flag) {
		return (formatDate(date, "yyyyMMdd"));
	}

	/**
	 * 当前日期格式化成字符串，采用格式为"yyyy-MM-dd"
	 * 
	 * @return
	 */
	public static String formatDate() {
		return (formatDate(now(), "yyyy-MM-dd"));
	}

	/**
	 * 日期的时间部分转化成字符串，采用格式为"HH:mm:ss"
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static String formatTime(Date date) {
		return (formatDate(date, "HH:mm:ss"));
	}

	/**
	 * 当前时间部分转化成字符串，采用格式为"HH:mm:ss"
	 * 
	 * @return
	 */
	public static String formatTime() {
		return (formatDate(now(), "HH:mm:ss"));
	}

	/**
	 * 日期的时间部分转化成字符串，采用格式为"HHmmss"
	 *
	 *            日期
	 * @return
	 */
	public static String formatTime2() {
		return (formatDate(now(), "HHmmss"));
	}

	/**
	 * 返回当前时间
	 * 
	 * @return
	 */
	public static Date now() {
		return (new Date());
	}

	/**
	 * 字符串转化城市间，采用格式为"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param datetime
	 *            日期
	 * @return
	 */
	public static Date parseDateTime(String datetime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if ((datetime == null) || (datetime.equals(""))) {
			return null;
		} else {
			try {
				return formatter.parse(datetime);
			} catch (ParseException e) {
				return parseDate(datetime);
			}
		}
	}

	/**
	 * 字符串转化日期，采用格式为"yyyy-MM-dd"
	 * 
	 * @param
	 *
	 * @return
	 */
	public static Date parseDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		if ((date == null) || (date.equals(""))) {
			return null;
		} else {
			try {
				return formatter.parse(date);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	/**
	 * 截取时间部分，返回只有年月日的日期
	 * 
	 * @param datetime
	 * @return
	 */
	public static Date parseDate(Date datetime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		if (datetime == null) {
			return null;
		} else {
			try {
				return formatter.parse(formatter.format(datetime));
			} catch (ParseException e) {
				return null;
			}
		}
	}
	
	
	/**
	 * 返回只有年月日 时分秒
	 * 
	 * @param datetime
	 * @return
	 */
	public static Date parseDateTime(Date datetime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (datetime == null) {
			return null;
		} else {
			try {
				return formatter.parse(formatter.format(datetime));
			} catch (ParseException e) {
				return null;
			}
		}
	}

	/**
	 * 字符串转化日期，采用格式为"yyyyMMdd"
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static Date parseDateYYMMDD(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

		if ((date == null) || (date.equals(""))) {
			return null;
		} else {
			try {
				return formatter.parse(date);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	/**
	 * 给时间加上或减去指定毫秒，秒，分，时，天、月或年等，返回变动后的时间
	 * 
	 * @param date
	 *            要加减前的时间，如果不传，则为当前日期
	 * @param field
	 *            时间域，有Calendar.MILLISECOND,Calendar.SECOND,Calendar.MINUTE,<br>
	 *            Calendar.HOUR,Calendar.DATE, Calendar.MONTH,Calendar.YEAR
	 * @param amount
	 *            按指定时间域加减的时间数量，正数为加，负数为减。
	 * @return 变动后的时间
	 */
	public static Date add(Date date, int field, int amount) {
		if (date == null) {
			date = new Date();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);

		return cal.getTime();
	}

	/**
	 * 日期增加毫秒数
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMilliSecond(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}

	/**
	 * 日期增加秒数部分
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addSecond(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	/**
	 * 日期增加分钟部分
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMiunte(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	/**
	 * 日期增加小时部分
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addHour(Date date, int amount) {
		return add(date, Calendar.HOUR, amount);
	}

	/**
	 * 日期增加天数部分
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addDay(Date date, int amount) {
		return add(date, Calendar.DATE, amount);
	}

	/**
	 * 日期增加月份部分
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMonth(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	/**
	 * 日期增加年份部分
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addYear(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}
	
	/**
	 * 计算两个日期相差多少秒
	 * @author jianhua.huang
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 计算结果
	 * */
	public static Long intervalSecond(Date startDate, Date endDate)
	{
		startDate = startDate!=null?startDate:new Date();
		endDate = endDate!=null?endDate:new Date();
		long start = startDate.getTime();
		long end = endDate.getTime();
		Long intervalMin = Math.abs(end - start)/1000;
		return intervalMin;
	}
	
	/**
	 * 计算两个日期相差多少毫秒
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 计算结果
	 * */
	public static Long intervalMilliSecond(Date startDate, Date endDate)
	{
		startDate = startDate!=null?startDate:new Date();
		endDate = endDate!=null?endDate:new Date();
		long start = startDate.getTime();
		long end = endDate.getTime();
		Long intervalMin = Math.abs(end - start);
		return intervalMin;
	}
	
	
	/**
	 * 求两个日期相差多少分钟
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int intervalMin(Date startDate, Date endDate){
		startDate = startDate!=null?startDate:new Date();
		endDate = endDate!=null?endDate:new Date();
		long start = startDate.getTime();
		long end = endDate.getTime();
		int intervalMin = (int) (Math.abs(end - start)/(1000*60));
		return intervalMin;
	}
	
	/**
	 * 求两个日期相差多少天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int intervalDay(Date startDate, Date endDate){
		startDate = startDate!=null?startDate:new Date();
		endDate = endDate!=null?endDate:new Date();
		long start = startDate.getTime();
		long end = endDate.getTime();
		int intervalMin = (int) (Math.abs(end - start)/(1000*60*60*24));
		return intervalMin;
	}
	
	/**
	 * 求两个日期相差多少月数，按每月30天计算，向下取整
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int intervalMonthBy30(Date startDate, Date endDate){
		startDate = startDate != null ? startDate : new Date();
		endDate = endDate != null ? endDate : new Date();
		long start = startDate.getTime();
		long end = endDate.getTime();
		int intervalMon = (int) (Math.abs(end - start) / (1000 * 60 * 60 * 24 * 30L));
		return intervalMon;
	}
	
	/**
	 * 求两个日期相差多少年数，按每年365天计算，向下取整
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int intervalYearBy365(Date startDate, Date endDate){
		startDate = startDate != null ? startDate : new Date();
		endDate = endDate != null ? endDate : new Date();
		long start = startDate.getTime();
		long end = endDate.getTime();
		int intervalYear = (int) (Math.abs(end - start) / (1000 * 60 * 60 * 24 * 365L));
		return intervalYear;
	}
	
	/**
	 * 开始时间减结束时间小于等于**天
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param day 间隔天数
	 * @return
	 * <br>[创建人:xiaoLei],[创建时间:2017年1月10日 下午5:10:11]
	 * <br>[修改人:**],[修改时间:**],[修改内容:**]
	 */
	public static boolean intervalDayByLessOrEqual(Date startTime, Date endTime, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startTime);
		calendar.add(Calendar.DATE, day);
		Date addDate = calendar.getTime();
		if (addDate.compareTo(endTime) != -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 开始时间减结束时间小于等于**月
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param month 间隔月份
	 * @return
	 * <br>[创建人:xiaoLei],[创建时间:2017年1月11日 下午3:10:17]
	 * <br>[修改人:**],[修改时间:**],[修改内容:**]
	 */
	public static boolean intervalMonthByLessOrEqual(Date startTime, Date endTime, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startTime);
		calendar.add(Calendar.MONTH, month);
		Date addDate = calendar.getTime();
		if (addDate.compareTo(endTime) != -1) {
			return true;
		}
		return false;
	}

	public static String changeYMDtoEn(String dateYMD){
		try {
			return new SimpleDateFormat("dd/MMM/yyyy", Locale.UK).format(new Date(dateYMD));
		} catch (Exception e) {

		}
		return dateYMD;
	}


	public static String changeYMDtoEn2(String dateYMD){
		try {
			return new SimpleDateFormat("MMM dd,yyyy", Locale.UK).format(new Date(dateYMD));
		} catch (Exception e) {

		}
		return dateYMD;
	}

	public static String changeYMDtoEn3(String dateYMD){
		try {
			String[] dates = dateYMD.split("-");
			return new SimpleDateFormat("dd/MMM/yyyy", Locale.UK).format(new Date(dates[2] + "/" + dates[1] + "/" + dates[0]));
		} catch (Exception e) {

		}
		return dateYMD;
	}
}
