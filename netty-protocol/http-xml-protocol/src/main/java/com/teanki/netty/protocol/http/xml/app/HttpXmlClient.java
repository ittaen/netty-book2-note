package com.teanki.netty.protocol.http.xml.app;

import com.teanki.netty.protocol.http.xml.handler.HttpXmlClientHandler;
import com.teanki.netty.protocol.http.xml.handler.codec.HttpXmlRequestEncoder;
import com.teanki.netty.protocol.http.xml.handler.codec.HttpXmlResponseDecoder;
import com.teanki.netty.protocol.http.xml.pojo.Order;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

import java.net.InetSocketAddress;

/**
 * HttpXmlClient
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/21 12:15
 */
public class HttpXmlClient {
    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        new HttpXmlClient().connect(port);
    }

    public void connect(int port) throws Exception {
        // 配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        try {
            b
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder", new HttpResponseDecoder());
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65535));
                            // XML 解码器
                            ch.pipeline().addLast("xml-decoder", new HttpXmlResponseDecoder(Order.class, true));
                            ch.pipeline().addLast("http-encoder", new HttpRequestEncoder());
                            ch.pipeline().addLast("xml-encoder", new HttpXmlRequestEncoder());
                            ch.pipeline().addLast("xmlClientHandler", new HttpXmlClientHandler());
                        }
                    });
            // 发起异步连接操作
            ChannelFuture future = b.connect(new InetSocketAddress(port)).sync();

            // 等待客户端链路关闭
            future.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放NIO线程组
            group.shutdownGracefully();
        }
    }
}
