/**
 *
 */
package edu.hm.cs.fh.dominion.ui.io;

import java.io.*;

/**
 * Handler for output to a file.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 21.04.2014
 */
public class FileIO implements ContentIO {
    /**
     * The output writer.
     */
    private final PrintWriter writer;

    /**
     * Creates a new file io handler.
     *
     * @param file to write at.
     * @throws IOException is thrown if the {@link FileWriter} does not find the directory/file to write at.
     */
    public FileIO(final File file) throws IOException {
        writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
    }

    @Override
    public void sendOutput(final String text) {
        writer.append(text).append("\n");
        writer.flush();
    }

    @Override
    public int getInput() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
        writer.close();
    }
}
