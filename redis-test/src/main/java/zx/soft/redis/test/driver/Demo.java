package zx.soft.redis.test.driver;

public class Demo {

	public static void main(String[] args) {

		while (true) {
			System.out.println("Test");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
