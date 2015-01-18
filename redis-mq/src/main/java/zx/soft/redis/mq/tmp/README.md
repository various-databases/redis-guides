# rmq (Redis Message Queue)

rmq is a small and very easy to use message queue based on [Redis](http://github.com/antirez/redis "Redis").

rmq uses [Jedis](http://github.com/xetorthio/jedis "Jedis") as a Redis client.

rmq in intended to be fast and reliable.

## What is the difference with Redis pub/sub?

The main difference is that subscribers don't need to be online or they will miss messages. rmq track which messages the client didn't read and it will ensure that the client will receive them once he comes online.

## How do I use it?

You can download the latests build at: 
    http://github.com/xetorthio/rmq/downloads

To use it just as a producer:

	Producer p = new Producer(new Jedis("localhost"),"some cool topic");
	p.publish("some cool message");

To use it just as a consumer you can consume messages as they become available (this will block if there are no new messages):

	Consumer c = new Consumer(new Jedis("localhost"),"consumer identifier","some cool topic");
	c.consume(new Callback() {
		public void onMessage(String message) {
			//do something here with the message
		}
	});

Consume next waiting message and return right away:

	Consumer c = new Consumer(new Jedis("localhost"),"consumer identifier","some cool topic");
	String message = c.consume();

Read next message without removing it from the queue:

	Consumer c = new Consumer(new Jedis("localhost"),"consumer identifier","some cool topic");
	String message = c.read();

And you are done!
