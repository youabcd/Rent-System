package com.example.rentproject.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 * OrdersId,RenterId,RoomId,ServiceId,OrdersType,OrdersTime,OrdersDate,OrdersItem,OrdersState,OrdersDueDate,OrdersPay,
 */
// 合同
public class Orders {
	private String id;
	private String renterId;
	private String roomId;
	private String serviceId;
	private String type;		// long 长租  short 短租
	private String time;		// 用/分隔，租的时间
	private String date;		// DateModel.dev = / 协议生效日期
	private String item;		// 续租申请时间
	private String state;		// 订单状态  waiting  accepted  rejected  renew
	private String duedate;		// 租客付款的到期时间
	private double pay;			// 租房单价
	
	
	public Orders(String id,String renterId,String roomId,String serviceId,String type,
			String time,String date,String item,String state,String duedate,double pay) {
		this.id = id; this.renterId = renterId; this.roomId = roomId; this.serviceId = serviceId; this.time = time;
		this.type = type; this.date = date.replace("-", "/"); this.item = item; this.pay = pay; this.state = state; this.duedate = duedate.replace("-", "/");
	}
	public Orders(String id,String renterId,String roomId,String serviceId,String type,
			String time,String item,String state,double pay) {
		this.id = id; this.renterId = renterId; this.roomId = roomId; this.serviceId = serviceId;
		this.type = type; this.item = item; this.pay = pay; this.state = state; this.time = time;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		this.date = sdf.format(new Date());
		this.duedate = date;
	}
	public Orders(String id,String renterId,String roomId,String type,String time,double pay) { 
		this.id=id;
		this.renterId=renterId;
		this.roomId=roomId;
		this.type=type;
		this.item = "";
		this.state="waiting";
		this.time=time;
		this.pay=pay;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		this.date= sdf.format(new Date());
		this.duedate = date;
	}
	
	/*
	 * check方法
	 * 
	 * 
	 */
	public static boolean checkId(String id) {
		return id.length() >= 1 && id.length() <= 50;
	}
	
	public static boolean checkRenterId(String renterId) {
		return Renter.checkId(renterId);
	}
	
	public static boolean checkServiceId(String serviceId) {
		return Service.checkId(serviceId);
	}
	
	public static boolean checkOrdersType(String type) {
		return type.equals("long") || type.equals("short");
	}
	
	public static boolean checkDate(String date) {
		return DateModel.CheckDate(date) >= 0;
	}
	
	public static boolean checkItem(String item) {
		return item.length() <= 2000;
	}
	public static boolean checkState(String state) {
		return state.equals("waiting") || state.equals("accepted") || state.equals("rejected");
	}
	
	/*
	 * 查找Orders的方法
	 * 
	 * 
	 */
	// 所有订单
	public static ArrayList<Orders> findAllOrders(){
		ArrayList<Orders> ret = new ArrayList<Orders>();
		try {
			ResultSet rs = Repository.getInstance().doSqlSelectStatement("SELECT * FROM Orders");
			while(rs.next()) {
				Orders r = new Orders( rs.getString("OrdersId"),rs.getString("RenterId"),rs.getString("RoomId"),rs.getString("ServiceId"),rs.getString("OrdersType"),
						rs.getString("OrdersTime"),rs.getString("OrdersDate"),rs.getString("OrdersItem"),rs.getString("OrdersState"),rs.getString("OrdersDueDate"),
				Double.valueOf(rs.getString("OrdersPay")));
				ret.add(r);
			}
			rs.close();
		}catch(Exception e) {
			// SQL Exception
			// e.printStackTrace();
		}
		return ret;
	}
	
	public static Orders findOrdersById(String id) {
		try {
			return Repository.getInstance().selectFromOrders("OrdersId", id, "total").get(0);
		}catch(Exception e) {
			return null;
		}
		
	}
	public static ArrayList<Orders> findOrdersByRenterId(String rid) {
		return Repository.getInstance().selectFromOrders("RenterId", rid, "total");
	}
	
