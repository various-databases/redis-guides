package zx.soft.redis.demo.string;

import redis.clients.jedis.Jedis;

public class SetDemo {

	public static void main(String[] args) {

		//		Jedis jedis = new Jedis("localhost");
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.auth("zxsoft");
		jedis.set("foo", "bar");
		String value = jedis.get("foo");
		System.out.println(value);
		jedis.close();
	}

}
