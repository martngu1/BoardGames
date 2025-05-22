package no.ntnu.idatg2003.mappe10.model.board;

import no.ntnu.idatg2003.mappe10.model.coordinate.Coordinate;
import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.engine.LostDiamondGame;
import no.ntnu.idatg2003.mappe10.model.engine.MonopolyGame;
import no.ntnu.idatg2003.mappe10.model.filehandler.gson.BoardFileReaderGson;
import no.ntnu.idatg2003.mappe10.model.tile.MonopolyTile;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;
import no.ntnu.idatg2003.mappe10.model.tile.tileaction.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * The BoardGameFactory class is responsible for creating instances of the
 * BoardGame class with specific configurations. It sets up the board, players,
 * and actions for the game.
 */
public class BoardGameFactory {

  private BoardFileReaderGson gsonReader;
  private Random rand;

  public BoardGameFactory() {
    this.gsonReader = new BoardFileReaderGson();
    rand = new Random();
  }

  private void connectTilesLinear(int numberOfTiles, BoardGame boardGame) {
    for (int i = 1; i <= numberOfTiles; i++) {
      Tile currentTile = boardGame.getBoard().getTile(i);
      Tile nextTile = boardGame.getBoard().getTile(i + 1);
      currentTile.setNextTile(nextTile);
    }
  }

