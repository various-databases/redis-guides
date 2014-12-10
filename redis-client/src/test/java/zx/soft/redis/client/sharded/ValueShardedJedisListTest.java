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
public class ValueShardedJedisListTest {
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
	public void list01_testLpush() {
		assertEquals(3L, shardedJedis.lpush("list:set", "test00", "test01", "test02").longValue());
		assertEquals(3L, shardedJedis.lpush("list:set", "test03", "test04", "test05").longValue());
		assertEquals(1L, shardedJedis.lpush("list:set", "test06").longValue());
	}

	@Test
	public void list02_testRpush() {
		assertEquals(3L, shardedJedis.rpush("list:set", "test07", "test08", "test09").longValue());
	}

	@Test
	public void list03_testRpop() {
		System.out.println(shardedJedis.rpop("list:set"));
	}

	@Test
	public void list04_testLpop() {
		System.out.println(shardedJedis.lpop("list:set"));
	}

}
