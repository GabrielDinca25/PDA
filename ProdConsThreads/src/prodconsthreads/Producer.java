package prodconsthreads;
import java.util.concurrent.locks.Lock;
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
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try
			{
				Main.semFree.acquire();
				_mutex.lock();
				if(Main.prodQueue.size() <= Main.STACK_CAPACITY - 1)
				{
					Main.prodQueue.add(1);
					System.out.println("[Producer Thread:" + threadId + "]Product produced, size = " + Main.prodQueue.size());
				}
			} catch (Exception e) {
	            e.printStackTrace();
	            continue;
	        }
			finally
			{
				_mutex.unlock();

			}
			Main.semBusy.release();

		}
		
	}
}
