package com.eduardo.udemy;

import java.util.ArrayList;
import java.util.List;
// We are going to implement this but NOT from scratch

class Processor2 {
	
	private List<Integer> list = new ArrayList<>();
	private final int LIMIT = 5;
	private final int BOTTOM = 0;
	private final Object lock = new Object();
	private int value = 0;
	
	public void producer() throws InterruptedException {
		synchronized (lock) {
			while(true) {
				if (list.size() == LIMIT) {
					System.out.println("Waiting for items to be removed...");
					lock.wait();
				} else {
					System.out.println("Adding: " +value);
					list.add(value);
					value++;
					// why doesn't this yield on the first addition?
					// Because it's going to execute the while loop
					// and won't notify until it has to wait.
					// But then why dont we just wait?
					// Cuz if I do not notify the threads will only run once and
					// both will just sit and wait for ever.
					// Notify just slaps them in the face.
					lock.notify(); 
				}
				Thread.sleep(500);
			}
		}
	}
	
	public void consumer() throws InterruptedException {
		synchronized(lock) {
			while(true) {
				if ( list.size() == BOTTOM ) {
					System.out.println("Waiting for items to be added to the list");
					lock.wait();
				} else {
					System.out.println("Removed: "+list.remove(--value));
					lock.notify();
				}
				
				Thread.sleep(500);
			}
		}
	}
}

public class ConsumerProducer {
	
	static Processor2 processor = new Processor2();
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					processor.producer();
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					processor.consumer();
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		t1.start();
		t2.start();
	}
}
