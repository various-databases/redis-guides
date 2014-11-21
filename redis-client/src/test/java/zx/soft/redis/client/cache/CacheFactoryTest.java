package zx.soft.redis.client.cache;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import zx.soft.redis.client.cache.Cache;
import zx.soft.redis.client.cache.CacheFactory;

@Ignore
public class CacheFactoryTest {

	@Test
	public void testEval() {
		Cache cache = CacheFactory.getInstance();
		cache.del("k1", "k2", "k3");

		cache.sadd("k1", "v1", "v2");
		cache.sadd("k2", "v3", "v4");
		assertEquals(0L, cache.scard("k3").longValue());
	}
}
