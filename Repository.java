package com.example.rentproject.model;

import java.sql.*;
import java.util.*;

// 数据库
public class Repository {
	static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static final String DB_URL = "jdbc:sqlserver://localhost:1433;DatabaseName=RentSystem";
	
	static final String USER = "RentSystem";
	static final String PWD = "rent";
	
	private static Repository instance = new Repository(); 
	private Connection con = null;
	private Statement stmt = null;
	
	// 单例模式
	public static Repository getInstance() {
		return instance;
	}
	
	// 连接数据库
	private Repository() {
		try {
			Class.forName(JDBC_DRIVER);
			// 连接数据库
			con = DriverManager.getConnection(DB_URL,USER,PWD);
			System.out.println("Database connect successful.");
			stmt = con.createStatement();
		}catch(Exception e){
			// Class.forName error
			System.out.println(e);
		}
	}
	
	// 执行sql查找语句
	public ResultSet doSqlSelectStatement(String sql) throws Exception {
		ResultSet rs = stmt.executeQuery(sql);
		return rs;
	}
	
	// 执行sql插入语句
	public boolean doSqlUpdateStatement(String sql) {
		try {
			stmt.executeUpdate(sql);
			return true;
		}catch(Exception e) {
			// SQL Exception
			//e.printStackTrace();
			return false;
		}
	}
	
	
	/*
	 * 从指定的表中搜索元组，保证输入数据合法
	 * type的值为：	front		从前开始匹配
	 * 				back		从后开始匹配
	 * 				all			中间开始匹配
	 * 				total		完全匹配
	 * 
	 * 返回Renter类，若不存在则返回空容器
	*/
	public ArrayList<Renter> selectFromRenter(String column,String value,String type) {
		ArrayList<Renter> ret = new ArrayList<Renter>();
		String sql = "SELECT * FROM Renter ";
		if(type.equals("front")) {
			sql += "WHERE "+column+" LIKE '"+value+"%'";
		}
		else if(type.equals("back")) {
			sql += "WHERE "+column+" LIKE '%"+value+"'";
		}
		else if(type.equals("all")) {
			sql += "WHERE "+column+" LIKE '%"+value+"%'";
		}
		else if(type.equals("total")) {
			sql += "WHERE "+column+" = "+"'"+value+"'";
		}
		else {
			return ret;
		}
		
		try {
			ResultSet rs = doSqlSelectStatement(sql);
			while(rs.next()) {
				Renter r = new Renter(rs.getString("RenterId"),rs.getString("RenterPassword"),rs.getString("RenterName"),
						rs.getString("RenterSex"),rs.getString("RenterAddress"),rs.getString("RenterPhone"),rs.getString("RenterRooms"));
				ret.add(r);
			}
			rs.close();
		}catch(Exception e) {
			// SQL Exception
			//e.printStackTrace();
		}
		return ret;
	}
	
	public ArrayList<Room> selectFromRoom(String column,String value,String type) {
		ArrayList<Room> ret = new ArrayList<Room>();
		String sql = "SELECT * FROM Room ";
		if(type.equals("front")) {
			sql += "WHERE "+column+" LIKE '"+value+"%'";
		}
		else if(type.equals("back")) {
			sql += "WHERE "+column+" LIKE '%"+value+"'";
		}
		else if(type.equals("all")) {
			sql += "WHERE "+column+" LIKE '%"+value+"%'";
		}
		else if(type.equals("total")) {
			sql += "WHERE "+column+" = '"+value+"'";
		}
		else {
			return ret;
		}
		
		try {
			ResultSet rs = doSqlSelectStatement(sql);
			while(rs.next()) {
				Room r = new Room( rs.getString("RoomId"),rs.getString("ServiceId"),rs.getString("RoomType"),rs.getString("RoomState"),rs.getString("RoomRent"),
						rs.getString("RoomAddress"),rs.getString("RoomArea"),rs.getString("RoomItem"),rs.getString("RoomPictures") );
				ret.add(r);
			}
			rs.close();
		}catch(Exception e) {
			// SQL Exception
			// e.printStackTrace();
		}
		return ret;
	}

