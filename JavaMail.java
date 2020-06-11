package com.example.rentproject.model;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;


public class JavaMail {
	public static String sendMail(String email,String type,String content) throws Exception {
		String code = getCode();
		// 收件人电子邮箱
		String to = email;

		// 发件人电子邮箱
		String from = "3343262025@qq.com";

		// 指定发送邮件的主机为 smtp.qq.com
		String host = "smtp.qq.com";  //QQ 邮件服务器

		// 获取系统属性
		Properties properties = System.getProperties();
		
		// 设置邮件服务器
		properties.setProperty("mail.smtp.host", host);

		properties.put("mail.smtp.auth", "true");
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", sf);
		// 获取默认session对象
		Session session = Session.getDefaultInstance(properties,new Authenticator(){
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("3343262025@qq.com", "uaidcxmobrvochdi"); //发件人邮件用户名、密码
			}
		});

		try{
			// 创建默认的 MimeMessage 对象
			MimeMessage message = new MimeMessage(session);
			// Set From: 头部头字段
			message.setFrom(new InternetAddress(from));
			// Set To: 头部头字段
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// 设置消息体
			if(type.equals("idcode")) {
				message.setSubject("【顷刻】邮箱验证码");
				message.setText("【顷刻】 尊敬的用户，您好！\n您正在使用邮箱验证码功能，您的验证码是："+code+
					"，请在1分钟内按照页面提示输入验证码以完成验证，切勿将验证码泄露于他人。");
			}
			else if(type.equals("rent")) {
				message.setSubject("【顷刻】系统提醒");
				message.setText(content);
			}
			// 发送消息
			Transport.send(message);
			
			System.out.println("Sent email successfully");
		}catch (MessagingException mex) {
			// mex.printStackTrace();
			System.out.println("Email error");
		}

		return code;
	}

	public static String getCode() {
		Random r = new Random();
		String s = "";
		for(int i=0; i<6; i++) {
			s = s + String.valueOf(r.nextInt(10));
		}
		return s;
	}
}
