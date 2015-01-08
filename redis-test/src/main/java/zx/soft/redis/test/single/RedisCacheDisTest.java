package zx.soft.redis.test.single;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

import zx.soft.utils.json.JsonUtils;
import zx.soft.utils.threads.ApplyThreadPool;

public class RedisCacheDisTest {

	private static final Random RANDOM = new Random();

	public static void main(String[] args) {

		final ThreadPoolExecutor pool = ApplyThreadPool.getThreadPoolExector(8);

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				pool.shutdown();
			}
		}));

		final RedisCache redisCache = new RedisCache();
		System.err.println("初始化完成......");

		for (int i = 0; i < 100; i++) {
			System.out.println("Add at : " + i);
			pool.execute(new Thread(new Runnable() {
				@Override
				public void run() {
					List<String> records = getRecords();
					redisCache.addRecord(records.toArray(new String[records.size()]));
				}
			}));
			if (i % 10 == 0) {
				System.err.println(redisCache.getSetSize());
				List<String> records = redisCache.getRecords();
				System.out.println(JsonUtils.toJson(records));
			}
		}

		try {
			Thread.sleep(3_000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pool.shutdown();

	}

	public static List<String> getRecords() {
		List<String> recordInfos = new ArrayList<>();
		recordInfos.add("test" + RANDOM.nextInt(10000));
		recordInfos.add("test" + RANDOM.nextInt(10000));
		return recordInfos;
	}

}
