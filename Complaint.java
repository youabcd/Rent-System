package com.example.rentproject.model;

import java.sql.ResultSet;
import java.util.ArrayList;

/*
 * ComplaintId,RenterId,ServiceId,ComplaintDate,ComplaintItem,ComplaintState,ComplaintPictures
 * 投诉单
 */
public class Complaint {
	private String id;
	private String renterId;
	private String serviceId;
	private String date;
	private String item;
	private String state;			// waiting   finished   returned   等待 已回复 已评价
	private ArrayList<String> pictures;
	
	public Complaint(String id,String renterId,String serviceId,String date,String item,String state,String pictures) {
		this.id = id; this.renterId = renterId; this.serviceId = serviceId; this.date = date;
		this.item = item; this.state = state; this.pictures = Room.stringToList(pictures);
	}
	/*
	 * 租客投诉的时候需要的方法
	 */
	public Complaint(String renterId,String serviceId,String renterContent) {
		this.id = getNextId();
		this.renterId = renterId;
		this.serviceId = serviceId;
		this.date = DateModel.now();
		this.addRenterItem(renterContent);
		this.state = "waiting";
		this.pictures = new ArrayList<String>();
	}

	/*
	 * 获取下一个id
	 */
	public static String getNextId() {
		try {
			ResultSet rs = Repository.getInstance().doSqlSelectStatement("SELECT ComplaintId FROM Complaint WHERE ComplaintId >= "
				+ "All( SELECT ComplaintId FROM Complaint )");
			if(rs.next()) {
				return String.valueOf(Integer.valueOf(rs.getString("ComplaintId"))+1);
			}
			return "1001";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/*
	 * 查找Complaint的方法
	 * 
	 * 
	 */

	public static Complaint findComplaintById(String id) {
		try{
			return Repository.getInstance().selectFromComplaint("ComplaintId", id, "total").get(0);
		}catch(Exception e) {
			return null;
		}
	}
	
	public static ArrayList<Complaint> findComplaintByRenterId(String renterId){
		return Repository.getInstance().selectFromComplaint("RenterId", renterId, "total");
	}
	
	public static ArrayList<Complaint> findWorkOrderByRoomId(String roomId){
		return Repository.getInstance().selectFromComplaint("RoomId", roomId, "total");
	}
	
	public static ArrayList<Complaint> findComplaintByServiceId(String serviceId){
		return Repository.getInstance().selectFromComplaint("ServiceId", serviceId, "total");
	}
	
	
	/*
	 * 添加和删除
	 * 
	 */
	// 所有投诉
	public static ArrayList<Complaint> findAllComplaint(){
		ArrayList<Complaint> ret = new ArrayList<Complaint>();
		try {
			ResultSet rs = Repository.getInstance().doSqlSelectStatement("SELECT * FROM Complaint");
			while(rs.next()) {
				Complaint r = new Complaint( rs.getString("ComplaintId"),rs.getString("RenterId"),rs.getString("ServiceId"),rs.getString("ComplaintDate"),
						rs.getString("ComplaintItem"),rs.getString("ComplaintState"),rs.getString("ComplaintPictures"));
				ret.add(r);
			}
			rs.close();
		}catch(Exception e) {
			// SQL Exception
			// e.printStackTrace();
		}
		return ret;
		
	}
	public static boolean addNewComplaint(Complaint c) {
		return Repository.getInstance().insertIntoComplaint(c);
	}
	public static boolean deleteComplaint(Complaint c) {
		return Repository.getInstance().deleteComplaint(c);
	}
	// 更新
	public boolean updateState() {
		return Repository.getInstance().updateComplaint(this, "ComplaintState", this.state);
	}
	public boolean updateItem() {
		return Repository.getInstance().updateComplaint(this, "ComplaintItem", this.item);
	}
	
	public boolean addPictures(String id) {
		return this.pictures.add(id);
	}
	
	/*
	 * 添加回复，投诉
	 */
	public boolean addRenterItem(String s) {
		this.item = s;
		return this.updateItem();
	}
	public boolean addServiceItem(String s) {
		this.state = "finished";
		this.item += "-" + s;
		return this.updateItem() && this.updateState();
	}
	public boolean addReturnItem(String r) {
		this.state = "returned";
		this.item += "-" + r;
		return this.updateItem() && this.updateState();
	}
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRenterId() {
		return renterId;
	}

	public void setRenterId(String renterId) {
		this.renterId = renterId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getDate() {
		return DateModel.formatDate(date.substring(0,10).replace("-", "/"));
	}


	public void setDate(String date) {
		this.date = date;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ArrayList<String> getPictures() {
		return pictures;
	}

	public void setPictures(ArrayList<String> pictures) {
		this.pictures = pictures;
	}
	
	public String toTupleInString() {
		return "('"+id+"','"+renterId+"','"+serviceId+"','"+date+"','"+item+"','"+state+"','"+Room.listToString(pictures)+"')";
	}
	
	// 获取投诉内容
	public String getRenterItem() {
		if(this.item.contains("-")) {
			return this.item.substring(0,this.item.indexOf("-"));
		}
		return this.item;
	}
	// 获取回复内容
	public String getServiceItem() {
		if(this.item.contains("-")) {
			return this.item.substring(this.item.indexOf("-")+1);
		}
		return "";
	}
	// 获取评价内容
	public String getReturnItem() {
		try {
			String s = this.item.substring(this.item.indexOf("-") + 1);
			if(s.contains("-")) {
				return s.substring(s.indexOf("-")+1);
			}
			return s;
		}catch(Exception e) {
			return "";
		}
	}
}
