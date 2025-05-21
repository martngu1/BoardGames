package no.ntnu.idatg2003.mappe10.model.board;

import no.ntnu.idatg2003.mappe10.model.coordinate.Coordinate;
import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.filehandler.gson.BoardFileReaderGson;
import no.ntnu.idatg2003.mappe10.model.tile.MonopolyTile;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;
import no.ntnu.idatg2003.mappe10.model.tile.tileaction.*;


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
    int numberOfTiles = 90;
    int rows = 9;
    int columns = 10;
    BoardGame boardGame = new BoardGame();
    boardGame.createDice(2);
    boardGame.createBoard(numberOfTiles, rows, columns);

    for (int i = 1; i <= numberOfTiles; i++) {
      Tile currentTile = boardGame.getBoard().getTile(i);
      Tile nextTile = boardGame.getBoard().getTile(i + 1);
      currentTile.setNextTile(nextTile);
    }

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
    boardGame.getBoard().getLastTile().setLandAction(new WinAction("You win!"));

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
    BoardGame boardGame = new BoardGame();
    boardGame.createDice(2);
    boardGame.createBoard(40, 11, 11);

    CountryFactory countryFactory = new CountryFactory();


    for (int i = 0; i < 3; i++) {
      int tileId = 2 + i;
      boardGame.getBoard().getTile(tileId).setMonopolyTile(new MonopolyTile(tileId, countryFactory.getPropertyByCountryAndIndex("Mongolia", i)));
      boardGame.getBoard().getTile(tileId).setLandAction(new PropertyAction("Property action for Mongolia"));
    }

    boardGame.getBoard().getTile(5).setLandAction(new ChanceCardAction("Chance"));


    for (int i = 0; i < 3; i++) {
      int tileId = 6 + i;
      boardGame.getBoard().getTile(tileId).setMonopolyTile(new MonopolyTile(tileId, countryFactory.getPropertyByCountryAndIndex("Philippines", i)));
      boardGame.getBoard().getTile(tileId).setLandAction(new PropertyAction("Property action for Philippines"));
    }

    boardGame.getBoard().getTile(9).setLandAction(new ChanceCardAction("Chance"));


    for (int i = 0; i < 3; i++) {
      int tileId = 10 + i;
      boardGame.getBoard().getTile(tileId).setMonopolyTile(new MonopolyTile(tileId, countryFactory.getPropertyByCountryAndIndex("Vietnam", i)));
      boardGame.getBoard().getTile(tileId).setLandAction(new PropertyAction("Property action for Vietnam"));
    }

    boardGame.getBoard().getTile(13).setLandAction(new ChanceCardAction("Chance"));


    for (int i = 0; i < 3; i++) {
      int tileId = 14 + i;
      boardGame.getBoard().getTile(tileId).setMonopolyTile(new MonopolyTile(tileId, countryFactory.getPropertyByCountryAndIndex("Thailand", i)));
      boardGame.getBoard().getTile(tileId).setLandAction(new PropertyAction("Property action for Thailand"));
    }


    boardGame.getBoard().getTile(17).setLandAction(new ChanceCardAction("Chance"));


    for (int i = 0; i < 3; i++) {
      int tileId = 18 + i;
      boardGame.getBoard().getTile(tileId).setMonopolyTile(new MonopolyTile(tileId, countryFactory.getPropertyByCountryAndIndex("Indonesia", i)));
      boardGame.getBoard().getTile(tileId).setLandAction(new PropertyAction("Property action for Indonesia"));
    }

    boardGame.getBoard().getTile(21).setLandAction(new ChanceCardAction("Chance"));

    for (int i = 0; i < 3; i++) {
      int tileId = 22 + i;
      boardGame.getBoard().getTile(tileId).setMonopolyTile(new MonopolyTile(tileId, countryFactory.getPropertyByCountryAndIndex("China", i)));
      boardGame.getBoard().getTile(tileId).setLandAction(new PropertyAction("Property action for China"));
    }


    boardGame.getBoard().getTile(25).setLandAction(new ChanceCardAction("Chance"));
    for (int i = 0; i < 3; i++) {
      int tileId = 26 + i;
      boardGame.getBoard().getTile(tileId).setMonopolyTile(new MonopolyTile(tileId, countryFactory.getPropertyByCountryAndIndex("South Korea", i)));
      boardGame.getBoard().getTile(tileId).setLandAction(new PropertyAction("Property action for South Korea"));
    }

    boardGame.getBoard().getTile(29).setLandAction(new ChanceCardAction("Chance"));

    for (int i = 0; i < 3; i++) {
      int tileId = 30 + i;
      boardGame.getBoard().getTile(tileId).setMonopolyTile(new MonopolyTile(tileId, countryFactory.getPropertyByCountryAndIndex("Japan", i)));
      boardGame.getBoard().getTile(tileId).setLandAction(new PropertyAction("Property action for Japan"));
    }

    setMonopolyTileCoordinates(boardGame);

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
    BoardGame boardGame = new BoardGame();
    boardGame.createDice(2);

    boardGame.createBoard(26, 50, 60);


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

    return boardGame;
  }

}
