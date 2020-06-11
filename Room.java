package com.example.rentproject.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * RoomId,ServiceId,RoomType,RoomState,RoomRent,RoomAddress,RoomArea,RoomItem,RoomPictures
 */

public class Room {
	public static final String ITEMDEV = "/";		// 房间备注的分隔符
	public static final String PICDEV = "-";		// 照片链接的分隔符	
	
	private String id;			// 房间编号		qkxxxxxx
	private String serviceId;
	private String type;		// 房间类型		"single","double","four"
	private String state;		// 房间状态		"available","stop","rented"
	private Double rent;			// 房间租金		元/月
	private String address;		// 房间地址		xx市xx区
	private Double area;			// 房间面积		m2
	private String item;		// 房间备注		xxx/xxx/xxx......
	private ArrayList<String> pictures; //房间照片链接
	
	public Room(String id,String serviceId,String type,String state,String rent,String address,String area,String item,String pictures) {
		this.id = id;
		this.type = type;
		this.state = state;
		this.rent = Double.valueOf(rent);
		this.serviceId = serviceId;
		this.address = address;
		this.area = Double.valueOf(area);
		this.item = item;
		this.pictures = stringToList(pictures);
	}
	public Room(String id,String serviceId,String type,String state,String rent,String address,String area) {
		this.id = id;
		this.type = type;
		this.state = state;
		this.rent = Double.valueOf(rent);
		this.serviceId = serviceId;
		this.address = address;
		this.area = Double.valueOf(area);
		this.item = "";
		this.pictures = new ArrayList<>();
	}
	public Room(String id,String serviceId,String type,String state,String rent,String address,String area,String item) {
		this.id = id;
		this.type = type;
		this.state = state;
		this.rent = Double.valueOf(rent);
		this.serviceId = serviceId;
		this.address = address;
		this.area = Double.valueOf(area);
		this.item = item;
		this.pictures = new ArrayList<>();
	}
	public Room(Room r) {
		this.id = r.id;
		this.type = r.type;
		this.state = r.state;
		this.rent = r.rent;
		this.serviceId = r.serviceId;
		this.address = r.address;
		this.area = r.area;
		this.item = r.item;
		this.pictures = r.pictures;
	}
	
	/*
	 * check属性方法
	 * 
	 * 
	 * 
	 */
	
	public static boolean checkId(String id) {
		return id.length() <= 50 && id.length() >= 1;
	}
	
	public static boolean checkType(String type) {
		return type.equals("single") || type.equals("double") || type.equals("four");
	}
	
	public static boolean checkState(String state) {
		return state.equals("available") || state.equals("stop") || state.equals("rented");
	}
	
	public static boolean checkRent(String rent) {
		try {
			return Float.valueOf(rent) > 0;
		}catch(Exception e) {
			return false;
		}
	}
	
	public static boolean checkOwnerName(String ownerName) {
		return Person.checkName(ownerName);
	}
	
	public static boolean checkOwnerPhone(String ownerPhone) {
		return Renter.checkPhone(ownerPhone);
	}
	
	public static boolean checkAddress(String address) {
		return Renter.checkAddress(address);
	}
	
	public static boolean checkArea(String area) {
		try {
			return Float.valueOf(area) > 0;
		}catch(Exception e) {
			return false;
		}
	}

	public static boolean checkItem(String item) {
		return item.length() >= 1 && item.length() <= 2000;
	}
	
	public static boolean checkPictures(String pictures) {
		return pictures.length() >= 1 && pictures.length() <= 2000;
	}
	

	/*
	 * 查找Room的方法
	 * 
	 * 
	 */
	
