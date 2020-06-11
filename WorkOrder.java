package com.example.rentproject.model;

import java.sql.ResultSet;
import java.util.*;
/*
 * WorkOrderId,RenterId,RoomId,RepairmanId,ServiceId,WorkOrderDate,WorkOrderItem,WorkOrderState,WorkOrderPictures,WorkOrderLevel
 */
// 工单
public class WorkOrder {
	private String id;
	private String renterId;
	private String roomId;
	private String repairmanId;
	private String serviceId;
	private String date;
	private String item;		// 顾客的描述内容 - 顾客的评价
	private String state;		// 工单状态  waiting  finished  returned   等待 已回复 已评价
	private ArrayList<String> pictures;		// 分隔符"-"
	private int level;			// 租客的评价等级 1-5 0为未评价
	
	public WorkOrder(String id,String renterId,String roomId,String repairmanId,String serviceId,String date,String item,String state,String pictures,int level) {
		this.id=id;
		this.renterId = renterId;
		this.roomId=roomId;
		this.repairmanId=repairmanId;
		this.serviceId=serviceId;
		this.date=date;
		this.item=item;
		this.state=state;
		this.pictures = Room.stringToList(pictures);
		this.level = level;
	}
	
	/*
	 * 租客提出的工单，新建工单
	 */
	public WorkOrder(String renterId,String roomId,String item) {
		this.id = WorkOrder.getNextId();
		this.renterId = renterId;
		this.roomId=roomId;
		this.repairmanId = null;
		this.serviceId = Room.findRoomById(roomId).getServiceId();
		this.date = DateModel.now();
		this.addRenterItem(item);
		this.state = "waiting";
		this.pictures = new ArrayList<String>();
		this.level = 0;
	}
	
	
	/*
	 * check属性的方法
	 * 
	 * 
	 */
	
	public static boolean checkId(String id) {
		return id.length() <= 50 && id.length() >= 1 && findWorkOrderById(id)==null;
	}
	public static boolean checkRenterId(String repairmanId) {
		return repairmanId.length() <= 50 && repairmanId.length() >= 1 && Repairman.findRepairmanById(repairmanId)!=null;
	}
	public static boolean checkRoomId(String roomId) {
		return roomId.length() <= 50 && roomId.length() >= 1 && Room.findRoomById(roomId)!=null;
	}
	public static boolean checkServiceId(String serviceId) {
		return serviceId.length() <= 50 && serviceId.length() >= 1 && Service.findServiceById(serviceId)!=null;
	}
	public static boolean checkItem(String item) {
		return item.length() >= 1 && item.length() <= 2000;
	}
	public static boolean checkDate(String date) {
		return DateModel.CheckDate(date) >= 0;
	}
	public static boolean checkState(String state) {
		return state.equals("finished") || state.equals("waiting");
	}
	public static boolean checkLevel(int level) {
		return level<=5 && level>=0;
	}
	
	/*
	 * 查找WorkOrder的方法
	 * 
	 * 
	 */
	
	// 所有工单
	public static ArrayList<WorkOrder> findAllWorkOrder(){
		ArrayList<WorkOrder> ret = new ArrayList<WorkOrder>();
		try {
			ResultSet rs = Repository.getInstance().doSqlSelectStatement("SELECT * FROM WorkOrder");
			while(rs.next()) {
				WorkOrder r = new WorkOrder( rs.getString("WorkOrderId"),rs.getString("RenterId"),rs.getString("RoomId"),rs.getString("RepairmanId"),rs.getString("ServiceId"),
						rs.getString("WorkOrderDate"),rs.getString("WorkOrderItem"),rs.getString("WorkOrderState"),rs.getString("WorkOrderPictures"),rs.getInt("WorkOrderLevel"));
				ret.add(r);
			}
			rs.close();
		}catch(Exception e) {
			// SQL Exception
			// e.printStackTrace();
		}
		return ret;
		
	}
	
	public static WorkOrder findWorkOrderById(String id) {
		try{
			return Repository.getInstance().selectFromWorkOrder("WorkOrderId", id, "total").get(0);
		}catch(Exception e) {
			return null;
		}
	}
	
