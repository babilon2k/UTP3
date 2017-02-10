/**
 *
 *  @author Czerniak Mieszko S14782
 *
 */

package zad3;

/*
 * Pokazać zastosowanie read/write locków i porównać ich efektywność w stosunku do zwykłej synchronizacji.
 */
public class Main
{

	public static void main(String[] args)
	{
		System.out.println("Synchronized Objects: ");
		Worker worker = new Worker();
		worker.main();
		System.out.println("Synchronized Objects Locks: ");
		WorkerLock worker2 = new WorkerLock();
		worker2.main();
		System.out.println("Read/Write Locks: ");
		WorkerRWL worker3 = new WorkerRWL();
		worker3.main();
	}
}
