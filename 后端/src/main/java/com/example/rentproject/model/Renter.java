package com.example.rentproject.model;

import java.sql.ResultSet;
import java.util.*;

/*
 * RenterId,RenterPassword,RenterName,RenterNickName,RenterSex,RenterAddress,RenterPhone,RenterRooms
 */
// 租客
public class Renter extends Person {
	private String address;
	private String phone;
	private ArrayList<String> rooms;	// 收藏的房间 分隔符 "-"
	
	public Renter(String id,String pwd,String name,String sex,String address,String phone,String rooms) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.sex = sex;
		this.address = address;
		this.phone = phone;
		this.rooms = Room.stringToList(rooms);
	}
	
	public Renter(String id,String pwd,String name,String phone) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.sex = "";
		this.address = "";
		this.phone = phone;
		this.rooms = new ArrayList<String>();
	}
	public Renter(String id,String pwd,String name) {
		this.id=id;
		this.name = name;
		this.pwd = pwd;
		this.sex = "";
		this.address = "";
		this.phone = "";
		this.rooms = new ArrayList<String>();
	}
	
	/*
	 * check属性的方法
	 * 
	 * 
	 */
	
	
	public static boolean checkId(String id) {
		return id.length() <= 50 && id.length() >= 1 && (findRenterById(id) == null);
	}
	public static boolean checkNewId(String id) {
		return id.length() <= 50 && id.length() >= 1;
	}
	public static boolean checkNickName(String nickName) {
		return nickName.length() <= 50 && nickName.length() >= 1;
	}
	public static boolean checkAddress(String address) {
		return address.length() <= 200 && address.length() >= 1;
	}
	public static boolean checkPhone(String phone) {
		return phone.length() <= 20 && phone.length() >= 1 && findRenterByPhone(phone) == null;
	}
	public static boolean checkNewPhone(String phone) {
		return phone.length() <= 20 && phone.length() >= 1;
	}
	
	/*
	 * 查找Renter的方法
	 * 
	 * 
	 */
	
	public static Renter findRenterById(String id) {
		try {
			return Repository.getInstance().selectFromRenter("RenterId", id, "total").get(0);
		}catch(Exception e) {
			return null;
		}
	}
	
	public static Renter findRenterByPhone(String phone){
		try{
			return Repository.getInstance().selectFromRenter("RenterPhone", phone, "total").get(0);
		}catch(Exception e) {
			return null;
		}
	}
	
	public static ArrayList<Renter> findRenterByName(String name){
		return Repository.getInstance().selectFromRenter("RenterName", name, "total");
	}
	
	public static ArrayList<Renter> findRenterByFamilyName(String familyName){
		return Repository.getInstance().selectFromRenter("RenterName", familyName, "front");
	}
	
	// 所有租客
	public static ArrayList<Renter> findAllRenter(){
		ArrayList<Renter> ret = new ArrayList<Renter>();
		try {
			ResultSet rs = Repository.getInstance().doSqlSelectStatement("SELECT * FROM Renter");
			while(rs.next()) {
				Renter r = new Renter( rs.getString("RenterId"),rs.getString("RenterPassword"),rs.getString("RenterName"),rs.getString("RenterSex"),rs.getString("RenterAddress"),
						rs.getString("RenterPhone"),rs.getString("RenterRooms"));
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
	 * 插入Renter的方法
	 * 
	 * 
	 */
	
	public static boolean addNewRenter(Renter r) {
		return Repository.getInstance().insertIntoRenter(r);
	}
	
	// 更新信息
	public boolean updateRenterInfo(Renter r) {
		try {
			if(!Repository.getInstance().updateRenter(this, "RenterId", r.id)) {
				return false;
			}
			if(!Repository.getInstance().updateRenter(this, "RenterPassword", r.pwd)) {
				return false;
			}
			if(!Repository.getInstance().updateRenter(this, "RenterName", r.name)) {
				return false;
			}
			if(!Repository.getInstance().updateRenter(this, "RenterSex", r.sex)) {
				return false;
			}
			if(!Repository.getInstance().updateRenter(this, "RenterAddress", r.address)) {
				return false;
			}
			if(!Repository.getInstance().updateRenter(this, "RenterPhone", r.phone)) {
				return false;
			}
			if(!Repository.getInstance().updateRenter(this, "RenterRooms", Room.listToString(r.rooms))) {
				return false;
			}
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}
	
	// 收藏新房间
	public boolean addNewRoom(String id) {
		this.rooms.add(id);
		return Repository.getInstance().updateRenter(this, "RenterRooms", Room.listToString(this.rooms) );
	}
	// 取消收藏房间
	public boolean deleteRoom(String id) {
		this.rooms.remove(id);
		return Repository.getInstance().updateRenter(this, "RenterRooms", Room.listToString(this.rooms) );
	}
	
	// 租客下订单
	public boolean payForRoom(String rid,String type,String time) {
		Room rm = Room.findRoomById(rid);
		if(!rm.getState().equals("available")) {
			System.out.println("1");
			return false;
		}
		String oid = Orders.getNextId();
		if(oid.equals("")) {
			System.out.println("2");
			return false;
		}
		String[] t = time.split("/");
		double money = type.equals("long") ? rm.getRent() : rm.getDayRent()*Double.valueOf(t[2]) + Double.valueOf(t[1])*rm.getRent();
		if(Repository.getInstance().insertIntoOrders(
				new Orders(oid,this.id,rid,rm.getServiceId(),type,time,"","waiting",money)) ) {
			Room.setRoomState(rid,"rented");
			return true;
		}
		System.out.println("3");
		return false;
	}
	// 租客的订单
	public ArrayList<Orders> myOrders(){
		return Orders.findOrdersByRenterId(this.id);
	}
	/*
	 * 租客租到的房子
	 */
	public ArrayList<Orders> myRentedOrders(){
		ArrayList<Orders> ret = new ArrayList<>();
		ArrayList<String> id = new ArrayList<>();
		try {
			ResultSet rs = Repository.getInstance().doSqlSelectStatement(
					"SELECT OrdersId FROM Orders WHERE Orders.OrdersState = 'accepted' AND Orders.RenterId = '"+this.id+"'");
			
			while(rs.next()) {
				id.add(rs.getString("OrdersId"));
			}
			rs.close();
			for(String s:id) {
				ret.add(Orders.findOrdersById(s));
			}
		}catch(Exception e) {
			//e.printStackTrace();
		}
		return ret;
	}
	// 租客的工单
	public ArrayList<WorkOrder> myWorkOrder(){
		return WorkOrder.findWorkOrderByRenterId(this.id);
	}
	// 投诉单
	public ArrayList<Complaint> myComplaint(){
		return Complaint.findComplaintByRenterId(this.id);
	}
	/*
	 * 删除Renter的方法
	 * 
	 * 
	 */
	
	public static boolean deleteRenter(Renter r) {
		return Repository.getInstance().deleteRenter(r);
	}
	
	/*
	 * get和set方法
	 * 
	 */
	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

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
		return "('"+id+"','"+pwd+"','"+name+"','"+sex+"','"+address+"','"+phone+"','"+Room.listToString(this.rooms)+"')";
	}

	public ArrayList<String> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<String> rooms) {
		this.rooms = rooms;
	}
	
}
