package com.eduardo.udemy;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// This is similar to the bounded buffer
// exercise that Jeff was mentioning.

class FirstWorker implements Runnable {
	
	private BlockingQueue<Integer> blockingQueue;
	
	public FirstWorker(BlockingQueue<Integer> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}
	
	@Override
	public void run() {
		int counter = 0;
		
		while(true) {
			try {
				blockingQueue.put(counter);
				System.out.println("Putting items..."+counter);
				counter++;
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}

class SecondWorker implements Runnable {
	
	private BlockingQueue<Integer> blockingQueue;
	
	public SecondWorker(BlockingQueue<Integer> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				int number = blockingQueue.take();
				System.out.println("Taking items..."+number);
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}


public class BlockingQueuePractice {
	public static void main(String[] args) {
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
		
		FirstWorker firstWorker = new FirstWorker(queue);
		SecondWorker secondWorker = new SecondWorker(queue);
		
		new Thread(firstWorker).start();
		new Thread(secondWorker).start();
	}
}
