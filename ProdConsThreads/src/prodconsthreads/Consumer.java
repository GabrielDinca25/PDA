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
				Main.semBusy.acquire();
				_mutex.lock();
				if(!Main.prodQueue.isEmpty())
				{
					Main.prodQueue.remove(0);
					System.out.println("[Consumer Thread:" + threadId + "]Product consumed, size = " + Main.prodQueue.size());
				}
			} catch (Exception e) {

	            e.printStackTrace();
	            continue;
	        } 
			finally
			{
	        	_mutex.unlock();
			}
        	Main.semFree.release();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
