package no.ntnu.idatg2003.mappe10.model.filehandler.gson;

import com.google.gson.*;
import no.ntnu.idatg2003.mappe10.exceptions.BoardWriteException;
import no.ntnu.idatg2003.mappe10.model.board.Board;
import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.engine.GameType;
import no.ntnu.idatg2003.mappe10.model.engine.LostDiamondGame;
import no.ntnu.idatg2003.mappe10.model.engine.MonopolyGame;
import no.ntnu.idatg2003.mappe10.model.tile.*;
import no.ntnu.idatg2003.mappe10.model.tile.tileaction.LadderAction;
import no.ntnu.idatg2003.mappe10.model.filehandler.BoardFileWriter;
import no.ntnu.idatg2003.mappe10.model.tile.tileaction.TaxAction;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class is responsible for writing a board to a file in JSON format.
 * Uses Gson library for serialization.
 * It implements the BoardFileWriter interface.
 */
public class BoardFileWriterGson implements BoardFileWriter {

  /**
   * Writes the given board to a file path in JSON format.
   * If the file already exists, it will be overwritten, if not, it will be created.
   *
   * @param filePath  the path to the file where the board will be written
   * @param boardGame the boardGame board to write
   */
  @Override
  public void writeBoard(String filePath, BoardGame boardGame) {
    JsonObject boardJson = new JsonObject();
    try {
      Board board = boardGame.getBoard();

      JsonElement numberOfRows = new JsonPrimitive(board.getNumberOfRows());
      JsonElement numberOfColumns = new JsonPrimitive(board.getNumberOfColumns());

      String game;
      if (boardGame instanceof MonopolyGame) {
        game = GameType.MONOPOLY.name();
      } else if (boardGame instanceof LostDiamondGame) {
        game = GameType.LOSTDIAMOND.name();
      } else {
        game = GameType.LADDERGAME.name();
      }

      boardJson.addProperty("gameName", game);
      boardJson.add("numberOfRows", numberOfRows);
      boardJson.add("numberOfColumns", numberOfColumns);

      if (boardGame instanceof MonopolyGame monopolyGame) {
        handleMonopolyGame(boardJson, monopolyGame);
      }

      JsonArray tilesJson = new JsonArray();
      board.getTilesList().values().forEach(tile -> {
        JsonObject tileJson = serializeTileObject(tile);
        tilesJson.add(tileJson);
      });
      boardJson.add("tiles", tilesJson);
    } catch (NullPointerException | JsonParseException e) {
      throw new BoardWriteException("Error: parsing board to JSON file format", e);
    } catch (IllegalArgumentException e) {
      throw new BoardWriteException("Invalid Argument passed to the board writer: " + filePath + " or " + boardGame, e);
    }

    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath))) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      gson.toJson(boardJson, writer);
    } catch (IOException | NullPointerException | JsonParseException e) {
      throw new BoardWriteException("Error writing board to JSON file: " + filePath, e);
    }
  }

  private void handleMonopolyGame(JsonObject boardJson, MonopolyGame boardGame) {
    JsonArray countries = new JsonArray();

    boardGame.getCountries().forEachRemaining(country -> {
      JsonObject countryJson = new JsonObject();
      countryJson.addProperty("countryName", country.getName());
      countryJson.addProperty("price", country.getPrice());
      countryJson.addProperty("rent", country.getRent());

      JsonArray properties = new JsonArray();
      HashSet<String> propertySeen = new HashSet<>();

      country.getProperties().forEachRemaining(property -> {
        if (!propertySeen.contains(property.getName())) {
          JsonObject propertyJson = new JsonObject();
          propertyJson.addProperty("cityName", property.getName());
          propertyJson.addProperty("tileID", property.getTileId());
          properties.add(propertyJson);
          propertySeen.add(property.getName());
        }
      });
      countryJson.add("properties", properties);
      countries.add(countryJson);
    });

    boardJson.add("countries", countries);

    JsonArray cruiseDocks = new JsonArray();
    boardGame.getCruiseDocks().forEachRemaining(cruiseDock -> {
      JsonObject cruiseDockJson = new JsonObject();
      cruiseDockJson.addProperty("dockName", cruiseDock.getName());
      cruiseDockJson.addProperty("price", cruiseDock.getPrice());
      cruiseDockJson.addProperty("baseRent", cruiseDock.getRent());
      cruiseDockJson.addProperty("tileID", cruiseDock.getTileId());

      cruiseDocks.add(cruiseDockJson);
    });

    boardJson.add("cruiseDocks", cruiseDocks);
  }

  private void handleLostDiamondGame(JsonObject boardJson, LostDiamondGame boardGame) {

  }

  private boolean monopolyTileCountryPropertyExists(Tile tile) {
    return tile.getMonopolyTile() != null &&
          tile.getMonopolyTile().getProperty() != null &&
          tile.getMonopolyTile().getProperty().getCountry() != null;
  }

  /**
   * Serializes a Tile object to a JSON object.
   *
   * @param tile the Tile object to serialize
   * @return the serialized JSON object as JsonObject
   */
  private JsonObject serializeTileObject(Tile tile) {
    JsonObject tileJson = new JsonObject();
    // Tile id
    tileJson.addProperty("tileId", tile.getTileId());

    // Handle next tile
    if (tile.getNextTile() == null) {
      tileJson.add("nextTile", JsonNull.INSTANCE);
    } else {
      tileJson.addProperty("nextTile", tile.getNextTile().getTileId());
    }

    // Handle the Coordinates row and column
    if (tile.getBoardCoords() != null) {
      tileJson.addProperty("row", (int) tile.getBoardCoords().getX0());
      tileJson.addProperty("column", (int) tile.getBoardCoords().getX1());
    } else {
      tileJson.add("row", JsonNull.INSTANCE);
      tileJson.add("column", JsonNull.INSTANCE);
    }

    // Handle the TileAction
    JsonObject actionJson = new JsonObject();
    if (tile.getLandAction() != null) {
      String actionType = tile.getLandAction().getClass().getSimpleName();
      actionJson.addProperty("type", actionType);
      serializeActionDetails(actionType, tile, actionJson);
      actionJson.addProperty("description", tile.getLandAction().getDescription());
    }
    tileJson.add("action", actionJson);

    // Handle connections
    JsonObject connectionsJson = new JsonObject();
    if (tile.getConnectedTiles() != null) {
      HashMap<String, Tile> connectedTiles = (HashMap<String, Tile>) tile.getConnectedTiles();
      connectedTiles
            .keySet()
            .forEach(key -> connectionsJson.addProperty(key, connectedTiles.get(key).getTileId()));
    }
    tileJson.add("connections", connectionsJson);

    return tileJson;
  }

  private void serializeActionDetails(String actionType, Tile tile, JsonObject actionJson) {
    switch (actionType) {
      case "LadderAction":
        LadderAction ladderAction = (LadderAction) tile.getLandAction();
        actionJson.addProperty("destinationTileID", ladderAction.getDestinationTileId());
        break;
      case "TaxAction":
        TaxAction taxAction = (TaxAction) tile.getLandAction();
        actionJson.addProperty("taxAmount", taxAction.getTaxAmount());
        break;
      default:
        break;
    }
  }

}
