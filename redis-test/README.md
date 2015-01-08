## Redis测试服务

> 基于jedis-2.5.1版本修改。

### 单Redis单机器多线程测试

* 各项目在pom.xml中增加以下内容：

```java
<project ...>
        ...
        
        <!-- 指定父pom信息 -->
        <parent>
            <groupId>zx.soft</groupId>
            <artifactId>redis-client</artifactId>
            <version>1.2.0</version>
        </parent>

        ...

        <!-- 指定公司私有Maven仓库地址，以便下载父pom -->
        <repositories>
	        <repository>
		        <id>zxsoft-public</id>
		        <name>Nexus Release Repository</name>
		        <url>http://192.168.3.23:18081/nexus/content/groups/public/</url>
	        </repository>
        </repositories>

        ...
</project>
```

### 多机器单Redis多线程测试


### 单机器



### 开发人员

WeChat: wgybzb

QQ: 1010437118

E-mail: wgybzb@sina.cn
