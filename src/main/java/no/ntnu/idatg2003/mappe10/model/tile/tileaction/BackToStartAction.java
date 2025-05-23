package no.ntnu.idatg2003.mappe10.model.tile.tileaction;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;

/**
 * Represents an action that sends the player back to the start tile.
 */
public class BackToStartAction implements TileAction{
    private String description;
    public BackToStartAction(String description) {
        this.description = description;
    }

    /**
     * Performs the action of sending the player back to the start tile.
     *
     * @param player the player who is performing the action
     * @param game   the game instance
     */
    @Override
    public void performAction(Player player, BoardGame game) {
        player.placeOnTile(game.getBoard().getFirstTile());
    }

    /**
     * Gets the description of the action.
     *
     * @return the description of the action
     */
    public String getDescription() {
        return description;
    }


}
