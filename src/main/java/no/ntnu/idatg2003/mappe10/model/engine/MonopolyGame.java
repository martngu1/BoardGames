package no.ntnu.idatg2003.mappe10.model.engine;

import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.property.CruiseDock;
import no.ntnu.idatg2003.mappe10.model.tile.MonopolyTile;
import no.ntnu.idatg2003.mappe10.model.property.Property;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;


/**
 * Represents a Monopoly game.
 */
public class MonopolyGame extends BoardGame {

    public MonopolyGame() {
        super();
    }


    /**
     * This method is called when a player passes the start tile.
     * The player receives 200 in balance.
     *
     * @param player the player who passed the start tile
     */
    @Override
    public void onPassStartTile(Player player){
        player.addToBalance(0);
        System.out.println(player.getName() + " passed the start tile and received 200.");
    }

    /**
     * Counts the number of cruise docks owned by a player.
     *
     * @param player the player who landed on the tile
     *
     * @return the number of cruise docks owned by the player
     */
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

