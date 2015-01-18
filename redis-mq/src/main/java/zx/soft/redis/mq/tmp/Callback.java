package zx.soft.redis.mq.tmp;

public interface Callback {

	public void onMessage(String message);

}
