package com.example.rentproject.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.rentproject.model.*;
import com.example.rentproject.service.*;



/*
 * 返回前端的类
 * 
 */
class Return{
	private boolean success = false;
	private String message = "";
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}

class Ret{
	private boolean success;
	private boolean collected;
	private String message;

	private List<Object> obj;
	private List<Object> pic;
	public Ret() {
		success = false;
		collected = false;
		message = "";
		obj = new ArrayList<Object>();
		pic = new ArrayList<Object>();
	}
	
	public void addObj(Object o) {
		obj.add(o);
	}
	public void addPic(Object p) {
		pic.add(p);
	}
	
	/*
	 * get和set方法
	 */
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<Object> getObj() {
		return obj;
	}
	public void setObj(List<Object> obj) {
		this.obj = obj;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Object> getPic() {
		return pic;
	}
	public void setPic(List<Object> pic) {
		this.pic = pic;
	}

	public boolean isCollected() {
		return collected;
	}

	public void setCollected(boolean collected) {
		this.collected = collected;
	}
	
}

class ComplaintRet extends Ret{
	private List<Object> serviceName;
	private List<Object> servicePhone;
	private List<Object> roomId;
	private List<Object> serviceId;
	private List<Object> pic;
	
	private List<Object> complaintState;
	private List<Object> renterItem;
	private List<Object> serviceItem;
	private List<Object> returnItem;
	private List<Object> complaintId;
	
	public ComplaintRet() {
		super();
		serviceName = new ArrayList<>();
		servicePhone = new ArrayList<>();
		roomId = new ArrayList<>();
		serviceId = new ArrayList<>();
		pic = new ArrayList<>();
		renterItem = new ArrayList<>();
		serviceItem = new ArrayList<>();
		returnItem = new ArrayList<>();
		complaintState = new ArrayList<>();
		complaintId = new ArrayList<>();
	}
	public void addNewRoom(RoomForRet rr) {
		this.serviceName.add(rr.getOwnerName());
		this.servicePhone.add(rr.getOwnerPhone());
		this.roomId.add(rr.getId());
		this.serviceId.add(rr.getServiceId());
	}
	public void addComplaint(Complaint c) {
		Service s = Service.findServiceById(c.getServiceId());
		this.serviceName.add(s.getName());
		this.servicePhone.add(s.getPhone());
		if(c.getPictures().size() > 0)
			this.pic.add(c.getPictures().get(0));
		this.renterItem.add(c.getRenterItem());
		this.serviceItem.add(c.getServiceItem());
		this.returnItem.add(c.getReturnItem());
		this.complaintState.add(c.getState());
		this.complaintId.add(c.getId());
	}
	public void addComplaint(List<Complaint> l) {
		for(Complaint c:l) {
			addComplaint(c);
		}
	}
	public List<Object> getServiceName() {
		return serviceName;
	}
	public void setServiceName(List<Object> serviceName) {
		this.serviceName = serviceName;
	}
	public List<Object> getServicePhone() {
		return servicePhone;
	}
	public void setServicePhone(List<Object> servicePhone) {
		this.servicePhone = servicePhone;
	}
	public List<Object> getRoomId() {
		return roomId;
	}
	public void setRoomId(List<Object> roomId) {
		this.roomId = roomId;
	}
	public List<Object> getServiceId() {
		return serviceId;
	}
	public void setServiceId(List<Object> serviceId) {
		this.serviceId = serviceId;
	}
	public List<Object> getPic() {
		return pic;
	}
	public void setPic(List<Object> pic) {
		this.pic = pic;
	}
	public List<Object> getRenterItem() {
		return renterItem;
	}
	public void setRenterItem(List<Object> renterItem) {
		this.renterItem = renterItem;
	}
	public List<Object> getServiceItem() {
		return serviceItem;
	}
	public void setServiceItem(List<Object> serviceItem) {
		this.serviceItem = serviceItem;
	}
	public List<Object> getReturnItem() {
		return returnItem;
	}
	public void setReturnItem(List<Object> returnItem) {
		this.returnItem = returnItem;
	}
	public List<Object> getComplaintState() {
		return complaintState;
	}
	public void setComplaintState(List<Object> complaintState) {
		this.complaintState = complaintState;
	}
	public List<Object> getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(List<Object> complaintId) {
		this.complaintId = complaintId;
	}
	
}

class WorkOrderRet extends Ret{
	private ArrayList<String> workorderid;
	private ArrayList<String> workorderstate;
	private ArrayList<String> serviceid;
	private ArrayList<String> servicename;
	private ArrayList<String> servicephone;
	private ArrayList<String> repairmanid;
	private ArrayList<String> repairmanname;
	private ArrayList<String> repairmanphone;
	private ArrayList<String> roomaddress;
	private ArrayList<String> workorderdate;
	private ArrayList<String> workorderpic;
	private ArrayList<String> renteritem;
	private ArrayList<String> returnitem;
	private ArrayList<String> repairmanitem;
	public WorkOrderRet() {
		super();
		workorderid = new ArrayList<>(); workorderstate = new ArrayList<>(); serviceid = new ArrayList<>();
		servicename = new ArrayList<>(); servicephone = new ArrayList<>(); repairmanid = new ArrayList<>();
		repairmanname = new ArrayList<>(); repairmanphone = new ArrayList<>(); repairmanitem = new ArrayList<>();
		roomaddress = new ArrayList<>(); workorderdate = new ArrayList<>(); workorderpic = new ArrayList<>();
		renteritem = new ArrayList<>(); returnitem = new ArrayList<>();
	}
	public void addWorkOrder(WorkOrder o) {
		this.workorderid.add(o.getId());
		this.workorderstate.add(o.getState());
		Service s = Service.findServiceById(o.getServiceId());
		this.serviceid.add(o.getServiceId());
		this.servicename.add(s.getName());
		this.servicephone.add(s.getPhone());
		Repairman p = Repairman.findRepairmanById(o.getRepairmanId());
		if(p!=null) {
			this.repairmanid.add(p.getId());
			this.repairmanname.add(p.getName());
			this.repairmanphone.add(p.getPhone());
		}
		Room r = Room.findRoomById(o.getRoomId());
		this.roomaddress.add(r.getAddress());
		this.workorderdate.add(o.getDate());
		if(o.getPictures().size() > 0)
			this.workorderpic.add(o.getPictures().get(0));
		this.renteritem.add(o.getRenterItem());
		this.returnitem.add(o.getReturnItem());
		this.repairmanitem.add(o.getRepairmanItem());
	}
	public void addWorkOrder(List<WorkOrder> l){
		for(WorkOrder wo:l) {
			addWorkOrder(wo);
		}
	}
	public ArrayList<String> getWorkorderid() {
		return workorderid;
	}
	public void setWorkorderid(ArrayList<String> workorderid) {
		this.workorderid = workorderid;
	}
	public ArrayList<String> getWorkorderstate() {
		return workorderstate;
	}
	public void setWorkorderstate(ArrayList<String> workorderstate) {
		this.workorderstate = workorderstate;
	}
	public ArrayList<String> getServiceid() {
		return serviceid;
	}
	public void setServiceid(ArrayList<String> serviceid) {
		this.serviceid = serviceid;
	}
	public ArrayList<String> getServicename() {
		return servicename;
	}
	public void setServicename(ArrayList<String> servicename) {
		this.servicename = servicename;
	}
	public ArrayList<String> getServicephone() {
		return servicephone;
	}
	public void setServicephone(ArrayList<String> servicephone) {
		this.servicephone = servicephone;
	}
	public ArrayList<String> getRepairmanid() {
		return repairmanid;
	}
	public void setRepairmanid(ArrayList<String> repairmanid) {
		this.repairmanid = repairmanid;
	}
	public ArrayList<String> getRepairmanname() {
		return repairmanname;
	}
	public void setRepairmanname(ArrayList<String> repairmanname) {
		this.repairmanname = repairmanname;
	}
	public ArrayList<String> getRepairmanphone() {
		return repairmanphone;
	}
	public void setRepairmanphone(ArrayList<String> repairmanphone) {
		this.repairmanphone = repairmanphone;
	}
	public ArrayList<String> getRoomaddress() {
		return roomaddress;
	}
	public void setRoomaddress(ArrayList<String> roomaddress) {
		this.roomaddress = roomaddress;
	}
	public ArrayList<String> getWorkorderdate() {
		return workorderdate;
	}
	public void setWorkorderdate(ArrayList<String> workorderdate) {
		this.workorderdate = workorderdate;
	}
	public ArrayList<String> getWorkorderpic() {
		return workorderpic;
	}
	public void setWorkorderpic(ArrayList<String> workorderpic) {
		this.workorderpic = workorderpic;
	}
	public ArrayList<String> getRenteritem() {
		return renteritem;
	}
	public void setRenteritem(ArrayList<String> renteritem) {
		this.renteritem = renteritem;
	}
	public ArrayList<String> getReturnitem() {
		return returnitem;
	}
	public void setReturnitem(ArrayList<String> returnitem) {
		this.returnitem = returnitem;
	}
	public ArrayList<String> getRepairmanitem() {
		return repairmanitem;
	}
	public void setRepairmanitem(ArrayList<String> repairmanitem) {
		this.repairmanitem = repairmanitem;
	}
}

class RoomRet{
	private List<Object> type;
	private List<Object> address;
	private List<Object> rent;
	private List<Object> pictures;
	private List<Object> state;
	
