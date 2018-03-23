package com.eduardo.udemy;

class Worker implements Runnable {
	
	// Makes sure that isTerminated gets stored in the
	// main memory unit and not the thread cache 
	// when we go about changing it from the main thread
	// then we are sure we are changing the right one.
	private volatile boolean isTerminated = false;
	
	@Override
	public void run() {
		while(!isTerminated) {
			System.out.println("Hello from worker class...");
			try {
				// Sleeps the current worker thread
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isTerminated() {
		return isTerminated;
	}

	public void setTerminated(boolean isTerminated) {
		this.isTerminated = isTerminated;
	}
}

public class Volatile {
	public static void main(String[] args) {
		Worker  worker = new Worker();
				
		Thread t1 = new Thread(worker);
		t1.start();
		
		try {
			// I think this sleeps the main thread not the actual thread that does the work
			Thread.sleep(3000); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		worker.setTerminated(true);
		System.out.println("Finishedd...");
	}
}

// Deadlock. Two threads waiting on each other to work to proceed
// Livelock. Two threads can make progress cuz they are too busy
// responding to each other.