	public static ArrayList<WorkOrder> findWorkOrderByRepairmanId(String repairmanId){
		return Repository.getInstance().selectFromWorkOrder("RepairmanId", repairmanId, "total");
	}
	
	public static ArrayList<WorkOrder> findWorkOrderByRoomId(String roomId){
		return Repository.getInstance().selectFromWorkOrder("RoomId", roomId, "total");
	}
	
	public static ArrayList<WorkOrder> findWorkOrderByRenterId(String renterId){
		return Repository.getInstance().selectFromWorkOrder("RenterId", renterId, "total");
	}
	
	public static ArrayList<WorkOrder> findWorkOrderByServiceId(String serviceId){
		return Repository.getInstance().selectFromWorkOrder("ServiceId", serviceId, "total");
	}
	
	public static ArrayList<WorkOrder> findWorkOrderByDate(String date){
		return Repository.getInstance().selectFromWorkOrder("WorkOrderDate", date, "total");
	}
	
	
	/*
	 * 插入WorkOrder的方法
	 * 
	 * 
	 */
	
	public static boolean addNewWorkOrder(WorkOrder wo) {
		return Repository.getInstance().insertIntoWorkOrder(wo);
	}
	
	/*
	 * 删除WorkOrder的方法
	 * 
	 * 
	 */
	
	public static boolean deleteWorkOrder(WorkOrder wo) {
		return Repository.getInstance().deleteWorkOrder(wo);
	}
	
	public boolean addPictures(String id) {
		return this.pictures.add(id);
	}
	
	// 更新
	public boolean updateState() {
		return Repository.getInstance().updateWorkOrder(this, "WorkOrderState", this.state);
	}
	public boolean updateItem() {
		return Repository.getInstance().updateWorkOrder(this, "WorkOrderItem", this.item);
	}
	
	/*
	 * 添加回复，投诉
	 */
	public boolean addRenterItem(String s) {
		this.item = s;
		return this.updateItem();
	}
	public boolean addRepairmanItem(String r) {
		this.item += "-" + r;
		return this.updateItem();
	}
	public boolean addReturnItem(String r) {
		this.state = "returned";
		this.item += "-" + r;
		return this.updateItem() && this.updateState();
	}
	
	
	/*
	 * get和set方法
	 * 
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

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRepairmanId() {
		return repairmanId;
	}

	public void setRepairmanId(String repairmanId) {
		this.repairmanId = repairmanId;
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
	// 获取用户报修内容
	public String getRenterItem() {
		if(this.item.contains("-")) {
			return this.item.substring(0,this.item.indexOf("-"));
		}
		return this.item;
	}
	// 获取师傅回复内容
	public String getRepairmanItem() {
		String s = this.item;
		try {
			if(s.contains("-")) {
				s = s.substring(s.indexOf("-") + 1);
				return s;
			}
			else {
				return "";
			}
		}catch(Exception e) {
			return "";
		}
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
	/*
	 * 获取下一个工单id
	 */
	public static String getNextId() {
		try {
			ResultSet rs = Repository.getInstance().doSqlSelectStatement("SELECT WorkOrderId FROM WorkOrder WHERE WorkOrderId >= "
				+ "All( SELECT WorkOrderId FROM WorkOrder )");
			if(rs.next()) {
				return String.valueOf(Integer.valueOf(rs.getString("WorkOrderId"))+1);
			}
			return "1001";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/*
	 * toString类似的方法
	 * 
	 * 
	 * 
	 */
	
	public String toTupleInString() {
		if(this.repairmanId!=null) {
			return "('"+id+"','"+renterId+"','"+roomId+"','"+repairmanId+"','"+serviceId+"','"+date+"','"+item+"','"+state+"','"+Room.listToString(pictures)+"','"+level+"')";
		}
		else {
			return "('"+id+"','"+renterId+"','"+roomId+"',"+null+",'"+serviceId+"','"+date+"','"+item+"','"+state+"','"+Room.listToString(pictures)+"','"+level+"')";
		}
	}

	public ArrayList<String> getPictures() {
		return pictures;
	}

	public void setPictures(ArrayList<String> pictures) {
		this.pictures = pictures;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getRenterId() {
		return renterId;
	}

	public void setRenterId(String renterId) {
		this.renterId = renterId;
	}
	
}

