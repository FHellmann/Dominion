package edu.hm.cs.fh.dominion;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import edu.hm.cs.fh.dominion.ui.RecorderStub;
import edu.hm.cs.fh.dominion.ui.ai.RobotDefender;
import edu.hm.cs.fh.dominion.ui.ai.RobotMilitia;
import edu.hm.cs.fh.dominion.ui.ai.RobotSorcerer;
import edu.hm.cs.fh.dominion.ui.ai.RobotX;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DominionIntegrationTest {
    private static final String RECORD_FILE = "SYSTEM_TEST";
    private static final String REPLAY_FILE = "REPLAY_SYSTEM_TEST";
    private File systemTestFile;
    private File systemTestReplayedFile;

    @Before
    public void setUp() {
        systemTestFile = new File(RecorderStub.DIRECTORY, RECORD_FILE);
        systemTestReplayedFile = new File(RecorderStub.DIRECTORY, REPLAY_FILE);
    }

    @After
    public void tearDown() {
        systemTestFile.delete();
        systemTestReplayedFile.delete();
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
                RECORD_FILE,
                "-r",
                REPLAY_FILE
        });

        final List<String> inputLines = Files.readLines(systemTestFile, Charsets.UTF_8);
        final List<String> replayedLines = Files.readLines(systemTestReplayedFile, Charsets.UTF_8);

        for (int lineNr = 0; lineNr < inputLines.size(); lineNr++) {
            assertThat(
                    "Line nr. " + lineNr + " mismatched",
                    inputLines.get(lineNr),
                    is(equalTo(replayedLines.get(lineNr)))
            );
        }
    }
}