package no.ntnu.idatg2003.mappe10.model.tile;

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

    public void performAction(Player player){
        System.out.println("LadderAction performed");
    }


}
