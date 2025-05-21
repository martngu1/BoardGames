package no.ntnu.idatg2003.mappe10.ui.view.renderer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import no.ntnu.idatg2003.mappe10.model.coordinate.Coordinate;
import no.ntnu.idatg2003.mappe10.model.tile.Country;
import no.ntnu.idatg2003.mappe10.model.tile.MonopolyTile;
import no.ntnu.idatg2003.mappe10.model.tile.Tile;
import no.ntnu.idatg2003.mappe10.ui.controller.BoardGameController;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MonopolyGameRenderer extends Renderer {
    // Map countries to colors for tile coloring
    private final Map<String, Color> countryColors = new HashMap<>();

    public MonopolyGameRenderer(BoardGameController controller, Canvas canvas) {
        super(controller, canvas);

        // Assign each country a distinct color (feel free to change these)
        countryColors.put("Mongolia", Color.SKYBLUE);
        countryColors.put("Philippines", Color.LIGHTPINK);
        countryColors.put("Vietnam", Color.LIGHTGREEN);
        countryColors.put("Thailand", Color.GOLD);
        countryColors.put("Indonesia", Color.ORANGE);
        countryColors.put("China", Color.LIGHTCORAL);
        countryColors.put("South Korea", Color.PLUM);
        countryColors.put("Japan", Color.LIGHTYELLOW);
    }

    @Override
    public void drawBoard() {
        GraphicsContext gc = super.getCanvas().getGraphicsContext2D();

        super.drawBackground();
        colorTiles(gc);
        super.drawTiles();
        numerateTiles(gc);
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
                String country = monopolyTile.getProperty().getCountry().getName();
                System.out.println("Tile " + tileId + " country: " + country);
                Color color = countryColors.getOrDefault(country, Color.WHITE);

                gc.setFill(color);
                gc.fillRect(x, y, super.getTileWidth(), super.getTileHeight());
            } else {
                gc.setFill(Color.LIGHTGRAY);
                gc.fillRect(x, y, super.getTileWidth(), super.getTileHeight());
            }
        }
    }

    private void numerateTiles(GraphicsContext gc) {
        int numberOfTiles = super.getController().getNumberOfTiles();

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.75);

        for (int tileId = 1; tileId <= numberOfTiles; tileId++) {
            Coordinate canvasCoords = super.getController()
                  .getCanvasCoords(tileId, super.getOffsetWidth(), super.getOffsetHeight());
            double x = canvasCoords.getX0();
            double y = canvasCoords.getX1();

            gc.strokeText(String.valueOf(tileId), x + super.getTileWidth() / 4, y + super.getTileHeight() / 3);
        }
    }
}
