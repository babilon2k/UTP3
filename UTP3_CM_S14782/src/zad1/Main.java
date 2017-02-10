/**
 *
 *  @author Czerniak Mieszko S14782
 *
 */

package zad1;

import javax.swing.SwingUtilities;

/*
 * Napisać program, w którym uruchamiane zadania pokazywane są na liście.
 * Zadania z listy możemy odpytywac o ich stan, anulować, pokazywac ich wyniki,
 * gdy są gotowe itp.
 */
public class Main
{

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{

			@Override
			public void run()
			{
				new App();
			}
		});

	}

}