  /**
   * Creates a BoardGame instance with a ladder game configuration.
   * The game has 90 tiles, 9 rows, and 10 columns. Some tiles are configured
   * with ladder actions.
   *
   * @return a configured BoardGame instance based on the ladder game
   */
  public BoardGame createLadderGame() {
    int numberOfTiles = 90;
    int rows = 9;
    int columns = 10;
    BoardGame boardGame = new BoardGame();
    boardGame.createDice(2);
    boardGame.createBoard(numberOfTiles, rows, columns);

    connectTilesLinear(numberOfTiles, boardGame);

    // Set up ladder actions on specific tiles
    boardGame.getBoard().getTile(3).setLandAction(new LadderAction(17, "Climb to tile 17"));
    boardGame.getBoard().getTile(11).setLandAction(new LadderAction(25, "Climb to tile 25"));
    boardGame.getBoard().getTile(29).setLandAction(new LadderAction(9, "Fall to tile 9"));
    boardGame.getBoard().getTile(41).setLandAction(new LadderAction(21, "Fall to tile 21"));
    boardGame.getBoard().getTile(46).setLandAction(new LadderAction(63, "Climb to tile 63"));
    boardGame.getBoard().getTile(56).setLandAction(new LadderAction(33, "Fall to tile 33"));
    boardGame.getBoard().getTile(72).setLandAction(new LadderAction(26, "Climb to tile 26"));
    boardGame.getBoard().getTile(87).setLandAction(new LadderAction(55, "Fall to tile 55"));

    // Set up prison action on board
    for (int i : new int[]{10, 32, 77}) {
      boardGame.getBoard().getTile(i).setLandAction(new PrisonAction("has been jailed for 1 turn"));
    }

    // Set up win action on the last tile
    boardGame.getBoard().getLastTile().setLandAction(new WinAction());

    // Set tile coords for canvas
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < columns; c++) {
        // if row is even, tileId = r * columns + c + 1.
        // if row is odd, tileId = r * columns + (columns - c)
        // Help from ChatGPT to find the formula.
        int tileId = (r % 2 == 0) ? r * columns + c + 1 : r * columns + (columns - c);
        boardGame.getBoard().getTile(tileId).setCoordinate(r, c);
      }
    }

    return boardGame;
  }

  public BoardGame createMonopolyGame() {
    BoardGame boardGame = new MonopolyGame();
    boardGame.createDice(2);
    boardGame.createBoard(40, 11, 11);

    connectTilesLinear(boardGame.getBoard().getNumberOfTiles(), boardGame);
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

    setMonopolyTileCoordinates(boardGame);

    boardGame.getBoard().getLastTile().setNextTile(boardGame.getBoard().getFirstTile());
    return boardGame;
  }

  private void setMonopolyTileCoordinates(BoardGame boardGame) {
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

  public BoardGame createLostDiamondGame() {
    BoardGame boardGame = new LostDiamondGame();
    boardGame.createDice(2);

    boardGame.createBoard(26, 50, 60);

    setLostDiamondTileCoordinates(boardGame);
    setLostDiamondTileConnections(boardGame);
    setLostDiamondTileActions(boardGame);

    return boardGame;
  }

  private void setLostDiamondTileCoordinates(BoardGame boardGame) {
    // Canvas (Width, Height): (708.0, 637.6)
    // Canvas coords from clicking on the map - see BoardGameView
    Coordinate canvasMax = new Coordinate(708.0, 637.6);

    Coordinate tile1CoordT = boardGame.transformCanvasToBoard(new Coordinate(307.2, 24.0), canvasMax);
    boardGame.getBoard().getTile(1).setCoordinate((int) Math.round(tile1CoordT.getX0()), (int) Math.round(tile1CoordT.getX1()));

    Coordinate tile2CoordT = boardGame.transformCanvasToBoard(new Coordinate(212.8, 75.2), canvasMax);
    boardGame.getBoard().getTile(2).setCoordinate((int) Math.round(tile2CoordT.getX0()), (int) Math.round(tile2CoordT.getX1()));

    Coordinate tile3CoordT = boardGame.transformCanvasToBoard(new Coordinate(379.2, 103.2), canvasMax);
    boardGame.getBoard().getTile(3).setCoordinate((int) Math.round(tile3CoordT.getX0()), (int) Math.round(tile3CoordT.getX1()));

    Coordinate tile4CoordT = boardGame.transformCanvasToBoard(new Coordinate(251.2, 151.2), canvasMax);
    boardGame.getBoard().getTile(4).setCoordinate((int) Math.round(tile4CoordT.getX0()), (int) Math.round(tile4CoordT.getX1()));

    Coordinate tile5CoordT = boardGame.transformCanvasToBoard(new Coordinate(127.2, 235.2), canvasMax);
    boardGame.getBoard().getTile(5).setCoordinate((int) Math.round(tile5CoordT.getX0()), (int) Math.round(tile5CoordT.getX1()));

    Coordinate tile6CoordT = boardGame.transformCanvasToBoard(new Coordinate(121.6, 101.6), canvasMax);
    boardGame.getBoard().getTile(6).setCoordinate((int) Math.round(tile6CoordT.getX0()), (int) Math.round(tile6CoordT.getX1()));

    Coordinate tile7CoordT = boardGame.transformCanvasToBoard(new Coordinate(175.2, 152.8), canvasMax);
    boardGame.getBoard().getTile(7).setCoordinate((int) Math.round(tile7CoordT.getX0()), (int) Math.round(tile7CoordT.getX1()));

    Coordinate tile8CoordT = boardGame.transformCanvasToBoard(new Coordinate(260.8, 232.0), canvasMax);
    boardGame.getBoard().getTile(8).setCoordinate((int) Math.round(tile8CoordT.getX0()), (int) Math.round(tile8CoordT.getX1()));

    Coordinate tile9CoordT = boardGame.transformCanvasToBoard(new Coordinate(360.8, 181.6), canvasMax);
    boardGame.getBoard().getTile(9).setCoordinate((int) Math.round(tile9CoordT.getX0()), (int) Math.round(tile9CoordT.getX1()));

    Coordinate tile10CoordT = boardGame.transformCanvasToBoard(new Coordinate(430.4, 246.4), canvasMax);
    boardGame.getBoard().getTile(10).setCoordinate((int) Math.round(tile10CoordT.getX0()), (int) Math.round(tile10CoordT.getX1()));

    Coordinate tile11CoordT = boardGame.transformCanvasToBoard(new Coordinate(288.0, 321.6), canvasMax);
    boardGame.getBoard().getTile(11).setCoordinate((int) Math.round(tile11CoordT.getX0()), (int) Math.round(tile11CoordT.getX1()));

    Coordinate tile12CoordT = boardGame.transformCanvasToBoard(new Coordinate(354.4, 243.2), canvasMax);
    boardGame.getBoard().getTile(12).setCoordinate((int) Math.round(tile12CoordT.getX0()), (int) Math.round(tile12CoordT.getX1()));

    Coordinate tile13CoordT = boardGame.transformCanvasToBoard(new Coordinate(420.8, 331.2), canvasMax);
    boardGame.getBoard().getTile(13).setCoordinate((int) Math.round(tile13CoordT.getX0()), (int) Math.round(tile13CoordT.getX1()));

    Coordinate tile14CoordT = boardGame.transformCanvasToBoard(new Coordinate(519.2, 166.4), canvasMax);
    boardGame.getBoard().getTile(14).setCoordinate((int) Math.round(tile14CoordT.getX0()), (int) Math.round(tile14CoordT.getX1()));

    Coordinate tile15CoordT = boardGame.transformCanvasToBoard(new Coordinate(476.8, 91.2), canvasMax);
    boardGame.getBoard().getTile(15).setCoordinate((int) Math.round(tile15CoordT.getX0()), (int) Math.round(tile15CoordT.getX1()));

    Coordinate tile16CoordT = boardGame.transformCanvasToBoard(new Coordinate(436.8, 158.4), canvasMax);
    boardGame.getBoard().getTile(16).setCoordinate((int) Math.round(tile16CoordT.getX0()), (int) Math.round(tile16CoordT.getX1()));

    Coordinate tile18CoordT = boardGame.transformCanvasToBoard(new Coordinate(512.8, 317.6), canvasMax);
    boardGame.getBoard().getTile(17).setCoordinate((int) Math.round(tile18CoordT.getX0()), (int) Math.round(tile18CoordT.getX1()));

    Coordinate tile19CoordT = boardGame.transformCanvasToBoard(new Coordinate(542.4, 396.8), canvasMax);
    boardGame.getBoard().getTile(18).setCoordinate((int) Math.round(tile19CoordT.getX0()), (int) Math.round(tile19CoordT.getX1()));

    Coordinate tile20CoordT = boardGame.transformCanvasToBoard(new Coordinate(320.0, 434.4), canvasMax);
    boardGame.getBoard().getTile(19).setCoordinate((int) Math.round(tile20CoordT.getX0()), (int) Math.round(tile20CoordT.getX1()));

    Coordinate tile21CoordT = boardGame.transformCanvasToBoard(new Coordinate(365.6, 364.0), canvasMax);
    boardGame.getBoard().getTile(20).setCoordinate((int) Math.round(tile21CoordT.getX0()), (int) Math.round(tile21CoordT.getX1()));

    Coordinate tile22CoordT = boardGame.transformCanvasToBoard(new Coordinate(450.4, 395.2), canvasMax);
    boardGame.getBoard().getTile(21).setCoordinate((int) Math.round(tile22CoordT.getX0()), (int) Math.round(tile22CoordT.getX1()));

    Coordinate tile23CoordT = boardGame.transformCanvasToBoard(new Coordinate(444.0, 445.6), canvasMax);
    boardGame.getBoard().getTile(22).setCoordinate((int) Math.round(tile23CoordT.getX0()), (int) Math.round(tile23CoordT.getX1()));

    Coordinate tile24CoordT = boardGame.transformCanvasToBoard(new Coordinate(351.2, 491.2), canvasMax);
    boardGame.getBoard().getTile(23).setCoordinate((int) Math.round(tile24CoordT.getX0()), (int) Math.round(tile24CoordT.getX1()));

    Coordinate tile27CoordT = boardGame.transformCanvasToBoard(new Coordinate(528.8, 488.8), canvasMax);
    boardGame.getBoard().getTile(24).setCoordinate((int) Math.round(tile27CoordT.getX0()), (int) Math.round(tile27CoordT.getX1()));

    Coordinate tile28CoordT = boardGame.transformCanvasToBoard(new Coordinate(417.6, 520.0), canvasMax);
    boardGame.getBoard().getTile(25).setCoordinate((int) Math.round(tile28CoordT.getX0()), (int) Math.round(tile28CoordT.getX1()));

    Coordinate tile29CoordT = boardGame.transformCanvasToBoard(new Coordinate(304.8, 88.8), canvasMax);
    boardGame.getBoard().getTile(26).setCoordinate((int) Math.round(tile29CoordT.getX0()), (int) Math.round(tile29CoordT.getX1()));
  }

  private void setLostDiamondTileConnections(BoardGame boardGame) {
    boardGame.getBoard().getTile(1).addConnection("left", boardGame.getTileById(26));
    boardGame.getBoard().getTile(1).addConnection("up", boardGame.getTileById(3));

    boardGame.getBoard().getTile(2).addConnection("up", boardGame.getTileById(26));
    boardGame.getBoard().getTile(2).addConnection("left", boardGame.getTileById(4));
    boardGame.getBoard().getTile(2).addConnection("down", boardGame.getTileById(7));

    boardGame.getBoard().getTile(3).addConnection("up", boardGame.getTileById(15));
    boardGame.getBoard().getTile(3).addConnection("left", boardGame.getTileById(16));
    boardGame.getBoard().getTile(3).addConnection("down", boardGame.getTileById(9));

    boardGame.getBoard().getTile(4).addConnection("up", boardGame.getTileById(26));
    boardGame.getBoard().getTile(4).addConnection("left", boardGame.getTileById(8));
    boardGame.getBoard().getTile(4).addConnection("down", boardGame.getTileById(7));
    boardGame.getBoard().getTile(4).addConnection("right", boardGame.getTileById(2));

    boardGame.getBoard().getTile(5).addConnection("up", boardGame.getTileById(7));
    boardGame.getBoard().getTile(5).addConnection("left", boardGame.getTileById(11));
    boardGame.getBoard().getTile(5).addConnection("right", boardGame.getTileById(6));

    boardGame.getBoard().getTile(6).addConnection("up", boardGame.getTileById(7));
    boardGame.getBoard().getTile(6).addConnection("left", boardGame.getTileById(5));

    boardGame.getBoard().getTile(7).addConnection("up", boardGame.getTileById(4));
    boardGame.getBoard().getTile(7).addConnection("down", boardGame.getTileById(5));

    boardGame.getBoard().getTile(8).addConnection("up", boardGame.getTileById(12));
    boardGame.getBoard().getTile(8).addConnection("right", boardGame.getTileById(4));
    boardGame.getBoard().getTile(8).addConnection("left", boardGame.getTileById(11));

    boardGame.getBoard().getTile(9).addConnection("up", boardGame.getTileById(3));
    boardGame.getBoard().getTile(9).addConnection("left", boardGame.getTileById(12));
    boardGame.getBoard().getTile(9).addConnection("right", boardGame.getTileById(10));

    boardGame.getBoard().getTile(10).addConnection("left", boardGame.getTileById(9));
    boardGame.getBoard().getTile(10).addConnection("down", boardGame.getTileById(13));
    boardGame.getBoard().getTile(10).addConnection("right", boardGame.getTileById(16));

    boardGame.getBoard().getTile(11).addConnection("up", boardGame.getTileById(12));
    boardGame.getBoard().getTile(11).addConnection("right", boardGame.getTileById(8));
    boardGame.getBoard().getTile(11).addConnection("down", boardGame.getTileById(19));
    boardGame.getBoard().getTile(11).addConnection("left", boardGame.getTileById(5));

    boardGame.getBoard().getTile(12).addConnection("down", boardGame.getTileById(8));
    boardGame.getBoard().getTile(12).addConnection("right", boardGame.getTileById(9));
    boardGame.getBoard().getTile(12).addConnection("left", boardGame.getTileById(11));
    boardGame.getBoard().getTile(12).addConnection("up", boardGame.getTileById(13));

    boardGame.getBoard().getTile(13).addConnection("down", boardGame.getTileById(12));
    boardGame.getBoard().getTile(13).addConnection("up", boardGame.getTileById(21));
    boardGame.getBoard().getTile(13).addConnection("right", boardGame.getTileById(10));

    boardGame.getBoard().getTile(14).addConnection("right", boardGame.getTileById(15));
    boardGame.getBoard().getTile(14).addConnection("down", boardGame.getTileById(16));
    boardGame.getBoard().getTile(14).addConnection("left", boardGame.getTileById(17));

    boardGame.getBoard().getTile(15).addConnection("left", boardGame.getTileById(14));
    boardGame.getBoard().getTile(15).addConnection("down", boardGame.getTileById(3));

    boardGame.getBoard().getTile(16).addConnection("up", boardGame.getTileById(14));
    boardGame.getBoard().getTile(16).addConnection("left", boardGame.getTileById(10));
    boardGame.getBoard().getTile(16).addConnection("right", boardGame.getTileById(3));

    boardGame.getBoard().getTile(17).addConnection("right", boardGame.getTileById(14));
    boardGame.getBoard().getTile(17).addConnection("down", boardGame.getTileById(18));

    boardGame.getBoard().getTile(18).addConnection("up", boardGame.getTileById(17));
    boardGame.getBoard().getTile(18).addConnection("left", boardGame.getTileById(24));
    boardGame.getBoard().getTile(18).addConnection("down", boardGame.getTileById(21));

    boardGame.getBoard().getTile(19).addConnection("up", boardGame.getTileById(11));
    boardGame.getBoard().getTile(19).addConnection("left", boardGame.getTileById(23));
    boardGame.getBoard().getTile(19).addConnection("right", boardGame.getTileById(20));

    boardGame.getBoard().getTile(20).addConnection("left", boardGame.getTileById(19));
    boardGame.getBoard().getTile(20).addConnection("up", boardGame.getTileById(13));
    boardGame.getBoard().getTile(20).addConnection("right", boardGame.getTileById(22));

    boardGame.getBoard().getTile(21).addConnection("up", boardGame.getTileById(18));
    boardGame.getBoard().getTile(21).addConnection("down", boardGame.getTileById(13));
    boardGame.getBoard().getTile(21).addConnection("left", boardGame.getTileById(22));

    boardGame.getBoard().getTile(22).addConnection("left", boardGame.getTileById(25));
    boardGame.getBoard().getTile(22).addConnection("right", boardGame.getTileById(21));
    boardGame.getBoard().getTile(22).addConnection("down", boardGame.getTileById(20));

    boardGame.getBoard().getTile(23).addConnection("right", boardGame.getTileById(19));
    boardGame.getBoard().getTile(23).addConnection("up", boardGame.getTileById(25));

    boardGame.getBoard().getTile(24).addConnection("right", boardGame.getTileById(18));
    boardGame.getBoard().getTile(24).addConnection("down", boardGame.getTileById(25));

    boardGame.getBoard().getTile(25).addConnection("up", boardGame.getTileById(24));
    boardGame.getBoard().getTile(25).addConnection("right", boardGame.getTileById(22));
    boardGame.getBoard().getTile(25).addConnection("down", boardGame.getTileById(23));

    boardGame.getBoard().getTile(26).addConnection("down", boardGame.getTileById(1));
    boardGame.getBoard().getTile(26).addConnection("left", boardGame.getTileById(3));
    boardGame.getBoard().getTile(26).addConnection("right", boardGame.getTileById(4));
    boardGame.getBoard().getTile(26).addConnection("down-right", boardGame.getTileById(2)); // optional label
  }

  private void setLostDiamondTileActions(BoardGame boardGame) {
    boardGame.getBoard().getTile(1).setLandAction(new WinAction());
    List<Integer> robberTiles = List.of(2, 5, 9, 13, 15, 24, 23);
    robberTiles.forEach(tileID -> boardGame.getBoard().getTile(tileID).setLandAction(new RobberAction()));
  }
}