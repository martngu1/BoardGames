package no.ntnu.idatg2003.mappe10.model.tile.tileaction;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;

/**
 * Represents an action that moves the player to a different tile on the board.
 * The action is represented by a ladder, which takes the player to a specified tile.
 */
public class LadderAction implements TileAction {
    private int destinationTileId;
    private String description;

    public LadderAction(int destinationTileId, String description){
        this.destinationTileId = destinationTileId;
        this.description = description;
    }

    /**
     * Gets the ID of the destination tile.
     *
     * @return the ID of the destination tile
     */
    public int getDestinationTileId() {
        return destinationTileId;
    }
    /**
     * Gets the description of the action.
     *
     * @return the description of the action
     */
    public String getDescription(){
        return description;
    }


    /**
     * Performs the action of moving the player to the destination tile.
     *
     * @param player the player who is performing the action
     * @param game   the game instance
     */
    @Override
    public void performAction(Player player, BoardGame game) {
        player.placeOnTile(game.getTileById(destinationTileId));
    }

}
