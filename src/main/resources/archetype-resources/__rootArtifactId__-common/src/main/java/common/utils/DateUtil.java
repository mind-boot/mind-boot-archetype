#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

/**
 * 日期工具类
 *
 * @author caoyong
 * @version 1.0.0
 * @since 2018-11-28 18:44
 **/
public class DateUtil {
    private static Logger log = LoggerFactory
            .getLogger(DateUtil.class);

    private static final ThreadLocal<Map<String, SimpleDateFormat>> sdfMap = new ThreadLocal<>();

    public static String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

    public static SimpleDateFormat yyyyMMddWithLineSDF = new SimpleDateFormat(
            "yyyy-MM-dd");

    /**
     * yyyy-MM-dd
     */
    public static String ZH_CN_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String ZH_CN_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyyMMddHHmmss
     */
    public static String NOMARK_DATETIME_PATTERN = "yyyyMMddHHmmss";

    public DateUtil() {
    }

    private static String setMsgContent(String msg, String content) {
        StringBuffer buffer = new StringBuffer(msg);
        buffer.append(content);
        return buffer.substring(buffer.length() - msg.length(), buffer.length());
    }

    /**
     * 功能: 将字符串转换为指定格式的日期返回
     *
     * @param dateStr   要转换的字符串
     * @param inFormat  与dateStr字符串相同格式的原日期格式
     * @param outFormat 目标日期格式
     * @return
     */
    public static String formatStrToDate(String dateStr, String inFormat, String outFormat) {
        if (dateStr == null || "".equals(dateStr))
            return "";
        SimpleDateFormat sdf1 = new SimpleDateFormat(inFormat);
        Date date = null;
        try {
            date = sdf1.parse(dateStr);
        } catch (ParseException e) {
            //            e.printStackTrace();
            log.error(e.getMessage(), e);
            return "";
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat(outFormat);
        return sdf2.format(date);
    }

    /**
     * 将datefield取出的String型,直接转换为Timestamp型
     *
     * @param datefield (String)Tue Sep 7 12:00:00 UTC+0800 2010
     * @param pattern   (String)yyyy-MM-dd 23:59:59
     * @return (Timestamp)2010-09-07 23:59:59.0
     */
    public static Timestamp formatDatefield(String datefield, String pattern) throws Exception {
        datefield = datefield.replaceAll("UTC ", "UTC+");//url传参时+号会被过滤
        @SuppressWarnings("deprecation")
        Date date = new Date(datefield);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String time = sdf.format(date);
        Timestamp ts = Timestamp.valueOf(time);
        return ts;
    }

    /**
     * 将String类型转换为Timestamp
     *
     * @param registerTime (String)2010-09-07 23:59:59
     * @return (Timestamp)2010-09-07 23:59:59.0
     */
    public static Timestamp formatDate(String registerTime) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(registerTime);
        Timestamp ts = Timestamp.valueOf(time);
        return ts;
    }

    /**
     * 功能：当天是当年的第几天
     */
    public static String getDayOfYear() {
        Calendar cal = Calendar.getInstance();
        int dayno = cal.get(Calendar.DAY_OF_YEAR);
        return setMsgContent("000", "" + (dayno - 1));
    }

