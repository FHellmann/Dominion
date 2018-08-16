package edu.hm.cs.fh.dominion.ui;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.logic.Logic;

import java.io.File;
import java.io.IOException;

public class RecorderStub extends Recorder {
    public static final File DIRECTORY = Recorder.TEMP_DIRECTORY_DOMINION;

    /**
     * Creates a new recorder.
     *
     * @param game  of datastoreage.
     * @param logic for every logical check.
     * @param name  of the logger.
     * @throws IOException is thrown by the file-io.
     */
    public RecorderStub(ReadonlyGame game, Logic logic, String name) throws IOException {
        super(game, logic, name);
    }
}