package com.eduardo.udemy;

public class Syncronized {
	
	private static int counter = 0;
	
	// Counter is now a share state between the 2 threads
	// Changing this variable is not an atomic operation if
	// The threads do not know about each other which may cause
	// issues. e.g like the final sum of counter is 200.
	// synchronized makes it so that threads wait on each other to 
	// perform a given task on the share state
	// NOTE that synchronized must be defined at the work level

	public static synchronized void increment() {
		++counter;
	}
	
	public static void process() {
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				for(int i=0; i < 100; ++i) {
					increment();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				for(int i=0; i < 100; ++i) {
					increment();
				}
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
	}
	
	public static void main(String[] args) {
		process();
		System.out.println(counter);
	}
}
