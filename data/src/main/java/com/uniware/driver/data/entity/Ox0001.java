package com.uniware.driver.data.entity;

import com.uniware.util.Tools;
import io.sting.buffer.StingBuffer;

/**
 * 终端通用应答
 */
public class Ox0001 extends JT905MessageBody {
	/**
	 * 应答消息流水号
	 */
	private short responseMessageSerialNo;

	public final short getResponseMessageSerialNo() {
		return responseMessageSerialNo;
	}

	public final void setResponseMessageSerialNo(short value) {
		responseMessageSerialNo = value;
	}

	/**
	 * 应答消息ID
	 */
	private short responseMessageId;

	public final short getResponseMessageId() {
		return responseMessageId;
	}

	public final void setResponseMessageId(short value) {
		responseMessageId = value;
	}

	/**
	 * 应答结果，0：成功/确认；1：失败；2：消息有误；3：不支持
	 */
	private byte responseResult;

	public final byte getResponseResult() {
		return responseResult;
	}

	public final void setResponseResult(byte value) {
		responseResult = value;
	}

	public final byte[] WriteToBytes() {
		StingBuffer buff = new StingBuffer();
		try {
			buff.putShort(getResponseMessageSerialNo());
			buff.putShort(getResponseMessageId());
			buff.putByte(getResponseResult());
		} finally {

		}
		return buff.array();

	}

	public final void ReadFromBytes(byte[] messageBodyBytes) {
		StingBuffer buff = new StingBuffer(messageBodyBytes);

		setResponseMessageSerialNo(buff.getShort());
		setResponseMessageId(buff.getShort());
		setResponseResult(buff.getByte());
	}

	@Override
	public String toString() {
		return "应答流水号：" + Tools.ToHexString((short) responseMessageSerialNo)
				+ " 应答ID：" + Tools.ToHexString((short) responseMessageId)
				+ " 应答结果：" + responseResult;
	}
}