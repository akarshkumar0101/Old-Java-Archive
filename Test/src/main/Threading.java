package main;

public class Threading {

	public static void main(String[] args) {

		MyThread t1 = new MyThread("first", true);
		MyThread t2 = new MyThread("second", false);
		t1.start();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		t2.start();
	}

	static Object o = new Object();

	public static void printMyThread() {
		// synchronized (o) {
		// actual();
		// }

		try {
			synchronized (o) {
				o.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// System.out.println("aight go from " +
		// Thread.currentThread().getName());
	}

	public static void printMyThread2() {
		// synchronized (o) {
		// actual();
		// }

		synchronized (o) {
			o.notify();
		}
	}

	public static void actual() {
		while (true) {
			MyThread thread = (MyThread) Thread.currentThread();
			long thisTime = System.currentTimeMillis();
			System.out.println(formatString(thread.prints++ + ": ", 6) + thread.getName() + ", last run: "
					+ (thisTime - thread.lastTime));
			thread.lastTime = thisTime;
			if (thread.prints > 100) {
				break;
			}
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static String formatString(String a, int size) {
		String add = "";
		for (int i = 0; i < size - a.length(); i++) {
			add += ' ';
		}
		return add + a;
	}

	static class MyThread extends Thread {
		int prints = 0;
		long lastTime;
		boolean first;

		public MyThread(String name, boolean first) {
			super(name);
			this.first = first;
		}

		@Override
		public void run() {
			lastTime = System.currentTimeMillis();
			if (first) {
				printMyThread();
			} else {
				printMyThread2();
			}

		}
	}
}
