package com.eduardo.udemy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

// This enum implementation is thread safe
// 
enum Downloader {
	INSTANCE;
	
	// args number or threads, fairness
	private Semaphore semaphore = new Semaphore(3, true);
	
	public void downloadData() {
		try {
			semaphore.acquire();
			download();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}
	}

	private void download() {
		System.out.println("Downloading data from the web..");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

public class Semaphore2 {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		// this service instantiates the semaphores threads.
		// If you have to create many threads then they are pretty useful
		
		for(int i=0;i<12;i++) {
			executorService.execute(new Runnable() {
				public void run() {
					Downloader.INSTANCE.downloadData();
				}
			});
		}
	}
}
