package com.teanki.netty.protocol.http.xml.factory;

import com.teanki.netty.protocol.http.xml.pojo.Address;
import com.teanki.netty.protocol.http.xml.pojo.Customer;
import com.teanki.netty.protocol.http.xml.pojo.Order;
import com.teanki.netty.protocol.http.xml.pojo.Shipping;

/**
 * OrderFactory
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/21 8:24
 */
public class OrderFactory {
    public static Order create(int orderNumber) {
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setTotal(9999.999f);
        Address address = new Address();
        address.setCity("广州市");
        address.setCountry("中国");
        address.setState("广东省");
        address.setPostCode("4004440");
        address.setStreet1("东篱大街");
        order.setBillTo(address);
        Customer customer = new Customer();
        customer.setCustomerNumber(orderNumber);
        customer.setFirstName("乔");
        customer.setLastName("峰");
        order.setCustomer(customer);
        order.setShipping(Shipping.INTERNATIONAL_MALL);
        order.setShipTo(address);
        return order;
    }
}
