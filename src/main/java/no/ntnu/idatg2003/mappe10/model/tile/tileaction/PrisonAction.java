package no.ntnu.idatg2003.mappe10.model.tile.tileaction;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;

/**
 * Represents an action that sends a player to prison.
 * The action is represented by a prison tile, which can only be occupied by one player at a time.
 */
public class PrisonAction implements TileAction {

    private final String description;
    private Player currentPrisoner;
    public PrisonAction(String description) {
        this.description = description;
    }

    /**
     * Gets the description of the action.
     *
     * @return the description of the action
     */
    public String getDescription() {
        return description;
    }
    /**
     * Sends the player to prison if no one is currently imprisoned.
     *
     */
    @Override
    public void performAction(Player player, BoardGame game) {
        // Only imprison if no one is currently in prison
        if (currentPrisoner == null || !currentPrisoner.shouldSkipTurn()) {
            currentPrisoner = player;
            player.setTurnsToSkip(1);
            System.out.println(player.getName() + " has been sent to prison and will skip their next turn.");
        } else {
            System.out.println(player.getName() + " avoided prison because it's already occupied by " + currentPrisoner.getName());
        }
    }
}