	private int num;
	public RoomRet() {
		type = new ArrayList<>();
		address = new ArrayList<>();
		rent = new ArrayList<>();
		pictures = new ArrayList<>();
		state = new ArrayList<>();
		num = 0;
	}
	public void addRoom(Room r) {
		type.add(r.getTypeShow());
		String add;
		if(r.getAddress().length()>10)
			add = r.getAddress().substring(0,10) + "...";
		else
			add = r.getAddress();
		address.add(add);
		rent.add(r.getRent());
		pictures.add(r.getPictures().toArray()[0]);
		state.add(r.getState());
		this.num++;
	}
	public void addAllRoom(List<Room> l) {
		for(Room r:l) {
			addRoom(r);
		}
	}
	public void addRoom(List<Room> l,int b,int h,String t) {
		for(Room r:l) {
			if((b<0 || r.getRent()>=b) && (h<0||r.getRent()<h) && 
					(t.equals("all") || t.equals(r.getType()) ) ) {
				if(r.getAddress().length()>10)
					r.setAddress(r.getAddress().substring(0,10)+"...");
				addRoom(r);
			}
		}
	}
	public List<Object> getType() {
		return type;
	}
	public void setType(List<Object> type) {
		this.type = type;
	}
	public List<Object> getAddress() {
		return address;
	}
	public void setAddress(List<Object> address) {
		this.address = address;
	}
	public List<Object> getRent() {
		return rent;
	}
	public void setRent(List<Object> rent) {
		this.rent = rent;
	}
	public List<Object> getPictures() {
		return pictures;
	}
	public void setPictures(List<Object> pictures) {
		this.pictures = pictures;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public List<Object> getState() {
		return state;
	}
	public void setState(List<Object> state) {
		this.state = state;
	}
}
class OrdersRet extends RoomRet{
	private ArrayList<Object> date;
	private ArrayList<Object> ordersState;
	private ArrayList<Object> pay;
	private ArrayList<Object> delaydate;
	private ArrayList<Object> ordersId;
	private ArrayList<Object> money;
	private ArrayList<Object> ordersType;
	private ArrayList<Object> contracturl;
	
	public OrdersRet() {
		super();
		date = new ArrayList<>();
		ordersState = new ArrayList<>();
		pay = new ArrayList<>();
		delaydate = new ArrayList<>();
		ordersId = new ArrayList<>();
		money = new ArrayList<>();
		ordersType = new ArrayList<>();
		contracturl = new ArrayList<>();
	}
	
	public void addOrders(Orders o) {
		Room r = Room.findRoomById(o.getRoomId());
		super.addRoom(r);
		String d = DateModel.getDueDate(o);
		d = d.replaceFirst("/", "年");
		d = d.replace("/", "月");
		d = d + "日";
		date.add(d);
		ordersState.add(o.getState());
		pay.add(o.isPay());

		if(o.getType().equals("short")) {
			d = o.getTime();
			String t[] = d.split("/");
			if(t[1].equals("0"))
				d = t[2] + "日";
			else
				d = "1月";
		}
		else {
			d = o.getDelayDate().replaceFirst("/", "年");
			d = d + "月";
		}
		delaydate.add(d);
		ordersId.add(o.getId());
		money.add(o.dueRent());
		ordersType.add(o.getType());
		contracturl.add("static/contract/"+o.getOrdersContract());
	}
	
	public void addAllOrders(ArrayList<Orders> o) {
		for(Orders oo:o) {
			addOrders(oo);
		}
	}
	
	public ArrayList<Object> getDate() {
		return date;
	}

	public void setDate(ArrayList<Object> date) {
		this.date = date;
	}

	public ArrayList<Object> getOrdersState() {
		return ordersState;
	}

	public void setOrdersState(ArrayList<Object> ordersState) {
		this.ordersState = ordersState;
	}

	public ArrayList<Object> getPay() {
		return pay;
	}

	public void setPay(ArrayList<Object> pay) {
		this.pay = pay;
	}

	public ArrayList<Object> getDelaydate() {
		return delaydate;
	}

	public void setDelaydate(ArrayList<Object> delaydate) {
		this.delaydate = delaydate;
	}

	public ArrayList<Object> getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(ArrayList<Object> ordersId) {
		this.ordersId = ordersId;
	}

	public ArrayList<Object> getMoney() {
		return money;
	}

	public void setMoney(ArrayList<Object> money) {
		this.money = money;
	}

	public ArrayList<Object> getOrdersType() {
		return ordersType;
	}

	public void setOrdersType(ArrayList<Object> ordersType) {
		this.ordersType = ordersType;
	}

	public ArrayList<Object> getContracturl() {
		return contracturl;
	}

	public void setContracturl(ArrayList<Object> contracturl) {
		this.contracturl = contracturl;
	}
	
}
class RoomForRet extends Room{
	private String ownerName;
	private String ownerPhone;
	public RoomForRet(Room r) {
		super(r);
		Service s = Service.findServiceById(r.getServiceId());
		this.ownerName = s.getName();
		this.ownerPhone = s.getPhone();
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerPhone() {
		return ownerPhone;
	}
	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}
	
}
/*
 * 前端控制器
 * http://localhost:8090/login
 * 
 */

@RestController
public class Controller {
	public static String message;		// 报错信息	
	/*
	 * 租客的操作
	 */
	/*
	 * 发送验证码
	 */
	
	@RequestMapping(value = "/idcode")
	@ResponseBody
	@CrossOrigin
	public String idCode(@RequestParam("useremail") String email) {
		String code="";
		System.out.println("Received idcode.");
		try{
			code = JavaMail.sendMail(email,"idcode","");
		}catch(Exception e) {
			System.out.println("Sent error.");
		}
		return code;
	}
	
	
	/*
	 * 注册功能
	 */
	
	@RequestMapping(value = "/register")
	@ResponseBody
	@CrossOrigin
	public Object register(@RequestParam("useremail") String email,
			@RequestParam("password1") String password1,
			@RequestParam("password2") String password2,
			@RequestParam("userphone") String phone,
			@RequestParam("username") String name,
			@RequestParam("idcode") String idcode,
			@RequestParam("type") String type,
			@RequestParam("subidcode") String subIdCode) {
		message = "";
		System.out.println("Received register info.");
		Ret r = new Ret();
		r.setSuccess(Execute.addPerson(type,email,password1,password2,name,phone,idcode,subIdCode));
		r.setMessage(message);
		return r;
	}
	
