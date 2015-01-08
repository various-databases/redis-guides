package zx.soft.redis.ha.core;

public class Demo {

	public static void main(String[] args) {

		//		Set<String> sentinels = new HashSet<>();
		//		sentinels.add("192.168.3.21:26379");
		//		sentinels.add("192.168.3.22:26379");
		//		sentinels.add("192.168.3.23:26379");
		//		JedisSentinelPool pool = new JedisSentinelPool("zxsoft-master", sentinels, "zxsoft");
		//		Jedis jedis = pool.getResource();
		//		for (int i = 0; i < 100; i++) {
		//			jedis.set("jedis" + i, "jedis" + i);
		//			try {
		//				Thread.sleep(5_000);
		//			} catch (InterruptedException e) {
		//				e.printStackTrace();
		//			}
		//		}
		//		pool.returnResource(jedis);
		//		pool.destroy();
		String a = "测试内容\"测试数据\\\"";
		System.out.println(a);
	}

}
