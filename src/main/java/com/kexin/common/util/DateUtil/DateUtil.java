package com.kexin.common.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static Date stringToDate(String strDate) throws ParseException {
//        String strDate1 = "2018-07-18T08:12:08.000+0000";
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+0000'");
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+0000'");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(strDate);
        return date;
    }

    public static void main(String[] args) throws ParseException {
        Date date=DateUtil.stringToDate("2020-04-01 00:00:00");
        System.out.println(date);
    }
}
