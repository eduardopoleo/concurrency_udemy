package com.eduardo.udemy;

public class SyncronizedBlock {
	
	private static int count1 = 0;
	private static int count2 = 0;
	
// We have to be smart about synchronizing this cuz
// this current implementation this scenario is possible; 
// t1 wants to update count1 --> blocks the whole class including add2
// t2 wants to update count2 --> but has to wait until t1 is done with add1
// This is a waste of time and it happens cuz the program is using a class
// level lock.
// We need a more local level locking so that threads can operate on independent
// resource without blocking each other for no reason.
	
//	public synchronized static void add1() {
//		count1++;
//	}
//	
//	public synchronized static void add2() {
//		count2++;
//	}
//
	
	private static Object lock1 = new Object();
	private static Object lock2 = new Object();
	
	// How can you lock using this objects?
	
	public static void add1() {
		synchronized (lock1) {
			count1++;
		}
	}
	
	public static void add2() {
		// I do not really understand why this can be an ordinary object.
		// We always lock on lock2 cuz count2 is always the same variable
		// so as long as lock2 so this will work as long as lock2 is the 
		// same at all times.
		// is count2 is an specific resource like a contact we could use 
		// something more specific like an id so that we specifically block 
		// the object that we want.
		synchronized (lock2) {
			count2++;
		}
	}
	
	public static void compute() {
		for(int i=0; i<100;++i) {
			add1();
			add2();
		}
	}
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				compute();
			}
		});
		
		Thread t2 = new Thread(new Runnable(){
			@Override
			public void run() {
				compute();
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
		// This one was very clear how the threads were accessing the same
		// variable at the same time.
		// count1 = 3 at some point in time
		// t1 reads count1 and goes on to change it to 4
		// t2 reads count1 as well before t1 gets to change it to 4
		// both change it to 4 and we basically lose work.
		System.out.println("Count1="+ count1 + "- Count2="+count2);
	}
}
