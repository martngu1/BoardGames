package no.ntnu.idatg2003.mappe10.ui.view.renderer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import no.ntnu.idatg2003.mappe10.model.coordinate.Coordinate;
import no.ntnu.idatg2003.mappe10.ui.controller.BoardGameController;

import java.io.InputStream;

public class LadderGameRenderer implements Renderer {

    private BoardGameController controller;
    private Canvas canvas;


    public LadderGameRenderer(BoardGameController controller, Canvas canvas){
        this.canvas = canvas;
        this.controller = controller;
    }


    private void drawCanvas(double width, double height, GraphicsContext gc){
        gc.clearRect(0, 0, width, height);

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, width, height);
    }
    private void drawTiles(double width, double height, double tileWidth, double tileHeight, GraphicsContext gc){

        double offsetWidth = width - tileWidth;
        double offsetHeight = height - tileHeight;

        // Draw the board
        int numberOfTiles = controller.getNumberOfTiles();
        for (int i = 1; i <= numberOfTiles; i++) {
            Coordinate canvasCoords = controller.getCanvasCoords(i, offsetWidth, offsetHeight);
            double x = canvasCoords.getX0();
            double y = canvasCoords.getX1();

            gc.setLineWidth(2);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(x, y, tileWidth, tileHeight);
        }
    }
    private void numerateTiles(double width, double height, double tileWidth, double tileHeight, GraphicsContext gc) {
        double offsetWidth = width - tileWidth;
        double offsetHeight = height - tileHeight;

        gc.setStroke(Color.BLACK);
        // Numerate tiles
        int numberOfTiles = controller.getNumberOfTiles();
        for (int i = 1; i <= numberOfTiles; i++) {
            Coordinate canvasCoords = controller.getCanvasCoords(i, offsetWidth, offsetHeight);
            double x = canvasCoords.getX0();
            double y = canvasCoords.getX1();

            gc.setLineWidth(0.75);
            gc.strokeText(String.valueOf(i), x + tileWidth / 2, y + tileHeight / 2);
        }
    }
    private void drawActionTiles(double canvasWidth, double canvasHeight, double tileWidth, double tileHeight) {
        int numberOfTiles = controller.getNumberOfTiles();
        for (int id = 1; id <= numberOfTiles; id++) {
            // Java does not support switch cases with instanceof, so we use if-else statements instead
            if (controller.checkIfTileAction(id).equals("Ladder")) {

                Coordinate sourceCoords = controller.getCanvasCoords(id, canvasWidth, canvasHeight);
                double startX = sourceCoords.getX0();
                double startY = sourceCoords.getX1();

                int destTileId = controller.getDestinationTileId(id);
                Coordinate destCoords = controller.getCanvasCoords(destTileId, canvasWidth, canvasHeight);
                double endX = destCoords.getX0();
                double endY = destCoords.getX1();

                drawLadders(id, destTileId, startX, startY, endX, endY, tileWidth, tileHeight);
                drawArrowHeads(startX + tileWidth / 2, startY + tileHeight / 2, endX + tileWidth * (3.0/8.0)
                        , endY + tileHeight * (3.0/8.0));
            } else if (controller.checkIfTileAction(id).equals("Prison")) {
                drawPrison(id, canvasWidth, canvasHeight, tileWidth, tileHeight);
            }
        }
    }
    private void colorActionTiles(double canvasWidth, double canvasHeight,double tileWidth, double tileHeight,
                                  GraphicsContext gc){
        int numberOfTiles = controller.getNumberOfTiles();
        for (int tileId = 1; tileId <= numberOfTiles; tileId++) {
            // Java does not support switch cases with instanceof, so we use if-else statements instead
            if (controller.checkIfTileAction(tileId).equals("Ladder")) {
                Coordinate sourceCoords = controller.getCanvasCoords(tileId, canvasWidth, canvasHeight);
                double startX = sourceCoords.getX0();
                double startY = sourceCoords.getX1();

                int destTileId = controller.getDestinationTileId(tileId);
                Coordinate destCoords = controller.getCanvasCoords(destTileId, canvasWidth, canvasHeight);
                double endX = destCoords.getX0();
                double endY = destCoords.getX1();

                colorLadderTiles(tileId, destTileId, startX, startY, endX, endY, tileWidth, tileHeight, gc);
            } else if (controller.checkIfTileAction(tileId).equals("Prison")) {
                colorPrisonTiles(tileId, canvasWidth, canvasHeight, tileWidth, tileHeight);
            }
        }
    }
    private void colorLadderTiles(int tileId, int destTileId,
                            double startX, double startY, double endX, double endY,
                            double tileWidth, double tileHeight,
                            GraphicsContext gc){
            gc.setLineWidth(2);
        if (destTileId > tileId) {
            gc.setFill(Color.DARKGREEN);
            gc.fillRect(startX, startY, tileWidth, tileHeight);

            gc.setFill(Color.LIGHTGREEN);
            gc.fillRect(endX, endY, tileWidth, tileHeight);

            //gc.setLineWidth(1);
            //gc.fillText(String.valueOf(tileId), startX + tileWidth / 4 + tileWidth / 8, startY + tileHeight / 4 + tileHeight / 8);
            //gc.fillText(String.valueOf(destTileId),endX + tileWidth / 4 + tileWidth / 8, endY + tileHeight / 4 + tileHeight / 8);
        } else {
            gc.setFill(Color.RED);
            gc.fillRect(startX, startY, tileWidth, tileHeight);

            gc.setFill(Color.DARKRED);
            gc.fillRect(endX, endY, tileWidth, tileHeight);

            //gc.setLineWidth(1);
            //gc.fillText(String.valueOf(tileId), startX + tileWidth / 4 + tileWidth / 8, startY + tileHeight / 4 + tileHeight / 8);
            //gc.fillText(String.valueOf(destTileId), endX + tileWidth / 4 + tileWidth / 8, endY + tileHeight / 4 + tileHeight / 8);
        }
    }
    private void colorPrisonTiles(int tileId, double canvasWidth, double canvasHeight, double tileWidth, double tileHeight) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Coordinate rectCoords = controller.getCanvasCoords(tileId, canvasWidth, canvasHeight);
        double x = rectCoords.getX0();
        double y = rectCoords.getX1();
        gc.setFill(Color.ORANGE);
        gc.fillRect(x, y, tileWidth, tileHeight);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeRect(x, y, tileWidth, tileHeight);
    }


    @Override
    public void drawBoard() {
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        double tileWidth = controller.getTileWidth(width);
        double tileHeight = controller.getTileHeight(height);

        double offsetWidth = width - tileWidth;
        double offsetHeight = height - tileHeight;

        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawCanvas(width, height, gc);
        colorActionTiles(offsetWidth, offsetHeight, tileWidth, tileHeight, gc);
        drawTiles(width, height, tileWidth, tileHeight, gc);
        drawActionTiles(offsetWidth, offsetHeight, tileWidth, tileHeight);
        numerateTiles(width, height, tileWidth, tileHeight, gc);
        drawPlayers(offsetWidth, offsetHeight, tileWidth, tileHeight, gc);
}

    public void drawPlayers(double offsetWidth, double offsetHeight,
                            double tileWidth, double tileHeight, GraphicsContext gc) {

        controller.getPlayerListIterator().forEachRemaining(player -> {
            Coordinate canvasCoords = controller.getCanvasCoords(
                    player.getCurrentTile().getTileId(),
                    offsetWidth,
                    offsetHeight);

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

    private void drawPrison(int tileId, double canvasWidth, double canvasHeight,
                            double tileWidth, double tileHeight) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Coordinate rectCoords = controller.getCanvasCoords(tileId, canvasWidth, canvasHeight);
        double x = rectCoords.getX0();
        double y = rectCoords.getX1();
        InputStream stream = getClass().getResourceAsStream("/tileIcons/prisonBars.png");
        if (stream == null) {
            System.err.println("Image resource not found!");
            return;
        }
        Image prisonImage = new Image(stream);
        // Draw the image scaled to fit the tile size
        gc.drawImage(prisonImage, x, y, tileWidth, tileHeight);
    }


private void drawLadders(int tileId, int destTileId,
                         double startX, double startY, double endX, double endY,
                         double tileWidth, double tileHeight) {
    GraphicsContext gc = canvas.getGraphicsContext2D();

    if (destTileId > tileId) {
        gc.setStroke(Color.GREEN);

        // gc.setFill(Color.BLACK);
        // gc.setLineWidth(1);
        //gc.fillText(String.valueOf(tileId), startX + tileWidth / 4 + tileWidth / 8, startY + tileHeight / 4 + tileHeight / 8);/
        // gc.fillText(String.valueOf(destTileId),endX + tileWidth / 4 + tileWidth / 8, endY + tileHeight / 4 + tileHeight / 8);
    } else {
        gc.setStroke(Color.MEDIUMVIOLETRED);

        //gc.setFill(Color.BLACK);
        //gc.setLineWidth(1);
        //gc.fillText(String.valueOf(tileId), startX + tileWidth / 4 + tileWidth / 8, startY + tileHeight / 4 + tileHeight / 8);
        //gc.fillText(String.valueOf(destTileId), endX + tileWidth / 4 + tileWidth / 8, endY + tileHeight / 4 + tileHeight / 8);
    }
    double scaledDestX = endX - tileWidth / 8;
    double scaledDestY = endY - tileHeight / 8;

    double x0 = startX + tileWidth / 2;
    double y0 = startY + tileHeight / 2;
    double x1 = scaledDestX + tileWidth / 2;
    double y1 = scaledDestY + tileHeight / 2;

    gc.strokeLine(x0, y0, x1, y1);
}

private void drawArrowHeads(double x, double y, double destX, double destY) {
    double arrowSize = 10;
    double angle = Math.atan2(destY - y, destX - x);
    double arrowAngle = Math.toRadians(20);

    GraphicsContext gc = canvas.getGraphicsContext2D();
    // Help from chatGPT
    gc.strokeLine(destX, destY, destX - arrowSize * Math.cos(angle - arrowAngle), destY - arrowSize * Math.sin(angle - arrowAngle));
    gc.strokeLine(destX, destY, destX - arrowSize * Math.cos(angle + arrowAngle), destY - arrowSize * Math.sin(angle + arrowAngle));
 }
}
