package zx.soft.redis.test.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 驱动类
 * 
 * @author wanggang
 *
 */
public class RedisTestDriver {

	private static Logger logger = LoggerFactory.getLogger(RedisTestDriver.class);

	/**
	 * 主函数，需要修改，启动接口后，又关闭了
	 */
	public static void main(String[] args) {

		if (args.length == 0) {
			System.err.println("Usage: Input <class-name>, eg: \n" + //
					"`sentimentIndexServer` 索引接口服务\n" + //
					"`firstPageServer` OA首页信息缓存服务\n" + //
					"`harmfulInfoServer` OA有害信息缓存服务");
			System.exit(-1);
		}
		String[] leftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, leftArgs, 0, leftArgs.length);

		switch (args[0]) {
		case "demo":
			logger.info("索引接口服务");
			Demo.main(leftArgs);
			break;
		default:
			return;
		}

	}

}