	public static Room findRoomById(String id) {
		try{
			return Repository.getInstance().selectFromRoom("RoomId", id, "total").get(0);
		}catch(Exception e) {
			return null;
		}
	}
	// 所有房间
	public static ArrayList<Room> findAllRoom(){
		ArrayList<Room> ret = new ArrayList<Room>();
		try {
			ResultSet rs = Repository.getInstance().doSqlSelectStatement("SELECT * FROM ROOM");
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
	// 设置房间状态
	public static boolean setRoomState(String rid,String state) {
		if(!checkState(state)) {
			return false;
		}
		try {
			Room r = Room.findRoomById(rid);
			return Repository.getInstance().updateRoom(r, "RoomState", state);
		}catch(Exception e) {
			return false;
		}
	}
	
	// 空闲房间
	public static ArrayList<Room> findavailableRoom(){
		return Repository.getInstance().selectFromRoom("RoomState", "available", "total");
	}
	// 已租出房间
	public static ArrayList<Room> findRentedRoom(){
		return Repository.getInstance().selectFromRoom("RoomState", "rented", "total");
	}
	// 停止出租房间
	public static ArrayList<Room> findStopRentRoom(){
		return Repository.getInstance().selectFromRoom("RoomState", "stop", "total");
	}
	// 根据地址关键字查找房间
	public static ArrayList<Room> findRoomByAddress(String address){
		return Repository.getInstance().selectFromRoom("RoomAddress", address, "all");
	}
	public static ArrayList<Room> findavailableRoomByAddress(String address){
		ArrayList<Room> allroom = findRoomByAddress(address), ans = new ArrayList<>();
		for(Room r:allroom) {
			if(r.state.equals("available"))
				ans.add(r);
		}
		return ans;
	}
	// 按照价格和面积排序  first和second是排序的先后顺序，取值area或rent，forder和sorder为升序或降序排序，取值ASC或DESC
	public static ArrayList<Room> findRoomInOrder(ArrayList<Room> list,String first,String forder,String second,String sorder){
		ArrayList<Room> rooms = list;
		if(first.equals("area")){
			Collections.sort(rooms,new Comparator<Room>() {
				public int compare(Room p1,Room p2) {
					if(p1.area.compareTo(p2.area)>0) {
						if(forder.equals("ASC"))
							return 1;
						else
							return -1;
					}
					else if(p1.area.compareTo(p2.area)==0) {
						if(p1.rent.compareTo(p2.rent)>0) {
							if(sorder.equals("ASC"))
								return 1;
							else
								return -1;
						}
						else if(p1.rent.compareTo(p2.rent)==0)
							return 0;
						else {
							if(sorder.equals("ASC"))
								return -1;
							else
								return 1;
						}
					}
					else {
						if(forder.equals("ASC"))
							return -1;
						else
							return 1;
					}
						
				}
			});
		}
		else {
			Collections.sort(rooms,new Comparator<Room>() {
				public int compare(Room p1,Room p2) {
					if(p1.rent.compareTo(p2.rent)>0) {
						if(forder.equals("ASC"))
						return 1;
					else
						return -1;
					}
					else if(p1.rent.compareTo(p2.rent)==0) {
						if(p1.area.compareTo(p2.area)>0) {
							if(sorder.equals("ASC"))
								return 1;
							else
								return -1;
						}
						else if(p1.area.compareTo(p2.area)==0)
							return 0;
						else {
							if(sorder.equals("ASC"))
								return -1;
							else
								return 1;
						}
					}
					else {
						if(forder.equals("ASC"))
							return -1;
						else
							return 1;
					}
						
				}
			});
		}
		return rooms;
	}
	
	/*
	 * 根据面积范围查找房间
	 * f	面积下限
	 * c	面积上限
	 * by	根据属性排序
	 * type	排序方式，DESC或ASC
	 */
	public static ArrayList<Room> findRoomByArea(Float f,Float c,String by,String type){
		ArrayList<Room> ret = new ArrayList<Room>();
		try {
			ResultSet rs = Repository.getInstance().doSqlSelectStatement("SELECT * FROM ROOM WHERE RoomArea >= "+f+" AND RoomArea < "+c+
					"ORDER BY "+by+" "+type);
			while(rs.next()) {
				Room r = new Room( rs.getString("RoomId"),rs.getString("ServiceId"),rs.getString("RoomType"),rs.getString("RoomState"),rs.getString("RoomRent"),
						rs.getString("RoomAddress"),rs.getString("RoomArea"),rs.getString("RoomItem"),rs.getString("RoomPictures") );
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
	 * 根据租金范围查找房间
	 * f	租金下限
	 * c	租金上限
	 * by	根据属性排序
	 * type	排序方式，DESC或ASC
	 */
	public static ArrayList<Room> findRoomByRent(Float f,Float c,String by,String type){
		ArrayList<Room> ret = new ArrayList<Room>();
		try {
			ResultSet rs = Repository.getInstance().doSqlSelectStatement("SELECT * FROM ROOM WHERE RoomRent >= "+f+" AND RoomRent < "+c+
					"ORDER BY "+by+" "+type);
			while(rs.next()) {
				Room r = new Room( rs.getString("RoomId"),rs.getString("ServiceId"),rs.getString("RoomType"),rs.getString("RoomState"),rs.getString("RoomRent"),
						rs.getString("RoomAddress"),rs.getString("RoomArea"),rs.getString("RoomItem"),rs.getString("RoomPictures") );
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

	public String getType() {
		return type;
	}
	
	public String getTypeShow() {
		if(type.equals("single"))
			return "单人间";
		else if(type.equals("double"))
			return "双人间";
		else
			return "四人间";
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getRent() {
		return Math.round(rent*100)/100.0;
	}

	public void setRent(Double rent) {
		this.rent = rent;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getItem() {
		return item;
	}
	
	public String getItemShow() {
		return item.replace("/", "；")+"。";
	}
	
	public void setItem(String item) {
		this.item = item;
	}
	
	public ArrayList<String> getPictures() {
		return pictures;
	}
	
	public void setPictures(ArrayList<String> pictures) {
		this.pictures = pictures;
	}
	
	public Double getArea() {
		return area;
	}
	
	public void setArea(Double area) {
		this.area = area;
	}
	
	/*
	 * 
	 * 插入和删除Room的方法
	 * 
	 */
	public static boolean addNewRoom(Room r) {
		return Repository.getInstance().insertIntoRoom(r);
	}
	public static boolean deleteRoom(Room r) {
		return Repository.getInstance().deleteRoom(r);
	}
	
	
	/*
	 * 
	 * 
	 * toString类似的方法
	 * 
	 * 
	 */
	public static String listToString(ArrayList<String> al) {
		StringBuilder sd = new StringBuilder();
		for(String s:al) {
			sd.append(s);
			sd.append(PICDEV);
		}
		return sd.toString();
	}
	
	public static ArrayList<String> stringToList(String st){
		ArrayList<String> al = new ArrayList<String>();
		if(st==null) {
			return al;
		}
		String[] ss = st.split(PICDEV);
		for(String s:ss) {
			if(s!="") {
				al.add(s);
			}
		}
		return al;
	}
	
	public static String getNextId() {
		try {
			ResultSet rs = Repository.getInstance().doSqlSelectStatement("SELECT TOP 1 RoomId FROM Room " + 
					"ORDER BY RoomId DESC;");
			if(rs.next()) {
				String dd = rs.getString("RoomId").substring(2);
				return "qk" + String.valueOf(Integer.valueOf(dd)+1);
			}
			return "0";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/*
	 * 每天的租金
	 */
	public double getDayRent() {
		return this.rent/30.0;
	}
	
	public String toTupleInString() {
		return "('"+id+"','"+serviceId+"','"+type+"','"+state+"',"+rent+"','"+address+"',"+area+",'"+item+"','"+listToString(pictures)+"')";
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	
}
