package zx.soft.redis.client.standone.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class RedisStandoneClient {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(RedisStandoneClient.class);

	private final Jedis jedis;

	public RedisStandoneClient(String server, int port, String password) {
		jedis = new Jedis(server, port);
		jedis.auth(password);

	}

	public void close() {
		jedis.shutdown();
	}

	public Jedis getJedis() {
		return jedis;
	}

}
