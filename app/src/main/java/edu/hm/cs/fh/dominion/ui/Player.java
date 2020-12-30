package edu.hm.cs.fh.dominion.ui;

import edu.hm.cs.fh.dominion.ui.ai.*;
import edu.hm.cs.fh.dominion.ui.ai.dql.RobotReinforced;

import java.util.Optional;
import java.util.stream.Stream;

public enum Player {
    /* AI */
    ROBOT1(Robot1.class.getName()),
    ROBOT_DEFENDER(RobotDefender.class.getName()),
    ROBOT_MILITIA(RobotMilitia.class.getName()),
    ROBOT_SORCERER(RobotSorcerer.class.getName()),
    ROBOT_X(RobotX.class.getName()),
    ROBOT_REINFORCED(RobotReinforced.class.getName()),
    /* HUMAN */
    JAVA_FX_PLAYER(JavaFxPlayer.class.getName()),
    CONSOLE_PLAYER(ConsolePlayer.class.getName()),
    NET_PLAYER(NetPlayer.class.getName()),
    /* TEST */
    RECORDER(Recorder.class.getName()),
    REPLAYER(Replayer.class.getName());

    private final String playerClass;

    Player(final String playerClass) {
        this.playerClass = playerClass;
    }

    public static Optional<String> findPlayerClassByName(final String playerClassName) {
        return Stream.of(values())
                .map(player -> player.playerClass)
                .filter(playerClass -> playerClass.endsWith(playerClassName))
                .findFirst();
    }
}
