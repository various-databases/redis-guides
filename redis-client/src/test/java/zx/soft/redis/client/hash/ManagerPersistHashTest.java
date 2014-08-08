package zx.soft.redis.client.hash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import zx.soft.redis.client.conf.Config;

public class ManagerPersistHashTest {

	private static JedisPool pool;
	private static String password;

	private static final long MAX_SPAN_TIME = 3_000;
	private static final String KEY_NAME = "testdata";

	private static final String[] FIELDS = { "aaa", "bbb", "ccc" };

	@BeforeClass
	public static void prepare() {
		pool = new JedisPool(new JedisPoolConfig(), Config.get("redisServers"));
		password = Config.get("password");
	}

	@AfterClass
	public static void cleanup() {
		if (pool != null) {
			pool.destroy();
		}
	}

	@Test
	public void testAddField() {
		Jedis jedis = pool.getResource();
		jedis.auth(password);
		try {
			ManagerPersistHash managerPersistHash = new ManagerPersistHash(jedis, KEY_NAME, MAX_SPAN_TIME);
			managerPersistHash.addFields(FIELDS);
			List<String> fields = managerPersistHash.getFields();
			for (String field : fields) {
				boolean flag = Boolean.FALSE;
				for (String f : FIELDS) {
					if (field.equals(f)) {
						flag = Boolean.TRUE;
					}
				}
				assertTrue(flag);
			}
			jedis.del(KEY_NAME);
		} finally {
			pool.returnResource(jedis);
		}
	}

	@Test
	public void testIsExistedOne() {
		Jedis jedis = pool.getResource();
		jedis.auth(password);
		try {
			ManagerPersistHash managerPersistHash = new ManagerPersistHash(jedis, KEY_NAME, MAX_SPAN_TIME);
			managerPersistHash.addFields(FIELDS);
			for (String field : FIELDS) {
				assertTrue(managerPersistHash.isExisted(field));
			}
			jedis.del(KEY_NAME);
		} finally {
			pool.returnResource(jedis);
		}
	}

	@Test
	public void testIsExisted() {
		Jedis jedis = pool.getResource();
		jedis.auth(password);
		try {
			jedis.hset("keyname", "foo", "bar");
			assertEquals("bar", jedis.hget("keyname", "foo"));
			assertTrue(jedis.hexists("keyname", "foo"));
			jedis.del("keyname");
		} finally {
			pool.returnResource(jedis);
		}
	}

	@Test
	public void testDelFieldsOne() {
		Jedis jedis = pool.getResource();
		jedis.auth(password);
		try {
			ManagerPersistHash managerPersistHash = new ManagerPersistHash(jedis, KEY_NAME, MAX_SPAN_TIME);
			managerPersistHash.addFields(FIELDS);
			Thread.sleep(1_000);
			List<String> fields = managerPersistHash.getFields();
			for (String field : fields) {
				boolean flag = Boolean.FALSE;
				for (String f : FIELDS) {
					if (field.equals(f)) {
						flag = Boolean.TRUE;
					}
				}
				assertTrue(flag);
			}
			Thread.sleep(3_000);
			managerPersistHash.delFields();
			fields = managerPersistHash.getFields();
			for (String field : fields) {
				boolean flag = Boolean.FALSE;
				for (String f : FIELDS) {
					if (field.equals(f)) {
						flag = Boolean.TRUE;
					}
				}
				assertFalse(flag);
			}
			jedis.del(KEY_NAME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
	}

	@Test
	public void testDelFields() {
		List<String> list = new ArrayList<>();
		list.add("aa");
		list.add("bb");
		String[] str = list.toArray(new String[list.size()]);
		for (int i = 0; i < list.size(); i++) {
			assertEquals(list.get(i), str[i]);
		}
	}

}
