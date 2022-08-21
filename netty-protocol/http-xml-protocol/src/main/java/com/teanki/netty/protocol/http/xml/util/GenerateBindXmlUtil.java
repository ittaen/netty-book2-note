package com.teanki.netty.protocol.http.xml.util;

import org.jibx.binding.generator.BindGen;

/**
 * GenerateBindXmlUtil
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/21 8:50
 */
public class GenerateBindXmlUtil {
    public static void main(String[] args) throws Exception {
        // fixme 参数方式待确认
        genBindFiles();
    }

    private static void genBindFiles() throws Exception {
        String[] args = new String[9];

        // 指定pojo源码路径（指定父包也是可以的）。必须
        args[0] = "-c";
        args[1] = "";

        // 自定义生成的binding文件名，默认文件名binding.xml。可选
        args[2] = "-b";
        args[3] = "binding.xml";

        // 打印生成过程的一些信息。可选
        args[4] = "-v";

        // 如果目录已经存在，就删除目录。可选
        args[5] = "-w";

        //- t 指定xml和xsd输出路径 路径。默认路径 .（当前目录,即根目录）。
        args[6] = "-t";
        args[7] = "E:\\develop\\java\\code\\github.com\\netty\\netty-book2-note\\netty-protocol\\http-xml-protocol\\src\\main\\resources\\jibx";

        // 告诉 BindGen 使用下面的类作为 root 生成 binding 和 schema。必须
        args[8] = "com.teanki.netty.protocol.http.xml.pojo.Address com.teanki.netty.protocol.http.xml.pojo.Customer com.teanki.netty.protocol.http.xml.pojo.Order com.teanki.netty.protocol.http.xml.pojo.Shipping";

        BindGen.main(args);
    }

    private static void genBindFiles2() throws Exception {
        String[] args = new String[9];

        // 指定pojo源码路径（指定父包也是可以的）。必须
        args[0] = "-c";
        args[1] = "com.teanki.netty.protocol.http.xml.pojo";

        // 自定义生成的binding文件名，默认文件名binding.xml。可选
        args[2] = "-b";
        args[3] = "binding.xml";

        // 打印生成过程的一些信息。可选
        args[4] = "-v";

        // 如果目录已经存在，就删除目录。可选
        args[5] = "-w";

        //- t 指定xml和xsd输出路径 路径。默认路径 .（当前目录,即根目录）。
        args[6] = "-t";
        args[7] = "./src/main/java/com/example/cp10http/xml/pojo/order";

        // 告诉 BindGen 使用下面的类作为 root 生成 binding 和 schema。必须
        args[8] = "com.example.cp10http.xml.pojo.Order";

        BindGen.main(args);
    }
}
