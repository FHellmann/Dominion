/**
 *
 */
package edu.hm.cs.fh.dominion.test;

import java.io.IOException;

import edu.hm.cs.fh.dominion.DominionMain;
import edu.hm.cs.fh.dominion.ui.Recorder;
import edu.hm.cs.fh.dominion.ui.Replayer;

/**
 * Tests the system
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 23.05.2014
 */
public class SystemTestReplayer {
	/**
	 * Starts the system test.
	 *
	 * @param args
	 *            ignored!
	 * @throws IOException
	 *             ignored!
	 */
	public static void main(final String[] args) throws Exception {
		final String testFile = "=HumanTest";
		DominionMain.main(new String[] { Replayer.class.getSimpleName() + testFile,
				Replayer.class.getSimpleName() + testFile, Replayer.class.getSimpleName() + testFile,
				Replayer.class.getSimpleName() + testFile, Recorder.class.getSimpleName() + testFile + "Equal" });
	}
}
