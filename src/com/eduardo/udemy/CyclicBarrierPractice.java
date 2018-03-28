package com.eduardo.udemy;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Worker5 implements Runnable {
	private int id;
	private Random random;
	private CyclicBarrier cyclicBarrier;
	
	public Worker5(int id, CyclicBarrier cyclicBarrier) {
		this.cyclicBarrier = cyclicBarrier;
		this.random = new Random();
		this.id = id;
	}
	
	@Override
	public void run() {
		doWork();
	}
	
	private void doWork() {
		System.out.println("Thread with ID"+id+ " starts the tasks..");
		try {
			Thread.sleep(random.nextInt(3000));
		} catch ( InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Thread with ID "+ id + " finished...");
		
		try {
			cyclicBarrier.await();
			// This will be executed after the runnable method that
			// happens at the end.
			System.out.println("After await...");
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() { return ""+this.id; };
}

public class CyclicBarrierPractice {
	public static void main(String[] string) {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {
			// You can pass this task to be executed at the end when
			// all other workers have finished their job.
			@Override
			public void run() {
				System.out.println("All the tasks are finised");
			}
		});
		
		// Instantiates all the 5 threads each running a worker work 
		for(int i=0; i<5; ++i)
			executorService.execute(new Worker5(i+1, barrier));
		
		executorService.shutdown();
	}
}

// NOTE TO SELF: Some this patters/tools basically help you to 
// achieve different application/thread life cycles.
