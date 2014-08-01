package zx.soft.redis.client.hash;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

/**
 * 管理有生存周期的Hash表
 * 注：如果超过生存周期，时间设为0
 * 
 * 作用：存放带有生存周期的URL列表，并定期删除超过一定期限的URL；
 * 
 * @author wanggang
 *
 */
public class ManagerExpiredHash implements ManagerHash {

	private static Logger logger = LoggerFactory.getLogger(ManagerExpiredHash.class);

	// Hash表的主key
	private final String keyName;

	// Redis单机器客户端
	private final Jedis jedis;

	// 生存周期
	private final int expire;

	public ManagerExpiredHash(Jedis jedis, int expire, String keyName) {
		this.keyName = keyName;
		this.expire = expire;
		this.jedis = jedis;
	}

	/**
	 * 在集合中插入一条数据，并更新为生存周期
	 */
	@Override
	public void addValue(String value) {
		//
	}

	/**
	 * 获取生存周期为0的数据列表
	 */
	@Override
	public List<String> getValues() {
		//
		return null;
	}

	@Override
	public boolean isExisted(String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delValues(int timeSpan) {
		// TODO Auto-generated method stub
	}

}
