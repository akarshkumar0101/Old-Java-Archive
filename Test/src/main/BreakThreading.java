package main;

public class BreakThreading {

	static Object o = new Object();

	public static void main(String[] args) {
		MyThread wait1 = new MyThread("firstwait", null);
		MyThread wait2 = new MyThread("secondwait", null);
		MyThread wait3 = new MyThread("thirdwait", null);
		MyThread wait4 = new MyThread("fourthwait", null);

		wait1.start();
		wait2.start();
		wait3.start();
		wait4.start();

		MyThread notify = new MyThread("notify", new Runnable() {
			@Override
			public void run() {
				synchronized (o) {
					o.notifyAll();
					System.out.println("all notified");
				}
			}
		});
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		notify.start();

	}

	static class MyThread extends Thread {
		Runnable target;

		public MyThread(String name, Runnable target) {
			super(name);
			this.target = target;
		}

		@Override
		public void run() {
			if (target != null) {
				target.run();
				return;
			}
			System.out.println(getName() + " trying in");
			synchronized (o) {
				System.out.println(getName() + " success in");
				try {
					o.wait();
					System.out.println(getName() + " now out");
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
