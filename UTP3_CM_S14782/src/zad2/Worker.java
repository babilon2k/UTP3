package zad2;

import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Worker implements Future<Object>, Runnable
{
	protected Socket clientSocket = null;
	protected String serverText = null;

	public Worker(Socket clientSocket, String serverText)
	{
		this.clientSocket = clientSocket;
		this.serverText = serverText;
	}

	public Worker()
	{
	}

	public void run()
	{
		try
		{
			for (int i = 0; i < 5; i++)
			{
				Thread.sleep(1000);
				long time = System.currentTimeMillis();
				System.out.println("Request processed: " + time + "\n"
						+ "Proces " + (i + 1));
			}
		} catch (Exception e)
		{
			// report exception somewhere.
			e.printStackTrace();
		}
	}

	@Override
	public boolean cancel(boolean arg0)
	{
		return false;
	}

	@Override
	public Object get() throws InterruptedException, ExecutionException
	{
		return null;
	}

	@Override
	public Object get(long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException
	{
		return null;
	}

	@Override
	public boolean isCancelled()
	{
		return false;
	}

	@Override
	public boolean isDone()
	{
		return false;
	}
}
