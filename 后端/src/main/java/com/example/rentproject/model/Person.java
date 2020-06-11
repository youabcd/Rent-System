package com.example.rentproject.model;

// 人的父类
public class Person {
	protected String id;	// 账号
	protected String name;	// 姓名
	protected String pwd;	// 密码
	protected String sex;	// 性别
	
	
	
	
	/*
	 * 
	 * check属性的方法
	 * 
	 * 
	 * 
	 * 
	 */
	
	public static boolean checkName(String name) {
		return name.length() <= 20 && name.length() >=1;
	}
	public static boolean checkPwd(String pwd) {
		return pwd.length() >= 6 && pwd.length() <= 50;
	}
	public static boolean checkSex(String sex) {
		return sex.equals("男") || sex.equals("女");
	}
	
	
	
	/*
	 * 
	 * get和set方法
	 * 
	 * 
	 * 
	 */
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	
	
	
	
	
}
