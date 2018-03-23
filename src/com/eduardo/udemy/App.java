package com.eduardo.udemy;

// Runnable runs the run on a different thread
//class Runner1 implements Runnable{
//	
//	@Override
//	public void run() {
//		for(int i=0; i<10; ++i)
//			System.out.println("Runner1: "+i);
//	}
//}
// What's the difference between extends vs implements
// interfacing vs subclassing?

// A different way of doing this.
class Runner1 extends Thread {
	
	@Override
	public void run() {
		for(int i=0; i<10; ++i) {
			System.out.println("Runner1: "+i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class Runner2 extends Thread {
	
	@Override
	public void run() {
		for(int i=0; i<10; ++i) {
			System.out.println("Runner2: "+i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

public class App {
	public static void main(String[] args) {
		
// 		If using the Runnable interface		
//		Thread t1 = new Thread(new Runner1()); Thread can take another class that implements run method
//		Thread t2 = new Thread(new Runner2());
		
		Runner1 t1 = new Runner1();
		Runner2 t2 = new Runner2();
		
		t1.start();
		t2.start();
		
		try {
			t1.join(); // This is sort of like "Join the main thread"
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// This belongs to the main thread and it
		// will show first if we do not join the threads
		// Basically when we need the main thread to execute
		// something when the works of the thread is done.
		System.out.println("Finished the task");
		
	}
}
