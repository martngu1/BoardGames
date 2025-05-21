package no.ntnu.idatg2003.mappe10.ui.view.renderer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import no.ntnu.idatg2003.mappe10.model.coordinate.Coordinate;
import no.ntnu.idatg2003.mappe10.ui.controller.BoardGameController;

import java.io.InputStream;

public abstract class Renderer {

    private BoardGameController controller;
    private Canvas canvas;

    protected Renderer(BoardGameController controller, Canvas canvas) {
        this.controller = controller;
        this.canvas = canvas;
    }

    public abstract void drawBoard();

    public void drawBackground(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, getCanvasWidth(), getCanvasHeight());

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, getCanvasWidth(), getCanvasHeight());
    }

    public void drawTiles() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // Draw the board
        int numberOfTiles = controller.getNumberOfTiles();
        for (int i = 1; i <= numberOfTiles; i++) {
            Coordinate canvasCoords = controller.getCanvasCoords(i, getOffsetWidth(), getOffsetHeight());
            double x = canvasCoords.getX0();
            double y = canvasCoords.getX1();

            gc.setLineWidth(2);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(x, y, getTileWidth(), getTileHeight());
        }
    }

    public void drawPlayers() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        controller.getPlayerListIterator().forEachRemaining(player -> {
            int currentTileId = player.getCurrentTile().getTileId();
            Coordinate canvasCoords = controller.getCanvasCoords(currentTileId, getOffsetWidth(), getOffsetHeight());
            double x = canvasCoords.getX0() + getTileWidth() / 4;
            double y = canvasCoords.getX1() + getTileHeight() / 4;

            InputStream inputStream = getClass().getResourceAsStream("/playingPieces/" + player.getPlayingPiece() + ".png");
            if (inputStream == null) {
                System.out.println("Image not found: " + player.getPlayingPiece());
                return;
            }

            Image image = new Image(inputStream);
            gc.drawImage(image, x, y, getTileWidth() / 2, getTileHeight() / 2);
        });
    }

    protected Canvas getCanvas() {
        return canvas;
    }

    protected BoardGameController getController() {
        return controller;
    }

    protected double getCanvasWidth() {
        return canvas.getWidth();
    }

    protected double getCanvasHeight() {
        return canvas.getHeight();
    }

    protected double getTileWidth() {
        return controller.getTileWidth(getCanvasWidth());
    }

    protected double getTileHeight() {
        return controller.getTileHeight(getCanvasHeight());
    }

    protected double getOffsetWidth() {
        return getCanvasWidth() - getTileWidth();
    }

    protected double getOffsetHeight() {
        return getCanvasHeight() - getTileHeight();
    }

}
