package edu.hm.cs.fh.dominion.ui.ai;

import edu.hm.cs.fh.dominion.database.ReadonlyGame;
import edu.hm.cs.fh.dominion.database.ReadonlyPlayer;
import edu.hm.cs.fh.dominion.database.cards.Card;
import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.database.cards.TreasuryCard;
import edu.hm.cs.fh.dominion.database.cards.VictoryCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.logic.Logic;
import edu.hm.cs.fh.dominion.logic.moves.*;
import edu.hm.cs.fh.dominion.ui.AbstractRegisteredPlayer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RobotReinforced extends AbstractRegisteredPlayer {
    private static final Random RANDOM = new Random();
    private static final double ALPHA = 0.8;
    private static final double GAMMA = 0.95;
    private final Map<State, List<QValueContainer>> stateListMap = new HashMap<>();
    private final List<QValueContainer> historyList = new ArrayList<>();

    /**
     * Creates a new player.
     *
     * @param game  of datastoreage.
     * @param logic for every logical check.
     * @param name  of the player.
     */
    public RobotReinforced(ReadonlyGame game, Logic logic, String name) {
        super(game, logic, name);
        initSetupState();
        initActionState();
        initActionResolveState();
        initAttackState();
        initAttackYieldState();
        initPurchaseState();
        load();
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
                .map(card -> new QValueContainer(Action.PLAY_ACTION_CARD, State.ATTACK, card))
                .collect(Collectors.toCollection(() -> qValueContainers));
        stateListMap.put(State.ATTACK, qValueContainers);
    }

    private void initAttackYieldState() {
        final List<QValueContainer> qValueContainers = new ArrayList<>();
        Stream.of(KingdomCard.values())
                .map(card -> new QValueContainer(Action.PLAY_ACTION_CARD, State.ATTACK_YIELD, card))
                .collect(Collectors.toCollection(() -> qValueContainers));
        stateListMap.put(State.ATTACK_YIELD, qValueContainers);
    }

    private void initPurchaseState() {
        final List<QValueContainer> qValueContainers = new ArrayList<>();
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
        // Algorithm to select move
        final int moveIndex = RANDOM.nextInt(moves.size() - 1);
        final Move move = moves.get(moveIndex);

        // Find the right QValueContainer
        final List<QValueContainer> qValueContainers = stateListMap.get(getGame().getState());
        final Optional<QValueContainer> qValueContainer = qValueContainers.stream()
                .filter(container -> container.action.getMoveClass().isInstance(move))
                .findFirst();

        // Update Q-Value
        qValueContainer.ifPresent(container -> {
            historyList.add(container);
            container.qValue = calculateNewQValue(container, 0.0);
        });

        return move;
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
        if (data instanceof ViewGameResult) {
            // Check if the reinforced robot has won
            final int winnerPoints = getGame().getPlayers()
                    .mapToInt(player -> player.getVictoryPoints().getCount())
                    .max()
                    .orElseThrow(IllegalStateException::new);
            final ReadonlyPlayer winner = getGame().getPlayers()
                    .filter(player -> player.getVictoryPoints().getCount() == winnerPoints)
                    .findFirst()
                    .orElseThrow(IllegalStateException::new);
            if (winner.getName().equals(getPlayer().orElseThrow(IllegalStateException::new).getName())) {
                historyList.forEach(container -> calculateNewQValue(container, 10.0d));
                historyList.clear();
            }

            // Save q-value table
            save();
        }
    }

    private void save() {

    }

    private void load() {

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
    }

    private enum Action {
        BUY_CARD(BuyCard.class),
        PLAY_ACTION_CARD(PlayActionCard.class),
        SELECT_KINGDOM_CARD(SelectKingdomCard.class);

        private Class<? extends Move> moveClass;

        Action(Class<? extends Move> moveClass) {
            this.moveClass = moveClass;
        }

        public Class<? extends Move> getMoveClass() {
            return moveClass;
        }
    }
}
