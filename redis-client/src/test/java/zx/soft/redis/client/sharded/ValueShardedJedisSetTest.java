package zx.soft.redis.client.sharded;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import redis.clients.jedis.JedisShardInfo;
import zx.soft.redis.client.common.Config;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ValueShardedJedisSetTest {

	ValueShardedJedis shardedJedis = null;

	@Before
	public void setUp() {
		List<JedisShardInfo> jdsInfoList = new ArrayList<JedisShardInfo>();
		Properties properties = Config.getProps("cache-config.properties");
		String Servers = properties.getProperty("redis.servers");
		String[] redisServers = Servers.split(",");
		int redisPort = Integer.parseInt(properties.getProperty("redis.port"));
		String password = properties.getProperty("redis.password");
		JedisShardInfo jedis1 = new JedisShardInfo(redisServers[0], redisPort);
		JedisShardInfo jedis2 = new JedisShardInfo(redisServers[1], redisPort);
		JedisShardInfo jedis3 = new JedisShardInfo(redisServers[2], redisPort);
		jedis1.setPassword(password);
		jedis2.setPassword(password);
		jedis3.setPassword(password);

		jdsInfoList.add(jedis1);
		jdsInfoList.add(jedis2);
		jdsInfoList.add(jedis3);
		shardedJedis = new ValueShardedJedis(jdsInfoList);
	}

	@Test
	public void set01_testSadd() {
		assertEquals(shardedJedis.sadd("set:set", "test00").longValue(), 1L);
		assertEquals(shardedJedis.sadd("set:set", "test01", "test02").longValue(), 2L);
		assertEquals(shardedJedis.sadd("set:set", "test01", "test02").longValue(), 0);
	}

	@Test
	public void set02_testScard() {
		assertEquals(shardedJedis.scard("set:set").longValue(), 3L);
	}

	@Test
	public void set03_testSismember() {
		assertEquals(shardedJedis.sismember("set:set", "test00"), true);
		assertEquals(shardedJedis.sismember("set:set", "test03"), false);
	}

	@Test
	public void set04_testSmembers() {
		assertEquals(shardedJedis.smembers("set:set").size(), 3);
	}

	@Test
	public void set05_testSpop() {
		System.out.println(shardedJedis.spop("set:set"));
		System.out.println(shardedJedis.spop("set:set"));
		System.out.println(shardedJedis.spop("set:set"));
		assertEquals(shardedJedis.spop("set:set"), null);
	}

	@Test
	public void set06_testSrem() {
		shardedJedis.sadd("set:set", "test00", "test01", "test02");
		assertEquals(0, shardedJedis.srem("set:set", "test03").longValue());
		assertEquals(1L, shardedJedis.srem("set:set", "test00").longValue());
		assertEquals(2L, shardedJedis.srem("set:set", "test01", "test02").longValue());
	}

}
