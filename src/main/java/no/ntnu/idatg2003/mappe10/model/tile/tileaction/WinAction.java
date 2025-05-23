package no.ntnu.idatg2003.mappe10.model.tile.tileaction;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;

/**
 * Represents an action that indicates a player has won the game.
 * The action sets the winner of the game to the player who performed the action.
 */
public class WinAction implements TileAction {

  private String description;

  public WinAction(String description) {
    this.description = description;
  }

    /**
     * Sets the winner of the game to the player who performed the action.
     *
     * @param player the player who is performing the action
     * @param game   the game instance
     */
  @Override
  public void performAction(Player player, BoardGame game) {
    game.setWinner(player);
  }

    /**
     * Gets the description of the action.
     *
     * @return the description of the action
     */
  @Override
  public String getDescription() {
    return description;
  }
}
