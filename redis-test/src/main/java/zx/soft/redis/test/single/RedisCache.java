package zx.soft.redis.test.single;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;
import zx.soft.utils.config.ConfigUtil;
import zx.soft.utils.log.LogbackUtil;

public class RedisCache {

	private static Logger logger = LoggerFactory.getLogger(RedisCache.class);

	private final JedisPool pool;

	private static final String CACHE_SENTIMENT_KEY = "cache-records";

	public RedisCache() {
		Properties props = ConfigUtil.getProps("cache-sentiment.properties");
		pool = new JedisPool(new JedisPoolConfig(), props.getProperty("redis.servers"), Integer.parseInt(props
				.getProperty("redis.port")), 30_000, props.getProperty("redis.password"));
	}

	/**
	 * 添加数据
	 */
	public void addRecord(String... members) {
		Jedis jedis = pool.getResource();
		try {
			jedis.watch(CACHE_SENTIMENT_KEY);
			Transaction tx = jedis.multi();
			tx.sadd(CACHE_SENTIMENT_KEY, members);
			tx.exec();
			jedis.unwatch();
			//			tx.discard();
			// pipeline适用于批处理，管道比事务效率高
			// 不使用dsicard会出现打开文件数太多，使用的话DISCARD without MULTI。
			//			Pipeline p = jedis.pipelined();
			//			p.sadd(CACHE_SENTIMENT_KEY, members);
			//			p.sync();// 关闭pipeline
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		} finally {
			// 这里很重要，一旦拿到的jedis实例使用完毕，必须要返还给池中 
			pool.returnResource(jedis);
		}
	}

	/**
	 * 获取集合大小
	 */
	public long getSetSize() {
		long result = 0L;
		Jedis jedis = pool.getResource();
		try {
			// 在事务和管道中不支持同步查询
			result = jedis.scard(CACHE_SENTIMENT_KEY).longValue();
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		} finally {
			pool.returnResource(jedis);
		}
		return result;
	}

	/**
	 * 获取数据
	 */
	public List<String> getRecords() {
		List<String> records = new ArrayList<>();
		Jedis jedis = pool.getResource();
		try {
			String value = jedis.spop(CACHE_SENTIMENT_KEY);
			while (value != null) {
				records.add(value);
				value = jedis.spop(CACHE_SENTIMENT_KEY);
			}
			logger.info("Records'size = {}", records.size());
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		} finally {
			pool.returnResource(jedis);
		}
		return records;
	}

	public void close() {
		// 程序关闭时，需要调用关闭方法 
		pool.destroy();
	}

}
