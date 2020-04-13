package com.baizhi.wyj.util;

import java.util.UUID;

public class UUIDUtil {

	public static String getUUID(){
		String id = UUID.randomUUID().toString();
		String uid = id.replaceAll("-", "");
		
		return uid;
	}
	
	//测试
	public static void main(String[] args) {
		
		String uid = UUIDUtil.getUUID();
		System.out.println("===="+uid);
	}
}
