package com.jkazi.cyclicbarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierApp {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {

			@Override
			public void run() {
				System.out.println("All the tasks are finished...");
			}

		});

		for (int i = 0; i < 5; ++i) {
			executorService.execute(new Worker(i + 1, cyclicBarrier));
		}
		executorService.shutdown();
	}

}

class Worker implements Runnable {

	private int id;
	private CyclicBarrier cyclicBarrier;
	private Random random;

	public Worker(int id, CyclicBarrier cyclicBarrier) {
		this.id = id;
		this.cyclicBarrier = cyclicBarrier;
		this.random = new Random();
	}

	@Override
	public void run() {
		doWork();
	}

	private void doWork() {
		System.out.println("Thread with id " + this.id + " starts the work...");
		try {
			Thread.sleep(random.nextInt(3000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Thread with id " + this.id + " finished...");

		try {
			cyclicBarrier.await();
			System.out.println("After await...");
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String toString() {
		return "" + this.id;
	}

}