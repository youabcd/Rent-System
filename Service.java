package com.example.rentproject.model;

import java.util.ArrayList;

// 客服
public class Service extends Person {
	private String phone;
	
	public Service(String id,String pwd,String name,String sex,String phone) {
		this.id=id;
		this.name = name;
		this.pwd = pwd;
		this.sex = sex;
		this.phone = phone;
	}
	public Service(String id,String pwd,String name,String phone) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.sex = "";
		this.phone = phone;
	}
	
	/*
	 * check属性的方法
	 * 
	 * 
	 */
	
	public static boolean checkId(String id) {
		return id.length() <= 50 && id.length() >= 1 && findServiceById(id) == null;
	}
	public static boolean checkPhone(String phone) {
		return phone.length() <= 20 && phone.length() >= 1 && findServiceByPhone(phone) == null;
	}
	
	/*
	 * 查找Service的方法
	 * 
	 * 
	 */
	public static Service findServiceById(String id) {
		try{
			return Repository.getInstance().selectFromService("ServiceId", id, "total").get(0);
		}catch(Exception e) {
			return null;
		}
	}
	
	public static Service findServiceByPhone(String phone){
		try{
			return Repository.getInstance().selectFromService("ServicePhone", phone, "total").get(0);
		}catch(Exception e) {
			return null;
		}
	}
	
	public static ArrayList<Service> findServiceByName(String name){
		return Repository.getInstance().selectFromService("ServiceName", name, "total");
	}
	
	public static ArrayList<Service> findServiceByFamilyName(String familyName){
		return Repository.getInstance().selectFromService("ServiceName", familyName, "front");
	}
	
	/*
	 * 插入Service的方法
	 * 
	 * 
	 */
	public static boolean addNewService(Service s) {
		return Repository.getInstance().insertIntoService(s);
	}
	
	
	/*
	 * 删除Renter的方法
	 * 
	 * 
	 */
	
	public static boolean deleteService(Service s) {
		return Repository.getInstance().deleteService(s);
	}
	
	public boolean updateServiceInfo(Service s) {
		try {
			if(!Repository.getInstance().updateService(this, "ServiceId", s.id)) {
				return false;
			}
			if(!Repository.getInstance().updateService(this, "ServicePassword", s.pwd)) {
				return false;
			}
			if(!Repository.getInstance().updateService(this, "ServiceName", s.name)) {
				return false;
			}
			if(!Repository.getInstance().updateService(this, "ServiceSex", s.sex)) {
				return false;
			}
			if(!Repository.getInstance().updateService(this, "ServicePhone", s.phone)) {
				return false;
			}
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	/*
	 * get和set方法
	 * 
	 */
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	/*
	 * toString类似的方法
	 * 
	 * 
	 * 
	 */
	
	public String toTupleInString() {
		return "('"+id+"','"+pwd+"','"+name+"','"+sex+"','"+phone+"')";
	}
}
