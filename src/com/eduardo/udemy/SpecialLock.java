package com.eduardo.udemy;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SpecialLock {
	private static int counter = 0;
	private static Lock lock = new ReentrantLock();
	
	public static void increment() {
		lock.lock();
		
		// We need this to prevent deadlocks 
		// where we get an exception and the unlock never gets
		// handled.
		try {
			for(int i=0; i<10000;i++)
				counter++;			
		} finally {			
			lock.unlock();
		}
	}
	
	// we can also use the unlock in othe scenarios
	// such as
	
//	public static void add() {
//		lock.unlock();
//	}
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				increment();
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				increment();
			}
		});
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Total increment "+ counter);
	}
}