    /**
     * 格式化字符串日期
     * @param date
     * @return
     */
    public static String formatStrDate(String date) {
        if (StringUtils.isBlank(date)) return date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(ZH_CN_DATE_PATTERN);
            return sdf.format(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 功能：计算某一天是当年的第几天 strdate"yyyyMMdd"
     *
     * @throws Exception
     */
    public static String getDayOfYear(String strdate) throws Exception {
        Calendar cal = Calendar.getInstance();
        strdate = strdate.substring(0, 4) + "-" + strdate.substring(4, 6) + "-" + strdate.substring(6, 8);
        try {
            cal.setTime(getDate(strdate));
        } catch (Exception e) {
            throw new Exception();
        }
        int dayno = cal.get(Calendar.DAY_OF_YEAR);
        return setMsgContent("000", "" + (dayno - 1));
    }

    /**
     * 功能：取应用服务器日期并以"yyyy-MM-dd HH:mm:ss"格式返回
     */
    public static String getDateTime() {
        return getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取当前日期的时间戳
     */
    public static String getDateTimeString() {
        return getCurrentDateByFormat("yyyyMMddHHmmss");
    }

    /**
     * 功能：取应用服务器日期并以"yyyy-MM-dd格式返回
     */
    public static String getDateStr() {
        return getCurrentDateByFormat("yyyy-MM-dd");
    }

    /**
     * 功能：取应用服务器时间并以"yyyyMMddHHmmss"格式返回
     */
    public static String getDateTimeForLong() {
        return getCurrentDateByFormat("yyyyMMddHHmmss");
    }

    /**
     * 功能：取应用服务器时间并以"yyyyMMdd"格式返回
     */
    public static String getDateForLong() {
        return getCurrentDateByFormat("yyyyMMdd");
    }

    /**
     * 功能：取应用服务器时间并以"HHmmss"格式返回
     */
    public static String getTimeForLong() {
        return getCurrentDateByFormat("HHmmss");
    }

    /**
     * 功能：取当前服务器时间并以参数指定的格式返回
     */
    public static String getCurrentDateByFormat(String formatStr) {
        long currentTime = System.currentTimeMillis();
        java.util.Date date = new java.util.Date(currentTime);
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(formatStr);
        return formatter.format(date);
    }

    /**
     * 功能：转换long型为日期型字串并以"yyyy-MM-dd HH:mm:ss"格式返回
     */
    public static String getDateTime(long al_datetime) {
        java.util.Date date = new java.util.Date(al_datetime);
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * 转换Date为字符串类型,并以"yyyy-MM-dd HH:mm:ss"格式返回
     *
     * @param date
     * @return
     */
    public static String getDateTime(Date date) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(ZH_CN_DATETIME_PATTERN);
        return formatter.format(date);
    }

    /**
     * 转换Date为字符串类型,并以"yyyy-MM-dd"格式返回
     *
     * @param date
     * @return
     */
    public static String getDate(Date date) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(ZH_CN_DATE_PATTERN);
        return formatter.format(date);
    }

    public static String getYear() {
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy");
        return formatter.format(date);
    }

    /**
     * 功能：转换日期型为字串
     */
    public static String getDateString(java.util.Date inDate) {
        return inDate.toString();
    }

    /**
     * 功能：把给定日期与给定天数进行加减运算,返回一个新日期
     */
    public static java.util.Date getDateNDays(java.util.Date date, int days) {//
        if (days > 36500 || days < -36500) {
            //            System.out.println("请把日期限制在100年内");
            return null;
        }
        long l1 = 24, l2 = 60, l3 = 1000, l4 = days;
        long lDays = l1 * l2 * l2 * l3 * l4; //所改变天数的毫秒数
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long lCurrentDate = calendar.getTimeInMillis(); //给定日期的毫秒日期
        long lUpdatedDate = lCurrentDate + lDays; //给定日期与给定天数运算后的毫秒日期
        java.util.Date dateNew = new java.util.Date(lUpdatedDate); //结果日期
        return dateNew;
    }

    /**
     * 功能：把给定日期与给定天数进行加减运算,返回一个新日期
     */
    public static String getDateNDaysStr(java.util.Date date, int days) {//
        if (days > 36500 || days < -36500) {
            //            System.out.println("请把日期限制在100年内");
            return null;
        }
        long l1 = 24, l2 = 60, l3 = 1000, l4 = days;
        long lDays = l1 * l2 * l2 * l3 * l4; //所改变天数的毫秒数
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long lCurrentDate = calendar.getTimeInMillis(); //给定日期的毫秒日期
        long lUpdatedDate = lCurrentDate + lDays; //给定日期与给定天数运算后的毫秒日期
        java.util.Date dateNew = new java.util.Date(lUpdatedDate); //结果日期
        return yyyyMMddWithLineSDF.format(dateNew);
    }

    /**
     * 功能：把给定日期与给定天数进行加减运算,返回一个新日期
     *
     * @throws ParseException
     */
    public static java.util.Date getDateNDays(String date, int days) throws ParseException {//
        if (days > 36500 || days < -36500) {
            //            System.out.println("请把日期限制在100年内");
            return null;
        }
        long l1 = 24, l2 = 60, l3 = 1000, l4 = days;
        long lDays = l1 * l2 * l2 * l3 * l4; //所改变天数的毫秒数
        Calendar calendar = Calendar.getInstance();
        //        Date date1 = new Date(date);
        Date date1 = DateFormat.getDateTimeInstance().parse(date);
        calendar.setTime(date1);
        long lCurrentDate = calendar.getTimeInMillis(); //给定日期的毫秒日期
        long lUpdatedDate = lCurrentDate + lDays; //给定日期与给定天数运算后的毫秒日期
        java.util.Date dateNew = new java.util.Date(lUpdatedDate); //结果日期
        return dateNew;
    }

    /**
     * 功能：把给定日期与给定天数进行加减运算,返回一个yyyyMMdd的新日期
     */

    public static String getDateFromNDays(int days) {
        long currentTime = System.currentTimeMillis();
        Date date = getDateNDays(new Date(currentTime), days);
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
        return formatter.format(date);
    }

    /**
     * 功能：把给定日期与给定天数进行加减运算,返回一个yyyyMMdd的新日期
     */

    public static String getDateFromNYears(int years) {
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
        int day = Integer.parseInt(formatter.format(date));
        day += years * 10000;
        return "" + day;
    }

    /**
     * 把给定日期与给定月数进行运算,月数可以是负数.返回给定日期与给定日期的差或和
     *
     * @param as_date:格式是yyyymm的日期,现支持yyyy-mm-dd型日期
     * @param months
     * @return
     * @throws Exception
     */
    public static Date getDateNMonths(String as_date, int months) throws Exception {
        String as_dateTem = "";
        //yan_modi_20060708
        if (as_date.length() == 10) {//yyyy-mm-dd型日期
            as_dateTem = as_date.substring(0, 7) + "-01";
        } else {//yyyymm型日期
            as_dateTem = as_date.substring(0, 4) + "-" + as_date.substring(4, as_date.length()) + "-01"; //把yyyymm型的年月日期转换为yyyy-mm型的日期,在后面和-01相加,组成一个合法日期
        }
        Date ad_date = null;
        try {
            ad_date = getDate(as_dateTem);
        } catch (Exception ex) {
            // ex.setClientMessage("日期处理出错！");
            //ex.setLogMessage("getDateNMonths(String as_date, int months) 出错！");
            throw ex;
        }
        ad_date = getDateNMonths(ad_date, months);

        //入参字符串是格式为yyyymm的日期
        return ad_date;
    }

    /**
     * 功能：把给定日期与给定月数进行运算,月数可以是负数.返回给定日期与给定日期的差或和
     * 若形成的新日期非法,则自动对新日期进行调整,例如:2004年1月31日加1个月为2004年2月31日,系统自动调整为2004年2月29日
     */
    public static Date getDateNMonths(java.util.Date date, int months) {
        if (months == 0) { //月数为零,直接返回给定日期
            return date;
        }
        if (months > 1200 || months < -1200) { //日期限制在100年以内
            return null;
        }
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(Calendar.MONTH, months);
        java.util.Date date2 = gc.getTime();
        return date2;
    }

    /**
     * 得到当前日期(java.sql.Date类型)，注意：没有时间，只有日期
     *
     * @return 当前日期
     */
    public static java.sql.Date getDate() {
        Calendar oneCalendar = Calendar.getInstance();
        return getDate(oneCalendar.get(Calendar.YEAR), oneCalendar.get(Calendar.MONTH) + 1,
                oneCalendar.get(Calendar.DATE));
    }

    /**
     * 根据所给的起始,终止时间来计算间隔天数
     */
    public static int getIntervalDay(java.sql.Date startDate, java.sql.Date endDate) {//
        long startdate = startDate.getTime();
        long enddate = endDate.getTime();
        long interval = enddate - startdate;
        int intervalday = (int) interval / (1000 * 60 * 60 * 24);
        return intervalday;
    }

    /**
     * 根据所给年、月、日，得到日期(java.sql.Date类型)，注意：没有时间，只有日期。
     * 年、月、日不合法，会抛IllegalArgumentException(不需要catch)
     */
    public static java.sql.Date getDate(int yyyy, int MM, int dd) {
        if (!verityDate(yyyy, MM, dd)) {
            throw new IllegalArgumentException("This is illegimate date!");
        }

        Calendar oneCalendar = Calendar.getInstance();
        oneCalendar.clear();
        oneCalendar.set(yyyy, MM - 1, dd);
        return new java.sql.Date(oneCalendar.getTime().getTime());
    }

    /**
     * 根据所给的起始,终止时间来计算间隔天数
     */
    public static int getIntervalDay2(Date startDate, Date endDate) {//
        long startdate = startDate.getTime();
        long enddate = endDate.getTime();
        long interval = enddate - startdate;
        int intervalday = (int) (interval / (1000 * 60 * 60 * 24));
        return intervalday;
    }

    /**
     * 根据所给年、月、日，检验是否为合法日期。
     */
    public static boolean verityDate(int yyyy, int MM, int dd) {//
        boolean flag = false;

        if (MM >= 1 && MM <= 12 && dd >= 1 && dd <= 31) {
            if (MM == 4 || MM == 6 || MM == 9 || MM == 11) {
                if (dd <= 30) {
                    flag = true;
                }
            } else if (MM == 2) {
                if (yyyy % 100 != 0 && yyyy % 4 == 0 || yyyy % 400 == 0) {
                    if (dd <= 29) {
                        flag = true;
                    }
                } else if (dd <= 28) {
                    flag = true;
                }
            } else {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * @param as_startDate 格式：yyyymm或yyyy-mm-dd
     * @param as_endDate   格式：yyyymm或yyyy-mm-dd
     * @return
     * @throws Exception
     */
    public static int getIntervalMonth(String as_startDate, String as_endDate) throws Exception {
        String ls_startD = "", ls_endD = "";
        Date ld_start = null;
        Date ld_end = null;
        if (as_startDate.length() == 6) {//yyyymm型
            ls_startD = as_startDate.substring(0, 4) + "-" + as_startDate.substring(4, as_startDate.length()) + "-01"; //把yyyymm型的年月日期转换为yyyy-mm型的日期,在后面和-01相加,组成一个合法日期
            ls_endD = as_endDate.substring(0, 4) + "-" + as_endDate.substring(4, as_endDate.length()) + "-01"; //把yyyymm型的年月日期转换为yyyy-mm型的日期,在后面和-01相加,组成一个合法日期
        } else {
            ls_startD = as_startDate;
            ls_endD = as_endDate;
        }
        //System.out.println("as_startD:"+as_startD);    //System.out.println("as_endD:"+as_endD);

        try {
            ld_start = getDate(ls_startD);
            ld_end = getDate(ls_endD);
        } catch (Exception ex) {
            //            ex.printStackTrace();
            log.error(ex.getMessage(), ex);
        }
        int interval = getIntervalMonth(ld_start, ld_end);
        return interval;
    }

    /**
     * 功能：根据所给的起始,终止时间来计算间隔月数；
     *
     * @param startDate date
     * @param endDate   date
     * @return
     */
    public static int getIntervalMonth(java.util.Date startDate, java.util.Date endDate) {
        java.text.SimpleDateFormat mmformatter = new java.text.SimpleDateFormat("MM");
        int monthstart = Integer.parseInt(mmformatter.format(startDate));
        int monthend = Integer.parseInt(mmformatter.format(endDate));
        java.text.SimpleDateFormat yyyyformatter = new java.text.SimpleDateFormat("yyyy");
        int yearstart = Integer.parseInt(yyyyformatter.format(startDate));
        int yearend = Integer.parseInt(yyyyformatter.format(endDate));
        return (yearend - yearstart) * 12 + (monthend - monthstart);
    }

    /**
     * 功能：将入参为"yyyy-mm-dd HH:mm:ss"或"yyyy-mm-dd"形式的字符串转换为Date并进行校验；
     */
    public static java.util.Date getDate(String strdate) throws Exception {
        int yyyy = Integer.parseInt(strdate.substring(0, 4));
        String temp = strdate.substring(5, strdate.length());
        int MM = Integer.parseInt(temp.substring(0, temp.indexOf("-")));
        temp = temp.substring(temp.indexOf("-") + 1, temp.length());
        int dd;
        if (temp.indexOf(" ") > 0) {
            dd = Integer.parseInt(temp.substring(0, temp.indexOf(" ")));
        } else {
            dd = Integer.parseInt(temp);
        }
        if (!verityDate(yyyy, MM, dd)) {
            throw new Exception("非法日期数据");
        }

        java.util.Date d;
        try {
            if (strdate.length() > 10) {

                //d = DateFormat.getDateTimeInstance().parse(strdate);
                java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                d = formatter.parse(strdate.substring(0, 19));

            } else {
                java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
                d = formatter.parse(strdate);
            }
            //      System.out.println(formatter.format(d));
            return d;
        } catch (ParseException e) {
            throw new Exception("日期数据转换错" + e.toString());
        }
    }

    /**
     * 功能：将入参为"yyyyMMdd"形式的字符串转换为Date并进行校验；
     */
    public static java.util.Date getDate2(String strdate) throws Exception {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
        return formatter.parse(strdate);

    }

    public static String formatDate(String strdate, String inFomrat, String outFormat) {
        if (strdate == null || strdate.equals(""))
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(inFomrat);
        Date date;
        try {
            date = formatter.parse(strdate);
        } catch (ParseException e) {
            return "";
        }
        formatter = new SimpleDateFormat(outFormat);
        return formatter.format(date);
    }

    /* 得到了系统当前日期时间 */
    public static String getSysDate() {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = simpledateformat.format(Calendar.getInstance().getTime());
        return s;
    }

    public static String getCurYearMonth() {
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMM");
        return formatter.format(date);
    }

    public static java.sql.Timestamp formateSqlTimestamp(String formate) {
        if (formate == null || formate.equals(""))
            return null;
        return java.sql.Timestamp.valueOf(formate + " 00:00:00");
    }

    /**
     * 获取指定日期的0点0分0秒的时间
     *
     * @param date
     * @return
     */
    public static java.sql.Timestamp getFirstTime(String date) {
        if (date == null || date.equals("")) {
            return null;
        }
        return java.sql.Timestamp.valueOf(date + " 00:00:00.0");
    }

    /**
     * 获取指定日期的23点59分59秒的时间
     *
     * @param date
     * @return
     */
    public static java.sql.Timestamp getLastTime(String date) {
        if (date == null || date.equals("")) {
            return null;
        }
        return java.sql.Timestamp.valueOf(date + " 23:59:59.999");
    }

    /**
     * 将日期转换为yyyy-MM-dd格式的字符串
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        return myFormat.format(date);
    }

    /**
     * 将日期转换为指定格式的字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat myFormat = new SimpleDateFormat(format);
        return myFormat.format(date);
    }

    @SuppressWarnings("deprecation")
    public static Timestamp addTimes(Timestamp time, int year, int month, int days, int hours, int minutes,
                                     int seconds) {
        if (time == null) {
            return null;
        }
        return new Timestamp(time.getYear() + year, time.getMonth() + month, time.getDate() + days,
                time.getHours() + hours, time.getMinutes() + minutes, time.getSeconds() + seconds, 0);
    }

    @SuppressWarnings("deprecation")
    public static Timestamp addDays(Timestamp time, int days) {
        if (time == null) {
            return null;
        }
        return new Timestamp(time.getYear(), time.getMonth(), time.getDate() + days, time.getHours(), time.getMinutes(),
                time.getSeconds(), 0);
    }

    @SuppressWarnings("deprecation")
    public static Timestamp addHours(Timestamp time, int hours) {
        if (time == null) {
            return null;
        }
        return new Timestamp(time.getYear(), time.getMonth(), time.getDate(), time.getHours() + hours,
                time.getMinutes(), time.getSeconds(), 0);
    }

    @SuppressWarnings("deprecation")
    public static Date addDays(Date date, int days) {
        if (date == null) {
            return null;
        }
        return new Date(date.getYear(), date.getMonth(), date.getDate() + days);
    }

    @SuppressWarnings("deprecation")
    public static Date addYears(Date date, int years) {
        if (date == null) {
            return null;
        }
        return new Date(date.getYear() + years, date.getMonth(), date.getDate());
    }

    /**
     * 将时间转换为yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @param time
     * @return
     */
    public static String formatTime(Timestamp time) {
        if (time == null) {
            return "";
        }
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return myFormat.format(time);
    }

    /**
     * 获取指定日期的下一个日期
     *
     * @param date
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Date getNextDate(Date date) {
        if (date == null) {
            return null;
        }
        return new Date(date.getYear(), date.getMonth(), date.getDate() + 1);
    }

    /**
     * 将时间转换为指定格式的字符串
     *
     * @param time
     * @param format
     * @return
     */
    public static String formatTime(java.sql.Timestamp time, String format) {
        if (time == null) {
            return "";
        }
        SimpleDateFormat myFormat = new SimpleDateFormat(format);
        return myFormat.format(time);
    }

    /**
     * 将14位字符串时间转换为指定格式的字符串
     *
     * @param time
     * @return
     */
    public static String formatTimeFromString(String time) {
        String returns = "";
        if (time == null) {
            return "";
        }
        returns = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " "
                + time.substring(8, 10) + ":" + time.substring(10, 12) + ":" + time.substring(12, 14);
        return returns;
    }

    /**
     * 将8位字符串时间转换为指定格式的字符串(YY-mm-dd)
     *
     * @param time
     * @return
     */
    public static String formatTimeFromStringForDay(String time) {
        String returns = "";
        if (time == null || "".equals(time)) {
            return "";
        }
        returns = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8);
        return returns;
    }

    /**
     * 将8位字符串时间转换为指定格式的字符串(HH:mm:ss)
     *
     * @param time
     * @return
     */
    public static String formatTimeFromStringForTime(String time) {
        String returns = "";
        if (time == null) {
            return "";
        }
        returns = time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4, 6);
        return returns;
    }

    /**
     * 将时间转换为指定格式的字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(java.sql.Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat myFormat = new SimpleDateFormat(format);
        return myFormat.format(date);
    }

    /**
     * 获取指定日期的
     *
     * @param date
     * @return
     */
    public static java.sql.Date getSqlDate(String date) {
        if (date == null || date.equals("")) {
            return null;
        }
        return java.sql.Date.valueOf(date);
    }

    /**
     * 获取当前是一周中的第几天
     *
     * @param date yyyyMMdd 或者 yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static String getDayOfWeek(String date) throws ParseException {
        date = date.replaceAll("[-]", "");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        cal.setTime(sdf.parse(date));
        int week = cal.get(Calendar.DAY_OF_WEEK);
        return String.valueOf((week == 1) ? 7 : week - 1);
    }

    /**
     * 获取本月的最后一天
     *
     * @param date yyyyMMdd 或者 yyyy-MM-dd
     * @return yyyyMMdd
     * @throws ParseException
     */
    public static String getLastDayOfMonth(String date) throws ParseException {
        date = date.replaceAll("[-]", "");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        cal.setTime(sdf.parse(date));
        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, day);
        return sdf.format(cal.getTime());
    }

    /**
     * 获取当前时间最后一天
     */
    public static String getCurrentMonthLastDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, day);
        return sdf.format(cal.getTime());
    }

