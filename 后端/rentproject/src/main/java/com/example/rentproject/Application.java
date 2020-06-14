package com.example.rentproject;

import org.quartz.SchedulerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.example.rentproject.service.JobScheduler;

// 主程序
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	public static void main(String[] args) {
		try {
			JobScheduler.schedulerJob();
		} catch (SchedulerException e) {
			//e.printStackTrace();
		}
		SpringApplication.run(Application.class, args);
	}
	
	@Override//为了打包springboot项目
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
