package prodconsthreads;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Consumer extends Thread {

	public int threadId;
	private final Lock _mutex = new ReentrantLock(true); 
	
	Consumer(int i)
	{
		threadId = i;
	}
	
	public void run() {
		
		while(true)
		{
			try
			{	
				_mutex.lock();
				
				if(Main.prodQueue.isEmpty())
				{
					System.out.println("[Consumer Thread:" + threadId + "] Queue is empty");
					synchronized(Main.condCons){
						System.out.println("[Consumer Thread:" + threadId + "] waiting");
						Main.condCons.wait();
					}
					System.out.println("[Consumer Thread:" + threadId + "] stopped waiting (probably been notified)");
				}
				
				Main.prodQueue.remove(0);
				System.out.println("[Consumer Thread:" + threadId + "] consumed product");
				_mutex.unlock();
				System.out.println("[Consumer Thread:" + threadId + "]Product consumed, size = " + Main.prodQueue.size());
				synchronized(Main.condProd){
					System.out.println("[Consumer Thread:" + threadId + "] notifies producer");
					Main.condProd.notify();
				}

				
			} catch (Exception e) {
	            e.printStackTrace();
	            _mutex.unlock();
	            continue;
	        } 
        	
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				System.out.println("Exception caught: "+ e);			
			}

		}
		
	}
}