	/*
	 * 登录
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	@CrossOrigin
	public Object login(@RequestParam("useremail") String email,
			@RequestParam("password") String password,
			@RequestParam("subidcode") String subcode,
			@RequestParam("type") String type,
			@RequestParam("logintype") String logintype) {
		message = "";
		Ret r = new Ret();
		if(type.equals("password")) {
			System.out.print("Password login.");
			
			Object o = Execute.getPerson(logintype,email,password);
			if(o != null) {
				r.setSuccess(true);
				r.addObj(o);
			}
			else {
				r.setMessage(message);
			}
		}
		else {
			System.out.print("Idcode login.");
			
			Object o;
			if(logintype.equals("renter"))
				o = Renter.findRenterById(email);
			else if(logintype.equals("service"))
				o = Service.findServiceById(email);
			else
				o = Repairman.findRepairmanById(email);
			
			if(o != null) {
				if(subcode.equals(password)) {
					r.setSuccess(true);
					r.addObj(o);
				}
				else {
					r.setMessage("验证码不正确");
				}
			}
			else {
				r.setMessage("账号不存在");
			}
		}
		System.out.println(r.isSuccess());
		return r;
	}
	
	/*
	 * 租户主页推荐
	 * 8个房间
	 */
	@RequestMapping(value = "/renterhomepage")
	@ResponseBody
	@CrossOrigin
	public Object renterHomePage() {
		System.out.println("Show renter home page.");
		Ret r = new Ret();
		r.setSuccess(true);
		List<Object> pic = new ArrayList<>();
		List<Object> address = new ArrayList<>();
		List<Object> rent = new ArrayList<>();
		List<Object> type = new ArrayList<>();
		Random rd = new Random();
		ArrayList<Room> rooms = new ArrayList<>();
		int i=0;
		while(rooms.size() < 8 || i>100) {
			int a = rd.nextInt(Integer.valueOf(Room.getNextId().substring(3)) - 1)+1;
			String qk = a>=10 ? "qk100":"qk1000";
			String rid = qk+String.valueOf(a);
			Room rm = Room.findRoomById(rid);
			if(rm!=null && !rooms.contains(rm) ) {
				rooms.add(rm);
			}
			i++;
		}
		
		pic.add(rooms.get(0).getPictures().toArray()[0]); address.add(rooms.get(0).getAddress()); type.add(rooms.get(0).getTypeShow()); rent.add(rooms.get(0).getRent()); 
		pic.add(rooms.get(1).getPictures().toArray()[0]); address.add(rooms.get(1).getAddress()); type.add(rooms.get(1).getTypeShow()); rent.add(rooms.get(1).getRent());
		pic.add(rooms.get(2).getPictures().toArray()[0]); address.add(rooms.get(2).getAddress()); type.add(rooms.get(2).getTypeShow()); rent.add(rooms.get(2).getRent());
		pic.add(rooms.get(3).getPictures().toArray()[0]); address.add(rooms.get(3).getAddress()); type.add(rooms.get(3).getTypeShow()); rent.add(rooms.get(3).getRent());
		pic.add(rooms.get(4).getPictures().toArray()[0]); address.add(rooms.get(4).getAddress()); type.add(rooms.get(4).getTypeShow()); rent.add(rooms.get(4).getRent());
		pic.add(rooms.get(5).getPictures().toArray()[0]); address.add(rooms.get(5).getAddress()); type.add(rooms.get(5).getTypeShow()); rent.add(rooms.get(5).getRent());
		pic.add(rooms.get(6).getPictures().toArray()[0]); address.add(rooms.get(6).getAddress()); type.add(rooms.get(6).getTypeShow()); rent.add(rooms.get(6).getRent());
		pic.add(rooms.get(7).getPictures().toArray()[0]); address.add(rooms.get(7).getAddress()); type.add(rooms.get(7).getTypeShow()); rent.add(rooms.get(7).getRent());
		r.addObj(pic); r.addObj(address); r.addObj(type); r.addObj(rent);
		return r;
	}
	
	/*
	 * 租户点击推荐跳转到房间页面
	 */
	@RequestMapping(value = "/roompage")
	@ResponseBody
	@CrossOrigin
	public Object renterHomePageJump(@RequestParam("pic") String pic,
			@RequestParam("user") String id) {
		System.out.println("Show room page.");
		Ret r = new Ret();
		r.setSuccess(true);
		String rid = pic.substring(0,7);
		Room rm = Room.findRoomById(rid);
		if(rm == null) {
			return null;
		}
		rm.setItem(rm.getItemShow());
		rm.setType(rm.getTypeShow());
		r.addObj(new RoomForRet(rm));
		Renter rt = Renter.findRenterById(id);
		if(rt!=null && rt.getRooms().contains(rid))
			r.setCollected(true);
		for(String s:rm.getPictures()) {
			r.addPic(s);
		}
		return r;
	}
	
	/*
	 * 租户搜索房间
	 */
	@RequestMapping(value = "/rentersearchroom")
	@ResponseBody
	@CrossOrigin
	public Object renterSearchRoom(@RequestParam("key") String key,
			@RequestParam("value1") String v1,
			@RequestParam("value2") String v2,
			@RequestParam("value3") String v3,
			@RequestParam("value4") String v4) {
		System.out.println("Show search room page.");
		RoomRet r = new RoomRet();
		String first="rent",forder="ASC",second="area",sorder="ASC";
		if(v2.equals("b")) {
			first = "rent";
			forder = "DESC";
		}
		else if(v2.equals("c")) {
			first = "rent";
			forder = "ASC";
		}
		if(v3.equals("5")) {
			second = "area";
			sorder = "DESC";
		}
		else if(v3.equals("6")) {
			second = "area";
			sorder = "ASC";
		}
		List<Room> l;
		if(key.equals(""))
			l = Room.findRoomInOrder(Room.findavailableRoom(),first, forder, second, sorder);
		else
			l = Room.findRoomInOrder(Room.findavailableRoomByAddress(key),first, forder, second, sorder);
		
		int b,h;
		if(v4.equals("A"))
			b=h=-1;
		else if(v4.equals("B")) {
			b=0;
			h=1000;
		}
		else if(v4.equals("C")) {
			b=1000;
			h=3000;
		}
		else if(v4.equals("D")) {
			b=3000;
			h=6000;
		}
		else{
			b=6000;
			h=-1;
		}
		String t;
		if(v1.equals("0"))
			t = "single";
		else if(v1.equals("1"))
			t = "double";
		else if(v1.equals("2"))
			t = "four";
		else
			t = "all";
		
		r.addRoom(l,b,h,t);
		r.setNum(r.getType().size());
		
		return r;
	}
	
	
	/*
	 * 查看个人信息
	 */
	@RequestMapping(value = "/showinfo")
	@ResponseBody
	@CrossOrigin
	public Object showInfo(@RequestParam("useremail") String email,
			@RequestParam("type") String type) {
		System.out.println("Show "+type+" info.");
		Object o;
		if(type.equals("renter")) {
			o = Renter.findRenterById(email);
		}
		else if(type.equals("service")){
			o = Service.findServiceById(email);
		}
		else {
			o = Repairman.findRepairmanById(email);
		}
		return o;
	}
	
	/*
	 * 修改个人信息
	 */
	@RequestMapping(value = "/changeinfo")
	@ResponseBody
	@CrossOrigin
	public Object changeInfo(@RequestParam("email") String email,
			@RequestParam("useremail") String useremail,
			@RequestParam("type") String type,
			@RequestParam("subidcode") String subidcode,
			@RequestParam("password1") String password1,
			@RequestParam("password2") String password2,
			@RequestParam("idcode") String idcode,
			@RequestParam("username") String username,
			@RequestParam("userphone") String userphone) {
		System.out.println("Change "+type+" info.");
		Ret r = new Ret();
		r.setSuccess(Execute.changeInfo(email,useremail,type,subidcode,idcode,password1,password2,username,userphone));
		if(!r.isSuccess()) {
			r.setMessage(message);
		}
		System.out.println(r.isSuccess());
		return r;
	}
	/*
	 * 租客收藏房间
	 */
	@RequestMapping(value = "/collectroom")
	@ResponseBody
	@CrossOrigin
	public Object collectRoom(@RequestParam("user") String u,
			@RequestParam("pic") String id) {
		System.out.println("Renter "+u+" collect room "+id+".");
		id = id.substring(0,7);
		Renter r = Renter.findRenterById(u);
		Return rt = new Return();
		if(r.getRooms().contains(id)) {
			rt.setSuccess(false);
			rt.setMessage("已收藏过此房间");
			System.out.println("false");
		}
		else {
			r.addNewRoom(id);
			rt.setSuccess(true);
			rt.setMessage("收藏成功");
			System.out.println("true");
		}
		return rt;
	}
	/*
	 * 展示租客收藏房间
	 */
	@RequestMapping(value = "/showcollection")
	@ResponseBody
	@CrossOrigin
	public Object showCollection(@RequestParam("user") String id) {
		System.out.println("Show renter collections. "+id);
		RoomRet r = new RoomRet();
		Renter rt = Renter.findRenterById(id);
		for(String s:rt.getRooms()) {
			if(!s.equals(""))
				r.addRoom(Room.findRoomById(s));
		}
		r.setNum(r.getType().size());
		return r;
	}
	/*
	 * 租客取消收藏房间
	 */
	@RequestMapping(value = "/deletecollection")
	@ResponseBody
	@CrossOrigin
	public Object showCollection(@RequestParam("user") String id,
			@RequestParam("roomid") String rid) {
		System.out.println("Delete renter collection.");
		rid = rid.substring(0,7);
		Renter rt = Renter.findRenterById(id);
		boolean b = rt.deleteRoom(rid);
		System.out.println(b);
		return b;
	}
	
