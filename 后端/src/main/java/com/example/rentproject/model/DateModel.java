package com.example.rentproject.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateModel {
	public static String dev = "/";
	public static int CheckDate(String d) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy"+dev+"MM"+dev+"dd");
		Date date = new Date();
		
		try {
			f.setLenient(false);
			date = f.parse(d);
		}catch(Exception e) {
			return -1;
		}
		
		Calendar now = Calendar.getInstance();
		Calendar birth = Calendar.getInstance();
		birth.setTime(date);
		
		if(birth.after(now)) {
			return -1;
		}
		else {
			int age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
			if( now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR) ) {
				age += 1;
			}
			return age-1;
		}
	}
	// 获取订单的到期时间
	public static String getDueDate(Orders o) {
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
			f.setLenient(false);
			Date date = f.parse(o.getDate());
			String t = o.getTime();
			
			String [] time = t.split("/");
			if(time.length!=3)
				return "";
			
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			
			c.add(Calendar.YEAR, Integer.valueOf(time[0]));
			c.add(Calendar.MONTH, Integer.valueOf(time[1]));
			c.add(Calendar.DATE, Integer.valueOf(time[2]));
			System.out.println(time[1]);
			
			return f.format(c.getTime());
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/*
	 * d1为日期，d2为加的天数
	 */
	public static String addDate(String d1,String d2) {
		try {
			String s[] = d2.split("/");
			int i1 = Integer.valueOf(s[0]);
			int i2 = Integer.valueOf(s[1]);
			int i3 = Integer.valueOf(s[2]);
			
			SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
			f.setLenient(false);
			Calendar c1 = Calendar.getInstance();
			
			Date date1 = new Date();
			if(d1!=null) {
				date1 = f.parse(d1);	
			}
			
			c1.setTime(date1);
			
			c1.add(Calendar.YEAR, i1);
			c1.add(Calendar.MONTH, i2);
			c1.add(Calendar.DATE, i3);
			return f.format(c1.getTime());
		}catch(Exception e) {
			e.printStackTrace();
			return d2;
		}
	}
	
	public static String subDate(String d1,String d2) {
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
			f.setLenient(false);
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			
			if(d1!=null) {
				Date date1 = f.parse(d1);
				c1.setTime(date1);
			}
			Date date2 = f.parse(d2);
			c2.setTime(date2);
			int y = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
			int m = c1.get(Calendar.MONTH)-c2.get(Calendar.MONTH);
			int d = c1.get(Calendar.DATE)-c2.get(Calendar.DATE);
			if(d>0)
				m++;
			if(m < 0) {
				y = y - 1;
				m = m + 12;
			}
			
			return String.valueOf(y) + "/" + 
			String.valueOf(m);
			
		}catch(Exception e) {
			return "0/0";
		}
	}
	
	// 若d1早于d2，返回1
	public static int compare(String d1,String d2) {
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
			f.setLenient(false);
			Date date1 = new Date();
			if(d1 != null) {
				date1 = f.parse(d1);
			}
			else {
				date1 = f.parse(now());
			}
			
			Date date2 = f.parse(d2);
			return date1.equals(date2) ? 0 : (date1.after(date2) ? -1:1);
		}catch(Exception e) {
			return 0;
		}
	}
	
	public static String now() {
		Date d = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
		return f.format(d);
	}
	
	public static String formatDate(String date) {
		try {
			String y = date.substring(0, date.indexOf("/"));
			String s = date.substring(date.indexOf("/") + 1);
			String m = s.substring(0, s.indexOf("/"));
			String d = s.substring(s.indexOf("/") + 1);
			return y + "年" + m + "月" + d + "日";
		}catch(Exception e) {
			return date;
		}

	}
	
	public static String addDateFormat(String d1,String d2) {
		String s1[] = d1.split("/");
		String s2[] = d2.split("/");
		if(s1.length!=3||s2.length!=3) {
			return "";
		}
		s1[0] = String.valueOf(Integer.valueOf(s1[0]) + Integer.valueOf(s2[0]));
		s1[1] = String.valueOf(Integer.valueOf(s1[1]) + Integer.valueOf(s2[1]));
		s1[2] = String.valueOf(Integer.valueOf(s1[2]) + Integer.valueOf(s2[2]));
		return s1[0]+"/"+s1[1]+"/"+s1[2];
	}
}
