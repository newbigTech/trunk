package com.uniware.driver.data.entity;

import io.sting.buffer.StingBuffer;

/**
 * @Description: 驾驶员抢答
 * @author Comsys-hf
 * @date 2015年6月18日 下午1:31:59
 *
 */
public class Ox0B01 extends JT905MessageBody {

	/**
	 * 业务 ID
	 */
	private int bizId;

	@Override
	public byte[] WriteToBytes() {

		StingBuffer buff = new StingBuffer();

		buff.putInt(getBizId());

		return buff.array();
	}

	@Override
	public void ReadFromBytes(byte[] messageBodyBytes) {
		StingBuffer buff = new StingBuffer(messageBodyBytes);

		setBizId(buff.getInt());

	}

	public final int getBizId() {
		return bizId;
	}

	public final void setBizId(int bizId) {
		this.bizId = bizId;
	}

	@Override public String toString() {
		return "业务ID：" + bizId;
	}
}
