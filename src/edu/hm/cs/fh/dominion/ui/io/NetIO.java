/**
 *
 */
package edu.hm.cs.fh.dominion.ui.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Handler for in- and output from the network.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 21.04.2014
 */
public class NetIO implements ContentIO {
	/** The port for the server to be opened. */
	private static final int SOCKET_PORT = 2014;
	/** To send a message over the network. */
	private final PrintWriter writer;
	/** To read a message from the network. */
	private final Scanner scanner;
	/** The server socket where the communication goes over. */
	private final ServerSocket serverSocket;

	/**
	 * Creates a new network io handler.
	 *
	 * @throws IOException
	 *             is thrown from the {@link ServerSocket}.
	 *
	 */
	public NetIO() throws IOException {
		serverSocket = new ServerSocket(SOCKET_PORT);
		final Socket socket = serverSocket.accept();
		writer = new PrintWriter(socket.getOutputStream(), true);
		scanner = new Scanner(socket.getInputStream());
	}

	@Override
	public int getInput() {
		writer.print("Please enter your choice: ");
		writer.flush();
		return scanner.nextInt();
	}

	@Override
	public void sendOutput(final String text) {
		writer.println(text);
	}

	@Override
	public void close() throws IOException {
		serverSocket.close();
	}
}
