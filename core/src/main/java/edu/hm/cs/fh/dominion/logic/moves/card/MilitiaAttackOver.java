/**
 *
 */
package edu.hm.cs.fh.dominion.logic.moves.card;

import edu.hm.cs.fh.dominion.database.Settings;
import edu.hm.cs.fh.dominion.database.cards.KingdomCard;
import edu.hm.cs.fh.dominion.database.full.State;
import edu.hm.cs.fh.dominion.database.full.WriteableGame;
import edu.hm.cs.fh.dominion.database.full.WriteablePlayer;
import edu.hm.cs.fh.dominion.logic.moves.BaseMove;
import edu.hm.cs.fh.dominion.logic.moves.check.CheckFactory;
import edu.hm.cs.fh.dominion.logic.moves.check.IsCurrentPlayerCheck;
import edu.hm.cs.fh.dominion.logic.moves.check.IsCurrentStateCheck;

/**
 * The attack by the militia is over.
 *
 * @author Fabio Hellmann, info@fabio-hellmann.de
 * @version 06.05.2014
 */
public class MilitiaAttackOver extends BaseMove {

    /**
     * Creates a new attack militia over move.
     *
     * @param game   to reference.
     * @param player who want to act.
     */
    public MilitiaAttackOver(final WriteableGame game, final WriteablePlayer player) {
        super(game, player);
        addCheck(new IsCurrentStateCheck(State.ATTACK_YIELD));
        addCheck(new IsCurrentPlayerCheck());
        addCheck(CheckFactory.isNotAttacker());
        addCheck(CheckFactory.isAttackCard(KingdomCard.MILITIA));
        addCheck(CheckFactory.isHandcardSizeEqual(Settings.MILITIA_CARDS_TO_HOLD));
    }

    @Override
    public void onFire() {
        getGame().setState(State.ATTACK);
        getGame().nextPlayer();
    }
}
