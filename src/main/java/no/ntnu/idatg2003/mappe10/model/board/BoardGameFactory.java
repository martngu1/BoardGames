package no.ntnu.idatg2003.mappe10.model.board;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.engine.MonopolyGame;
import no.ntnu.idatg2003.mappe10.model.filehandler.gson.BoardFileReaderGson;
import no.ntnu.idatg2003.mappe10.model.tile.Country;
import no.ntnu.idatg2003.mappe10.model.tile.MonopolyTile;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;
import no.ntnu.idatg2003.mappe10.model.tile.tileaction.*;

import java.util.List;


/**
 * The BoardGameFactory class is responsible for creating instances of the
 * BoardGame class with specific configurations. It sets up the board, players,
 * and actions for the game.
 */
public class BoardGameFactory {

    private BoardFileReaderGson gsonReader;

    public BoardGameFactory() {
        this.gsonReader = new BoardFileReaderGson();
    }

    /**
     * Creates a BoardGame instance with a ladder game configuration.
     * The game has 90 tiles, 9 rows, and 10 columns. Some tiles are configured
     * with ladder actions.
     *
     * @return a configured BoardGame instance based on the ladder game
     */
    public BoardGame createLadderGame() {
        BoardGame boardGame = new BoardGame();
        boardGame.createDice(2);
        boardGame.createBoard(90, 9, 10);

        // Set up ladder actions on specific tiles
        boardGame.getBoard().getTile(3).setLandAction(new LadderAction(17, "Climb to tile 17" ));
        boardGame.getBoard().getTile(11).setLandAction(new LadderAction(25, "Climb to tile 25" ));
        boardGame.getBoard().getTile(29).setLandAction(new LadderAction(9, "Fall to tile 9" ));
        boardGame.getBoard().getTile(41).setLandAction(new LadderAction(21, "Fall to tile 21" ));
        boardGame.getBoard().getTile(46).setLandAction(new LadderAction(63, "Climb to tile 63" ));
        boardGame.getBoard().getTile(56).setLandAction(new LadderAction(33, "Fall to tile 33" ));
        boardGame.getBoard().getTile(72).setLandAction(new LadderAction(26, "Climb to tile 26" ));
        boardGame.getBoard().getTile(87).setLandAction(new LadderAction(55, "Fall to tile 55" ));

        // Set up prison action on board
        for (int i : new int[]{10, 32, 77}) {
            boardGame.getBoard().getTile(i).setLandAction(new PrisonAction("has been jailed for 1 turn" ));
        }

        // Set up win action on the last tile
        boardGame.getBoard().getLastTile().setLandAction(new WinAction("You win!"));

        // Set tile coords for canvas
        int rows = boardGame.getBoard().getNumberOfRows();
        int columns = boardGame.getBoard().getNumberOfColumns();
        int numberOfTiles = boardGame.getBoard().getLastTile().getTileId();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                // if row is even, tileId = r * columns + c + 1.
                // if row is odd, tileId = r * columns + (columns - c)
                // Help from ChatGPT to find the formula.
                int tileId = (r % 2 == 0) ? r * columns + c + 1 : r * columns + (columns - c);
                if (tileId > numberOfTiles) {
                    break;
                }
                boardGame.getBoard().getTile(tileId).setCoordinate(r, c);
            }
        }

        return boardGame;
    }

    public BoardGame createMonopolyGame() {
        BoardGame boardGame = new MonopolyGame();
        boardGame.createDice(2);
        boardGame.createBoard(40, 11, 11);

        CountryFactory countryFactory = new CountryFactory();

        boardGame.getBoard().getTile(2).setMonopolyTile(new MonopolyTile(2, countryFactory.getPropertyByCountryAndIndex("Mongolia", 0)));
        boardGame.getBoard().getTile(2).setLandAction(new PropertyAction("Property action for Philippines"));
        boardGame.getBoard().getTile(3).setMonopolyTile(new MonopolyTile(3, countryFactory.getPropertyByCountryAndIndex("Mongolia", 1)));
        boardGame.getBoard().getTile(3).setLandAction(new PropertyAction("Property action for Philippines"));
        boardGame.getBoard().getTile(4).setMonopolyTile(new MonopolyTile(4, countryFactory.getPropertyByCountryAndIndex("Mongolia", 2)));
        boardGame.getBoard().getTile(4).setLandAction(new PropertyAction("Property action for Philippines"));


        boardGame.getBoard().getTile(5).setMonopolyTile(new MonopolyTile(5, countryFactory.getCruiseDockByName("Yokohoma Harbour Terminal")));
        boardGame.getBoard().getTile(5).setLandAction(new PropertyAction("Property action for Cruise Dock: Yokohoma Harbour Terminal"));

        boardGame.getBoard().getTile(6).setLandAction(new TaxAction(75, "Taxed 75"));

        boardGame.getBoard().getTile(7).setMonopolyTile(new MonopolyTile(7, countryFactory.getPropertyByCountryAndIndex("Philippines", 0)));
        boardGame.getBoard().getTile(7).setLandAction(new PropertyAction("Property action for Philippines"));
        boardGame.getBoard().getTile(8).setMonopolyTile(new MonopolyTile(8, countryFactory.getPropertyByCountryAndIndex("Philippines", 1)));
        boardGame.getBoard().getTile(8).setLandAction(new PropertyAction("Property action for Philippines"));

        boardGame.getBoard().getTile(9).setLandAction(new ChanceCardAction("Chance"));

        boardGame.getBoard().getTile(10).setMonopolyTile(new MonopolyTile(10, countryFactory.getPropertyByCountryAndIndex("Philippines", 2)));
        boardGame.getBoard().getTile(10).setLandAction(new PropertyAction("Property action for Philippines"));

        boardGame.getBoard().getTile(11).setLandAction(new PrisonAction("has been jailed for 1 turn"));

        boardGame.getBoard().getTile(12).setMonopolyTile(new MonopolyTile(12, countryFactory.getPropertyByCountryAndIndex("Vietnam", 0)));
        boardGame.getBoard().getTile(12).setLandAction(new PropertyAction("Property action for Vietnam"));
        boardGame.getBoard().getTile(13).setMonopolyTile(new MonopolyTile(13, countryFactory.getPropertyByCountryAndIndex("Vietnam", 1)));
        boardGame.getBoard().getTile(13).setLandAction(new PropertyAction("Property action for Vietnam"));
        boardGame.getBoard().getTile(14).setMonopolyTile(new MonopolyTile(14, countryFactory.getPropertyByCountryAndIndex("Vietnam", 2)));
        boardGame.getBoard().getTile(14).setLandAction(new PropertyAction("Property action for Vietnam"));

        boardGame.getBoard().getTile(15).setMonopolyTile(new MonopolyTile(15, countryFactory.getCruiseDockByName("Jade Lotus Dock")));
        boardGame.getBoard().getTile(15).setLandAction(new PropertyAction("Property action for Cruise Dock: Jade Lotus Dock"));

        boardGame.getBoard().getTile(16).setLandAction(new TaxAction(100, "Taxed 100"));

        boardGame.getBoard().getTile(17).setMonopolyTile(new MonopolyTile(17, countryFactory.getPropertyByCountryAndIndex("Thailand", 0)));
        boardGame.getBoard().getTile(17).setLandAction(new PropertyAction("Property action for Thailand"));
        boardGame.getBoard().getTile(18).setMonopolyTile(new MonopolyTile(18, countryFactory.getPropertyByCountryAndIndex("Thailand", 1)));
        boardGame.getBoard().getTile(18).setLandAction(new PropertyAction("Property action for Thailand"));

        boardGame.getBoard().getTile(19).setLandAction(new ChanceCardAction("Chance"));

        boardGame.getBoard().getTile(20).setMonopolyTile(new MonopolyTile(20, countryFactory.getPropertyByCountryAndIndex("Thailand", 2)));
        boardGame.getBoard().getTile(20).setLandAction(new PropertyAction("Property action for Thailand"));

        // Tile 21, Free Parking

        // Tile 22, Indonesia
        boardGame.getBoard().getTile(22).setMonopolyTile(new MonopolyTile(22, countryFactory.getPropertyByCountryAndIndex("Indonesia", 0)));
        boardGame.getBoard().getTile(22).setLandAction(new PropertyAction("Property action for Indonesia"));
        boardGame.getBoard().getTile(23).setMonopolyTile(new MonopolyTile(23, countryFactory.getPropertyByCountryAndIndex("Indonesia", 1)));
        boardGame.getBoard().getTile(23).setLandAction(new PropertyAction("Property action for Indonesia"));

        boardGame.getBoard().getTile(24).setMonopolyTile(new MonopolyTile(24, countryFactory.getCruiseDockByName("Marina Bay Cruise Center")));
        boardGame.getBoard().getTile(24).setLandAction(new PropertyAction("Property action for Cruise Dock: Marina Bay Cruise Center"));


        boardGame.getBoard().getTile(25).setMonopolyTile(new MonopolyTile(25, countryFactory.getPropertyByCountryAndIndex("Indonesia", 2)));
        boardGame.getBoard().getTile(25).setLandAction(new PropertyAction("Property action for Indonesia"));



        boardGame.getBoard().getTile(26).setMonopolyTile(new MonopolyTile(26, countryFactory.getPropertyByCountryAndIndex("China", 0)));
        boardGame.getBoard().getTile(26).setLandAction(new PropertyAction("Property action for China"));
        boardGame.getBoard().getTile(27).setMonopolyTile(new MonopolyTile(27, countryFactory.getPropertyByCountryAndIndex("China", 1)));
        boardGame.getBoard().getTile(27).setLandAction(new PropertyAction("Property action for China"));
        boardGame.getBoard().getTile(28).setMonopolyTile(new MonopolyTile(28, countryFactory.getPropertyByCountryAndIndex("China", 2)));
        boardGame.getBoard().getTile(28).setLandAction(new PropertyAction("Property action for China"));

        boardGame.getBoard().getTile(29).setLandAction(new ChanceCardAction("Chance"));
        boardGame.getBoard().getTile(30).setLandAction(new TaxAction(100, "Taxed 150"));

        // Tile 31 Free Parking

        boardGame.getBoard().getTile(32).setLandAction(new ChanceCardAction("Chance"));

        boardGame.getBoard().getTile(33).setMonopolyTile(new MonopolyTile(33, countryFactory.getPropertyByCountryAndIndex("South Korea", 0)));
        boardGame.getBoard().getTile(33).setLandAction(new PropertyAction("Property action for South Korea"));
        boardGame.getBoard().getTile(34).setMonopolyTile(new MonopolyTile(34, countryFactory.getPropertyByCountryAndIndex("South Korea", 1)));
        boardGame.getBoard().getTile(34).setLandAction(new PropertyAction("Property action for South Korea"));
        boardGame.getBoard().getTile(35).setMonopolyTile(new MonopolyTile(35, countryFactory.getPropertyByCountryAndIndex("South Korea", 2)));
        boardGame.getBoard().getTile(35).setLandAction(new PropertyAction("Property action for South Korea"));

        boardGame.getBoard().getTile(36).setMonopolyTile(new MonopolyTile(36, countryFactory.getCruiseDockByName("Dragon Pearl Port")));
        boardGame.getBoard().getTile(36).setLandAction(new PropertyAction("Property action for Cruise Dock: Dragon Pearl Port"));

        boardGame.getBoard().getTile(37).setMonopolyTile(new MonopolyTile(37, countryFactory.getPropertyByCountryAndIndex("Japan", 0)));
        boardGame.getBoard().getTile(37).setLandAction(new PropertyAction("Property action for Japan"));

        boardGame.getBoard().getTile(38).setLandAction(new TaxAction(200, "Taxed 200"));

        boardGame.getBoard().getTile(39).setMonopolyTile(new MonopolyTile(39, countryFactory.getPropertyByCountryAndIndex("Japan", 1)));
        boardGame.getBoard().getTile(39).setLandAction(new PropertyAction("Property action for Japan"));
        boardGame.getBoard().getTile(40).setMonopolyTile(new MonopolyTile(40, countryFactory.getPropertyByCountryAndIndex("Japan", 2)));
        boardGame.getBoard().getTile(40).setLandAction(new PropertyAction("Property action for Japan"));

        setTileCoordinates(boardGame);

        boardGame.getBoard().getLastTile().setNextTile(boardGame.getBoard().getFirstTile());
        return boardGame;
    }

    private void setTileCoordinates(BoardGame boardGame) {
        int rows = boardGame.getBoard().getNumberOfRows();
        int columns = boardGame.getBoard().getNumberOfColumns();
        int numberOfTiles = boardGame.getBoard().getLastTile().getTileId();
        int tileId = 1;

        // Top row
        for (int c = 0; c < columns; c++) {
            if (tileId > numberOfTiles) break;
            boardGame.getBoard().getTile(tileId).setCoordinate(0, c);
            tileId++;
        }

        // Right column
        for (int r = 1; r < rows; r++) {
            if (tileId > numberOfTiles) break;
            boardGame.getBoard().getTile(tileId).setCoordinate(r, columns - 1);
            tileId++;
        }

        // Bottom row
        for (int c = columns - 2; c >= 0; c--) {
            if (tileId > numberOfTiles) break;
            boardGame.getBoard().getTile(tileId).setCoordinate(rows - 1, c);
            tileId++;
        }

        // Left column
        for (int r = rows - 2; r > 0; r--) {
            if (tileId > numberOfTiles) break;
            boardGame.getBoard().getTile(tileId).setCoordinate(r, 0);
            tileId++;
        }
    }

}
