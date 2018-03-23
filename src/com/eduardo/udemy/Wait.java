package com.eduardo.udemy;

class Processor {
	public void produce () throws InterruptedException {
		// is this the class ? or the instance level lock?
		synchronized (this) {
			Thread.sleep(1000);
			System.out.println("We are in the producer method..");
			// you call this on the lock which in this case is 
			// the class lock "this"
			// why do we use the class lock in here. we just need
			// one lock might as well be the class
			wait();
			System.out.println("Again producer method...");
		}
	}
	
	public void consume () throws InterruptedException {
//		Thread.sleep(10000);
		
		synchronized (this) {
			System.out.println("Consumer method..");
			notify();
			// Will execute all code block even if notify
			// was called before.
			Thread.sleep(3000);
		}
	}
}

public class Wait {
	public static void main(String[] args) {
		
		Processor processor = new Processor();
		
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					processor.produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					processor.consume();
				} catch (InterruptedException e) {
					e.printStackTrace();
					}
				}
		});
		
		// t1 starts and does its thing t2 won't 
		// do anything cuz it's locked on the same resource.
		// t1 hits the wait clause and yields control of the class
		// lock to t2.
		// t2 does its thing and when finished notify() t1 and yields control
		// of the lock back to t1 which finish the script.
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}			
	}
}
