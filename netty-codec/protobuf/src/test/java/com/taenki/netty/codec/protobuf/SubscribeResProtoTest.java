package com.taenki.netty.codec.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * TestSubscribeResProto
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/19 14:47
 */
public class SubscribeResProtoTest {

    private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    /**
     * 这个方法存在问题
     * @return
     */
    private static SubscribeReqProto.SubscribeReq createSubscribeReq1() {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqId(0, 1);
        builder.setUsername(0, "Jack");
        builder.setProductName(0, "Netty Book");
        List<String> address = new ArrayList<>();
        address.add("BeiJing");
        address.add("ShenZheng");
        address.add("GuangZhou");
        builder.addAllAddress(address);
        return builder.build();
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq() {
        List<String> address = new ArrayList<>();
        address.add("BeiJing");
        address.add("ShenZheng");
        address.add("GuangZhou");
        return SubscribeReqProto.SubscribeReq.newBuilder()
                .addSubReqId(1)
                .addUsername("Jack")
                .addProductName("Netty Book")
                .addAllAddress(address)
                .build();
    }

    @Test
    public void testReqProto() throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq subscribeReq = createSubscribeReq();
        System.out.println("Before encode " + subscribeReq.toString());
        SubscribeReqProto.SubscribeReq decode = decode(encode(subscribeReq));
        System.out.println("After decode : " + decode.toString());
        System.out.println("Assert equal : --> " + decode.equals(subscribeReq));
    }

    @Test
    public void testRespProto() {
        SubscribeRespProto.SubscribeResp subscribeResp = SubscribeRespProto.SubscribeResp.newBuilder()
                .addSubReqId(1)
                .addRespCode(0)
                .addDesc("succeed")
                .build();
        System.out.println(subscribeResp.toString());
        System.out.println(subscribeResp.getSubReqId(0));
    }

}
