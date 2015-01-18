package zx.soft.redis.mq.core;

/**
 * Redis message class. Stores both the message payload and topic.
 */
public class RedisMessage {

	private final String topic;
	private final byte[] payload;

	public RedisMessage(String topic, byte[] payload) {
		this.topic = topic;
		this.payload = payload;
	}

	public String getTopic() {
		return topic;
	}

	public byte[] getPayload() {
		return payload;
	}

}