	public ArrayList<Service> selectFromService(String column,String value,String type) {
		ArrayList<Service> ret = new ArrayList<Service>();
		String sql = "SELECT * FROM Service ";
		if(type.equals("front")) {
			sql += "WHERE "+column+" LIKE '"+value+"%'";
		}
		else if(type.equals("back")) {
			sql += "WHERE "+column+" LIKE '%"+value+"'";
		}
		else if(type.equals("all")) {
			sql += "WHERE "+column+" LIKE '%"+value+"%'";
		}
		else if(type.equals("total")) {
			sql += "WHERE "+column+" = '"+value+"'";
		}
		else {
			return ret;
		}
		
		try {
			ResultSet rs = doSqlSelectStatement(sql);
			while(rs.next()) {
				Service r = new Service( rs.getString("ServiceId"),rs.getString("ServicePassword"),rs.getString("ServiceName"),rs.getString("ServiceSex"),
						rs.getString("ServicePhone") );
				ret.add(r);
			}
			rs.close();
		}catch(Exception e) {
			// SQL Exception
			//e.printStackTrace();
		}
		return ret;
	}
	
	public ArrayList<Repairman> selectFromRepairman(String column,String value,String type) {
		ArrayList<Repairman> ret = new ArrayList<Repairman>();
		String sql = "SELECT * FROM Repairman ";
		if(type.equals("front")) {
			sql += "WHERE "+column+" LIKE '"+value+"%'";
		}
		else if(type.equals("back")) {
			sql += "WHERE "+column+" LIKE '%"+value+"'";
		}
		else if(type.equals("all")) {
			sql += "WHERE "+column+" LIKE '%"+value+"%'";
		}
		else if(type.equals("total")) {
			sql += "WHERE "+column+" = '"+value+"'";
		}
		else {
			return ret;
		}
		
		try {
			ResultSet rs = doSqlSelectStatement(sql);
			while(rs.next()) {
				Repairman r = new Repairman( rs.getString("RepairmanId"),rs.getString("RepairmanName"),rs.getString("RepairmanPassword"),
						rs.getString("RepairmanSex"),rs.getString("RepairmanPhone"),rs.getString("RepairmanType") );
				ret.add(r);
			}
			rs.close();
		}catch(Exception e) {
			// SQL Exception
			//e.printStackTrace();
		}
		return ret;
	}

	public ArrayList<Orders> selectFromOrders(String column,String value,String type) {
		ArrayList<Orders> ret = new ArrayList<Orders>();
		String sql = "SELECT * FROM Orders ";
		if(type.equals("front")) {
			sql += "WHERE "+column+" LIKE '"+value+"%'";
		}
		else if(type.equals("back")) {
			sql += "WHERE "+column+" LIKE '%"+value+"'";
		}
		else if(type.equals("all")) {
			sql += "WHERE "+column+" LIKE '%"+value+"%'";
		}
		else if(type.equals("total")) {
			sql += "WHERE "+column+" = '"+value+"'";
		}
		else {
			return ret;
		}
		
		try {
			ResultSet rs = doSqlSelectStatement(sql);
			while(rs.next()) {
				Orders r = new Orders( rs.getString("OrdersId"),rs.getString("RenterId"),rs.getString("RoomId"),rs.getString("ServiceId"),
						rs.getString("OrdersType"),rs.getString("OrdersTime"),rs.getString("OrdersDate"),rs.getString("OrdersItem"),rs.getString("OrdersState"),rs.getString("OrdersDueDate"),rs.getFloat("OrdersPay") );
				ret.add(r);
			}
			rs.close();
		}catch(Exception e) {
			// SQL Exception
			//e.printStackTrace();
		}
		return ret;
	}
	
