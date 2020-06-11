package com.example.rentproject.service;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import org.apache.tomcat.util.codec.binary.Base64;

import com.example.rentproject.controller.Controller;
import com.example.rentproject.model.DateModel;
import com.example.rentproject.model.JavaMail;
import com.example.rentproject.model.Orders;
import com.example.rentproject.model.Person;
import com.example.rentproject.model.Renter;
import com.example.rentproject.model.Repairman;
import com.example.rentproject.model.Room;
import com.example.rentproject.model.Service;

public class Execute {
	public static String path = "./src/main/resources/static/static/pictures/";
	
	/*
	 * 用户注册功能
	 * 判断数据是否合法
	 * type:
	 * renter		租客
	 * service		客服
	 * repairman	师傅
	 * 
	 */
	public static boolean addPerson(String type,String id,String pwd1,String pwd2,String name,String phone,String idcode,String subcode) {
		/*
		 * 检查id
		 */
		if( type.equals("renter") && !Renter.checkId(id) ) {
			Controller.message = "email错误或已注册";
			return false;
		}
		else if( type.equals("service") && !Service.checkId(id) ) {
			Controller.message = "email错误或已注册";
			return false;
		}
		else if( type.equals("repairman") && !Repairman.checkId(id) ) {
			Controller.message = "email错误或已注册";
			return false;
		}
		/*
		 * 检查密码相同
		 */
		else if( !pwd1.equals(pwd2) ) {
			Controller.message = "两次密码不一样";
			return false;
		}
		/*
		 * 检查密码
		 */
		else if( type.equals("renter") && !Renter.checkPwd(pwd1) ) {
			Controller.message = "密码不合法，需要大于等于6位小于50个字符";
			return false;
		}
		else if( type.equals("service") && !Service.checkPwd(pwd1) ) {
			Controller.message = "密码不合法，需要大于等于6位小于50个字符";
			return false;
		}
		else if( type.equals("repairman") && !Repairman.checkPwd(pwd1) ) {
			Controller.message = "密码不合法，需要大于等于6位小于50个字符";
			return false;
		}
		/*
		 * 检查姓名
		 */
		else if( !Person.checkName(name) ) {
			Controller.message = "姓名不合法，需要小于50个字符";
			return false;
		}
		/*
		 * 检查电话
		 */
		else if( type.equals("renter") && !Renter.checkPhone(phone) ) {
			Controller.message = "电话号码不合法或已注册";
			return false;
		}
		else if( type.equals("service") && !Service.checkPhone(phone) ) {
			Controller.message = "电话号码不合法或已注册";
			return false;
		}
		else if( type.equals("repairman") && !Repairman.checkPhone(phone) ) {
			Controller.message = "电话号码不合法或已注册";
			return false;
		}
		/*
		 * 检查验证码
		 */
		else if(!subcode.equals(idcode)) {
			Controller.message = "验证码不正确";
			return false;
		}
		else {
			if( type.equals("renter") )
				Renter.addNewRenter(new Renter(id,pwd1,name,phone));
			else if( type.equals("service") )
				Service.addNewService(new Service(id,pwd1,name,phone));
			else
				Repairman.addNewRepairman(new Repairman(id,pwd1,name,phone));
			return true;
		}
	}
	
	
	
	/*
	 * 租客登录
	 * 
	 */
	
