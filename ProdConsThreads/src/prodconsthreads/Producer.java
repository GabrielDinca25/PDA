package prodconsthreads;
import java.util.concurrent.locks.Lock;
import java.util.logging.*;
import java.util.concurrent.locks.ReentrantLock;

public class Producer extends Thread {
	public int threadId;
	private final Lock _mutex = new ReentrantLock(true); 

	Producer(int i)
	{
		threadId = i;
	}
	
	public void run() {
		
		while(true)
		{
			try 
			{
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("Exception caught: "+ e);			
			}
			
			try
			{
				_mutex.lock();
				System.out.println("[Producer Thread:" + threadId + "] lock");
				
				if(Main.prodQueue.size() >= Main.STACK_CAPACITY)
				{
					System.out.println("[Producer Thread:" + threadId + "] size is greater or equal than :" + Main.STACK_CAPACITY);
					synchronized(Main.condProd){
						System.out.println("[Producer Thread:" + threadId + "] is waiting");
						Main.condProd.wait();
					}
					System.out.println("[Producer Thread:" + threadId + "] stopped waiting (probably been notified)");
				}
				Main.prodQueue.add(1);
				System.out.println("[Producer Thread:" + threadId + "] added product to queue, size: " + Main.prodQueue.size());
				_mutex.unlock();
				System.out.println("[Producer Thread:" + threadId + "] unlock");
				//System.out.println("[Producer Thread:" + threadId + "]Product produced, size = " + Main.prodQueue.size());
				synchronized(Main.condCons){
					System.out.println("[Producer Thread:" + threadId + "] notified consumers");
					Main.condCons.notify();
				}

				
			} catch (Exception e) {
	            e.printStackTrace();
	            _mutex.unlock();
				continue;
	        }


		}
		
	}
}
