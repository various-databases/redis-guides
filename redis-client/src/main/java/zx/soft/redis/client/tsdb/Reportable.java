package zx.soft.redis.client.tsdb;

import java.util.List;

/**
 * 报表接口
 * 
 * @author wanggang
 *
 */
public interface Reportable {

	List<Tsdb> report();

}
