package com.blacklth.utrs.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author : LiaoTianHong
 * @date : 2019/8/1 15:01
 */
public class StringUtil {

    /**
     *  判断字符串为空
     * @author     ：LiaoTianHong
     * @date       ：Created in 2019/8/1 15:11
     * @param       str 要判定的字符串
     * @return     : boolean
     */
    public static boolean isEmpty(String str){
        if(null == str || "".equals(str)){
            return true;
        }
        return false;
    }

    /**
     *  将字符串转成LocalDate
     * @author     ：LiaoTianHong
     * @date       ：Created in 2019/8/1 16:53
     * @param       str
     * @return     : java.time.LocalDate
     */
    public static LocalDate str2LocalDate(String str){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (isEmpty(str)) {
            return null;
        }
        LocalDate date = null;
        try{
            LocalDate.parse(str,df);
        }
        catch (Exception e){
            throw ErrorUtil.wrap(e.getMessage());
        }
        return date;
    }

    /**
     *  得到日期字符串的下一天
     * @author     ：LiaoTianHong
     * @date       ：Created in 2019/8/1 16:54
     * @param       str
     * @return     : java.lang.String
     */
    public static String getNextDay(String str){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String s;
        try {
            LocalDate date = LocalDate.parse(str,df).plusDays(1);
            s = df.format(date);
        }
        catch (Exception e){
            throw ErrorUtil.wrap(e.getMessage());
        }
        return s;
    }
    /**
     *  判断结束时间是否晚于开始时间
     * @author     ：LiaoTianHong
     * @date       ：Created in 2019/8/1 17:12
     * @param       startTime
     * @param       endTime
     * @return     : boolean
     */
    public static boolean isAfter(String startTime,String endTime){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        boolean flag;
        try {
            LocalDate startDate = LocalDate.parse(startTime,df);
            LocalDate endDate = LocalDate.parse(endTime,df);
            flag = endDate.isAfter(startDate);
        }
        catch (Exception e){
            throw ErrorUtil.wrap(e.getMessage());
        }
        return flag;
    }

    /**
     *  判断字符串是否为日期格式
     * @author     ：LiaoTianHong
     * @date       ：Created in 2019/8/1 18:26
     * @param       str
     * @return     : boolean
     */
    public static boolean isDateStr(String str){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
             LocalDate.parse(str,df);
        }
        catch (Exception e){
            return  false;
        }
        return true;

    }
}
