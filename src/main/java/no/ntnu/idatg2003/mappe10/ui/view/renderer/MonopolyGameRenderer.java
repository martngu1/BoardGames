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

public class MonopolyGameRenderer implements Renderer {

    private BoardGameController controller;
    private Canvas canvas;

    // Map countries to colors for tile coloring
    private final Map<String, Color> propertyColors = new HashMap<>();
    private final Map<String, String> specialTileImages = new HashMap<>();

    public MonopolyGameRenderer(BoardGameController controller, Canvas canvas) {
        this.controller = controller;
        this.canvas = canvas;

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
                gc.fillRect(x, y, tileWidth, tileHeight);

                if (imagePath != null) {
                    drawTileImage(gc, imagePath, x, y, tileWidth, tileHeight);
                }
                double price = monopolyTile.getProperty().getPrice();
                gc.setFill(Color.BLACK);
                gc.setLineWidth(0.75);
                gc.fillText("$" + price, x + tileWidth * 0.05, y + tileHeight * 0.20);
            }

            else if (tile.getLandAction() != null) { // Special Tiles
                String actionClassName = tile.getLandAction().getClass().getSimpleName();
                String imagePath = specialTileImages.get(actionClassName);

                if (imagePath != null) {
                    drawTileImage(gc, imagePath, x, y, tileWidth, tileHeight);
                }
            } else  if (tileId == 1) { // Start Tile
                Color color = Color.GREEN;
                gc.setFill(color);
                gc.fillRect(x, y, tileWidth, tileHeight);
            } else { // Free Parking and special tiles gets color lightgray
                gc.setFill(Color.LIGHTGRAY);
                gc.fillRect(x, y, tileWidth, tileHeight);

                String imagePath = specialTileImages.get("FreeParking");
                if (imagePath != null) {
                    drawTileImage(gc, imagePath, x, y, tileWidth, tileHeight);
                }
            }
        }
    }
    private void drawTileImage(GraphicsContext gc, String imagePath, double x, double y, double tileWidth, double tileHeight) {
        InputStream stream = getClass().getResourceAsStream(imagePath);
        if (stream == null) {
            System.err.println("Image not found: " + imagePath);
            return;
        }
        Image image = new Image(stream);
        gc.drawImage(image, x, y, tileWidth, tileHeight);
    }

    private void drawPropertyOwners(double width, double height, double tileWidth, double tileHeight, GraphicsContext gc) {

        double offsetWidth = width - tileWidth;
        double offsetHeight = height - tileHeight;

        for (int tileId = 1; tileId <= controller.getNumberOfTiles(); tileId++) {
            Tile tile = controller.getTileById(tileId);
            MonopolyTile monopolyTile = tile.getMonopolyTile();

            if (monopolyTile != null && monopolyTile.getProperty() != null) {
                if (monopolyTile.getProperty().isOwned()) {
                    String playingPiece = monopolyTile.getProperty().getOwner().getPlayingPiece();
                    InputStream inputStream = getClass().getResourceAsStream("/playingPieces/" + playingPiece + ".png");

                    if (inputStream == null) {
                        System.out.println("Image not found for owner: " + playingPiece);
                        continue;
                    }

                    Coordinate canvasCoords = controller.getCanvasCoords(tileId, offsetWidth, offsetHeight);
                    double x = canvasCoords.getX0();
                    double y = canvasCoords.getX1();

                    Image image = new Image(inputStream);

                    gc.drawImage(image, x + tileWidth * 0.7, y + tileHeight * 0.05, tileWidth * 0.25, tileHeight * 0.25);
                }
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

            gc.strokeText(String.valueOf(tileId), x + tileWidth * 0.75, y + tileHeight * 0.20);
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
        drawPropertyOwners(width, height, tileWidth, tileHeight, gc);
    }
}
