package edu.hm.cs.fh.dominion;

import edu.hm.cs.fh.dominion.ui.RecorderStub;
import edu.hm.cs.fh.dominion.ui.ai.RobotDefender;
import edu.hm.cs.fh.dominion.ui.ai.RobotMilitia;
import edu.hm.cs.fh.dominion.ui.ai.RobotSorcerer;
import edu.hm.cs.fh.dominion.ui.ai.RobotX;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class DominionIntegrationTest {
    private static final String RECORD_FILE = "SYSTEM_TEST";

    @After
    public void tearDown() throws IOException {
        java.nio.file.Files.delete(new File(RecorderStub.DIRECTORY, RECORD_FILE).toPath());
        java.nio.file.Files.delete(RecorderStub.DIRECTORY.toPath());
    }

    @Test(timeout = 10_000)
    public void test() throws Exception {
        final String playerName1 = "Sorcerer";
        final String playerName2 = "Militia";
        final String playerName3 = "Defender";
        final String playerName4 = "Random";

        DominionMain.main(new String[]{
                RobotSorcerer.class.getSimpleName() + "=" + playerName1,
                RobotMilitia.class.getSimpleName() + "=" + playerName2,
                RobotDefender.class.getSimpleName() + "=" + playerName3,
                RobotX.class.getSimpleName() + "=" + playerName4,
                "-r",
                RECORD_FILE
        });

        DominionMain.main(new String[]{
                "-p",
                RECORD_FILE
        });

        assertTrue(true);
    }
}