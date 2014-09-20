/**
 *
 */
package edu.hm.cs.fh.dominion.database.full;

/**
 * Different types of states.
 *
 * @author Fabio Hellmann, fhellman@hm.edu
 * @version 29.03.2014
 */
public enum State {
	/** The initialize state. */
	INITIALIZE,
	/** The setup state. */
	SETUP,
	/** The action state. */
	ACTION,
	/** The resolve action state (optional). */
	ACTION_RESOLVE,
	/** The attack state (optional). */
	ATTACK,
	/** The resolve attack state (optional). */
	ATTACK_YIELD,
	/** The purchase state. */
	PURCHASE,
	/** The cleanup state. */
	CLEANUP,
	/** The game over state. */
	OVER,
	/** The last state if the game is over. */
	RESULTS,
	/** The last state if the game is over. */
	QUIT
}
