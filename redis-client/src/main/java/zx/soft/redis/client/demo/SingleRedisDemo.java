package zx.soft.redis.client.demo;

import zx.soft.redis.client.cache.Cache;
import zx.soft.redis.client.cache.CacheFactory;

public class SingleRedisDemo {

	public static void main(String[] args) {

		Cache cache = CacheFactory.getInstance();
		String key = "record_key_md5";
		String[] members = { "v1", "v2", "v3", "v4", "v5", "v3" };
		cache.sadd(key, members);
		System.out.println(cache.scard(key));
		System.out.println(cache.sismember(key, "v3"));
		System.out.println(cache.sismember(key, "v6"));
		cache.sadd(key, "v5", "v7");
		System.out.println(cache.scard(key));
		System.out.println(cache.smembers(key));

	}

}
