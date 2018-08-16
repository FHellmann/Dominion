package edu.hm.cs.fh.dominion.ui.ai;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.database.ReadonlyPlayer;
import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.database.cards.TreasuryCard;
import edu.hm.cs.fh.dominion.database.cards.VictoryCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.json.CardTypeAdapter;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.moves.*;
import edu.hm.cs.fh.dominion.logic.moves.card.MilitiaAttack;
import edu.hm.cs.fh.dominion.logic.moves.card.WitchAttack;
import edu.hm.cs.fh.dominion.ui.AbstractRegisteredPlayer;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * A robot which learn's by using a reinforcement algorithm.
 *
 * @author Fabio Hellmann
 */
public class RobotReinforced extends AbstractRegisteredPlayer {
    private static final Strategy STRATEGY = Strategy.EPSILON_GREEDY;
    private static final double ALPHA = 0.8;
    private static final double GAMMA = 0.95;
    private static final double FINAL_WIN_REWARD = 100.0;
    private static final double FINAL_LOOSE_REWARD = -100.0;
    private static final double DEFAULT_REWARD = -0.1;
    private final Map<State, List<QValueContainer>> stateListMap = new HashMap<>();
    private final List<QValueContainer> historyList = new ArrayList<>();
    private final Gson gson;

    /**
     * Creates a new player.
     *
     * @param game  of datastoreage.
     * @param logic for every logical check.
     * @param name  of the player.
     */
    public RobotReinforced(ReadonlyGame game, Logic logic, String name) {
        super(game, logic, name);
        gson = new GsonBuilder()
                .registerTypeAdapter(Card.class, new CardTypeAdapter())
                .create();

        initSetupState();
        initActionState();
        initActionResolveState();
        initAttackState();
        initAttackYieldState();
        initPurchaseState();
    }

    private void initSetupState() {
        final List<QValueContainer> qValueContainers = new ArrayList<>();
        Stream.of(KingdomCard.values())
                .map(card -> new QValueContainer(Action.SELECT_KINGDOM_CARD, State.SETUP, card))
                .collect(Collectors.toCollection(() -> qValueContainers));
        stateListMap.put(State.SETUP, qValueContainers);
    }

    private void initActionState() {
        final List<QValueContainer> qValueContainers = new ArrayList<>();
        Stream.of(KingdomCard.values())
                .map(card -> new QValueContainer(Action.PLAY_ACTION_CARD, State.ACTION, card))
                .collect(Collectors.toCollection(() -> qValueContainers));
        stateListMap.put(State.ACTION, qValueContainers);
    }

    private void initActionResolveState() {
        final List<QValueContainer> qValueContainers = new ArrayList<>();
        Stream.of(KingdomCard.values())
                .map(card -> new QValueContainer(Action.PLAY_ACTION_CARD, State.ACTION_RESOLVE, card))
                .collect(Collectors.toCollection(() -> qValueContainers));
        stateListMap.put(State.ACTION_RESOLVE, qValueContainers);
    }

    private void initAttackState() {
        final List<QValueContainer> qValueContainers = new ArrayList<>();
        Stream.of(KingdomCard.values())
                .map(card -> new QValueContainer(Action.ATTACK, State.ATTACK, card))
                .collect(Collectors.toCollection(() -> qValueContainers));
        stateListMap.put(State.ATTACK, qValueContainers);
    }

    private void initAttackYieldState() {
        final List<QValueContainer> qValueContainers = new ArrayList<>();
        Stream.of(KingdomCard.values())
                .filter(card -> card.getMetaData().hasType(Card.Type.REACTION))
                .map(card -> new QValueContainer(Action.ATTACK, State.ATTACK_YIELD, card))
                .collect(Collectors.toCollection(() -> qValueContainers));
        stateListMap.put(State.ATTACK_YIELD, qValueContainers);
    }

    private void initPurchaseState() {
        final List<QValueContainer> qValueContainers = new ArrayList<>();
        Stream.of(TreasuryCard.values())
                .map(card -> new QValueContainer(Action.PLAY_TREASURY_CARD, State.PURCHASE, card))
                .collect(Collectors.toCollection(() -> qValueContainers));
        Stream.of(KingdomCard.values())
                .map(card -> new QValueContainer(Action.BUY_CARD, State.PURCHASE, card))
                .collect(Collectors.toCollection(() -> qValueContainers));
        Stream.of(VictoryCard.values())
                .map(card -> new QValueContainer(Action.BUY_CARD, State.PURCHASE, card))
                .collect(Collectors.toCollection(() -> qValueContainers));
        Stream.of(TreasuryCard.values())
                .map(card -> new QValueContainer(Action.BUY_CARD, State.PURCHASE, card))
                .collect(Collectors.toCollection(() -> qValueContainers));
        stateListMap.put(State.PURCHASE, qValueContainers);
    }

