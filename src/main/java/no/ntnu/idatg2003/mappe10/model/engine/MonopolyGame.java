package no.ntnu.idatg2003.mappe10.model.engine;

import no.ntnu.idatg2003.mappe10.model.board.BoardGameObserver;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.tile.*;

import java.util.ArrayList;
import java.util.Iterator;

public class MonopolyGame extends BoardGame {

    private ArrayList<Country> countries;
    private ArrayList<CruiseDock> cruiseDocks;

    public MonopolyGame() {
        super();
        this.countries = new ArrayList<>();
        this.cruiseDocks = new ArrayList<>();
    }

    public void addCountry(Country country) {
        countries.add(country);
    }

    public void addCruiseDock(CruiseDock cruiseDock) {
        cruiseDocks.add(cruiseDock);
    }

    public Iterator<Country> getCountries() {
        return countries.iterator();
    }

    public Iterator<CruiseDock> getCruiseDocks() {
        return cruiseDocks.iterator();
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
    public int getTotalSellValue(Player player){
        int totalValue = 0;
        int amountOfTiles = getBoard().getNumberOfTiles();
        for (int tileId = 1; tileId <= amountOfTiles; tileId++) {
            Tile tile = getBoard().getTile(tileId);

            MonopolyTile monopolyTile = tile.getMonopolyTile();
            if (monopolyTile != null) {
                Property property = monopolyTile.getProperty();
                if (property.getOwner() == player) {
                    // Sell value of property is the property price divided by 2
                    totalValue += property.getPrice() / 2;
                }
            }
        }
        return totalValue;
    }



}

