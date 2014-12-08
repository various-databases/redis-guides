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
	public void string02_testDel() {
		assertEquals(1, shardedJedis.del(string_key[5]).longValue());
	}

	//	@Test
	//	public void testSadd() {
	//		assertEquals(0L, shardedJedis.sadd(set_key, value).longValue());
	//	}

}
