# JiBX 绑定文件生成

```shell script
java -cp E:\develop\java\code\github.com\netty\netty-book2-note\tools\jibx\lib\jibx-tools.jar;E:\develop\java\code\github.com\netty\netty-book2-note\tools\jibx\lib\log4j-1.2.17.jar org.jibx.binding.generator.BindGen -t E:\develop\java\code\github.com\netty\netty-book2-note\netty-protocol\http-xml-protocol\src\main\resources\jibx -v  com.teanki.netty.protocol.http.xml.pojo.Address com.teanki.netty.protocol.http.xml.pojo.Customer com.teanki.netty.protocol.http.xml.pojo.Order com.teanki.netty.protocol.http.xml.pojo.Shipping
```

```
java -cp ..libx-tools.jar ..BindGen -t 生成文件保存地址 -v 需要绑定文件的class文件 完整包名.类名
```