	public static ArrayList<Orders> findOrdersByRoomId(String rid) {
		return Repository.getInstance().selectFromOrders("RoomId", rid, "total");
	}
	
	public static ArrayList<Orders> findOrdersByServiceId(String sid) {
		return Repository.getInstance().selectFromOrders("ServiceId", sid, "total");
	}
	
	public static ArrayList<Orders> findOrdersByType(String type) {
		return Repository.getInstance().selectFromOrders("OrdersType", type, "total");
	}
	
	public static ArrayList<Orders> findOrdersInDate(String date) {
		return Repository.getInstance().selectFromOrders("OrdersDate", date, "total");
	}
	
	public static ArrayList<Orders> findRenewableOrders(String rid){
		ArrayList<Orders> ret = new ArrayList<Orders>();
		try {
			ResultSet rs = Repository.getInstance().doSqlSelectStatement("SELECT * FROM Orders WHERE OrdersState = 'accepted'"
					+ " AND OrdersType = 'long' AND RenterId = '"+rid+"'");
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
	
	public static ArrayList<Orders> findOngoingOrders(){
		ArrayList<Orders> ret = new ArrayList<Orders>();
		try {
			ResultSet rs = Repository.getInstance().doSqlSelectStatement("SELECT * FROM Orders "
					+ "WHERE ( OrdersState = 'accepted' OR OrdersState = 'renew' ) "
					+ " AND OrdersType = 'long'");
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

	/*
	 * 插入Orders的方法
	 * 
	 * 
	 */
	
	public static boolean addNewOrders(Orders c) {
		return Repository.getInstance().insertIntoOrders(c);
	}
	
	/*
	 * 删除Orders的方法
	 * 
	 * 
	 */
	
	public static boolean deleteOrders(Orders c) {
		return Repository.getInstance().deleteOrders(c);
	}
	
	/*
	 * 申请续租
	 */
	public boolean renewThis(String time) {
		this.state = "renew";
		this.item = time;
		return this.updateThisState() && this.updateThisItem();
	}
	/*
	 * 同意续租
	 */
	public boolean acceptRenew() {
		this.time = DateModel.addDateFormat(this.time, this.item);
		this.state = "accepted";
		this.item = "";
		return this.updateThisTime() && this.updateThisState() && this.updateThisItem();
	}
	/*
	 * 拒绝续租
	 */
	public boolean rejectRenew() {
		this.state = "accepted";
		this.item = "";
		return this.updateThisState() && this.updateThisItem();
	}
	
	/*
	 * 更新订单的信息
	 */
	public boolean updateThisState() {
		return Repository.getInstance().updateOrders(this, "OrdersState", this.state);
	}
	public boolean updateThisItem() {
		return Repository.getInstance().updateOrders(this, "OrdersItem", this.item);
	}
	public boolean updateThisTime() {
		return Repository.getInstance().updateOrders(this, "OrdersTime", this.time);
	}
	
	// 客服通过
	public boolean acceptThis() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		this.date = sdf.format(new Date());
		this.duedate = this.date;
		
		return Repository.getInstance().updateOrders(this, "OrdersState", "accepted") &&
		Repository.getInstance().updateOrders(this, "OrdersDate", date) &&
		Repository.getInstance().updateOrders(this, "OrdersDueDate", duedate);
	}
	// 不通过
	public boolean rejectThis() {
		return Repository.getInstance().updateOrders(this, "OrdersState", "rejected")
				&& Room.setRoomState(this.roomId, "available");
	}
	
	// 未付款时间
	public String getDelayDate() {
		if(this.state.equals("accepted")) {
			if(isPay()) {
				return "0/0";
			}
			return DateModel.subDate(DateModel.addDate(DateModel.now(),"0/1/0"),this.getDuedate()).substring(0,3);
		}
		return "0/0";
	}
	// 付清欠款
	public boolean payed() {
		if(this.type.equals("short")) {
			this.duedate = DateModel.getDueDate(this);
			Repository.getInstance().updateOrders(this, "OrdersDueDate", this.duedate);
			return true;
		}
		String len = getDelayDate()+"/0";
		String last = this.duedate;		
		this.duedate = DateModel.addDate(last, len);
		Repository.getInstance().updateOrders(this, "OrdersDueDate", this.duedate);
		return true;
	}
	
	/*
	 * 获取下一个订单id
	 */
	public static String getNextId() {
		try {
			ResultSet rs = Repository.getInstance().doSqlSelectStatement("SELECT OrdersId FROM Orders WHERE OrdersId >= "
				+ "All( SELECT OrdersId FROM Orders )");
			if(rs.next()) {
				return String.valueOf(Integer.valueOf(rs.getString("OrdersId"))+1);
			}
			return "1001";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	// 是否应付款
	public boolean isPay() {
		if(this.state.equals("accepted")) {
			if(DateModel.compare(DateModel.getDueDate(this), this.getDuedate()) >= 0) {
				return true;
			}
			return DateModel.compare(DateModel.addDate(DateModel.now(), "0/1/0"), this.getDuedate()) >= 0;
		}
		return true;
	}
	// 欠款
	public double dueRent() {
		if(this.state.equals("accepted")) {
			String s[] = getDelayDate().split("/");
			if(this.type.equals("long")) {
				return (Double.valueOf(s[0])*12+Double.valueOf(s[1]) )*this.pay;
			}
			return (double)Math.round(this.pay*100)/100.0;
		}
		return 0;
	}
	
	// 导出合同 返回文件名
	public String getOrdersContract() {
		if(this.type.equals("short")) {
			return null;
		}
		String inpath = "./contract/contract.txt";
		String name = "./contract/";
		StringBuffer content = new StringBuffer();
		File in = new File(inpath);
		name +=  "order" + this.id + ".txt";
		File out = new File(name);
		try {
			if(this.type.equals("short") || this.state.equals("waiting")) {
				return null;
			}
			if(!out.exists()) {
				out.createNewFile();
			}
			
			FileReader r = new FileReader(in);
			FileWriter w = new FileWriter(out);
			
			BufferedReader br = new BufferedReader(r);
			BufferedWriter bw = new BufferedWriter(w);
			
			String line = null;
			int n = 1;
			Service s = Service.findServiceById(this.serviceId);
			Renter rt = Renter.findRenterById(this.renterId);
			Room rm = Room.findRoomById(this.roomId);
			while((line = br.readLine()) != null) {
				StringBuffer str = new StringBuffer(line);
				if(n == 3) {
					str = str.append(s.getId());
				}
				else if(n == 4) {
					str = str.append(s.name);
				}
				else if(n == 5) {
					str = str.append(s.getPhone());
				}
				else if(n == 7) {
					str = str.append(rt.getId());
				}
				else if(n == 8) {
					str = str.append(rt.name);
				}
				else if(n == 9) {
					str = str.append(rt.getPhone());
				}
				else if(n == 13) {
					str = str.insert(15, rm.getAddress());
				}
				else if(n == 17) {
					str = str.insert(6, DateModel.formatDate(this.getDate()) );
					str = str.insert(19, DateModel.formatDate(DateModel.getDueDate(this)) );
				}
				else if(n == 19) {
					str = str.insert(7, rm.getRent());
				}
				bw.write(str.toString());
				bw.newLine();
				content.append(str.toString()+"\n");
				n++;
			}
			
			bw.close();
			w.close();
			
			br.close();
			r.close();
			
			return content.toString();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/*
	 * toString类似的方法
	 * 
	 * 
	 * 
	 */
	public String toTupleInString() {
		return "('"+id+"','"+renterId+"','"+roomId+"','"+serviceId+"','"+type+"','"+time+"','"+date+"','"+item+"','"+state+"','"+duedate+"','"+String.valueOf(pay)+"')";
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDuedate() {
		return duedate.replace("-", "/").substring(0,10);
	}
	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}
	
	/*
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

	public String getRenterId() {
		return renterId;
	}

	public void setRenterId(String renterId) {
		this.renterId = renterId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date.substring(0,10).replace("-", "/");
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
	public double getPay() {
		return pay;
	}

	public void setPay(double pay) {
		this.pay = pay;
	}
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
