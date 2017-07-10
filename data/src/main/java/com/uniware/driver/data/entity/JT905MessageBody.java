package com.uniware.driver.data.entity;

public abstract class JT905MessageBody{

	short getId() {
		String type = getClass().getSimpleName().split("x")[1];
		int i = Integer.parseInt(type, 16);
		return (short)i;
	}
	
	public abstract byte[] WriteToBytes();

	public abstract void ReadFromBytes(byte[] messageBodyBytes);
}