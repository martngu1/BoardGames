package no.ntnu.idatg2003.mappe10.model.tile.tileaction;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;

public class LadderAction implements TileAction {
    private int destinationTileId;
    private String description;

    public LadderAction(int destinationTileId, String description){
        this.destinationTileId = destinationTileId;
        this.description = description;
    }

    public int getDestinationTileId() {
        return destinationTileId;
    }
    public String getDescription(){
        return description;
    }



    @Override
    public void performAction(Player player, BoardGame game) {
        player.placeOnTile(game.getTileById(destinationTileId));
    }


}
