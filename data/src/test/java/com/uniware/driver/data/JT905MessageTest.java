package com.uniware.driver.data;

import com.uniware.driver.data.entity.JT905Message;
import com.uniware.driver.data.entity.Ox8B01;
import com.uniware.util.Tools;
import org.junit.Test;

/**
 * Created by jian on 16/03/29.
 */
public class JT905MessageTest {
  @Test public void Test8B00() {
    Ox8B01 ox8B00 = new Ox8B01();
    ox8B00.setBizId(0000);
    ox8B00.setBizType((byte) 0);
    ox8B00.setBizDescription("从柏彦大厦到天安门！");
    JT905Message message = JT905Message.builder().body(ox8B00).isuId("018888886666").build();
    String str = Tools.ToHexString(message.WriteToBytes());
    System.out.println(str);
  }
}