	public static Object getPerson(String ptype,String id,String pwd) {
		if(ptype.equals("renter")) {
			Renter r = Renter.findRenterById(id);
			if(r == null) {
				Controller.message = "用户不存在";
				return null;
			}
			else if( pwd.equals(r.getPwd()) ) {
				return r;
			}
			else {
				Controller.message = "密码不正确";
				return null;
			}
		}
		else if(ptype.equals("service")) {
			Service r = Service.findServiceById(id);
			if(r == null) {
				Controller.message = "用户不存在";
				return null;
			}
			else if( pwd.equals(r.getPwd()) ) {
				return r;
			}
			else {
				Controller.message = "密码不正确";
				return null;
			}
		}
		else {
			Repairman r = Repairman.findRepairmanById(id);
			if(r == null) {
				Controller.message = "用户不存在";
				return null;
			}
			else if( pwd.equals(r.getPwd()) ) {
				return r;
			}
			else {
				Controller.message = "密码不正确";
				return null;
			}
		}
	}
	
	
	/*
	 * 
	 * 
	 * 修改个人信息功能
	 * 
	 */
	public static boolean changeInfo(String email,String useremail,String type,String subidcode,String idcode,
			String password1,String password2,String username,String userphone) {
		if(type.equals("renter")) {
			Renter r = Renter.findRenterById(email);
			if(r==null)
				return false;
			
			if(!Renter.checkNewId(useremail)) {
				Controller.message = "邮箱不合法或已被注册";
			}
			else if(!password1.equals(password2)) {
				Controller.message = "两次密码不相同";
			}
			else if(!Person.checkPwd(password1)) {
				Controller.message = "密码不合法";
			}
			else if(!Person.checkName(username)) {
				Controller.message = "姓名不合法";
			}
			else if(!Renter.checkNewPhone(userphone)) {
				Controller.message = "电话号码不合法或已被注册";
			}
			else if(!subidcode.equals(idcode)) {
				Controller.message = "验证码不正确";
			}
			else {
				Renter rt = new Renter(email,password1,username,userphone);
				return r.updateRenterInfo(rt);
			}
			return false;
		}
		else if(type.equals("service")) {
			Service r = Service.findServiceById(email);
			if(r==null)
				return false;
			if(!Service.checkId(useremail)) {
				Controller.message = "邮箱不合法或已被注册";
			}
			else if(!password1.equals(password2)) {
				Controller.message = "两次密码不相同";
			}
			else if(!Person.checkPwd(password1)) {
				Controller.message = "密码不合法";
			}
			else if(!Person.checkName(username)) {
				Controller.message = "姓名不合法";
			}
			else if(!Service.checkPhone(userphone)) {
				Controller.message = "电话号码不合法或已被注册";
			}
			else if(!subidcode.equals(idcode)) {
				Controller.message = "验证码不正确";
			}
			else {
				Service rt = new Service(email,password1,username,userphone);
				return r.updateServiceInfo(rt);
			}
			return false;
		}
		else {
			Repairman r = Repairman.findRepairmanById(email);
			if(r==null)
				return false;
			if(!Repairman.checkId(useremail)) {
				Controller.message = "邮箱不合法或已被注册";
			}
			else if(!password1.equals(password2)) {
				Controller.message = "两次密码不相同";
			}
			else if(!Person.checkPwd(password1)) {
				Controller.message = "密码不合法";
			}
			else if(!Person.checkName(username)) {
				Controller.message = "姓名不合法";
			}
			else if(!Repairman.checkPhone(userphone)) {
				Controller.message = "电话号码不合法或已被注册";
			}
			else if(!subidcode.equals(idcode)) {
				Controller.message = "验证码不正确";
			}
			else {
				Repairman rt = new Repairman(email,password1,username,userphone);
				return r.updateRepairmanInfo(rt);
			}
			return false;
		}
	}
	
	/*
	 * 更新租客的订单状态
	 */
	public static void updateRenterOrders() {
		ArrayList<Renter> rlist = Renter.findAllRenter();
		for(Renter r:rlist) {
			ArrayList<Orders> olist = r.myOrders();
			for(Orders o:olist) {
				if(DateModel.compare(null,DateModel.getDueDate(o)) < 0) {
					o.setState("finished");
					Room.setRoomState(o.getId(),"available");
					o.updateThisState();
				}
			}
		}
	}
	
	/*
	 * 每月最后一星期发邮件提醒
	 */
	public static void sendEmailToRenter() {
		ArrayList<Orders> olist = Orders.findOngoingOrders();
		for(Orders o:olist) {
			if(!o.isPay()){
				try {
					Room r = Room.findRoomById(o.getRoomId());
					JavaMail.sendMail(o.getRenterId(), "rent", "【顷刻】 尊敬的用户，您好！\n您租的位于"+r.getAddress()
					+"的房间"+r.getId()+"需要在月底之前缴租费，请登录系统查看房间租金，请按时缴纳租金。");
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * 文件操作
	 */
	public static boolean writeFile(String filename,String base64) {
		try {
			String filePath = Execute.path + filename;
			Files.write(Paths.get(filePath),Base64.decodeBase64(base64), StandardOpenOption.CREATE);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/*
	 * 获取文件格式 图片
	 */
	public static String getFileForm(String file) {
		try {
			String t = file.substring(file.indexOf("/")+1,file.indexOf(";"));
			if(t.equals(""))
				t = "jpg";
			return "." + t;
		}catch(Exception e) {
			return "";
		}
	}
	/*
	 * 获取文件内容 图片
	 */
	public static String getFileContent(String file) {
		try {
			return file.substring(file.indexOf(",")+1);
		}catch(Exception e) {
			return "";
		}
	}
	
	/*
	 * 转换为byte
	 */
	public static byte[] fileToByte(File file) {
		byte[] data = null;
		try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
 
            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
 
            data = baos.toByteArray();
 
            fis.close();
            baos.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return data;
	}
	
}
