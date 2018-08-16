package edu.hm.cs.fh.dominion;

import edu.hm.cs.fh.dominion.database.full.Game;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.ui.UserInterface;
import edu.hm.cs.fh.dominion.ui.ai.RobotDefender;
import edu.hm.cs.fh.dominion.ui.ai.RobotMilitia;
import edu.hm.cs.fh.dominion.ui.ai.RobotReinforced;
import edu.hm.cs.fh.dominion.ui.ai.RobotSorcerer;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DominionSimulationMain {
    public static void main(String[] args) {
        System.out.println("Start simulation");
        for (int iteration = 0; iteration < 15_000; iteration++) {
            final long start = System.currentTimeMillis();
            final WriteableGame game = new Game();
            final Logic logic = new Logic(game);
            final List<UserInterface> uis = new ArrayList<>();

            final RobotReinforced robotReinforced = new RobotReinforced(game, logic, "A");
            final File reinforcementCache = new File("A-reinforced.json");
            robotReinforced.loadIfAvailable(reinforcementCache);
            uis.add(robotReinforced);
            uis.add(new RobotMilitia(game, logic, "Militia"));
            uis.add(new RobotSorcerer(game, logic, "Sorcerer"));
            uis.add(new RobotDefender(game, logic, "Defender"));
            //uis.add(new PublicViewer(game, logic));

            UserInterface.loop(game, logic, uis);

            robotReinforced.save(reinforcementCache);

            final String winnerName = game.getRwPlayers()
                    .max(Comparator.comparingInt(p -> p.getVictoryPoints().getCount()))
                    .get()
                    .getName();
            final long timeNeeded = System.currentTimeMillis() - start;
            final PrintStream printStream;
            if (winnerName.equals("A")) {
                printStream = System.err;
            } else {
                printStream = System.out;
            }
            printStream.println(iteration + ". Simulation -> Result: " + winnerName + " (Time needed: " + timeNeeded + " ms)");
        }
    }
}
