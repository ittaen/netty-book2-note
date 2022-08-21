package com.taenki.netty.protocol.http.server;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * TODO
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/20 13:55
 */
public class CurrentPathDemo {
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        File f = new File(CurrentPathDemo.class.getResource("/").getPath());
        System.out.println(f);
        File f2 = new File(CurrentPathDemo.class.getResource("").getPath());
        System.out.println(f2);
        File directory = new File("");
        String courseFile = directory.getCanonicalPath() ;
        System.out.println(courseFile);
        URL xmlpath = CurrentPathDemo.class.getClassLoader().getResource("selected.txt");
        System.out.println(xmlpath);
    }
}
