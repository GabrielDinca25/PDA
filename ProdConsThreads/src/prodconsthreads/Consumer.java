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
				
				if(!Main.prodQueue.isEmpty())
				{
					Main.condCons.wait();
					
				}
				Main.prodQueue.remove(0);
				_mutex.unlock();
				System.out.println("[Consumer Thread:" + threadId + "]Product consumed, size = " + Main.prodQueue.size());
				Main.condProd.notify();
				
			} catch (Exception e) {
	            e.printStackTrace();
	            continue;
	        } 
			finally
			{
	        	_mutex.unlock();
			}
        	
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				System.out.println("Exception caught: "+ e);			
			}

		}
		
	}
}
