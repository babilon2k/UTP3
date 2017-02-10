/**
 *
 *  @author Czerniak Mieszko S14782
 *
 */

package zad2;

/* Napisać w sposób właściwy prosty przykładowy serwer wielowątkowy TCP/IP(na gniazdach).
 * Obsługę zleceń zrealizowac w postaci FutureTask.Zadbać o możliwość przerywania obsługi w każdym momencie.
 */

public class Main
{

	public static void main(String[] args)
	{
		Worker w = new Worker();
		Server server = new Server(8080);
		System.out.println("Starting Server...");
		new Thread(server).start();
		
		System.out.println("Server started!");
		try
		{	new Thread(w).start();
			Thread.sleep(10 * 1010);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("Stopping Server!");
		server.stop();
	}
}