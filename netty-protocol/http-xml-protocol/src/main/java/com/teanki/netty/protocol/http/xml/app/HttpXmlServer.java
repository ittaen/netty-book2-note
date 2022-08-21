package com.teanki.netty.protocol.http.xml.app;

import com.teanki.netty.protocol.http.xml.handler.HttpXmlServerHandler;
import com.teanki.netty.protocol.http.xml.handler.codec.HttpXmlRequestDecoder;
import com.teanki.netty.protocol.http.xml.handler.codec.HttpXmlResponseEncoder;
import com.teanki.netty.protocol.http.xml.pojo.Order;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetSocketAddress;

/**
 * HttpXmlServer
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/21 12:30
 */
public class HttpXmlServer {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new HttpXmlServer().run(port);
    }

    public void run(final int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b
                    .group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            channel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65535));
                            channel.pipeline().addLast("xml-decoder", new HttpXmlRequestDecoder(Order.class, true));
                            channel.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                            channel.pipeline().addLast("xml-encoder", new HttpXmlResponseEncoder());
                            channel.pipeline().addLast("httpXmlServerHandler", new HttpXmlServerHandler());
                        }
                    });
            ChannelFuture future = b.bind(new InetSocketAddress(port)).sync();
            System.out.println("HTTP 订购服务器启动， 网址是 ：" + "http://127.0.0.1:" + port);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
