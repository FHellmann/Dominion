/**
 *
 */
package edu.hm.cs.fh.dominion.ui.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Enumeration;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Handler for in- and output from the network.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 21.04.2014
 */
public class NetIO implements ContentIO {
    /**
     * The port for the server to be opened.
     */
    private static final int SOCKET_PORT = 3947;
    /**
     * The server where the communication goes over.
     */
    private final NetServer server;
    private final NetClient client;

    /**
     * Creates a new network io handler.
     *
     * @throws IOException is thrown from the {@link ServerSocket}.
     */
    public NetIO() throws IOException {
        // init Server if necessary
        final Optional<String> serverHost = NetServer.isAvailable();
        if (serverHost.isPresent()) {
            server = null;
        } else {
            final NetServer server = new NetServer();
            server.start();
            this.server = server;
        }

        final String host = serverHost.orElse(InetAddress.getLocalHost().getHostName());
        client = new NetClient(new Socket(host, SOCKET_PORT));
    }

    @Override
    public int getInput() {
        return client.getInput();
    }

    @Override
    public void sendOutput(final String text) {
        client.sendOutput(text);
    }

    @Override
    public void close() throws IOException {
        client.close();
        Optional.ofNullable(server).ifPresent(NetServer::stop);
    }

    private static final class NetServer {
        /**
         * The server socket where the communication goes over.
         */
        private final ServerSocket serverSocket;

        private final ExecutorService services;

        NetServer() throws IOException {
            serverSocket = new ServerSocket(SOCKET_PORT);
            services = Executors.newCachedThreadPool();
        }

        void start() {
            services.execute(() -> {
                while (!services.isShutdown()) {
                    try {
                        final Socket socket = serverSocket.accept();
                        services.execute(new NetClient(socket));
                    } catch (IOException e) {
                        // ignore
                    }
                }
            });
        }

        void stop() {
            services.shutdown();
        }

        static Optional<String> isAvailable() {
            try {
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    final NetworkInterface networkInterface = interfaces.nextElement();
                    final Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        final InetAddress inetAddress = inetAddresses.nextElement();
                        if (inetAddress.isLoopbackAddress() || inetAddress.isAnyLocalAddress()) {
                            continue;
                        }
                        final String hostAddress = inetAddress.getHostAddress();

                        final int subNetEnding = hostAddress.lastIndexOf(".");
                        final String subNetMask = hostAddress.substring(0, subNetEnding);

                        final Optional<String> host = IntStream.rangeClosed(1, 254)
                                .mapToObj(index -> subNetMask + "." + index)
                                .peek(tmp -> System.out.println("Search Server: " + tmp))
                                .filter(tmp -> {
                                    try (Socket s = new Socket(tmp, SOCKET_PORT)) {
                                        return true;
                                    } catch (Exception ex) {
                                        return false;
                                    }
                                })
                                .peek(tmp -> System.out.println("Server detected: " + tmp))
                                .findAny();
                        if (host.isPresent()) {
                            return host;
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
            return Optional.empty();
        }
    }

    private static final class NetClient implements ContentIO, Runnable {
        private final Socket socket;
        /**
         * To send a message over the network.
         */
        private final PrintWriter out;
        /**
         * To read a message from the network.
         */
        private final Scanner in;

        NetClient(final Socket socket) throws IOException {
            this.socket = socket;
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        }

        @Override
        public void run() {

        }

        @Override
        public void sendOutput(String text) {
            out.println(text);
        }

        @Override
        public int getInput() {
            out.print("Please enter your choice: ");
            out.flush();
            return in.nextInt();
        }

        @Override
        public void close() throws IOException {
            socket.close();
        }
    }
}
