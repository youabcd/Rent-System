package com.example.rentproject.service;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class updateInfo implements Job {
	public void execute(JobExecutionContext con) throws JobExecutionException {
		System.out.println("Update all info......");
		Execute.updateRenterOrders();
		//Execute.sendEmailToRenter();
		System.out.println("Finished.");
	}
}
