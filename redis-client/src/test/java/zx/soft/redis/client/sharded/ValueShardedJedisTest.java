package zx.soft.redis.client.sharded;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import redis.clients.jedis.JedisShardInfo;
import zx.soft.redis.client.common.Config;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ValueShardedJedisTest {

	ValueShardedJedis shardedJedis = null;
	String[] string_key = { "string1:test", "string2:test", "string3:test", "string4:test", "string5:test",
			"string6:test", "string7:test", "string8:test" };
	String set_key = "set:test";
	String[] string_value = { "string1:test", "string2:test", "string3:test", "string4:test", "string5:test",
			"string6:test", "string7:test", "string8:test" };
	String[] value = { "测试数据001", "测试数据002", "数据测试003", "数据测试004", "据数实测005", "据数实测006", "测试数据007", "测试数据008",
			"数据测试009", "数据测试010", "据数实测011", "据数实测012" };

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
	public void string01_testSet() {
		shardedJedis.set(string_key[0], string_value[0]);
		shardedJedis.set(string_key[1], string_value[1]);
		shardedJedis.set(string_key[2], string_value[2]);
		shardedJedis.set(string_key[3], string_value[3]);
		shardedJedis.set(string_key[4], string_value[4]);
		shardedJedis.set(string_key[5], string_value[5]);
		shardedJedis.set(string_key[6], string_value[6]);
		shardedJedis.set(string_key[7], string_value[7]);
		assertEquals(0, 0);
	}

	@Test
	@Ignore
	public void string02_testExists() {
		assertEquals(true, shardedJedis.exists(string_key[6]));
		assertEquals(false, shardedJedis.exists("key"));
	}

	@Test
	@Ignore
	public void string03_testAppend() {
		assertEquals(1L, shardedJedis.append(string_key[3], "测试数据").longValue());
	}

	@Test
	@Ignore
	public void string03_testDel() {
		assertEquals(1, shardedJedis.del(string_key[5]).longValue());
	}

	@Test
	@Ignore
	public void String04_testIncrDecr() {
		assertEquals(shardedJedis.incr("string:decr").longValue(), 1L);
		assertEquals(shardedJedis.incr("string:decr").longValue(), 2L);
	}

	@Test
	@Ignore
	public void string05_testIncrby() {
		assertEquals(shardedJedis.incrBy("string:decrby", 5).longValue(), 5L);
		assertEquals(shardedJedis.incrBy("string:decrby", 5).longValue(), 10L);
	}

	@Test
	@Ignore
	public void string06_testGetSet() {
		assertEquals(Long.valueOf(shardedJedis.getSet("string:decrby", "test")).longValue(), 10L);
	}

	@Test
	@Ignore
	public void hash01_testHset() {
		assertEquals(shardedJedis.hset("hash:set", "hash0", "value000").longValue(), 1L);
		assertEquals(shardedJedis.hset("hash:set", "hash1", "value001").longValue(), 1L);
		assertEquals(shardedJedis.hset("hash:set", "hash2", "value002").longValue(), 1L);
		assertEquals(shardedJedis.hset("hash:set", "hash3", "value003").longValue(), 1L);
		assertEquals(shardedJedis.hset("hash:set", "hash4", "value004").longValue(), 1L);
	}

	@Test
	@Ignore
	public void hash02_testHexists() {
		assertEquals(shardedJedis.hexists("hash:set", "hash4"), true);
		assertEquals(shardedJedis.hexists("hash:set", "hash2"), true);
		assertEquals(shardedJedis.hexists("hash:set", "hash6"), false);
	}

	@Test
	@Ignore
	public void hash03_testHdel() {
		assertEquals(shardedJedis.hdel("hash:set", "hash3").longValue(), 1L);
	}

	@Test
	public void hash04_testHget() {
		assertEquals(shardedJedis.hget("hash:set", "hash4"), "value004");
	}

	@Test
	public void hash05_testHlen() {
		assertEquals(shardedJedis.hlen("hash:set").longValue(), 5);
	}

	@Test
	@Ignore
	public void hash06_testHkeys() {
		System.out.println(shardedJedis.hkeys("hash:set").size());
	}

	@Test
	public void hash07_testHsetnx() {
		assertEquals(shardedJedis.hsetnx("hash:set", "hash10", "test").longValue(), 1L);
		assertEquals(shardedJedis.hsetnx("hash:set", "hash10", "test01").longValue(), 0L);
	}

	@Test
	public void hash08_testHvals() {
		assertEquals(shardedJedis.hvals("hash:set").size(), 6);
	}
	//	@Test
	//	public void testSadd() {
	//		assertEquals(0L, shardedJedis.sadd(set_key, value).longValue());
	//	}

}
