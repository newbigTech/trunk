package com.uniware.driver.data.entity;

import com.uniware.util.Converter;
import com.uniware.util.Tools;
import io.sting.buffer.StingBuffer;

/**
 * 平台通用应答
 */
public class Ox8001 extends JT905MessageBody {

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
	 * 应答结果，0：成功/确认；1：失败；2：消息有误；3：不支持；4：报警处理确认；
	 */
	private byte responseResult;

	public final byte getResponseResult() {
		return responseResult;
	}

	public final void setResponseResult(byte value) {
		responseResult = value;
	}

	@Override
	public String toString() {
		return "应答流水号：" + Tools.ToHexString((short) responseMessageSerialNo)
				+ " 应答ID：" + Tools.ToHexString((short) responseMessageId)
				+ " 应答结果：" + responseResult;
	}

	public final byte[] WriteToBytes() {
		StingBuffer buff = new StingBuffer();

		buff.putShort(getResponseMessageSerialNo());
		buff.putShort(getResponseMessageId());
		buff.putByte(getResponseResult());

		return buff.array();

	}

	public final void ReadFromBytes(byte[] messageBodyBytes) {

		setResponseMessageSerialNo(Converter.toInt16(messageBodyBytes, 0));
		setResponseMessageId(Converter.toInt16(messageBodyBytes, 2));
		setResponseResult(messageBodyBytes[4]);
	}
}