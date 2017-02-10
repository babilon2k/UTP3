/**
 *
 *  @author Czerniak Mieszko S14782
 *
 */

package zad4;

/*	Napisać program Author-Writer z wykładu przy użyciu blokujących kolejek.
*	Jako argumenty program otrzymuje napisy, które co sekundę ma generować Author.
*	Writer ma je wypisywać na konsoli.
*/

public class Main
{
	public static void main(String[] args)
	{
		Author autor = new Author(args);
		new Thread(autor).start();
		new Thread(new Writer(autor)).start();
	}
}
