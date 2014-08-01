package zx.soft.redis.client.hash;

import java.util.List;

/**
 * 管理Hash表的接口
 * 
 * @author wanggang
 *
 */
public interface ManagerHash {

	public void addValue(String value);

	public boolean isExisted(String value);

	public List<String> getValues();

	public void delValues(int timeSpan);

}
