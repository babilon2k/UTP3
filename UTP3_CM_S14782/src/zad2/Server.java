package zad2;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.io.IOException;

public class Server implements Future<Object>, Runnable
{
	protected Thread runningThread = null;
	protected int serverPort;
	protected boolean isStopped = false;
	protected ServerSocket serverSocket = null;

	public Server(int port)
	{
		this.serverPort = port;
	}

	public void run()
	{
		synchronized (this)
		{
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();
		while (!isStopped())
		{
			Socket clientSocket = null;
			try
			{
				clientSocket = this.serverSocket.accept();
			} catch (IOException e)
			{
				if (isStopped())
				{
					System.out.println("Server stopped.");
					return;
				}
				throw new RuntimeException("RuntimeException. Problems with connection",
						e);
			}
			new Thread(new Worker(clientSocket, "Server"))
					.start();
		}
		System.out.println("Server stopped.");
	}

	private synchronized boolean isStopped()
	{
		return this.isStopped;
	}

	public synchronized void stop()
	{
		this.isStopped = true;
		try
		{
			this.serverSocket.close();
		} catch (IOException e)
		{
			throw new RuntimeException("Error closing server", e);
		}
	}

	private void openServerSocket()
	{
		try
		{
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e)
		{
			throw new RuntimeException("Cannot open port"+this.serverPort, e);
		}
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning)
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
		return true;
	}
}