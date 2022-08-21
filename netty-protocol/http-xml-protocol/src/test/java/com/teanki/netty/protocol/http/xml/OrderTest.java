package com.teanki.netty.protocol.http.xml;

import com.teanki.netty.protocol.http.xml.factory.OrderFactory;
import com.teanki.netty.protocol.http.xml.pojo.Order;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;

/**
 * OrderTest
 * https://developer.aliyun.com/article/819610
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/20 20:32
 */
public class OrderTest {

    private IBindingFactory factory = null;
    private StringWriter writer = null;
    private StringReader reader = null;
    private final static String CHARSET_NAME = "UTF-8";
    private String encode2Xml(Order order) throws Exception {
        factory = BindingDirectory.getFactory(Order.class);
        writer = new StringWriter();
        IMarshallingContext mctx = factory.createMarshallingContext();
        mctx.setIndent(2);
        mctx.marshalDocument(order, CHARSET_NAME, null, writer);
        String xmlStr = writer.toString();
        writer.close();
        System.out.println(xmlStr.toString());
        return xmlStr;
    }

    private Order decode2Order(String xmlBody) throws Exception {
        reader = new StringReader(xmlBody);
        IUnmarshallingContext uctx = factory.createUnmarshallingContext();
        Order order = (Order) uctx.unmarshalDocument(reader);
        return order;
    }

    @Test
    public void test() throws Exception {
        Order order = OrderFactory.create(123);
        String body = encode2Xml(order);
        Order order2 = decode2Order(body);
        System.out.println(order2);
    }
}
