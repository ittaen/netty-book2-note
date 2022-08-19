# protobu 使用

protoc的使用的命令如下：

```shell script
protoc --experimental_allow_proto3_optional -I=SRC_DIR --java_out=DST_DIR $SRC_DIR/student.proto
```
如果编译proto3,则需要添加–experimental_allow_proto3_optional选项。
