package com.eduardo.udemy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class Processor5 implements Callable<String> {
	private int id;
	
	public Processor5(int id) {
		this.id = id;
	}
	
	@Override
	// Callable similar to runnable interface but 
	// one can use it to store values return by threads
	public String call() throws Exception {
		Thread.sleep(1000);
		return "Id: " + id;
	}
}

public class FutureAndCall {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		
		List<Future<String>> list = new ArrayList<>();
		
		for(int i=0; i<5; ++i) {
			// A future is necessary here cuz submit does not wait for 
			// the thread to be done. basically stores the value at a later
			// 'future' moment when the thread is done.
			Future<String> future = executorService.submit(new Processor5(i+1));
			list.add(future);
		}
		
		for(Future<String> future : list) {
			try {				
				System.out.println(future.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		executorService.shutdown();
	}
}

