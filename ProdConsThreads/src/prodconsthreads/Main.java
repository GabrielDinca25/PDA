package prodconsthreads;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
public class Main {

	public static int STACK_CAPACITY = 4;
	public static Semaphore semFree = new Semaphore(4);
	public static Semaphore semBusy = new Semaphore(0);
	public static List<Integer> prodQueue = new ArrayList<Integer>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int numberOfThreads = 5;
		ArrayList<Thread> threads = new ArrayList<>();
		for(int i=0; i<numberOfThreads; i++)
		{
			Producer producer = new Producer(i);
			threads.add(producer);
			Consumer consumer = new Consumer(i);
			threads.add(consumer);
		}
		
		for(Thread t : threads)
		{
			t.start();
		}
		
		for(Thread t : threads)
		{
			try
			{
				t.join();
			}
			catch (Exception e)
			{
				System.out.println("Exception: " + e);
			}
		}
//		for(int i=0; i<numberOfThreads; i++)
//		{
//			try
//			{
//				producer[i].join();
//				consumer[i].join();
//			}
//			catch (Exception e)
//			{
//				System.out.println("Exception: " + e);
//			}
//		}
//		
	}
}
