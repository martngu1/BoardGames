package no.ntnu.idatg2003.mappe10.model.filehandler.gson;

import com.google.gson.*;
import no.ntnu.idatg2003.mappe10.exceptions.BoardParsingException;
import no.ntnu.idatg2003.mappe10.model.board.Board;
import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.engine.GameType;
import no.ntnu.idatg2003.mappe10.model.filehandler.BoardType;
import no.ntnu.idatg2003.mappe10.model.tile.*;
import no.ntnu.idatg2003.mappe10.model.tile.tileaction.*;
import no.ntnu.idatg2003.mappe10.model.filehandler.BoardFileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for reading a board from a file in JSON format.
 * Uses Gson library for deserialization.
 * It implements the BoardFileReader interface.
 */
public class BoardFileReaderGson implements BoardFileReader {

  /**
   * Reads a board from a file path in JSON format.
   * If the file does not exist, it will throw a FileNotFoundException.
   *
   * @param filePath the path to the file where the board will be read from
   * @return the Board object read from the file
   */
  @Override
  public BoardType readBoard(String filePath) {
    try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
      JsonObject boardJson = JsonParser.parseReader(reader).getAsJsonObject();

      JsonArray tilesJson = boardJson.getAsJsonArray("tiles");
      Map<Integer, Tile> tilesList = new HashMap<>();

      tilesJson.forEach(tileElement -> {
        JsonObject tileJson = tileElement.getAsJsonObject();
        Tile tileObject = deserializeJsonTileObject(tileJson, tilesList);
        tilesList.put(tileObject.getTileId(), tileObject);
      });

      tilesList.values().forEach(tile -> {
        if (tile.getTileId() == tilesList.size()) {
          tile.setNextTile(null);
        } else {
          tile.setNextTile(tilesList.get(tile.getTileId() + 1));
        }
      });

      int numberOfRows = boardJson.get("numberOfRows").getAsInt();
      int numberOfColumns = boardJson.get("numberOfColumns").getAsInt();

      if (numberOfRows <= 0 || numberOfColumns <= 0) {
        throw new BoardParsingException("Invalid board dimensions: " + numberOfRows + " rows and " + numberOfColumns + " columns.");
      }

      Board board = new Board(
            tilesList.size(),
            boardJson.get("numberOfRows").getAsInt(),
            boardJson.get("numberOfColumns").getAsInt()
      );
      board.setTilesList(tilesList);

      String gameName = boardJson.get("gameName").getAsString();

      if (GameType.LOSTDIAMOND.name().equalsIgnoreCase(gameName)) {
        addTileConnections(tilesJson, board);
      } else if (GameType.MONOPOLY.name().equalsIgnoreCase(gameName)) {
        List<Country> countryList = getListOfCountries(boardJson);
        List<CruiseDock> cruiseDocksList = getListOfCruiseDocks(boardJson);
        board.getTilesList().values().forEach(tile -> {
          setMonopolyTileProperties(tile, countryList, cruiseDocksList);
        });
      }
      GameType gameType = GameType.valueOf(gameName.toUpperCase());

      return new BoardType(gameType, board);
    } catch (IOException | NullPointerException | JsonParseException e) {
      throw new BoardParsingException("Error reading/parsing the board to JSON file: " + filePath, e);
    }
  }

  private void setMonopolyTileProperties(Tile tile, List<Country> countryList, List<CruiseDock> cruiseDocksList) {
    countryList.forEach(country -> {
      country.getProperties().forEachRemaining(property -> {
        if (property.getTileId() == tile.getTileId()) {
          MonopolyTile monopolyTile = new MonopolyTile(tile.getTileId(), property);
          tile.setMonopolyTile(monopolyTile);
        }
      });
    });

    cruiseDocksList.forEach(cruiseDock -> {
      if (cruiseDock.getTileId() == tile.getTileId()) {
        MonopolyTile monopolyTile = new MonopolyTile(tile.getTileId(), cruiseDock);
        tile.setMonopolyTile(monopolyTile);
      }
    });
  }

  private void addTileConnections(JsonArray tilesJson, Board board) {
    ArrayList<String> directions = new ArrayList<>(List.of("up", "down", "left", "right"));

    // Iterate through the tiles in json file to add connections to each tile if exists.
    tilesJson.forEach(tileElement -> {
      JsonObject tileJson = tileElement.getAsJsonObject();
      int currentTileID = tileJson.get("tileId").getAsInt();
      JsonObject connections = tileJson.getAsJsonObject("connections");

      if (!connections.isEmpty()) {
        directions.forEach(direction -> {
          if (connections.has(direction)) {
            int connectedTilesID = connections.get(direction).getAsInt();
            Tile connectedTile = board.getTilesList().get(connectedTilesID);
            board.getTile(currentTileID).addConnection(direction, connectedTile);
          }
        });
      }
    });
  }

  public List<Country> getListOfCountries(JsonObject boardJson) {
    JsonArray countries = boardJson.getAsJsonArray("countries");

    List<Country> countryList = new ArrayList<>();

    // Retrieve all countries to countryList
    countries.forEach(countryElement -> {
      JsonObject countryJson = countryElement.getAsJsonObject();
      String countryName = countryJson.get("countryName").getAsString();
      int price = countryJson.get("price").getAsInt();
      int rent = countryJson.get("rent").getAsInt();

      Country country = new Country(countryName, price, rent);

      JsonArray propertiesJson = countryJson.get("properties").getAsJsonArray();
      propertiesJson.forEach(propertyElement -> {
        JsonObject propertyJson = propertyElement.getAsJsonObject();
        String cityName = propertyJson.get("cityName").getAsString();
        int tileID = propertyJson.get("tileID").getAsInt();

        Property property = new Property(cityName, country);
        property.setTileId(tileID);
      });

      countryList.add(country);
    });

    return countryList;
  }

  private List<CruiseDock> getListOfCruiseDocks(JsonObject boardJson) {
    List<CruiseDock> cruiseDocksList = new ArrayList<>();

    // Retrieve all cruise docks to cruiseDocksList
    boardJson.getAsJsonArray("cruiseDocks").forEach(cruiseDockElement -> {
      JsonObject cruiseDockJson = cruiseDockElement.getAsJsonObject();
      String dockName = cruiseDockJson.get("dockName").getAsString();
      int price = cruiseDockJson.get("price").getAsInt();
      int baseRent = cruiseDockJson.get("baseRent").getAsInt();
      int tileID = cruiseDockJson.get("tileID").getAsInt();

      CruiseDock cruiseDock = new CruiseDock(dockName, price, baseRent);

      cruiseDock.setTileId(tileID);
      cruiseDocksList.add(cruiseDock);
    });

    return cruiseDocksList;
  }

  /**
   * Deserializes a JSON element to a Tile object.
   *
   * @param tileJson  the JSON element to deserialize
   * @param tilesList the map of tiles to set the next tile
   * @return the deserialized Tile object
   */
  private Tile deserializeJsonTileObject(JsonObject tileJson, Map<Integer, Tile> tilesList) {
    int tileId = tileJson.get("tileId").getAsInt();

    int row = tileJson.get("row").getAsInt();
    int column = tileJson.get("column").getAsInt();
    Tile tile = new Tile(tileId);
    tile.setCoordinate(row, column);

    if (!tileJson.has("action")) {
      return tile;
    }

    JsonObject actionJson = tileJson.getAsJsonObject("action");
    if (!actionJson.has("type") || !actionJson.has("description")) {
      return tile;
    }

    String actionType = actionJson.get("type").getAsString();
    String description = actionJson.get("description").getAsString();
    TileAction action = null;
    switch (actionType) {
      case "LadderAction":
        int destinationTileId = actionJson.get("destinationTileID").getAsInt();
        action = new LadderAction(destinationTileId, description);
        break;
      case "WinAction":
        action = new WinAction();
        break;
      case "PrisonAction":
        action = new PrisonAction(description);
        break;
      case "ChanceCardAction":
        action = new ChanceCardAction(description);
        break;
      case "PropertyAction":
        action = new PropertyAction(description);
        break;
      case "RobberAction":
        action = new RobberAction();
        break;
      case "TaxAction":
        int taxAmount = actionJson.get("taxAmount").getAsInt();
        action = new TaxAction(taxAmount, description);
        break;
      default:
        break;
    }
    tile.setLandAction(action);
    System.out.println(tile.getLandAction().getDescription());
    return tile;
  }
}
