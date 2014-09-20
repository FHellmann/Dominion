/**
 *
 */
package edu.hm.cs.fh.dominion.test;

import java.io.IOException;

import edu.hm.cs.fh.dominion.DominionMain;
import edu.hm.cs.fh.dominion.ui.Recorder;
import edu.hm.cs.fh.dominion.ui.RobotDefender;
import edu.hm.cs.fh.dominion.ui.RobotMilitia;
import edu.hm.cs.fh.dominion.ui.RobotSorcerer;
import edu.hm.cs.fh.dominion.ui.RobotX;

/**
 * Tests the system
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 23.05.2014
 */
public class SystemTestRecorder {
	/**
	 * Starts the system test.
	 *
	 * @param args
	 *            ignored!
	 * @throws IOException
	 *             ignored!
	 */
	public static void main(final String[] args) throws IOException {
		DominionMain.main(new String[] { RobotSorcerer.class.getSimpleName() + "=Merlin",
				RobotMilitia.class.getSimpleName() + "=Rambo", RobotDefender.class.getSimpleName() + "=Feigling",
				RobotX.class.getSimpleName() + "=Zufall", Recorder.class.getSimpleName() + "=SystemTest" });
	}
}
