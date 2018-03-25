package com.eduardo.udemy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExectuterExp {
	public static void main(String[] args) {
		// It will spawn up up to 5 threads to execute the work we need.
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		//only one thread
//		ExecutorService executorService = Executors.newSingleThreadExecutor();
		// as many threads required. if they are available is going to use 
		// them if not it will create more.
//		ExecutorService executorService = Executors.newCachedThreadPool();
		
		// each thread has to run this job fully ? so print 0 to 10
		// but it's going to run 3 first and then 2 cuz by means of the fixed
		// pool it can only do 3 at a time.
		for (int i=0; i<5; i++) {
			executorService.submit(new Worker3());
		}
	}
}

class Worker3 implements Runnable {
	public void run() {
		for(int i=0; i<10; i++) {
			System.out.println(i);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}