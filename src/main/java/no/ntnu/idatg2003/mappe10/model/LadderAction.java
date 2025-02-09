package no.ntnu.idatg2003.mappe10.model;

public class LadderAction implements TileAction {
    private int destinationTileId;

    public LadderAction(int destinationTileId, String description){
        this.destinationTileId = destinationTileId;
    }
    public void performAction(Player player){
        System.out.println("LadderAction performed");
    }


}
