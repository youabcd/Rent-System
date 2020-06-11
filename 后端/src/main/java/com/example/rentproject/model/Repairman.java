package com.example.rentproject.model;

import java.sql.ResultSet;
import java.util.ArrayList;

// 修理师傅
public class Repairman extends Person {
	private String phone;
	private String type;
	
	public Repairman(String id,String name,String pwd,String sex,String phone,String type) {
		this.id=id;
		this.name = name;
		this.pwd = pwd;
		this.sex = sex;
		this.phone = phone;
		this.type = type;
	}
	public Repairman(String id,String pwd,String name,String phone) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.sex = "";
		this.phone = phone;
		this.type = "";
	}
	public Repairman(String id,String pwd,String name) {
		this.id=id;
		this.name=name;
		this.pwd=pwd;
		this.sex="";
		this.phone="";
		this.type="";
	}
	
	/*
	 * check属性的方法
	 * 
	 * 
	 */
	
	public static boolean checkId(String id) {
		return id.length() <= 50 && id.length() >= 1 && findRepairmanById(id) == null;
	}
	public static boolean checkPhone(String phone) {
		return phone.length() <= 20 && phone.length() >= 1 && findRepairmanByPhone(phone) == null;
	}
	
	/*
	 * 查找Repairman的方法
	 * 
	 * 
	 */
	
	public static Repairman findRepairmanById(String id) {
		try{
			return Repository.getInstance().selectFromRepairman("RepairmanId", id, "total").get(0);
		}catch(Exception e) {
			return null;
		}
	}
	
	public static Repairman findRepairmanByPhone(String phone){
		try{
			return Repository.getInstance().selectFromRepairman("RepairmanPhone", phone, "total").get(0);
		}catch(Exception e) {
			return null;
		}
	}
	
	public static ArrayList<Repairman> findRepairmanByName(String name){
		return Repository.getInstance().selectFromRepairman("RepairmanName", name, "total");
	}
	
	public static ArrayList<Repairman> findRepairmanByFamilyName(String familyName){
		return Repository.getInstance().selectFromRepairman("RepairmanName", familyName, "front");
	}
	
	// 所有师傅
	public static ArrayList<Repairman> findAllRepairman(){
		ArrayList<Repairman> ret = new ArrayList<Repairman>();
		try {
			ResultSet rs = Repository.getInstance().doSqlSelectStatement("SELECT * FROM Repairman");
			while(rs.next()) {
				Repairman r = new Repairman( rs.getString("RepairmanId"),rs.getString("RepairmanName"),rs.getString("RepairmanPassword"),rs.getString("RepairmanSex"),
						rs.getString("RepairmanPhone"),rs.getString("RepairmanType"));
				ret.add(r);
			}
			rs.close();
		}catch(Exception e) {
			// SQL Exception
			// e.printStackTrace();
		}
		return ret;		
	}
	
	/*
	 * 插入Repairman的方法
	 * 
	 * 
	 */
	
	public static boolean addNewRepairman(Repairman rm) {
		return Repository.getInstance().insertIntoRepairman(rm);
	}
	
	
	/*
	 * 删除Repairman的方法
	 * 
	 * 
	 */
	
	public static boolean deleteRepairman(Repairman r) {
		return Repository.getInstance().deleteRepairman(r);
	}
	
	public boolean updateRepairmanInfo(Repairman s) {
		try {
			if(!Repository.getInstance().updateRepairman(this, "RepairmanId", s.id)) {
				return false;
			}
			if(!Repository.getInstance().updateRepairman(this, "RepairmanPassword", s.pwd)) {
				return false;
			}
			if(!Repository.getInstance().updateRepairman(this, "RepairmanName", s.name)) {
				return false;
			}
			if(!Repository.getInstance().updateRepairman(this, "RepairmanSex", s.sex)) {
				return false;
			}
			if(!Repository.getInstance().updateRepairman(this, "RepairmanPhone", s.phone)) {
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
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	/*
	 * toString类似的方法
	 * 
	 * 
	 * 
	 */
	
	public String toTupleInString() {
		return "('"+id+"','"+name+"','"+pwd+"','"+sex+"','"+phone+"','"+type+"')";
	}
}