	/*
	 * 租客下单
	 */
	@RequestMapping(value = "/pay")
	@ResponseBody
	@CrossOrigin
	public Object pay(@RequestParam("user") String id,
			@RequestParam("roomid") String rid,
			@RequestParam("type") String t) {
		System.out.println("Renter pay for room "+rid+"."); 
		String type,time = "0/0/0";
		switch(t) {
		case "0":
			time = "0/0/3";
			break;
		case "1":
			time = "0/0/7";
			break;
		case "2":
			time = "0/0/14";
			break;
		case "3":
			time = "0/0/21";
			break;
		case "4":
			time = "0/1/0";
			break;
		case "5":
			time = "0/2/0";
			break;
		case "6":
			time = "0/3/0";
			break;
		case "7":
			time = "0/4/0";
			break;
		case "8":
			time = "1/0/0";
			break;
		}
		if(Integer.valueOf(t) < 5) {
			type = "short";
		}
		else {
			type = "long";
		}
		rid = rid.substring(0,7);
		Renter rt = Renter.findRenterById(id);
		return rt.payForRoom(rid, type,time);
	}
	/*
	 * 租客的房间
	 */
	@RequestMapping(value = "/showrenterroom")
	@ResponseBody
	@CrossOrigin
	public Object showRenterRoom(@RequestParam("user") String id) {
		System.out.println("Show renter "+id+" room."); 
		OrdersRet ot = new OrdersRet();
		
		Renter re = Renter.findRenterById(id);
		ArrayList<Orders> rl = re.myOrders();
		ot.addAllOrders(rl);
		return ot;
	}
	/*
	 * 租客欠款
	 */
	@RequestMapping(value = "/getpay")
	@ResponseBody
	@CrossOrigin
	public Object getPay(@RequestParam("ordersid") String oid) {
		System.out.println("Get pay of orders "+oid);
		Orders o = Orders.findOrdersById(oid);
		if(o!=null)
			return o.dueRent();
		else {
			return "";
		}
	}
	/*
	 * 租客付清欠款
	 */
	@RequestMapping(value = "/payed")
	@ResponseBody
	@CrossOrigin
	public Object payed(@RequestParam("ordersid") String oid) {
		System.out.println("Pay orders "+oid);
		Orders o = Orders.findOrdersById(oid);
		return o.payed();
	}
	/*
	 * 租客删除订单
	 */
	@RequestMapping(value = "/deleterenterorder")
	@ResponseBody
	@CrossOrigin
	public Object deleteRenterOrder(@RequestParam("ordersid") String oid) {
		System.out.println("Delete order "+oid);
		Orders o = Orders.findOrdersById(oid);
		if(!o.getState().equals("accepted") ) {
			return Orders.deleteOrders(o);
		}
		return false;
	}
	/*
	 * 租客报修
	 */
	
	@RequestMapping(value = "/rentercomplaint", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Object renterComplaint(@RequestParam(value = "roomid") String rid,
			@RequestParam(value = "user") String id,
			@RequestParam(value = "content") String content,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "pictures") String[] pictures) {
		System.out.print("User " + id + " complaint " +type+ " "+rid);
		System.out.println(" pic:" + pictures.length);
		
		if(type.equals(""))
			return false;
		
		// 报修
		if(type.equals("room")) {
			WorkOrder wo = new WorkOrder(id,rid,content);
			String name = "";
			for(int i=0; i<pictures.length; i++) {
				if(i%2 == 0) { // 图片名
					name = "workorder" + wo.getId() + String.valueOf(i/2+1) + Execute.getFileForm(pictures[i]);
					System.out.println(name);
				}
				else{ // 图片内容
					if(Execute.writeFile(name,pictures[i]) == false) {
						System.out.println("false");
						return i/2+1;
					}
					wo.addPictures("static/pictures/" + name);
				}
			}
			WorkOrder.addNewWorkOrder(wo);
		}
		else { // 投诉 此时roomid存储客服的id'
			Service s = Service.findServiceById(Room.findRoomById(rid).getServiceId());
			Complaint c = new Complaint(id,s.getId(),content);
			String name = "";
			for(int i=0; i<pictures.length; i++) {
				if(i%2 == 0) { // 图片名
					name = "complaint" + c.getId() + String.valueOf(i/2+1) + Execute.getFileForm(pictures[i]);
					System.out.print(name+" ");
				}
				else{ // 图片内容
					if(Execute.writeFile(name,pictures[i]) == false) {
						System.out.println("false");
						return i/2+1;
					}
					c.addPictures("static/pictures/" + name);
				}
			}
			Complaint.addNewComplaint(c);
		}
		System.out.println("true");
		return true;
	}
	/*
	 * 租客租到的房间
	 */
	@RequestMapping(value = "/renterrentedroom")
	@ResponseBody
	@CrossOrigin
	public Object RenterRentedRoom(@RequestParam("user") String id) {
		System.out.println("Show renter "+id+" rented room."); 
		ComplaintRet rt = new ComplaintRet();
		Renter re = Renter.findRenterById(id);
		ArrayList<Orders> olist = re.myRentedOrders();
		for(Orders o:olist) {
			rt.addNewRoom(new RoomForRet(Room.findRoomById(o.getRoomId())));
		}
		rt.setSuccess(true);
		return rt;
	}
	/*
	 * 显示工单
	 */
	@RequestMapping(value = "/getmyworkorder")
	@ResponseBody
	@CrossOrigin
	public Object getMyWorkOrder(@RequestParam("user") String id) {		
		System.out.println("Get "+id+" workorder");
		Renter r = Renter.findRenterById(id);
		WorkOrderRet ret = new WorkOrderRet();
		if(r!=null) {
			ret.addWorkOrder(r.myWorkOrder());
			ret.setSuccess(true);
		}
		else {
			System.out.println("null");
		}
		return ret;
	}
	/*
	 * 租客评价工单
	 */
	@RequestMapping(value = "/returnworkorder")
	@ResponseBody
	@CrossOrigin
	public Object returnWorkOrder(@RequestParam("workorderId") String wid,
			@RequestParam("content") String content,
			@RequestParam("level") int level) {
		System.out.println("Set return "+wid+" workorder " + content);
		WorkOrder wo = WorkOrder.findWorkOrderById(wid);
		wo.setLevel(level);
		return wo.addReturnItem(content);
	}
	/*
	 * 显示投诉
	 */
	@RequestMapping(value = "/getmycomplaint")
	@ResponseBody
	@CrossOrigin
	public Object getMyComplaint(@RequestParam("user") String id) {		
		System.out.println("Get "+id+" complaint");
		Renter r = Renter.findRenterById(id);
		ComplaintRet ret = new ComplaintRet();
		if(r!=null) {
			ret.addComplaint(r.myComplaint());
			ret.setSuccess(true);
		}
		else {
			System.out.println("null");
		}
		return ret;
	}
	