	public ArrayList<Complaint> selectFromComplaint(String column,String value,String type) {
		ArrayList<Complaint> ret = new ArrayList<Complaint>();
		String sql = "SELECT * FROM Complaint ";
		if(type.equals("front")) {
			sql += "WHERE "+column+" LIKE '"+value+"%'";
		}
		else if(type.equals("back")) {
			sql += "WHERE "+column+" LIKE '%"+value+"'";
		}
		else if(type.equals("all")) {
			sql += "WHERE "+column+" LIKE '%"+value+"%'";
		}
		else if(type.equals("total")) {
			sql += "WHERE "+column+" = '"+value+"'";
		}
		else {
			return ret;
		}
		
		try {
			ResultSet rs = doSqlSelectStatement(sql);
			while(rs.next()) {
				Complaint r = new Complaint( rs.getString("ComplaintId"),rs.getString("RenterId"),rs.getString("ServiceId"),
						rs.getString("ComplaintDate"),rs.getString("ComplaintItem"),rs.getString("ComplaintState"),rs.getString("ComplaintPictures") );
				ret.add(r);
			}
			rs.close();
		}catch(Exception e) {
			// SQL Exception
			e.printStackTrace();
		}
		return ret;
	}

	public ArrayList<WorkOrder> selectFromWorkOrder(String column,String value,String type) {
		ArrayList<WorkOrder> ret = new ArrayList<WorkOrder>();
		String sql = "SELECT * FROM WorkOrder ";
		if(type.equals("front")) {
			sql += "WHERE "+column+" LIKE '"+value+"%'";
		}
		else if(type.equals("back")) {
			sql += "WHERE "+column+" LIKE '%"+value+"'";
		}
		else if(type.equals("all")) {
			sql += "WHERE "+column+" LIKE '%"+value+"%'";
		}
		else if(type.equals("total")) {
			sql += "WHERE "+column+" = '"+value+"'";
		}
		else {
			return ret;
		}
		
		try {
			ResultSet rs = doSqlSelectStatement(sql);
			while(rs.next()) {
				WorkOrder r = new WorkOrder( rs.getString("WorkOrderId"),rs.getString("RenterId"),rs.getString("RoomId"),rs.getString("RepairmanId"),
						rs.getString("ServiceId"),rs.getString("WorkOrderDate"),rs.getString("WorkOrderItem"),rs.getString("WorkOrderState"),rs.getString("WorkOrderPictures"),rs.getInt("WorkOrderLevel") );
				ret.add(r);
			}
			rs.close();
		}catch(Exception e) {
			// SQL Exception
			//e.printStackTrace();
		}
		return ret;
	}
	
	
	
	
	
	
	/*
	 * 对指定表进行操作，添加删除修改元组，保证输入数据合法
	 * 
	 * 
	 */
	public boolean insertIntoRenter(Renter r) {
		String sql = "INSERT INTO Renter(RenterId,RenterPassword,RenterName,RenterSex,RenterAddress,RenterPhone,RenterRooms)"
				+ " VALUES "+r.toTupleInString();
		return doSqlUpdateStatement(sql);
	}
	public boolean deleteRenter(Renter r) {
		String sql = "DELETE FROM Renter WHERE RenterId = '"+r.id+"'";
		return doSqlUpdateStatement(sql);
	}
	public boolean updateRenter(Renter r,String col,String value) {
		String sql = "UPDATE Renter SET "+col+" = '"+value+"' WHERE RenterId = '"+r.id+"'";
		return doSqlUpdateStatement(sql);
	}
	
	public boolean insertIntoRoom(Room r) {
		String sql = "INSERT INTO Room(RoomId,ServiceId,RoomType,RoomState,RoomRent,OwnerName,OwnerPhone,RoomAddress,RoomArea,RoomItem,RoomPictures)"
				+ " VALUES "+r.toTupleInString();
		return doSqlUpdateStatement(sql);
	}
	public boolean deleteRoom(Room r) {
		String sql = "DELETE FROM Room WHERE RoomId = '"+r.getId()+"'";
		return doSqlUpdateStatement(sql);
	}
	public boolean updateRoom(Room r,String col,String value) {
		String sql = "UPDATE Room SET "+col+" = '"+value+"' WHERE RoomId = '"+r.getId()+"'";
		return doSqlUpdateStatement(sql);
	}
	
