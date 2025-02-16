package no.ntnu.idatg2003.mappe10.model;

import java.util.List;

/**
 * Represents a set of n individual die.
 */

public class BoardGame {
    private Board board;
    private Player currentPlayer;
    private List<Player> playerList;
    private Dice dice;
    private final int numberOfTiles = 100;

    public void addPlayer(Player player) {
        playerList.add(player);
    }

    public void createBoard(){
        Board board = new Board();
        Tile dummy = null;
        for (int i = numberOfTiles; i >= 1; i--){
            Tile tile = new Tile(i);
            board.addTile(tile);
            tile.setNextTile(dummy);
            dummy = tile;
        }

    }
    public void createDice(){
        dice = new Dice(2);
    }

    public void play(){
        for (Player player : playerList) {
            currentPlayer = player;
            int diceRoll = dice.roll();
            currentPlayer.move(diceRoll);
        }
    }
    public Player getWinner(){
        return null;
    }

}
