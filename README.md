# JSON Parser & Formatter

前一段时间在用JSON的时候，发现格式化JSON很有意思，于是乎老王就用空闲时间写了一个JSON的解析和格式化器。<br>
这个解析的状态机严格按照JSON规范来编写，详细见: [http://www.json.org/](http://www.json.org/)

## 代码组织

### Java代码: 在项目的/java目录下, 包名com.simplemain.kit.json
* element: 存放的是Json的实体对象
* error: 错误相关的类，分为错误和警告
* format: 做格式化的类，都从JsonFormatter集成。可以自己集成实现想要的效果
* parser: 解析类。对外接口为JsonParser类。
* Main.java: 入口main函数类

### JavaScript代码: 待开发

### C代码: 待开发

## 编译 & 运行
* 下载相关代码
* 切换到项目目录
* 编译: ant
* 运行: java -jar jar/json-formatter-1.0.0.jar
* 版本号有可能发生变化，请根据编译后的文件执行相应的命令