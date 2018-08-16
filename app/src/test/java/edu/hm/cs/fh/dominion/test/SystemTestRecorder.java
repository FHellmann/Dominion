/**
 *
 */
package edu.hm.cs.fh.dominion.test;

import edu.hm.cs.fh.dominion.DominionMain;
import edu.hm.cs.fh.dominion.ui.Recorder;
import edu.hm.cs.fh.dominion.ui.ai.RobotDefender;
import edu.hm.cs.fh.dominion.ui.ai.RobotMilitia;
import edu.hm.cs.fh.dominion.ui.ai.RobotSorcerer;
import edu.hm.cs.fh.dominion.ui.ai.RobotX;

import java.io.IOException;

/**
 * Tests the system
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 23.05.2014
 */
class SystemTestRecorder {
    /**
     * Starts the system test.
     *
     * @param args ignored!
     * @throws IOException ignored!
     */
    public static void main(final String[] args) throws Exception {
        DominionMain.main(new String[]{RobotSorcerer.class.getSimpleName() + "=Merlin",
                RobotMilitia.class.getSimpleName() + "=Rambo", RobotDefender.class.getSimpleName() + "=Feigling",
                RobotX.class.getSimpleName() + "=Zufall", Recorder.class.getSimpleName() + "=SystemTest"});
    }
}
