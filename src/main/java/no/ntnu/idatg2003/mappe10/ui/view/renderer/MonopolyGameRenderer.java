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

public class MonopolyGameRenderer implements Renderer {

    private BoardGameController controller;
    private Canvas canvas;

    // Map countries to colors for tile coloring
    private final Map<String, Color> countryColors = new HashMap<>();

    public MonopolyGameRenderer(BoardGameController controller, Canvas canvas) {
        this.controller = controller;
        this.canvas = canvas;

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

    private void drawCanvas(double width, double height, GraphicsContext gc) {
        gc.clearRect(0, 0, width, height);
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, width, height);
    }

    private void colorTiles(double width, double height, double tileWidth, double tileHeight, GraphicsContext gc) {
        int numberOfTiles = controller.getNumberOfTiles();

        double offsetWidth = width - tileWidth;
        double offsetHeight = height - tileHeight;

        for (int tileId = 1; tileId <= numberOfTiles; tileId++) {
            Coordinate canvasCoords = controller.getCanvasCoords(tileId, offsetWidth, offsetHeight);
            double x = canvasCoords.getX0();
            double y = canvasCoords.getX1();

            Tile tile = controller.getTileById(tileId);
            MonopolyTile monopolyTile = tile.getMonopolyTile();

            if (monopolyTile != null && monopolyTile.getProperty() != null) {
                String country = monopolyTile.getProperty().getCountry().getName();
                System.out.println("Tile " + tileId + " country: " + country);
                Color color = countryColors.getOrDefault(country, Color.WHITE);

                gc.setFill(color);
                gc.fillRect(x, y, tileWidth, tileHeight);
            } else {
                gc.setFill(Color.LIGHTGRAY);
                gc.fillRect(x, y, tileWidth, tileHeight);
            }
        }
    }


    private void drawTiles(double width, double height, double tileWidth, double tileHeight, GraphicsContext gc) {
        int numberOfTiles = controller.getNumberOfTiles();

        double offsetWidth = width - tileWidth;
        double offsetHeight = height - tileHeight;

        gc.setLineWidth(2);
        gc.setStroke(Color.BLACK);

        for (int tileId = 1; tileId <= numberOfTiles; tileId++) {
            Coordinate canvasCoords = controller.getCanvasCoords(tileId, offsetWidth, offsetHeight);
            double x = canvasCoords.getX0();
            double y = canvasCoords.getX1();

            gc.strokeRect(x, y, tileWidth, tileHeight);
        }
    }

    private void numerateTiles(double width, double height, double tileWidth, double tileHeight, GraphicsContext gc) {
        int numberOfTiles = controller.getNumberOfTiles();

        double offsetWidth = width - tileWidth;
        double offsetHeight = height - tileHeight;

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.75);

        for (int tileId = 1; tileId <= numberOfTiles; tileId++) {
            Coordinate canvasCoords = controller.getCanvasCoords(tileId, offsetWidth, offsetHeight);
            double x = canvasCoords.getX0();
            double y = canvasCoords.getX1();

            gc.strokeText(String.valueOf(tileId), x + tileWidth / 4, y + tileHeight / 3);
        }
    }

    private void drawPlayers(double offsetWidth, double offsetHeight, double tileWidth, double tileHeight, GraphicsContext gc) {
        controller.getPlayerListIterator().forEachRemaining(player -> {
            int currentTileId = player.getCurrentTile().getTileId();
            Coordinate canvasCoords = controller.getCanvasCoords(currentTileId, offsetWidth, offsetHeight);
            double x = canvasCoords.getX0() + tileWidth / 4;
            double y = canvasCoords.getX1() + tileHeight / 4;

            InputStream inputStream = getClass().getResourceAsStream("/playingPieces/" + player.getPlayingPiece() + ".png");
            if (inputStream == null) {
                System.out.println("Image not found: " + player.getPlayingPiece());
                return;
            }

            Image image = new Image(inputStream);
            gc.drawImage(image, x, y, tileWidth / 2, tileHeight / 2);
        });
    }

    @Override
    public void drawBoard() {
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        double tileWidth = controller.getTileWidth(width);
        double tileHeight = controller.getTileHeight(height);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawCanvas(width, height, gc);
        colorTiles(width, height, tileWidth, tileHeight, gc);
        drawTiles(width, height, tileWidth, tileHeight, gc);
        numerateTiles(width, height, tileWidth, tileHeight, gc);
        drawPlayers(width - tileWidth, height - tileHeight, tileWidth, tileHeight, gc);
    }
}
