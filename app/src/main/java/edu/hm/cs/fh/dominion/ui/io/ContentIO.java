/**
 *
 */
package edu.hm.cs.fh.dominion.ui.io;

import java.io.Closeable;

/**
 * The input and output interface.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 21.04.2014
 */
public interface ContentIO extends Closeable {
	/**
	 * Send a text to the specified outputstream.
	 *
	 * @param text
	 *            to send.
	 */
	void sendOutput(String text);

	/**
	 * Reads an input from the specified inputstream.
	 *
	 * @return the input as int value.
	 */
	int getInput();
}
