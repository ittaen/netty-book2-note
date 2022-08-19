package com.taenki.aio;


/**
 * aio TimeServer 示例
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/18 9:29
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // 采用默认值
            }
        }
        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
        new Thread(timeServer, "AIO-MultiplexerTimeServer-001").start();
    }
}
