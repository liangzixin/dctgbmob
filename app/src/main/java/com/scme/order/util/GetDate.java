package com.scme.order.util;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class GetDate{ 
	
	/**
	 * 获取当前时间
	 */
   public String getDate()
      { 
            Date now = new Date(); 
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式 
            String date = dateFormat.format( now ); 
            return date;
     }
   
   /**
    * 获取精确到秒的时间
    */
   public String getMSDate()
   { 
         Date now = new Date(); 
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式 
         String date = dateFormat.format( now ); 
         return date;
  }
   /**
	 * 获取当前时间
	 */
  public String birthdayDToC(Date date)
     { 
        
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式 
           String date1 = dateFormat.format(date); 
           return date1;
    }
  public  Date getNowDate() {
		 Date currentTime = new Date();
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String dateString = formatter.format(currentTime);
		 ParsePosition pos = new ParsePosition(1);
		 Date currentTime_2 = formatter.parse(dateString, pos);
//		  System.out.println(currentTime_2 );
		 return currentTime_2;
		}
	//编写获得系统日期的方法．
	public  Date getDate1(){								//以Date对象为返回值创建getDate()方法
		Date dateU = new Date();                                  //创建Date类对象
		java.sql.Date date= new java.sql.Date(dateU.getTime());   //getTime()方法可得到当前系统的日期
	    return date;
	}
	  //获取月日格式
//	@Test
    public String formatyy1(){
    	 Date currentTime = new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String str=format.format(currentTime);
        str=str.substring(0, 4)+str.substring(5, 7);
        return str;
    }
    //	判断date1是否在date2之前
    public double  isLongtime(String date1,String date) {
        double day =0.0;
        try
        {
        Date a1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        Date b1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date1);

//获取相减后天数
//         day = (a1.getTime()-b1.getTime())/(24*60*60*1000);
            day = (a1.getTime()-b1.getTime())/(1000*60*60);
        }
        catch (Exception e)
        {
        }
        return day;
    }
    // java计算两个时间相差（天、小时、分钟、秒）
    public  double dateDiff(String startTime, String endTime) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        double day= 0.0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long hour0=0;
        long day0=0;
        // 获得两个时间的毫秒时间差异
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            hour = diff % nd / nh + (int)day* 24;// 计算差多少小时
            min = diff % nd % nh / nm + (int)day * 24 * 60;// 计算差多少分钟
            sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果

            hour0=hour - (int)day * 24;
            if(hour0>=2&&hour0<5){
                day=day+0.5;
            }else if(hour0>=5){
                day=day+1;
            }else if(day==0&&hour0<2){
                day=1;
            }
            day0=getSpecialNonHolidays(StrToDate(startTime),StrToDate(endTime));
            day=day-day0;
            return day;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return   day;
    }
    /*
    *字符转日期
     */
    public  Date StrToDate(String date1) {

        Date date=null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date1);      //判断date1是否在date2之前
        } catch (Exception e) {
        }
        return date;
    }
    /**
     * 返回两个日期相差的天数,有一个时间为null返回-1
     * @param d1  长的时间
     * @param d2  短的时间
     * @return int
     */
    public  int diff_in_date(Date d1, Date d2){

        if(null == d1 || null == d2){
            return -1;
        }
        return (int)((d1.getTime() - d2.getTime())/86400000)+1;
    }
    //判断两时间段内的周六周日天数
    public static int getSpecialNonHolidays(Date startDate,Date endDate) {

        Calendar start = Calendar.getInstance();

        start.setTime(startDate);

        Calendar end = Calendar.getInstance();

        end.setTime(endDate);

        Calendar compareDate = Calendar.getInstance();

        int days = 0;

        while (start.compareTo(end) <= 0) {

            int day =start.get(Calendar.DAY_OF_WEEK);

            //       if (day == Calendar.SUNDAY || day == Calendar.SATURDAY) {
            if (day ==1 || day ==7) {

                // if (start.compareTo(compareDate) == 0) {

                days++;

            } else {

                // do nothing, 过滤掉输入的周末日期

            }

            start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);

        }
        return days;
    }
    public boolean isDateBefore1(Date date1,Date date) {
        boolean b = true;									//根据该方法的返回值设置变量
        DateFormat df = DateFormat.getDateTimeInstance();  //获得时间格式，为系统默认的格式

        //b=df.parse(date1).before(df.parse(date));        //判断date1是否在date2之前
        if(date1.before(date))
        { b=true;}
        else
        { b=false; }

        return b;
    }
    public  boolean isDateBefore(String date1,String date) {
        boolean b = true;									//根据该方法的返回值设置变量
//        DateFormat df = DateFormat.getDateTimeInstance();  //获得时间格式，为系统默认的格式

        try {
            DateFormat df =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            b=df.parse(date1).before(df.parse(date));        //判断date1是否在date2之前
        } catch (Exception e) {


        }
        return b;
    }

    		public String formatTime(Date date){
			SimpleDateFormat format=new SimpleDateFormat("dd/MM");
			String str=format.format(date);
			if(date.getHours()<13){
				str=str+" 中餐";
			}
			else{
				str=str+" 晚餐";
			}
			return str;
		}
    public String formatTime1(Date date){
        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
        String str=format.format(date);
        if(date.getHours()<13){
            str=str+" 中餐";
        }
        else{
            str=str+" 晚餐";
        }
        return str;
    }

    public  String getHour(String shdate) {

        String dateString=shdate;
        int hour=Integer.parseInt(dateString.substring(11, 13));
        if(hour<13)
            dateString=dateString.substring(0, 4)+"年"+dateString.substring(5, 7)+"月"+dateString.substring(8, 10)+"日"+" 中餐";
        else  dateString=dateString.substring(0, 4)+"年"+dateString.substring(5, 7)+"月"+dateString.substring(8, 10)+"日"+" 晚餐";
        return dateString;
    }
    // 出差java计算两个时间相差（天、小时、分钟、秒）
    public  double dateDiffChuchai(String startTime, String endTime) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        double day= 0.0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long hour0=0;
        long day0=0;
        // 获得两个时间的毫秒时间差异
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            hour = diff % nd / nh + (int)day* 24;// 计算差多少小时
            min = diff % nd % nh / nm + (int)day * 24 * 60;// 计算差多少分钟
            sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果

            hour0=hour - (int)day * 24;
            if(hour0>=2){
                day=day+1;
            }else if(day==0&&hour0<2){
                day=1;
            }
            //  day0=getSpecialNonHolidays(CharTowDate(startTime),CharTowDate(endTime));
            //   day=day-day0;
            return day;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return   day;
    }
    //格式化日期时间为“年-月-日 时：分：秒”的格式
    public String DtoC1(Date date){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str=format.format(date);
        return str;
    }
}