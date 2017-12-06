/**
 *
 */
package edu.hm.cs.fh.dominion.ui.io;

import java.io.IOException;
import java.util.Scanner;

/**
 * Handler for in- and output from the console.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 21.04.2014
 */
public class ConsoleIO implements ContentIO {
	/** Reades the input from the console. */
	private final Scanner scanner = new Scanner(System.in);

	@Override
	public int getInput() {
		System.out.print("Please enter your choice: ");
		System.out.flush();
		return scanner.nextInt();
	}

	@Override
	public void sendOutput(final String text) {
		System.out.println(text);
	}

	@Override
	public void close() throws IOException {
		scanner.close();
	}
}