    /**
     * 获取下月的最后一天
     *
     * @param date yyyyMMdd 或者 yyyy-MM-dd
     * @return yyyyMMdd
     * @throws ParseException
     */
    public static String getLastDayOfLastMonth(String date) throws ParseException {
        date = date.replaceAll("[-]", "");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        cal.setTime(sdf.parse(date));
        cal.add(Calendar.MONTH, -1);
        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, day);
        return sdf.format(cal.getTime());
    }

    /*
     * 判断传入参数格式为yyyyMMdd的数据是否是有效的日期类型
     */
    public static boolean validDate(String inputDate) {
        return inputDate.matches("[1-9][0-9]{3}(0[0-9]|1[0-2])(0[0-9]|1[0-9]|2[0-9]|3[0-1])");
    }

    /**
     * 获得系统当前时间
     */
    public static String nowDate(String df) {
        if (df == null || "".equals(""))
            return getDateFormat().format(System.currentTimeMillis());
        else
            return getDateFormat(df).format(System.currentTimeMillis());
    }

    /**
     * 获得默认日期格式
     */
    protected static DateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    /**
     * 获得指定日期格式
     */
    protected static DateFormat getDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * 默认日期格式
     */
    protected static String getDefaultDateFormat() {
        return "yyyy-MM-dd HH:mm";
    }

    /**
     * 获得昨天时间
     */
    @SuppressWarnings("deprecation")
    public static String yesterdayDate(String df) {
        java.util.Date d = Calendar.getInstance().getTime();
        d.setDate(d.getDate() - 1);
        return getDateFormat(df).format(d);
    }

    /**
     * 获得昨天时间
     */
    @SuppressWarnings("deprecation")
    public static String yesterdayDate() {
        java.util.Date d = Calendar.getInstance().getTime();
        d.setDate(d.getDate() - 1);
        return getDateFormat("yyyy-MM-dd").format(d);
    }

    public static Date parseDate(String date, String pattern) throws ParseException {
        if (sdfMap.get() == null) {
            sdfMap.set(new HashMap<String, SimpleDateFormat>());
        }
        if (sdfMap.get().get(pattern) == null) {
            sdfMap.get().put(pattern, new SimpleDateFormat(pattern));
        }
        Date ret = sdfMap.get().get(pattern).parse(date);
        return ret;

    }

    /**
     * 获取当前时间的前三个月的时间
     *
     * @return String
     */
    public static String getFirstThreeMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    /**
     * 判断生日是否在年龄范围内 @param @throws ParseException @throws
     */
    public static boolean checkAgeInTheRage(String birthDayStr, int startAge, int endAge) throws ParseException {
        birthDayStr = birthDayStr.replace("-", "").replace("/", "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date birthDay = simpleDateFormat.parse(birthDayStr);
        Date endDate = addYears(birthDay, endAge + 1);
        Date startDate = addYears(birthDay, startAge);
        endDate = addDays(endDate, -1);
        int endDateInt = Integer.parseInt(simpleDateFormat.format(endDate));
        int startDateInt = Integer.parseInt(simpleDateFormat.format(startDate));
        int dateInt = Integer.parseInt(simpleDateFormat.format(new Date()));
        if (dateInt >= startDateInt && dateInt <= endDateInt) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断生日是否在年龄范围内 @param @throws ParseException @throws
     */
    public static boolean checkAgeInTheRage(Date birthDay, int startAge, int endAge) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date endDate = addYears(birthDay, endAge + 1);
        Date startDate = addYears(birthDay, startAge);
        endDate = addDays(endDate, -1);
        int endDateInt = Integer.parseInt(simpleDateFormat.format(endDate));
        int startDateInt = Integer.parseInt(simpleDateFormat.format(startDate));
        int dateInt = Integer.parseInt(simpleDateFormat.format(new Date()));
        if (dateInt >= startDateInt && dateInt <= endDateInt) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断生日是否在年龄范围内,设定当前时间 @param @throws ParseException @throws
     */
    public static boolean checkAgeInTheRageInSomeDay(Date birthDay, Date now, int startAge, int endAge)
            throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date endDate = addYears(birthDay, endAge + 1);
        Date startDate = addYears(birthDay, startAge);
        endDate = addDays(endDate, -1);
        int endDateInt = Integer.parseInt(simpleDateFormat.format(endDate));
        int startDateInt = Integer.parseInt(simpleDateFormat.format(startDate));
        int dateInt = Integer.parseInt(simpleDateFormat.format(now));
        if (dateInt >= startDateInt && dateInt <= endDateInt) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据生效时间判断生日是否在年龄范围内
     *
     * @param birthDayStr
     * @param startAge
     * @param endAge
     * @param effDate     生效日
     * @return
     * @throws ParseException
     */
    public static boolean checkAgeInTheRageByEffDate(String birthDayStr, int startAge, int endAge, Date effDate)
            throws ParseException {
        birthDayStr = birthDayStr.replace("-", "").replace("/", "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date birthDay = simpleDateFormat.parse(birthDayStr);
        Date endDate = addYears(birthDay, endAge + 1);
        Date startDate = addYears(birthDay, startAge);
        endDate = addDays(endDate, -1);
        int endDateInt = Integer.parseInt(simpleDateFormat.format(endDate));
        int startDateInt = Integer.parseInt(simpleDateFormat.format(startDate));
        int dateInt = Integer.parseInt(simpleDateFormat.format(effDate));
        if (dateInt >= startDateInt && dateInt <= endDateInt) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 年龄是否小于多少岁
     *
     * @param birthDayStr
     * @param age
     * @return false(不小于传入年龄)
     * @throws ParseException
     */
    public static boolean isAgeLessThan(String birthDayStr, int age) throws ParseException {
        birthDayStr = birthDayStr.replace("-", "").replace("/", "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date birthDay = simpleDateFormat.parse(birthDayStr);
        Date thanDay = addYears(birthDay, age);
        int thanDayInt = Integer.parseInt(simpleDateFormat.format(thanDay));
        int dateInt = Integer.parseInt(simpleDateFormat.format(new Date()));
        if (dateInt >= thanDayInt) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 年龄是否小于多少岁
     *
     * @param birthDay
     * @param age
     * @return
     * @throws ParseException
     */
    public static boolean isAgeLessThan(Date birthDay, int age) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date thanDay = addYears(birthDay, age);
        int thanDayInt = Integer.parseInt(simpleDateFormat.format(thanDay));
        int dateInt = Integer.parseInt(simpleDateFormat.format(new Date()));
        if (dateInt >= thanDayInt) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 年龄是否小于多少岁,在某一天
     *
     * @param birthDay
     * @param now
     * @param age
     * @return false(不小于传入年龄)
     * @throws ParseException
     */
    public static boolean isAgeLessThanInSomeDay(Date birthDay, Date now, int age) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date thanDay = addYears(birthDay, age);
        int thanDayInt = Integer.parseInt(simpleDateFormat.format(thanDay));
        int dateInt = Integer.parseInt(simpleDateFormat.format(now));
        if (dateInt >= thanDayInt) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 转换日期，变成yyyy年MM月DD日
     */
    public static String conventDateToChinese(Date date) {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance(); //创建一个日历对象
        calendar.setTime(date); //用当前时间初始化日历时间
        String dateStr = String.valueOf(calendar.get(Calendar.YEAR)) + "年"
                + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "月"
                + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "日";
        return dateStr;
    }

    /**
     * 去除当前DATE的时分秒
     *
     * @param date
     * @return
     */
    public static Date cleanHourMinSec(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 设置时间的时分秒
     *
     * @param date
     * @param hour
     * @param mintues
     * @param second
     * @return
     */
    public static Date setDateHourMinSec(Date date, int hour, int mintues, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, mintues);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 增加月份
     *
     * @param date
     * @param count
     * @return
     */
    public static Date addMonth(Date date, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, count);
        return calendar.getTime();
    }

    public static Date parseStr2Date(String date, String pattern) throws ParseException {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        if (StringUtils.isBlank(pattern)) {
            pattern = ZH_CN_DATE_PATTERN;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(date);
    }

    /**
     * 计算年龄
     */
    public static String getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        }

        return age + "";
    }

    /**
     * 获取两个日期间隔的所有日期(默认倒推365天)
     *
     * @param start 格式必须为'2018-01-25'
     * @param end   格式必须为'2018-01-25'
     * @return
     */
    public static List<String> getBetweenDate(String start, String end) {

        if (StringUtils.isBlank(start))
            start = LocalDate.now().minus(1, ChronoUnit.YEARS).format(DateTimeFormatter.ISO_LOCAL_DATE);
        if (StringUtils.isBlank(end))
            end = LocalDate.now().minus(1, ChronoUnit.DAYS).format(DateTimeFormatter.ISO_LOCAL_DATE);

        List<String> list = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        long distance = ChronoUnit.DAYS.between(startDate, endDate);
        if (distance < 1) {
            return list;
        }
        Stream.iterate(startDate, d -> d.plusDays(1)).limit(distance + 1).forEach(f -> list.add(f.toString()));
        return list;
    }

    /**
     * 返回当前周的周一
     *
     * @param
     * @return
     */
    public static String getThisWeekMonday() {
        int mondayPlus;
        GregorianCalendar currentDate = new GregorianCalendar();
        int dayOfWeek = currentDate.get(Calendar.DAY_OF_WEEK) - 1;//一周一为一周的第一天
        if (dayOfWeek == 1)
            mondayPlus = 0;
        else
            mondayPlus = 1 - dayOfWeek;
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        //        DateFormat df = DateFormat.getDateInstance();
        //        String preMonday = df.format(monday);
        return yyyyMMddWithLineSDF.format(monday);
    }

    /**
     * 返回当前周的周一
     *
     * @param
     * @return
     */
    public static Date getThisWeekDateMonday() {
        int mondayPlus;
        GregorianCalendar currentDate = new GregorianCalendar();
        int dayOfWeek = currentDate.get(Calendar.DAY_OF_WEEK) - 1;//一周一为一周的第一天
        if (dayOfWeek == 1)
            mondayPlus = 0;
        else
            mondayPlus = 1 - dayOfWeek;
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        return monday;
    }

    /**
     * 返回本周的周日
     *
     * @return
     */
    public static String getThisWeekSunday() {
        Date date = getDateNDays(getThisWeekDateMonday(), 6);
        return yyyyMMddWithLineSDF.format(date);
    }

    /**
     * 返回上周周一
     *
     * @return
     * @throws Exception
     */
    public static String getLastWeekMonday() {
        return yyyyMMddWithLineSDF.format(getDateNDays(getThisWeekDateMonday(), -7));
    }

    /**
     * 返回上周周日
     *
     * @return
     */
    public static String getLastWeekSunday() {
        return yyyyMMddWithLineSDF.format(getDateNDays(getThisWeekDateMonday(), -1));
    }

    /**
     * 返回字符串对应当月第几周
     *
     * @return
     */
    public static int getWeek(String str) throws Exception {
        Date date = yyyyMMddWithLineSDF.parse(str);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取当前日期在一个月的第几周
     *
     * @return
     */
    public static int getThisWeekInMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取某年-某月-第几周-周内第几天
     *
     * @param year
     * @param month
     * @param weekInMonth
     * @param dayInWeek
     * @return
     */
    public static String getDateStr(int year, int month, int weekInMonth, int dayInWeek) {
        Calendar date = Calendar.getInstance();
        date.clear();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month - 1);
        date.set(Calendar.DAY_OF_WEEK_IN_MONTH, weekInMonth);
        date.set(Calendar.DAY_OF_WEEK, dayInWeek + 1);
        return yyyyMMddWithLineSDF.format(date.getTime());
    }

    /**
     * 获取某年-某月-第几周-周内第几天
     *
     * @param year
     * @param month
     * @param weekInMonth
     * @param dayInWeek
     * @return
     */
    public static Date getDate(int year, int month, int weekInMonth, int dayInWeek) {
        Calendar date = Calendar.getInstance();
        date.clear();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month - 1);
        date.set(Calendar.DAY_OF_WEEK_IN_MONTH, weekInMonth);
        date.set(Calendar.DAY_OF_WEEK, dayInWeek + 1);
        return date.getTime();
    }

    //    public templates int[] getYear

    /**
     * 返回年月字符串
     *
     * @return
     */
    public static String getThisYearAndMon() {
        Calendar date = Calendar.getInstance();
        String[] ymd = yyyyMMddWithLineSDF.format(date.getTime()).split("-");
        return new StringBuffer().append(ymd[0]).append("-").append(ymd[1]).toString();
    }

    /**
     * 某年某月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfYearAndMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return yyyyMMddWithLineSDF.format(calendar.getTime());
    }

    /**
     * 返回某年某月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfYearAndMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return yyyyMMddWithLineSDF.format(calendar.getTime());
    }

    /**
     * 前天
     *
     * @return
     */
    public static String beforeYesterday() {
        Calendar calendar = Calendar.getInstance();
        return getDateNDaysStr(calendar.getTime(), -2);
    }

    /**
     * 昨天
     *
     * @return
     */
    public static String yesterday() {
        Calendar calendar = Calendar.getInstance();
        return getDateNDaysStr(calendar.getTime(), -1);
    }

    /**
     * 今天
     *
     * @return
     */
    public static String today() {
        Calendar calendar = Calendar.getInstance();
        return getDateNDaysStr(calendar.getTime(), 0);
    }

    /**
     * 可以获取后退N天的日期 格式：传入2 得到2017-02-01
     *
     * @param backDay 过去多少天
     * @return String 返回的日期
     */
    public static String getBackDate(Integer backDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -backDay);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    /**
     * 可以获取未来N天的日期
     *
     * @param backDay 未来多少天
     * @return String 返回的日期
     */
    public static String getFutureDate(Integer backDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, backDay);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    /**
     * 返回某年某月的第一天
     *
     * @param date 格式yyyy-MM
     * @return
     */
    public static String getFirstDayOfYearAndMonth(String date) {
        String[] dateArray = date.split("-");
        return getFirstDayOfYearAndMonth(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]));
    }

    /**
     * 返回某年某月的最后一天
     *
     * @param date 格式yyyy-MM
     * @return
     */
    public static String getLastDayOfYearAndMonth(String date) {
        String[] dateArray = date.split("-");
        return getLastDayOfYearAndMonth(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]));
    }

    /**
     * 根据输入的年月返回当月的起始日期和结束日期
     *
     * @param yearAndMonth
     * @return例如:2018-01-01:2018-01-31
     */
    public static String getStartAndEndDate(String yearAndMonth) {
        StringBuffer startAndEndDate = new StringBuffer();
        if (yearAndMonth == null || "".equals(yearAndMonth) || yearAndMonth.length() < 5)
            return startAndEndDate.toString();
        String rawStr = yearAndMonth.replace("-", "");
        //获得年份
        int year = Integer.valueOf(rawStr.substring(0, 4));
        //获得月份
        int month = Integer.valueOf(rawStr.substring(4, rawStr.length()));
        //初始日期
        startAndEndDate.append(getFirstDayOfYearAndMonth(year, month));
        //分隔符
        startAndEndDate.append(":");
        //结束日期
        startAndEndDate.append(getLastDayOfYearAndMonth(year, month));
        return startAndEndDate.toString();
    }

    /**
     * 获取去年同一月
     *
     * @param yearAndMonth
     * @return
     */
    public static String getYearOnYearPeriod(String yearAndMonth) {
        StringBuffer startAndEndDate = new StringBuffer();
        if (yearAndMonth == null || "".equals(yearAndMonth) || yearAndMonth.length() < 5)
            return startAndEndDate.toString();
        String rawStr = yearAndMonth.replace("-", "");
        //获得年份
        int year = Integer.valueOf(rawStr.substring(0, 4)) - 1;
        //获得月份
        int month = Integer.valueOf(rawStr.substring(4, rawStr.length()));
        //初始日期
        startAndEndDate.append(getFirstDayOfYearAndMonth(year, month));
        //分隔符
        startAndEndDate.append(":");
        //结束日期
        startAndEndDate.append(getLastDayOfYearAndMonth(year, month));
        return startAndEndDate.toString();
    }

    /**
     * 将非标准日期转换为标准日期，例如2018-1转换为201801
     *
     * @param rawYearAndMonth
     * @return
     */
    public static String standardYearAndMonth(String rawYearAndMonth) {
        StringBuffer standardYearAndMonth = new StringBuffer();
        if (rawYearAndMonth == null || "".equals(rawYearAndMonth) || rawYearAndMonth.length() < 5)
            return null;
        String rawStr = rawYearAndMonth.replace("-", ""); //获得年份
        int year = Integer.valueOf(rawStr.substring(0, 4));
        standardYearAndMonth.append(year);
        //获得月份
        int month = Integer.valueOf(rawStr.substring(4, rawStr.length()));
        if (month < 10)
            standardYearAndMonth.append(0);
        standardYearAndMonth.append(month);
        return standardYearAndMonth.toString();
    }

    /**
     * 获取去上一个月的起始日期
     *
     * @param yearAndMonth
     * @return
     */
    public static String getLastMonthPeriod(String yearAndMonth) {
        StringBuffer startAndEndDate = new StringBuffer();
        if (yearAndMonth == null || "".equals(yearAndMonth) || yearAndMonth.length() < 5)
            return startAndEndDate.toString();
        String rawStr = yearAndMonth.replace("-", "");
        //获得年份
        int year = Integer.valueOf(rawStr.substring(0, 4));
        //获得月份
        int month = Integer.valueOf(rawStr.substring(4, rawStr.length()));

        int lastMonth = month - 1;

        if (lastMonth == 0) {
            year = year - 1;
            lastMonth = 12;
        }
        //初始日期
        startAndEndDate.append(getFirstDayOfYearAndMonth(year, lastMonth));
        //分隔符
        startAndEndDate.append(":");
        //结束日期
        startAndEndDate.append(getLastDayOfYearAndMonth(year, lastMonth));
        return startAndEndDate.toString();
    }

    /**
     * 获取指定日期的最后一天
     */
    public static String getMonthLastDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, day);
        return sdf.format(cal.getTime());
    }

    /**
     * 判断当前时间是否在活动时间内
     *
     * @param time 结束时间
     * @param df   时间格式
     * @return
     * @throws Exception
     */
    public static Integer compareTime(String time, String df) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat(df);
        String nowtime = getCurrentDateByFormat(df);
        Date date1 = format.parse(nowtime);//当前时间
        Date date2 = format.parse(time);//活动时间
        if (date1.before(date2)) {
            return 0;
        } else if (date1.equals(date2)) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 获取多少天之后的日期 23点59分59秒
     *
     * @param days
     * @return
     */
    public static Date getDateNDayLastSecond(int days) {
        String ndayStr = getDateNDaysStr(new Date(), days);
        ndayStr += " 23:59:59";
        try {
            return parseDate(ndayStr, ZH_CN_DATETIME_PATTERN);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断选择的日期是否是今天
     *
     * @param time 时间
     * @return 是否
     */
    public static boolean isToday(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //参数时间
        String param = sdf.format(date);
        //当前时间
        String now = sdf.format(new Date());
        return param.equals(now);
    }

    public static Long getNDayTime(int days) {
        Date nowDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DAY_OF_YEAR, -6);
        return calendar.getTime().getTime();
    }

    /**
     * 获取今天离0点的秒数
     *
     * @return 结果
     */
    public static String getTodayZeroSeconds() {
        // 当前时间毫秒数
        long current = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long todayZero = calendar.getTimeInMillis();
        //数据格式化
        return String.format("%05d", (current - todayZero) / 1000);
    }

    /**
     * 获取某天是星期几
     *
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(String date) throws ParseException {
        Map<String, String> weekMap = new HashMap<>();
        weekMap.put("星期一", "1");
        weekMap.put("星期二", "2");
        weekMap.put("星期三", "3");
        weekMap.put("星期四", "4");
        weekMap.put("星期五", "5");
        weekMap.put("星期六", "6");
        weekMap.put("星期日", "7");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(StringUtils.isBlank(date) ? new Date() : new SimpleDateFormat(ZH_CN_DATE_PATTERN).parse(date));
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekMap.get(weekDays[w]);
    }

}
