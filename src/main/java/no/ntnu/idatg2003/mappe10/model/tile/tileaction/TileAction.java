package no.ntnu.idatg2003.mappe10.model.tile.tileaction;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;

/**
 * Represents an action that can be performed on a tile in the game.
 * The action can be performed by a player and may have a description.
 */
public interface TileAction {
    void performAction(Player player, BoardGame game);
    String getDescription();
}
