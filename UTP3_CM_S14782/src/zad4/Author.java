/**
 *
 *  @author Czerniak Mieszko S14782
 *
 */

package zad4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Author extends Thread implements Runnable
{

	private BlockingQueue<String> queue = new ArrayBlockingQueue<String>(6);
	String[] s = {"Pies", "Kot", "Zebra", "Lew", "Owca", "Słoń"};

	public Author(String[] args)
	{
		s = args;
	}
	public void write()
	{

		for (int i = 0; i < s.length; i++)
		{
			System.out.println(s[i]);
			try
			{
				queue.put(s[i]);
				System.out.println(queue.take());
			} catch (InterruptedException e)
			{
				e.toString();
			}
		}

	}
	public BlockingQueue<String> getQueue()
	{

		return queue;
	}
	public void setQueue(BlockingQueue<String> queue)
	{
		this.queue = queue;
	}
	@Override
	public void run()
	{
		write();

	}
}
