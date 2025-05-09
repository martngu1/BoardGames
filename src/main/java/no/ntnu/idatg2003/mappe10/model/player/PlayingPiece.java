package no.ntnu.idatg2003.mappe10.model.player;

import no.ntnu.idatg2003.mappe10.model.tile.Tile;

public class PlayingPiece {
    private int id;
    private Player player;
    private Tile currentTile;

    public PlayingPiece(int id, Player player) {
        this.id = id;
        this.player = player;
    }

    public int getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }


    


}
