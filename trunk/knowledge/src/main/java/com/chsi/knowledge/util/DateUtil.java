package com.chsi.knowledge.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static String getDateWeek(int offset){
        Calendar   cal   =   Calendar.getInstance();
        cal.add(Calendar.DATE,   +offset);
        String date = new SimpleDateFormat( "yyyy年MM月dd日 E ").format(cal.getTime());
        return date;
    }
    public static String getDateWeek(){
        Calendar   cal   =   Calendar.getInstance();
        String date = new SimpleDateFormat( "yyyy年MM月dd日 E ").format(cal.getTime());
        return date;
    }
    public static String getDate(int offset){
        Calendar   cal   =   Calendar.getInstance();
        cal.add(Calendar.DATE,   +offset);
        String date = new SimpleDateFormat( "yyyy年MM月dd日 ").format(cal.getTime());
        return date;
    }
    public static String getDate(){
        Calendar   cal   =   Calendar.getInstance();
        String date = new SimpleDateFormat( "yyyy年MM月dd日 ").format(cal.getTime());
        return date;
    }
    public static String getTime(){
        
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("a HH:mm:ss");
        String time = sdf.format(date);
        return time;
    }
    
    public static String calendarToString(Calendar   cal){
        String date = new SimpleDateFormat( "yyyy年MM月dd日 a HH:mm:ss ").format(cal.getTime());
        return date;
    }
}