	public boolean insertIntoService(Service s) {
		String sql = "INSERT INTO Service(ServiceId,ServicePassword,ServiceName,ServiceSex,ServicePhone)"
				+ " VALUES "+s.toTupleInString();
		return doSqlUpdateStatement(sql);
	}
	public boolean deleteService(Service s) {
		String sql = "DELETE FROM Service WHERE ServiceId = '"+s.id+"'";
		return doSqlUpdateStatement(sql);
	}
	public boolean updateService(Service r,String col,String value) {
		String sql = "UPDATE Service SET "+col+" = '"+value+"' WHERE ServiceId = '"+r.id+"'";
		return doSqlUpdateStatement(sql);
	}
	
	public boolean insertIntoRepairman(Repairman r) {
		String sql = "INSERT INTO Repairman(RepairmanId,RepairmanName,RepairmanPassword,RepairmanSex,RepairmanPhone,RepairmanType)"
				+ " VALUES "+r.toTupleInString();
		return doSqlUpdateStatement(sql);
	}
	public boolean deleteRepairman(Repairman r) {
		String sql = "DELETE FROM Repairman WHERE RepairmanId = '"+r.id+"'";
		return doSqlUpdateStatement(sql);
	}
	public boolean updateRepairman(Repairman r,String col,String value) {
		String sql = "UPDATE Repairman SET "+col+" = '"+value+"' WHERE RepairmanId = '"+r.id+"'";
		return doSqlUpdateStatement(sql);
	}
	
	public boolean insertIntoOrders(Orders o) {
		String sql = "INSERT INTO Orders(OrdersId,RenterId,RoomId,ServiceId,OrdersType,OrdersTime,OrdersDate,OrdersItem,OrdersState,OrdersDueDate,OrdersPay)"
				+ " VALUES "+o.toTupleInString();
		return doSqlUpdateStatement(sql);
	}
	public boolean deleteOrders(Orders o) {
		String sql = "DELETE FROM Orders WHERE OrdersId = '"+o.getId()+"'";
		return doSqlUpdateStatement(sql);
	}
	public boolean updateOrders(Orders r,String col,String value) {
		String sql = "UPDATE Orders SET "+col+" = '"+value+"' WHERE OrdersId = '"+r.getId()+"'";
		return doSqlUpdateStatement(sql);
	}
	
	public boolean insertIntoComplaint(Complaint wo) {
		String sql = "INSERT INTO Complaint(ComplaintId,RenterId,ServiceId,ComplaintDate,ComplaintItem,ComplaintState,ComplaintPictures)"
				+ " VALUES "+wo.toTupleInString();
		return doSqlUpdateStatement(sql);
	}
	public boolean deleteComplaint(Complaint wo) {
		String sql = "DELETE FROM Complaint WHERE ComplaintId = '"+wo.getId()+"'";
		return doSqlUpdateStatement(sql);
	}
	public boolean updateComplaint(Complaint r,String col,String value) {
		String sql = "UPDATE Complaint SET "+col+" = '"+value+"' WHERE ComplaintId = '"+r.getId()+"'";
		return doSqlUpdateStatement(sql);
	}
	
	public boolean insertIntoWorkOrder(WorkOrder wo) {
		String sql = "INSERT INTO WorkOrder(WorkOrderId,RenterId,RoomId,RepairmanId,ServiceId,WorkOrderDate,WorkOrderItem,WorkOrderState,WorkOrderPictures,WorkOrderLevel)"
				+ " VALUES "+wo.toTupleInString();
		return doSqlUpdateStatement(sql);
	}
	public boolean deleteWorkOrder(WorkOrder wo) {
		String sql = "DELETE FROM WorkOrder WHERE WorkOrderId = '"+wo.getId()+"'";
		return doSqlUpdateStatement(sql);
	}
	public boolean updateWorkOrder(WorkOrder r,String col,String value) {
		String sql = "UPDATE WorkOrder SET "+col+" = '"+value+"' WHERE WorkOrderId = '"+r.getId()+"'";
		return doSqlUpdateStatement(sql);
	}
	
	
	
	// 终止和数据库的连接
	public void closeConnect() {
		try{
			if(con != null)
				con.close();
			if(stmt != null)
				stmt.close();
		}catch(Exception e) {
			// SQL Exception
		}
	}
}
