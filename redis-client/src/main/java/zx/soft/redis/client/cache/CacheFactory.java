package zx.soft.redis.client.cache;

import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.exceptions.JedisConnectionException;
import zx.soft.redis.client.common.Config;
import zx.soft.utils.log.LogbackUtil;
import zx.soft.utils.retry.RetryHandler;

/**
 * 缓存工厂类
 * 
 * @author wanggang
 *
 */
public class CacheFactory {

	private static Logger logger = LoggerFactory.getLogger(CacheFactory.class);

	private static Cache instance;

	static {
		try {
			instance = (Cache) Proxy.newProxyInstance(
					Cache.class.getClassLoader(),
					new Class[] { Cache.class },
					new RetryHandler<Cache>(new RedisCache(Config.get("redis.servers"), Integer.parseInt(Config
							.get("redis.port")), Config.get("redis.password")), 5000, 10) {
						@Override
						protected boolean isRetry(Throwable e) {
							return e instanceof JedisConnectionException;
						}
					});
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	public static Cache getInstance() {
		return instance;
	}

}
