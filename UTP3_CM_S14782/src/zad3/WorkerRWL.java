package zad3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class WorkerRWL
{
	private Random random = new Random();
	private final ReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock readLock = rwl.readLock();
	private final Lock writeLock = rwl.writeLock();

	private List<Integer> list1 = new ArrayList<>();
	private List<Integer> list2 = new ArrayList<>();

	public void stageOne()
	{

		writeLock.lock();
		{
			try
			{
				Thread.sleep(1);
				list1.add(random.nextInt(100));
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			finally
			{

				writeLock.unlock();
			}
		}
	}

	public void stageTwo()
	{
		readLock.lock();
		{
			try
			{
				Thread.sleep(1);
				list2.add(random.nextInt(100));
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			} finally
			{
				readLock.unlock();
			}
		}

	}

	public void process()
	{
		for (int i = 0; i < 1000; i++)
		{
			stageOne();
			stageTwo();
		}
	}

	public void main()
	{
		System.out.println("Starting ...");
		long start = System.currentTimeMillis();
		Thread t1 = new Thread(new Runnable()
		{
			public void run()
			{
				process();
			}
		});

		Thread t2 = new Thread(new Runnable()
		{
			public void run()
			{
				process();
			}
		});

		t1.start();
		t2.start();

		try
		{
			t1.join();
			t2.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();

		System.out.println("Time taken: " + (end - start));
		System.out
				.println("List1: " + list1.size() + "; List2: " + list2.size());
	}
}
