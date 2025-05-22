package no.ntnu.idatg2003.mappe10.ui.view.renderer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import no.ntnu.idatg2003.mappe10.model.coordinate.Coordinate;
import no.ntnu.idatg2003.mappe10.model.tile.Country;
import no.ntnu.idatg2003.mappe10.model.tile.CruiseDock;
import no.ntnu.idatg2003.mappe10.model.tile.MonopolyTile;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;
import no.ntnu.idatg2003.mappe10.ui.controller.BoardGameController;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MonopolyGameRenderer extends Renderer {
  // Map countries to colors for tile coloring
  private final Map<String, Color> propertyColors = new HashMap<>();
  private final Map<String, String> specialTileImages = new HashMap<>();

  public MonopolyGameRenderer(BoardGameController controller, Canvas canvas) {
    super(controller, canvas);

    // Assign each country a distinct color (feel free to change these)
    propertyColors.put("Mongolia", Color.LIGHTSEAGREEN);
    propertyColors.put("Philippines", Color.MEDIUMPURPLE);
    propertyColors.put("Vietnam", Color.YELLOW);
    propertyColors.put("Thailand", Color.DARKCYAN);
    propertyColors.put("Indonesia", Color.ORANGE);
    propertyColors.put("China", Color.RED);
    propertyColors.put("South Korea", Color.PLUM);
    propertyColors.put("Japan", Color.WHITESMOKE);
    propertyColors.put("CruiseDock", Color.BLUE);

    specialTileImages.put("ChanceCardAction", "/tileIcons/fortuneCookie.png");
    specialTileImages.put("TaxAction", "/tileIcons/taxIcon.png");
    specialTileImages.put("PrisonAction", "/tileIcons/prisonBars.png");
    specialTileImages.put("FreeParking", "/tileIcons/freeParking.png");

  }

  @Override
  public void drawBoard() {
    GraphicsContext gc = super.getCanvas().getGraphicsContext2D();

    super.drawBackground();
    colorTiles(gc);
    super.drawTiles();
    super.numerateTiles();
    drawPropertyOwners(gc);
    super.drawPlayers();
  }

  private void colorTiles(GraphicsContext gc) {
    int numberOfTiles = super.getController().getNumberOfTiles();

    for (int tileId = 1; tileId <= numberOfTiles; tileId++) {
      Coordinate canvasCoords = super.getController()
            .getCanvasCoords(tileId, super.getOffsetWidth(), super.getOffsetHeight());
      double x = canvasCoords.getX0();
      double y = canvasCoords.getX1();

      Tile tile = super.getController().getTileById(tileId);
      MonopolyTile monopolyTile = tile.getMonopolyTile();

      if (monopolyTile != null && monopolyTile.getProperty() != null) {
        Color color;
        String imagePath = null;

        if (monopolyTile.getProperty() instanceof CruiseDock) { // Cruise Docks
          color = propertyColors.getOrDefault("CruiseDock", Color.LIGHTBLUE);
          imagePath = "/tileIcons/cruiseDock.png";
        } else { // Get the color based on the country
          String country = monopolyTile.getProperty().getCountry().getName();
          color = propertyColors.getOrDefault(country, Color.WHITE);
        }

        gc.setFill(color);
        gc.fillRect(x, y, super.getTileWidth(), super.getTileHeight());

        if (imagePath != null) {
          drawTileImage(gc, imagePath, x, y);
        }
        double price = monopolyTile.getProperty().getPrice();
        gc.setFill(Color.BLACK);
        gc.setLineWidth(0.75);
        gc.fillText("$" + price, x + super.getTileWidth() * 0.05, y + super.getTileHeight() * 0.20);
      } else if (tile.getLandAction() != null) { // Special Tiles
        String actionClassName = tile.getLandAction().getClass().getSimpleName();
        String imagePath = specialTileImages.get(actionClassName);

        if (imagePath != null) {
          drawTileImage(gc, imagePath, x, y);
        }
      } else if (tileId == 1) { // Start Tile
        Color color = Color.GREEN;
        gc.setFill(color);
        gc.fillRect(x, y, super.getTileWidth(), super.getTileHeight());
      } else {
        // Free Parking and special tiles gets color lightgray
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(x, y, super.getTileWidth(), super.getTileHeight());
        gc.fillRect(x, y, super.getTileWidth(), super.getTileHeight());

        String imagePath = specialTileImages.get("FreeParking");
        if (imagePath != null) {
          drawTileImage(gc, imagePath, x, y);
        }
      }
    }
  }

  private void drawTileImage(GraphicsContext gc, String imagePath, double x, double y) {
    InputStream stream = getClass().getResourceAsStream(imagePath);
    if (stream == null) {
      System.err.println("Image not found: " + imagePath);
      return;
    }
    Image image = new Image(stream);
    gc.drawImage(image, x, y, super.getTileWidth(), super.getTileHeight());
  }

  private void drawPropertyOwners(GraphicsContext gc) {
    for (int tileId = 1; tileId <= super.getController().getNumberOfTiles(); tileId++) {
      Tile tile = super.getController().getTileById(tileId);
      MonopolyTile monopolyTile = tile.getMonopolyTile();

      if (monopolyTile != null && monopolyTile.getProperty() != null) {
        if (monopolyTile.getProperty().isOwned()) {
          String playingPiece = monopolyTile.getProperty().getOwner().getPlayingPiece();
          InputStream inputStream = getClass().getResourceAsStream("/playingPieces/" + playingPiece + ".png");

          if (inputStream == null) {
            System.out.println("Image not found for owner: " + playingPiece);
            continue;
          }

          Coordinate canvasCoords = super.getController().
                getCanvasCoords(tileId, super.getOffsetWidth(), super.getOffsetHeight());
          double x = canvasCoords.getX0();
          double y = canvasCoords.getX1();

          Image image = new Image(inputStream);

          gc.drawImage(image,
                x + super.getTileWidth() * 0.7, y + super.getTileHeight() * 0.05,
                super.getTileWidth() * 0.25, super.getTileHeight() * 0.25);
        }
      }
    }
  }
}