    @Override
    public Move selectMove(List<Move> moves) {
        if (stateListMap.containsKey(getGame().getState())) {
            // Find the right QValueContainer
            final List<QValueContainer> qValueContainers = stateListMap.get(getGame().getState());
            final List<QValueContainer> qValueSelections = qValueContainers.stream()
                    .filter(container -> moves.stream().anyMatch(container.action::hasMoveClass))
                    .collect(Collectors.toList());

            // Move selection algorithm
            final QValueContainer qValueContainer = STRATEGY.apply(qValueSelections);

            // Update Q-Value
            historyList.add(qValueContainer);
            qValueContainer.qValue = calculateNewQValue(qValueContainer, DEFAULT_REWARD);
            return moves.stream()
                    .filter(qValueContainer.action::hasMoveClass)
                    .findFirst()
                    .orElseThrow(IllegalMonitorStateException::new);
        } else {
            return moves.get(0);
        }
    }

    private double calculateNewQValue(QValueContainer container, double reward) {
        return container.qValue + ALPHA * (reward + GAMMA * getMaxQValue(container) - container.qValue);
    }

    private double getMaxQValue(QValueContainer container) {
        return stateListMap.get(container.state).stream()
                .filter(tmp -> tmp.card == container.card)
                .mapToDouble(tmp -> tmp.qValue)
                .max()
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (data instanceof ViewGameResult || data instanceof ExitGame) {
            // Check if the reinforced robot has won
            final int winnerPoints = getGame().getPlayers()
                    .mapToInt(player -> player.getVictoryPoints().getCount())
                    .max()
                    .orElseThrow(IllegalStateException::new);
            final ReadonlyPlayer winner = getGame().getPlayers()
                    .filter(player -> player.getVictoryPoints().getCount() == winnerPoints)
                    .findFirst()
                    .orElseThrow(IllegalStateException::new);
            // Recalculate q values depending on reward
            if (winner.getName().equals(getPlayer().orElseThrow(IllegalStateException::new).getName())) {
                historyList.forEach(container -> calculateNewQValue(container, FINAL_WIN_REWARD));
            } else {
                historyList.forEach(container -> calculateNewQValue(container, FINAL_LOOSE_REWARD));
            }
        }
    }

    public void save(File file) {
        try {
            final String json = gson.toJson(stateListMap.values().stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList()));
            Files.asCharSink(file, Charsets.UTF_8).write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadIfAvailable(File file) {
        if (file.exists()) {
            try {
                final String cache = String.join("", Files.readLines(file, Charsets.UTF_8));
                final List<QValueContainer> loadedCache = gson.fromJson(cache, new TypeToken<List<QValueContainer>>() {
                }.getType());
                stateListMap.forEach((key, value) -> value.clear());
                loadedCache.forEach(container -> stateListMap.get(container.state).add(container));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static final class QValueContainer {
        private final Action action;
        private final State state;
        private final Card card;
        private double qValue;

        private QValueContainer(final Action action, final State state, final Card card) {
            this.action = action;
            this.state = state;
            this.card = card;
            qValue = Double.MAX_VALUE; // Optimistic
        }

        @Override
        public String toString() {
            return "{ Action=" + action + ", State=" + state + ", Card=" + card.getName() + ", qValue=" + qValue + " }";
        }
    }

    private enum Strategy {
        RANDOM(qValues -> qValues.size() == 1 ? 0 : new Random().nextInt(qValues.size() - 1)),
        Q_MAX(qValues -> IntStream.range(0, qValues.size())
                .filter(index -> qValues.get(index).qValue == qValues.stream()
                        .mapToDouble(tmp -> tmp.qValue)
                        .max()
                        .orElseThrow(IllegalStateException::new))
                .findFirst()
                .orElseThrow(IllegalStateException::new)),
        EPSILON_GREEDY(qValues -> new Random().nextDouble() >= 0.2 ? Q_MAX.strategyFunction.apply(qValues) : RANDOM.strategyFunction.apply(qValues));

        private final Function<List<QValueContainer>, Integer> strategyFunction;

        Strategy(final Function<List<QValueContainer>, Integer> strategyFunction) {
            this.strategyFunction = strategyFunction;
        }

        public QValueContainer apply(final List<QValueContainer> qValueList) {
            return qValueList.get(strategyFunction.apply(qValueList));
        }
    }

    private enum Action {
        BUY_CARD(BuyCard.class),
        PLAY_ACTION_CARD(PlayActionCard.class),
        PLAY_TREASURY_CARD(PlayAllTreasuryCards.class),
        ATTACK(MilitiaAttack.class, WitchAttack.class),
        SELECT_KINGDOM_CARD(SelectKingdomCard.class);

        private final Class<? extends Move>[] moveClass;

        @SafeVarargs
        Action(Class<? extends Move>... moveClass) {
            this.moveClass = moveClass;
        }

        boolean hasMoveClass(final Move move) {
            return Stream.of(moveClass).anyMatch(moveClass -> moveClass.isInstance(move));
        }
    }
}
