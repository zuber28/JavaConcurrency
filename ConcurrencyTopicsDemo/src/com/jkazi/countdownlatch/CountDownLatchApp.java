package com.jkazi.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchApp {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		CountDownLatch countDownLatch = new CountDownLatch(5);
		for (int i = 1; i <= 5; i++) {
			executorService.submit(new Worker(i, countDownLatch));
		}

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("All the prequisites are done..");
		executorService.shutdown();
	}

}

class Worker implements Runnable {

	private int id;
	private CountDownLatch countDownLatch;

	public Worker(int id, CountDownLatch countDownLatch) {
		this.id = id;
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		doWork();
		countDownLatch.countDown();
	}

	private void doWork() {
		System.out.println("Thread with id " + this.id + " starts working..");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}