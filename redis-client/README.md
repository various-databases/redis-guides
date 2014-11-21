## zx.soft版Jedis

原版Jedis：https://github.com/xetorthio/jedis

相比原版，此版本多了通过value进行分片的功能，但是此功能并不完备，目前可以用在set结构上。

> 基于jedis-2.5.1版本修改。

### 依赖使用

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

### 构建测试

1. 启动本地Redis，参数如下

`port`：6397

`password`：zxsoft


2. 使用样例
```java

Cache redisCache = new RedisCache(Config.get("redis.servers"), Integer.parseInt(Config.get("redis.port")),
				Config.get("redis.password"));

或者

ValueShardedJedisPool pool = new ValueShardedJedisPool(config, shards);

```

### 开发人员

WeChat: wgybzb

QQ: 1010437118

E-mail: wgybzb@sina.cn
