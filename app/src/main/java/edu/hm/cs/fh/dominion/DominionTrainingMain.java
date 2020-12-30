package edu.hm.cs.fh.dominion;

import edu.hm.cs.fh.dominion.database.full.Game;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.ui.UserInterface;
import edu.hm.cs.fh.dominion.ui.ai.RobotDefender;
import edu.hm.cs.fh.dominion.ui.ai.RobotMilitia;
import edu.hm.cs.fh.dominion.ui.ai.dql.RobotReinforced;
import edu.hm.cs.fh.dominion.ui.ai.RobotSorcerer;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DominionTrainingMain {
    public static void main(String[] args) {
        System.out.println("Start simulation");
        final String aiName = "C";
        boolean aiWon = false;
        int iteration = 0;
        long averageTimePerEpisode = 0;
        do {
            final long start = System.currentTimeMillis();
            final WriteableGame game = new Game();
            final Logic logic = new Logic(game);
            final List<UserInterface> uis = new ArrayList<>();

            final RobotReinforced robotReinforced = new RobotReinforced(game, logic, aiName);
            final File reinforcementCache = new File(aiName + "-reinforced.json");
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

            if (iteration % 10 == 0 && iteration > 0) {
                averageTimePerEpisode /= 10;
                System.out.println((iteration - 10) + "-" + iteration + ". Simulation (AVG Time needed: " + averageTimePerEpisode + " ms)");
                averageTimePerEpisode = 0;
            } else {
                averageTimePerEpisode += timeNeeded;
            }
            iteration++;

            if (winnerName.equals(aiName)) {
                aiWon = true;
            }
        } while (!aiWon);
        System.out.println(iteration + ". Simulation the AI won!");
    }
}
