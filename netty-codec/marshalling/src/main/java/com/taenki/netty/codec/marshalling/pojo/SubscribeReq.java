package com.taenki.netty.codec.marshalling.pojo;

import java.io.Serializable;

/**
 * SubscribeReq
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/19 22:37
 */
public class SubscribeReq implements Serializable {

    private static final long serialVersionUID = -8819941535129671664L;
    private int subReqId;
    private String username;
    private String productName;
    private String address;

    public int getSubReqId() {
        return subReqId;
    }

    public void setSubReqId(int subReqId) {
        this.subReqId = subReqId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SubscribeReq{" +
                "subReqId=" + subReqId +
                ", username='" + username + '\'' +
                ", productName='" + productName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
