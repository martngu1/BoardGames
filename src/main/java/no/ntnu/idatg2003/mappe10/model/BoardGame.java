package no.ntnu.idatg2003.mappe10.model;

import java.util.ArrayList;
import java.util.List;

public class BoardGame {
    private Board board;
    private Player currentPlayer;
    private List<Player> playerList;
    private Dice dice;
    private final int numberOfTiles = 100;

    /**
     * Creates a new player list. Old player list is overwritten.
     */
    public void createPlayerList(){
        playerList = new ArrayList<>();
    }

    /**
     * Adds a player to the board game.
     *
     * @param player the player to add
     */
    public void addPlayer(Player player) {
        playerList.add(player);
    }

    /**
     * Creates a new board with the given number of tiles.
     */
    public void createBoard(){
        board = new Board();
        Tile dummy = null;
        for (int i = numberOfTiles; i >= 1; i--){
            Tile tile = new Tile(i);
            board.addTile(tile);
            tile.setNextTile(dummy);
            dummy = tile;
        }

    }

    /**
     * Creates a new dice with the given number of sides.
     *
     * @param numberOfDice the number of dice to create
     */
    public void createDice(int numberOfDice){
        dice = new Dice(numberOfDice);
    }

    /**
     * Plays the game. Each player in the player list rolls the dice and moves the number of steps rolled.
     */
    public void play(){
        for (Player player : playerList) {
            currentPlayer = player;
            int diceRoll = dice.roll();
            currentPlayer.move(diceRoll);
        }
    }

    /**
     * Returns the player that has won the game.
     *
     * @return the player that has won the game
     */
    public Player getWinner(){
        return null;
    }

}
