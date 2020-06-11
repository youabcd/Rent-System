package com.example.rentproject.model;

import java.text.SimpleDateFormat;
import java.util.*;

import com.example.rentproject.controller.Controller;
public class ModelTest {
	public static void main(String[] args) {

		System.out.println(DateModel.getDueDate(Orders.findOrdersById("0001")));
	}
}
