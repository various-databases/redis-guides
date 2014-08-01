package zx.soft.redis.client.hash;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

/**
 * 管理有生存周期的Hash表
 * 注：如果超过生存周期，时间设为0
 * 
 * 作用：存放URL队列，用于判断是否更新，并定期删除超过一定期限的URL。
 * 
 * @author wanggang
 *
 */
public class ManagerPersistHash implements ManagerHash {

	private static Logger logger = LoggerFactory.getLogger(ManagerPersistHash.class);

	// Hash表的主key
	private final String KEY_NAME;

	// Redis单机器客户端
	private final Jedis jedis;

	public ManagerPersistHash(Jedis jedis, String keyName) {
		KEY_NAME = keyName;
		this.jedis = jedis;
	}

	/**
	 * 在集合中插入一条数据，并更新时间为当前的时间
	 */
	@Override
	public void addValue(String value) {
		//
	}

	/**
	 * 判断value是否存在集合中
	 */
	@Override
	public boolean isExisted(String value) {
		//
		return true;
	}

	/**
	 * 删除距当前时间超过一定时间的值
	 */
	@Override
	public void delValues(int timeSpan) {
		//
	}

	@Override
	public List<String> getValues() {
		// TODO Auto-generated method stub
		return null;
	}

}
