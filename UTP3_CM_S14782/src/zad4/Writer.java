/**
 *
 *  @author Czerniak Mieszko S14782
 *
 */

package zad4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Writer implements Runnable
{
	// Teksty txtArea;
	Author autor;
	String[] s = {"Pies", "Kot", "Zebra", "Lew", "Owca", "Słoń"};
	private BlockingQueue<String> queue = new ArrayBlockingQueue<String>(7);
	public Writer(Author autor)
	{
		this.autor = autor;
	}

	public void print() throws InterruptedException
	{	
		for (int i = 0; i < s.length; i++)
		{
			Thread.sleep(1000);
			try
			{
				queue.put(s[i]);
				System.out.println("--->> "+queue.take());
			} catch (InterruptedException e)
			{
				e.toString();
			}}
	}

	@Override
	public void run()
	{
		try
		{
			print();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
