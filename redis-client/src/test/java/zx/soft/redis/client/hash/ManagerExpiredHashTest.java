package zx.soft.redis.client.hash;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import zx.soft.redis.client.common.Config;

public class ManagerExpiredHashTest {

	private static JedisPool pool;
	private static String password;

	private static final String KEY_NAME = "testdata";
	private static final long MAX_SPAN_TIME = 5_000;
	private static final long EXPIRE = 2_000;

	private static final String[] FIELDS = { "aaa", "bbb", "ccc" };

	@BeforeClass
	public static void prepare() {
		pool = new JedisPool(new JedisPoolConfig(), Config.get("redis.servers"));
		password = Config.get("redis.password");
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
			ManagerExpiredHash managerExpiredHash = new ManagerExpiredHash(jedis, KEY_NAME, EXPIRE, MAX_SPAN_TIME);
			managerExpiredHash.addFields(FIELDS);
			Set<String> allField = jedis.hkeys(KEY_NAME);
			for (String field : allField) {
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
	public void testGetFields() {
		Jedis jedis = pool.getResource();
		jedis.auth(password);
		try {
			ManagerExpiredHash managerExpiredHash = new ManagerExpiredHash(jedis, KEY_NAME, EXPIRE, MAX_SPAN_TIME);
			managerExpiredHash.addFields(FIELDS);
			List<String> fields = managerExpiredHash.getFields();
			assertTrue(fields.size() == 0);
			Thread.sleep(3_000);
			fields = managerExpiredHash.getFields();
			assertTrue(fields.size() == FIELDS.length);
			jedis.del(KEY_NAME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
	}

	@Test
	public void testIsExisted() {
		Jedis jedis = pool.getResource();
		jedis.auth(password);
		try {
			ManagerExpiredHash managerExpiredHash = new ManagerExpiredHash(jedis, KEY_NAME, EXPIRE, MAX_SPAN_TIME);
			managerExpiredHash.addFields(FIELDS);
			for (String field : FIELDS) {
				assertTrue(managerExpiredHash.isExisted(field));
			}
			jedis.del(KEY_NAME);
		} finally {
			pool.returnResource(jedis);
		}
	}

}
