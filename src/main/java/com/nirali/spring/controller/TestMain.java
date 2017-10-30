package com.nirali.spring.controller;

import com.google.gson.Gson;
import com.nirali.spring.pojo.ShiftTracker;

public class TestMain {

	public static void main(String[] args) {
		Gson gson = new Gson();
		ShiftTracker st = new ShiftTracker();
		st.setAcknowledged(true);
		
		System.out.println(gson.toJson(st));

	}

}
