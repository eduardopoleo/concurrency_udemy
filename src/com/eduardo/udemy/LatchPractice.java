package com.eduardo.udemy;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LatchPractice {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		
		// Used to make the main thread wait for any number of other threads to finish
		// their execution. The threads that are being instantiated do not have to wait
		// for each other to execute their work. We just want to make the main to keep going once
		// all the others are done.
		CountDownLatch latch = new CountDownLatch(5);
		
		for(int i=0; i<5; i++) {
			executorService.execute(new Worker4(i+1, latch));
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("All the prerequisites are done ....");
		executorService.shutdown();
	}
}

// We can imagine each thread doing something different as well. 
class Worker4 implements Runnable {
	private int id;
	private CountDownLatch countDownLatch;
	
	public Worker4(int id, CountDownLatch countDownLatch) {
		this.id = id;
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		doWork();
		countDownLatch.countDown();
	}
	
	private void doWork() {
		System.out.println("Thread with id " + this.id + " starts working...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