	/*
	 * 租客续租
	 */
	@RequestMapping(value = "/renterrenew")
	@ResponseBody
	@CrossOrigin
	public Object renterRenew(@RequestParam("ordersid") String oid,
			@RequestParam("time") String time) {
		System.out.println("Renter renew "+oid);
		switch(time) {
		case "1个月":
			time = "0/1/0";
			break;
		case "2个月":
			time = "0/2/0";
			break;
		case "3个月":
			time = "0/3/0";
			break;
		case "4个月":
			time = "0/4/0";
			break;
		case "5个月":
			time = "0/5/0";
			break;
		case "6个月":
			time = "0/6/0";
			break;
		}
		Orders o = Orders.findOrdersById(oid);
		return o.renewThis(time);
	}
	/*
	 * 查看租客可续租房间
	 */
	class RetForRenewableRoom{
		private ArrayList<String> roomId = new ArrayList<>();
		private ArrayList<String> address = new ArrayList<>();
		private ArrayList<String> duetime = new ArrayList<>();
		private ArrayList<String> ordersId = new ArrayList<>();
		public void add(Orders o) {
			this.roomId.add(o.getRoomId());
			this.address.add(Room.findRoomById(o.getRoomId()).getAddress());
			this.duetime.add(DateModel.formatDate(DateModel.getDueDate(o)) );
			this.ordersId.add(o.getId());
		}
		public ArrayList<String> getRoomId() {
			return roomId;
		}
		public void setRoomId(ArrayList<String> roomId) {
			this.roomId = roomId;
		}
		public ArrayList<String> getAddress() {
			return address;
		}
		public void setAddress(ArrayList<String> address) {
			this.address = address;
		}
		public ArrayList<String> getDuetime() {
			return duetime;
		}
		public void setDuetime(ArrayList<String> duetime) {
			this.duetime = duetime;
		}
		public ArrayList<String> getOrdersId() {
			return ordersId;
		}
		public void setOrdersId(ArrayList<String> ordersId) {
			this.ordersId = ordersId;
		}
		
	}
	@RequestMapping(value = "/showrenewableroom")
	@ResponseBody
	@CrossOrigin
	public Object showRenewableRoom(@RequestParam("user") String id) {
		System.out.println("Show renewable room.");
		ArrayList<Orders> list = Orders.findRenewableOrders(id);
		RetForRenewableRoom ret = new RetForRenewableRoom();
		for(Orders o:list)
			ret.add(o);
		return ret;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 客服的操作
	 * SERVICE
	 * 
	 */
	/*
	 * 客服添加房间
	 */
	@RequestMapping(value = "/serviceaddroom")
	@ResponseBody
	@CrossOrigin
	public Object serviceAddRoom(@RequestParam("RoomType") String roomtype,
		@RequestParam("RoomAvailable") String roomavailable,
			@RequestParam("RoomRent") String roomrent,
			@RequestParam("OwnerName") String ownername,
			@RequestParam("OwnerPhone") String ownerphone,
			@RequestParam("RoomAddress") String roomaddress,
			@RequestParam("RoomArea") String roomarea,
			@RequestParam("RoomItem") String roomitem) {
		System.out.println("Add new room");
		Ret r=new Ret();
		String id = Room.getNextId();
		r.setSuccess(Room.addNewRoom(new Room(id,roomtype,roomavailable,roomrent,ownername,ownerphone,roomaddress,roomarea,roomitem)));
		return r;
	}
	
	
	/*
	 * 客服修改房间信息
	 */
	@RequestMapping(value = "/modifyroom")
	@ResponseBody
	@CrossOrigin
	public Object modifyRoom(@RequestParam("RoomId") String roomid,
			@RequestParam("RoomType") String roomtype,
			@RequestParam("RoomAvailable") String roomavailable,
				@RequestParam("RoomRent") String roomrent,
				@RequestParam("RoomAddress") String roomaddress,
				@RequestParam("RoomArea") String roomarea,
				@RequestParam("RoomItem") String roomitem) {
			System.out.println("mpdify room");
			Ret r=new Ret();
			Room rm=Room.findRoomById(roomid);
			r.setSuccess(Repository.getInstance().updateRoom(rm, "RoomType", roomtype));
			r.setSuccess(Repository.getInstance().updateRoom(rm, "RoomState", roomavailable));
			r.setSuccess(Repository.getInstance().updateRoom(rm, "RoomRent", roomrent));
			r.setSuccess(Repository.getInstance().updateRoom(rm, "RoomAddress", roomaddress));
			r.setSuccess(Repository.getInstance().updateRoom(rm, "RoomArea", roomarea));
			r.setSuccess(Repository.getInstance().updateRoom(rm, "RoomItem", roomitem));
			return r;
		}
	
	/*
	  * 客服添加修理工
	  */
	 @RequestMapping(value = "/addrepairman")
	 @ResponseBody
	 @CrossOrigin
	 public Object addRepairman(@RequestParam("UserName") String userAddress,
	   @RequestParam("Ps1") String ps1,
	   @RequestParam("Ps2") String ps2) {
	   System.out.println("add new repairman");
	   Ret r=new Ret();
	   if(ps1.equals(ps2)){
		   char ch[]=userAddress.toCharArray();
		   int i;
		   for( i=0;i<ch.length;i++) {
			   if(ch[i]=='@')
				   break;
		   }
		   if(i>=ch.length-1)   
			   r.setSuccess(false);
		   else
			   r.setSuccess(Repairman.addNewRepairman(new Repairman(userAddress,ps1,"")));
	   }
	   else
		   r.setSuccess(false);
	   return r;
	  }
	 
	 /*
	  * 客服删除修理工
	  */
	 @RequestMapping(value = "/deleterepairman")
	 @ResponseBody
	 @CrossOrigin
	 public Object deleterepairman(@RequestParam("UserId") String userId) {
	   System.out.println("delete repairman");
	   Ret r=new Ret();
	   r.setSuccess(Repairman.deleteRepairman(Repairman.findRepairmanById(userId)));
	   return r;
	  }

	 /*
	  * 客服添加租客
	  */
	 @RequestMapping(value = "/addrenter")
	 @ResponseBody
	 @CrossOrigin
	 public Object addRenter(@RequestParam("UserName") String userName,
	   @RequestParam("Ps1") String ps1,
	   @RequestParam("Ps2") String ps2) {
	   System.out.println("add new renter");
	   Ret r=new Ret();
	   if(ps1.equals(ps2)){
		   char ch[]=userName.toCharArray();
		   int i;
		   for( i=0;i<ch.length;i++) {
			   if(ch[i]=='@')
				   break;
		   }
		   if(i>=ch.length-1)   
			   r.setSuccess(false);
		   else
			   r.setSuccess(Renter.addNewRenter(new Renter(userName,ps1,"")));
	   }
	   else
		   r.setSuccess(false);
	   return r;
	  }
	 
	 /*
	  * 客服删除租客
	  */
	 @RequestMapping(value = "/deleterenter")
	 @ResponseBody
	 @CrossOrigin
	 public Object deleteRenter(@RequestParam("UserId") String userId) {
	   System.out.println("delete renter");
	   Ret r=new Ret();
	   r.setSuccess(Renter.deleteRenter(Renter.findRenterById(userId)));
	   return r;
	  }
	 
	 /*
	  * 客服修改师傅姓名
	  */
	 @RequestMapping(value = "/changerepairmanname")
	 @ResponseBody
	 @CrossOrigin
	 public Object changeRepairmanName(@RequestParam("UserId") String userId,
			 @RequestParam("UserName") String userName) {
	   System.out.println("change repairman name");
	   Ret r=new Ret();
	   Repairman re=Repairman.findRepairmanById(userId);
	   r.setSuccess(Repository.getInstance().updateRepairman(re, "RepairmanName", userName));
	   return r;
	  }
	 
	 /*
	  * 客服修改师傅性别
	  */
	 @RequestMapping(value = "/changerepairmansex")
	 @ResponseBody
	 @CrossOrigin
	 public Object changerepairmansex(@RequestParam("UserId") String userId,
			 @RequestParam("UserSex") String userSex) {
	   System.out.println("change repairman sex");
	   Ret r=new Ret();
	   Repairman re=Repairman.findRepairmanById(userId);
	   r.setSuccess(Repository.getInstance().updateRepairman(re, "RepairmanSex", userSex));
	   return r;
	  }
	 
	 /*
	  * 客服修改师傅电话
	  */
	 @RequestMapping(value = "/changerepairmanphone")
	 @ResponseBody
	 @CrossOrigin
	 public Object changerepairmanphone(@RequestParam("UserId") String userId,
			 @RequestParam("UserPhone") String userPhone) {
	   System.out.println("change repairman phone");
	   Ret r=new Ret();
	   Repairman re=Repairman.findRepairmanById(userId);
	   r.setSuccess(Repository.getInstance().updateRepairman(re, "RepairmanPhone", userPhone));
	   return r;
	 }
	 
	 /*
	  * 客服修改租客姓名
	  */
	 @RequestMapping(value = "/changerentername")
	 @ResponseBody
	 @CrossOrigin
	 public Object changeRenterName(@RequestParam("UserId") String userId,
			 @RequestParam("UserName") String userName) {
	   System.out.println("change renter name");
	   Ret r=new Ret();
	   Renter re=Renter.findRenterById(userId);
	   r.setSuccess(Repository.getInstance().updateRenter(re, "RenterName", userName));
	   return r;
	 }
	 
	 /*
	  * 客服修改租客昵称
	  */
	 @RequestMapping(value = "/changerenternickname")
	 @ResponseBody
	 @CrossOrigin
	 public Object changeRenterNickname(@RequestParam("UserId") String userId,
			 @RequestParam("UserNickname") String userNickname) {
	   System.out.println("change renter Nickname");
	   Ret r=new Ret();
	   Renter re=Renter.findRenterById(userId);
	   r.setSuccess(Repository.getInstance().updateRenter(re, "RenterNickName", userNickname));
	   return r;
	  }

	 /*
	  * 客服修改租客性别
	  */
	 @RequestMapping(value = "/changerentersex")
	 @ResponseBody
	 @CrossOrigin
	 public Object changeRenterSex(@RequestParam("UserId") String userId,
			 @RequestParam("UserSex") String userSex) {
	   System.out.println("change renter sex");
	   Ret r=new Ret();
	   Renter re=Renter.findRenterById(userId);
	   r.setSuccess(Repository.getInstance().updateRenter(re, "RenterSex", userSex));
	   return r;
	  }
	 
	 /*
	  * 客服修改租客电话
	  */
	 @RequestMapping(value = "/changerenterphone")
	 @ResponseBody
	 @CrossOrigin
	 public Object changeRenterPhone(@RequestParam("UserId") String userId,
			 @RequestParam("UserPhone") String userPhone) {
	   System.out.println("change renter sex");
	   Ret r=new Ret();
	   Renter re=Renter.findRenterById(userId);
	   r.setSuccess(Repository.getInstance().updateRenter(re, "RenterPhone", userPhone));
	   return r;
	  }
	 
	 /*
	  * 客服删除订单
	  */
	 @RequestMapping(value = "/deleteorder")
	 @ResponseBody
	 @CrossOrigin
	 public Object deleteOrder(@RequestParam("OrderId") String orderId) {
	   System.out.println("delete order");
	   Ret r=new Ret();
	   r.setSuccess(Orders.deleteOrders(Orders.findOrdersById(orderId)));
	   return r;
	  }
	
	 /*
	  * 客服给工单添加师傅
	  */
	 @RequestMapping(value = "/addworkorder")
	 @ResponseBody
	 @CrossOrigin
	 public Object addWorkOrder(@RequestParam("WorkOrderId") String workOrderId,
			 @RequestParam("ServiceId") String serviceId,
			 @RequestParam("RepairmanId") String repairmanId) {
	   System.out.println("add workorder"+serviceId);
	   Ret r=new Ret();
	   WorkOrder wo=WorkOrder.findWorkOrderById(workOrderId);
	   r.setSuccess(Repository.getInstance().updateWorkOrder(wo, "RepairmanId", repairmanId));
	   if(Repairman.findRepairmanById(repairmanId)!=null) {
		   r.setSuccess(Repository.getInstance().updateWorkOrder(wo, "ServiceId", serviceId));
	   }
	   return r;
	  }
	
	 /*
	  * 客服查看所有房间
	  */
	 @RequestMapping(value = "/serviceallroom")
	 @ResponseBody
	 @CrossOrigin
	 public Object serviceAllRoom() {
	  System.out.println("All room");
	  Ret r=new Ret();
	  ArrayList<Room> room=Room.findAllRoom();
	  for(int i=0;i<room.size();i++) {
	   r.addObj(new Sroom(room.get(i).getId(),room.get(i).getAddress(),room.get(i).getRent(),room.get(i).getItem(),room.get(i).getPictures().get(0)));
	   r.addPic(room.get(i).getPictures().get(0));
	  }
	  r.setSuccess(true);
	  return r;
	 }
	 class Sroom{
	  public Object id,title,value,label,avatar;
	  public Sroom(Object id,Object title,Object value,Object lable,Object avatar) {
	   this.id=id;
	   this.title=title;
	   this.value=value;
	   this.label=lable;
	   this.avatar="";
	  }
	 }
	 
	 /*
	  * 客服搜索所有房间
	  */
	 @RequestMapping(value = "/searchallroom")
	 @ResponseBody
	 @CrossOrigin
	 public Object searchAllRoom(@RequestParam("Key") String key) {
	  System.out.println("search all room");
	  Ret r=new Ret();
	  ArrayList<Room> room=Repository.getInstance().selectFromRoom("RoomAddress", key, "all");
	  for(int i=0;i<room.size();i++) {
	   r.addObj(new Sroom(room.get(i).getId(),room.get(i).getAddress(),room.get(i).getRent(),room.get(i).getItem(),room.get(i).getPictures().get(0)));
	   r.addPic(room.get(i).getPictures().get(0));
	  }
	  r.setSuccess(true);
	  return r;
	 }
	 
	 /*
	  * 客服查看可用房间
	  */
	 @RequestMapping(value = "/availableroom")
	 @ResponseBody
	 @CrossOrigin
	 public Object availableRoom() {
	  System.out.println("available room");
	  Ret r=new Ret();
	  ArrayList<Room> room=Room.findavailableRoom();
	  for(int i=0;i<room.size();i++) {
	   r.addObj(new Sroom(room.get(i).getId(),room.get(i).getAddress(),room.get(i).getRent(),room.get(i).getItem(),room.get(i).getPictures().get(0)));
	   r.addPic(room.get(i).getPictures().get(0));
	  }
	  r.setSuccess(true);
	  return r;
	 }
	 
	 /*
	  * 客服搜索可用房间
	  */
	 @RequestMapping(value = "/searchavailableroom")
	 @ResponseBody
	 @CrossOrigin
	 public Object searchAvailableRoom(@RequestParam("Key") String key) {
		  System.out.println("search available room");
		  Ret r=new Ret();
		  ArrayList<Room> room=Repository.getInstance().selectFromRoom("RoomAddress", key, "all");
		  for(int i=0;i<room.size();i++) {
			  if(room.get(i).getState().equals("available")) {
				  r.addObj(new Sroom(room.get(i).getId(),room.get(i).getAddress(),room.get(i).getRent(),room.get(i).getItem(),room.get(i).getPictures().get(0)));
				  r.addPic(room.get(i).getPictures().get(0));
			  }
		  }
		  r.setSuccess(true);
		  return r;
	 }
	 
	 /*
	  * 租客列表
	  */
	 @RequestMapping(value = "/renterlist")
	 @ResponseBody
	 @CrossOrigin
	 public Object renterList() {
	  System.out.println("show renter");
	  Ret r=new Ret();
	  ArrayList<Renter> renter=Renter.findAllRenter();
	  for(int i=0;i<renter.size();i++) {
	   r.addObj(new Srenter(renter.get(i).getId(),renter.get(i).getName(),renter.get(i).getPhone()));
	  }
	  r.setSuccess(true);
	  return r;
	 }
	 class Srenter {
		 public String id,title,label;
		 public Srenter(String id,String title,String label) {
			 this.id=id;
			 this.title=title;
			 this.label=label;
		 }
	 }
	 
	 /*
	  * 查找租客
	  */
	 @RequestMapping(value = "/searchrenter")
	 @ResponseBody
	 @CrossOrigin
	 public Object searchRenter(@RequestParam("Key") String key) {
	  System.out.println("search renter");
	  Ret r=new Ret();
	  ArrayList<Renter> renter=Repository.getInstance().selectFromRenter("RenterName", key, "all");
	  for(int i=0;i<renter.size();i++) {
	   r.addObj(new Srenter(renter.get(i).getId(),renter.get(i).getName(),renter.get(i).getPhone()));
	  }
	  r.setSuccess(true);
	  return r;
	 }
	 
	 /*
	  * 修理工列表
	  */
	 @RequestMapping(value = "/repairmanlist")
	 @ResponseBody
	 @CrossOrigin
	 public Object repairmanList() {
	  System.out.println("show repairman");
	  Ret r=new Ret();
	  ArrayList<Repairman> repairman=Repairman.findAllRepairman();
	  for(int i=0;i<repairman.size();i++) {
	   r.addObj(new Srenter(repairman.get(i).getId(),repairman.get(i).getName(),repairman.get(i).getPhone()));
	  }
	  r.setSuccess(true);
	  return r;
	 }
	 
	 /*
	  * 查找修理工
	  */
	 @RequestMapping(value = "/searchrepairman")
	 @ResponseBody
	 @CrossOrigin
	 public Object searchRepairman(@RequestParam("Key") String key) {
	  System.out.println("search repairman");
	  Ret r=new Ret();
	  ArrayList<Repairman> renter=Repository.getInstance().selectFromRepairman("RepairmanName", key, "all");
	  for(int i=0;i<renter.size();i++) {
	   r.addObj(new Srenter(renter.get(i).getId(),renter.get(i).getName(),renter.get(i).getPhone()));
	  }
	  r.setSuccess(true);
	  return r;
	 }
	 
	 /*
	  * 传递租客
	  */
	 @RequestMapping(value = "/rentinitial")
	 @ResponseBody
	 @CrossOrigin
	 public Object rentInitial(@RequestParam("UserId") String id) {
	  System.out.println("rent initial");
	  Ret r=new Ret();
	  Renter rt=Renter.findRenterById(id);
	  r.addObj(rt.getName());
	  r.addObj(rt.getPhone());
	  String sex="";
	  sex=rt.getSex();
	 if(sex==null) {
		 sex="男";
	 }
	 r.addObj(sex);
	 r.setSuccess(true);
	  return r;
	 }
	 
	 /*
	  * 传递师傅
	  */
	 @RequestMapping(value = "/repairmaninitial")
	 @ResponseBody
	 @CrossOrigin
	 public Object repairmanInitial(@RequestParam("UserId") String id) {
	  System.out.println("repairman initial");
	  Ret r=new Ret();
	  Repairman rt=Repairman.findRepairmanById(id);
	  r.addObj(rt.getName());
	  r.addObj(rt.getPhone());
	  String sex="";
	  sex=rt.getSex();
	 if(sex==null) {
		 sex="男";
	 }
	 r.addObj(sex);
	 r.setSuccess(true);
	  return r;
	 }
	 
	 /*
	  * 传递工单
	  */
	 @RequestMapping(value = "/workorderinitial")
	 @ResponseBody
	 @CrossOrigin
	 public Object workOrderInitial(@RequestParam("WorkOrderId") String id) {
	  System.out.println("workorder initial");
	  Ret r=new Ret();
	  WorkOrder rt=WorkOrder.findWorkOrderById(id);
	  r.addObj(rt.getRoomId());
	  r.addObj(rt.getItem());
	  if(rt.getServiceId()!=null)
		  r.addObj(rt.getServiceId());
	  else
		  r.addObj("");
	  if(rt.getRepairmanId()!=null)
		  r.addObj(rt.getRepairmanId());
	  else
		  r.addObj("");
	  if(rt.getPictures().size()>0)
		  r.addObj(rt.getPictures().get(0));
	 r.setSuccess(true);
	  return r;
	 }
	 
	 /*
	  * 工单列表
	  */
	 @RequestMapping(value = "/workorderlist")
	 @ResponseBody
	 @CrossOrigin
	 public Object workOrderList() {
	  System.out.println("show workorder");
	  Ret r=new Ret();
	  ArrayList<WorkOrder> wo=WorkOrder.findAllWorkOrder();
	  for(int i=0;i<wo.size();i++) {
	   r.addObj(new Swo(wo.get(i).getId(),wo.get(i).getRoomId(),wo.get(i).getRepairmanId(),wo.get(i).getServiceId(),wo.get(i).getItem(),wo.get(i).getState()));
	  }
	  r.setSuccess(true);
	  return r;
	 }
	 class Swo{
		 public String WorkOrderId,RoomId,RepairmanId,ServiceId,WorkOrderItem,replied;
		 public Swo(String wid,String rid,String reid,String sid,String item,String reply) {
			 this.RepairmanId=reid;
			 this.WorkOrderId=wid;
			 this.RoomId=rid;
			 this.ServiceId=sid;
			 this.WorkOrderItem=item;
			 if(reply.equals("waiting"))
				 this.replied="false";
			 else
				 this.replied="true";
		 }
	 }
	 /*
	  * 查找工单
	  */
	 @RequestMapping(value = "/searchworkorder")
	 @ResponseBody
	 @CrossOrigin
	 public Object searchWorkOrder(@RequestParam("Key") String key) {
	  System.out.println("search workorder");
	  Ret r=new Ret();
	  ArrayList<WorkOrder> wo=Repository.getInstance().selectFromWorkOrder("RepairmanId", key, "all");
	  for(int i=0;i<wo.size();i++) {
		   r.addObj(new Swo(wo.get(i).getId(),wo.get(i).getRoomId(),wo.get(i).getRepairmanId(),wo.get(i).getServiceId(),wo.get(i).getItem(),wo.get(i).getState()));
	}
	  r.setSuccess(true);
	  return r;
	 }
	 
	 /*
	  * 订单列表
	  */
	 @RequestMapping(value = "/orderslist")
	 @ResponseBody
	 @CrossOrigin
	 public Object orderList() {
	  System.out.println("show order");
	  Ret r=new Ret();
	  ArrayList<Orders> wo=Orders.findAllOrders();
	  for(int i=0;i<wo.size();i++) {
	   r.addObj(new So(wo.get(i).getId(),wo.get(i).getRoomId(),wo.get(i).getState()));
	  }
	  r.setSuccess(true);
	  return r;
	 }
	 class So{
		 public String title,value,viewed;
		 public So(String title,String value,String viewed) {
			this.title=title;
			this.value=value;
			this.viewed=viewed;
		 }
	 }
	 
	 /*
	  * 传递订单
	  */
	 @RequestMapping(value = "/orderinitial")
	 @ResponseBody
	 @CrossOrigin
	 public Object orderInitial(@RequestParam("OrderId") String id) {
	  System.out.println("order initial");
	  Ret r=new Ret();
	  Orders rs=Orders.findOrdersById(id);
	  r.addObj(rs.getRoomId());
	  r.addObj(rs.getRenterId());
	  String s=rs.getType();
	  if(s.equals("long"))
		  r.addObj("长租");
	  else
		  r.addObj("短租");
	  String t=rs.getState();
	  if(t.equals("waiting"))
		  r.addObj("未审核");
	  else if(t.equals("accepted"))
		  r.addObj("已通过");
	  else if(t.equals("renew"))
		  r.addObj("未审核");
	  else
		  r.addObj("未通过");
	  
	  if(t.equals("renew"))
		  r.addObj("申请续租到期："+DateModel.formatDate(DateModel.addDate(DateModel.getDueDate(rs), rs.getItem())));
	  else
		  r.addObj(rs.getItem());
	  r.setSuccess(true);
	  return r;
	 }
	 
	 /*
	  * 审核订单通过
	  */
	 @RequestMapping(value = "/acceptorder")
	 @ResponseBody
	 @CrossOrigin
	 public Object acceptOrder(@RequestParam("OrderId") String id,
			 @RequestParam("ServiceId") String sid) {
		 System.out.println("accpet order");
		 Ret r=new Ret();
		 Orders o=Orders.findOrdersById(id);
		 Repository.getInstance().updateOrders(o, "ServiceId", sid);
		 if(o.getState().equals("waiting")) {
			 o.acceptThis();
			 r.setSuccess(true);
		 }
		 else {
			 o.acceptRenew();
			 r.setSuccess(true);
		 }
		 return r;
	 }
	 
	 /*
	  * 审核订单不通过
	  */
	 @RequestMapping(value = "/rejectorder")
	 @ResponseBody
	 @CrossOrigin
	 public Object rejecOrder(@RequestParam("OrderId") String id,
			 @RequestParam("ServiceId") String sid) {
		 System.out.println("accpet order");
		  Ret r=new Ret();
		  Orders o=Orders.findOrdersById(id);
		  Repository.getInstance().updateOrders(o, "ServiceId", sid);
		  if(o.getState().equals("waiting")) {
			  o.rejectThis();
			  r.setSuccess(true);
		  }
		  else {
			  o.rejectRenew();
			  r.setSuccess(true);
		  }
		  return r;
	 }
	 
	 /*
	  * 生成合同
	  */
	 @RequestMapping(value = "/contract")
	 @ResponseBody
	 @CrossOrigin
	 public Object contract(@RequestParam("ContractId") String id) {
		 System.out.println("contract");
		  Ret r=new Ret();
		  Orders o=Orders.findOrdersById(id);
		  r.addObj(o.getRenterId());
		  r.addObj(o.getRoomId());
		  String s=o.getType();
		  if(s.equals("long"))
			  r.addObj("长租");
		  else
			  r.addObj("短租");
		  String date=o.getDate();
		  r.addObj(date.substring(0,10));
		  r.addObj(o.getItem());
		  r.setSuccess(true);
		  return r;
	 }
	 
	 /*
	  * 客服主页
	  */
	 @RequestMapping(value = "/servicehome")
	 @ResponseBody
	 @CrossOrigin
	 public Object serviceHome(@RequestParam("ServiceId") String id) {
		 System.out.println("service home "+id);
		  Ret r=new Ret();
		  Service s=Service.findServiceById(id);
		  r.addObj(s.getName());
		  r.addObj(s.getSex());
		  r.addObj(s.getPhone());
		  r.setSuccess(true);
		  return r;
	 }
	 
	 /*
	  * 投诉列表
	  */
	 @RequestMapping(value = "/clist")
	 @ResponseBody
	 @CrossOrigin
	 public Object cList() {
	  System.out.println("show complait");
	  Ret r=new Ret();
	  ArrayList<Complaint> c=Complaint.findAllComplaint();
	  System.out.println(c.size());
	  for(int i=0;i<c.size();i++) {
	   r.addObj(new Cm(c.get(i).getId(),c.get(i).getRenterId(),c.get(i).getRenterItem(),c.get(i).getState()));
	  }
	  r.setSuccess(true);
	  return r;
	 }
	 class Cm{
		 public String ComplaintID,ComplaintUser,ComplaintItem,replied;
		 public Cm(String cid,String cuser,String citem,String replied) {
			this.ComplaintID=cid;
			this.ComplaintItem=citem;
			this.ComplaintUser=cuser;
			if(replied.equals("finished"))
				this.replied="true";
			else
				this.replied="false";
		 }
	 }
	 
	 /*
	  * 查找工单
	  */
	 @RequestMapping(value = "/csearch")
	 @ResponseBody
	 @CrossOrigin
	 public Object cSearch(@RequestParam("Key") String key) {
	  System.out.println("search complaint");
	  Ret r=new Ret();
	  ArrayList<Complaint> c=Repository.getInstance().selectFromComplaint("RenterId", key, "all");
	  for(int i=0;i<c.size();i++) {
		  r.addObj(new Cm(c.get(i).getId(),c.get(i).getRenterId(),c.get(i).getRenterItem(),c.get(i).getState()));
	}
	  r.setSuccess(true);
	  return r;
	 }
	 /*
	  * 传递工单
	  */
	 @RequestMapping(value = "/cinitial")
	 @ResponseBody
	 @CrossOrigin
	 public Object cInitial(@RequestParam("CId") String id) {
	  System.out.println("complaint initial");
	  Ret r=new Ret();
	  Complaint c=Complaint.findComplaintById(id);
	  r.addObj(c.getItem());
	  for(int i=0;i<c.getPictures().size();i++) {
		  r.addPic(c.getPictures().get(i));
	  }
	  r.setSuccess(true);
	  return r;
	 }
	 
	 /*
	  * 回复工单
	  */
	 @RequestMapping(value = "/creply")
	 @ResponseBody
	 @CrossOrigin
	 public Object cReply(@RequestParam("CId") String id,
			 @RequestParam("Reply") String reply,
			 @RequestParam("Sid") String sid) {
	  System.out.println("reply complaint");
	  Ret r=new Ret();
	  Complaint c=Complaint.findComplaintById(id);
	  String re=c.getRenterItem()+"-"+reply;
	  Repository.getInstance().updateComplaint(c, "ComplaintState", "finished");
	  Repository.getInstance().updateComplaint(c, "ComplaintItem",re);
	  Repository.getInstance().updateComplaint(c, "ServiceId", sid);
	  r.setSuccess(true);
	  return r;
	 }
	 
	 /*
	  * 传递房间
	  */
	 @RequestMapping(value = "/roominitial")
	 @ResponseBody
	 @CrossOrigin
	 public Object roomInitial(@RequestParam("RoomId") String id) {
	  System.out.println("room initial");
	  Ret r=new Ret();
	  Room c=Room.findRoomById(id);
	  r.addObj(c.getType());
	  r.addObj(c.getArea());
	  r.addObj(c.getRent());
	  r.addObj(c.getServiceId());
	  r.addObj(c.getAddress());
	  r.addObj(c.getState());
	  r.addObj(c.getItem());
	  for(int i=0;i<c.getPictures().size();i++) {
		  r.addPic(c.getPictures().get(i));
	  }
	  r.setSuccess(true);
	  return r;
	 }
	 /*
	  * 师傅回复工单列表
	  */
	 @RequestMapping(value = "/rmreplylist")
	 @ResponseBody
	 @CrossOrigin
	 public Object rmReplyList(@RequestParam("Rid") String id) {
	  System.out.println("show rm reply workorder list");
	  Ret r=new Ret();
	  ArrayList<WorkOrder> w=WorkOrder.findWorkOrderByRepairmanId(id);
	  for(int i=0;i<w.size();i++) {
	   r.addObj(new rmReply(w.get(i).getId(),w.get(i).getRenterId(),w.get(i).getItem(),w.get(i).getRepairmanItem()));
	  }
	  r.setSuccess(true);
	  return r;
	 }
	 class rmReply{
		 public String ReportID,ReportUser,ReportItem,replied;
		 public rmReply(String rid,String ruser,String ritem,String replied) {
			this.ReportID=rid;
			this.ReportUser=ruser;
			this.ReportItem=ritem;
			if(replied.equals(""))
				this.replied="false";
			else
				this.replied="true";
		 }
	 }
	 
	 /*
	  * 师傅回复工单
	  */
	 @RequestMapping(value = "/rmreplywo")
	 @ResponseBody
	 @CrossOrigin
	 public Object rmReplyWo(@RequestParam("CId") String id,
			 @RequestParam("Reply") String reply) {
	  System.out.println("rm reply wo");
	  Ret r=new Ret();
	  WorkOrder w=WorkOrder.findWorkOrderById(id);
	  r.setSuccess(w.addRepairmanItem(reply));
	  return r;
	 }
	 /*
	  * 师傅查看工单列表
	  */
	 @RequestMapping(value = "/rmlist")
	 @ResponseBody
	 @CrossOrigin
	 public Object rmList(@RequestParam("Rid") String id) {
	  System.out.println("show rm workorder list");
	  Ret r=new Ret();
	  ArrayList<WorkOrder> w=WorkOrder.findWorkOrderByRepairmanId(id);
	  for(int i=0;i<w.size();i++) {
	   r.addObj(new rm(w.get(i).getId(),w.get(i).getRenterId(),w.get(i).getItem(),w.get(i).getState()));
	  }
	  r.setSuccess(true);
	  return r;
	 }
	 class rm{
		 public String Repair_jobID,Repair_jobUser,Repair_jobItem,replied;
		 public rm(String rid,String ruser,String ritem,String replied) {
			this.Repair_jobID=rid;
			this.Repair_jobUser=ruser;
			this.Repair_jobItem=ritem;
			if(replied.equals("waiting"))
				this.replied="false";
			else
				this.replied="true";
		 }
	 }
	 /*
	  * 师傅做工单
	  */
	 @RequestMapping(value = "/rmdowo")
	 @ResponseBody
	 @CrossOrigin
	 public Object rmDoWo(@RequestParam("CId") String id,
			 @RequestParam("Reply") String reply) {
	  System.out.println("rm do wo");
	  Ret r=new Ret();
	  WorkOrder w=WorkOrder.findWorkOrderById(id);
	  Repository.getInstance().updateWorkOrder(w, "WorkOrderState", "finished");
	  r.setSuccess(true);
	  return r;
	 }
	 /*
	  * 师傅主页信息
	  */
	 @RequestMapping(value = "/showrepairmaninfo")
	 @ResponseBody
	 @CrossOrigin
	 public Object showRepairmanInfo(@RequestParam("rapairId") String id) {
		 System.out.println("Show repairman "+id+" info.");
		 return Repairman.findRepairmanById(id);
	 }
}
