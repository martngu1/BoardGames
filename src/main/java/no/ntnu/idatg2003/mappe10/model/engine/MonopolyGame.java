package no.ntnu.idatg2003.mappe10.model.engine;

import no.ntnu.idatg2003.mappe10.model.board.BoardGameObserver;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.tile.CruiseDock;
import no.ntnu.idatg2003.mappe10.model.tile.MonopolyTile;
import no.ntnu.idatg2003.mappe10.model.tile.Property;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;

public class MonopolyGame extends BoardGame {

    public MonopolyGame() {
        super();
    }


    @Override
    public void onPassStartTile(Player player){
        player.addToBalance(200);
        System.out.println(player.getName() + " passed the start tile and received 200.");
    }

    public int countCruiseDocksOwnedByPlayer(Player player) {
        int count = 0;
        int amountOfTiles = getBoard().getNumberOfTiles();

        for (int tileId = 1; tileId <= amountOfTiles; tileId++) {
            Tile tile = getBoard().getTile(tileId);

            MonopolyTile monopolyTile = tile.getMonopolyTile();
            if (monopolyTile != null) {
                Property property = monopolyTile.getProperty();
                if (property instanceof CruiseDock && property.getOwner() == player) {
                    count++;
                }
            }
        }
        return count;
    }


}

