package com.taenki.netty.protocol.http.server;

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
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * HTTP 文件服务器 示例
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/20 9:07
 */
public class HttpFileServer {

    private static final String DEFAULT_URL = "/";

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String url = DEFAULT_URL;
        if (args.length > 1) {
            url = args[1];
        }
        new HttpFileServer().run(port, url);
    }

    public void run(final int port, final String url) throws Exception {
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
                            // 请求消息解码器
                            channel.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            // 添加 HttpObjectAggregator解码器,它的作用是将多个消息转换为单一的FullHttpRequest或者FullHttpResponse,原因是HTTP解码器在每个HTTP消息中会生成多个消息对象
                            channel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65535));
                            channel.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                            // 新增Chunked handler,它的主要作用是支持异步发送大的码流(例如大的文件传输),但不占用过多的内存,防止发生Java内存溢出错误
                            channel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                            channel.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
                        }
                    });
            ChannelFuture future = b.bind("192.168.100.30", port).sync();
            System.out.println("HTTP 文件目录服务启动， 网址是 ：" + "http://192.168.100.30:" + port + url);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
