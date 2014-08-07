## zx.soft版Jedis

原版Jedis：https://github.com/xetorthio/jedis

相比原版，此版本多了通过value进行分片的功能，但是此功能并不完备，目前可以用在set结构上。

### Maven构建

1. 启动本地Redis，参数如下

`port`：6397

`password`：zxsoft


样例：
```java
ValueShardedJedisPool pool = new ValueShardedJedisPool(config, shards);
```