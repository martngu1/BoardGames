package no.ntnu.idatg2003.mappe10.ui.view.renderer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import no.ntnu.idatg2003.mappe10.model.coordinate.Coordinate;
import no.ntnu.idatg2003.mappe10.ui.controller.BoardGameController;

import java.io.InputStream;

public class LadderGameRenderer extends Renderer {

  public LadderGameRenderer(BoardGameController controller, Canvas canvas) {
    super(controller, canvas);
  }

  @Override
  public void drawBoard() {
    GraphicsContext gc = super.getCanvas().getGraphicsContext2D();

    super.drawBackground();
    colorActionTiles(gc);
    super.drawTiles();
    drawActionTiles();
    numerateTiles(gc);
    super.drawPlayers();
  }

  private void colorActionTiles(GraphicsContext gc) {
    int numberOfTiles = super.getController().getNumberOfTiles();
    for (int tileId = 1; tileId <= numberOfTiles; tileId++) {
      // Java does not support switch cases with instanceof, so we use if-else statements instead
      if (super.getController().checkIfTileAction(tileId).equals("Ladder")) {
        Coordinate sourceCoords = super.getController()
              .getCanvasCoords(tileId, super.getOffsetWidth(), super.getOffsetHeight());
        double startX = sourceCoords.getX0();
        double startY = sourceCoords.getX1();

        int destTileId = super.getController().getDestinationTileId(tileId);
        Coordinate destCoords = super.getController()
              .getCanvasCoords(destTileId, super.getOffsetWidth(), getOffsetHeight());
        double endX = destCoords.getX0();
        double endY = destCoords.getX1();

        colorLadderTiles(tileId, destTileId, startX, startY, endX, endY, gc);
      } else if (super.getController().checkIfTileAction(tileId).equals("Prison")) {
        colorPrisonTiles(tileId);
      } else if (super.getController().checkIfTileAction(tileId).equals("Winner")) {
        colorWinnerTile(tileId);
      }
    }
  }

  private void colorLadderTiles(int tileId, int destTileId,
                                double startX, double startY, double endX, double endY,
                                GraphicsContext gc) {
    gc.setLineWidth(2);
    if (destTileId > tileId) {
      gc.setFill(Color.DARKGREEN);
      gc.fillRect(startX, startY, super.getTileWidth(), super.getTileHeight());

      gc.setFill(Color.LIGHTGREEN);
      gc.fillRect(endX, endY, super.getTileWidth(), super.getTileHeight());
    } else {
      gc.setFill(Color.RED);
      gc.fillRect(startX, startY, super.getTileWidth(), super.getTileHeight());

      gc.setFill(Color.DARKRED);
      gc.fillRect(endX, endY, super.getTileWidth(), super.getTileHeight());
    }
  }


  private void colorPrisonTiles(int tileId) {
    GraphicsContext gc = super.getCanvas().getGraphicsContext2D();
    Coordinate rectCoords = super.getController().getCanvasCoords(tileId, super.getOffsetWidth(), super.getOffsetHeight());
    double x = rectCoords.getX0();
    double y = rectCoords.getX1();
    gc.setFill(Color.ORANGE);
    gc.fillRect(x, y, super.getTileWidth(), super.getTileHeight());

    gc.setStroke(Color.BLACK);
    gc.setLineWidth(1);
    gc.strokeRect(x, y, super.getTileWidth(), super.getTileHeight());
  }

  private void colorWinnerTile(int tileId) {
    GraphicsContext gc = super.getCanvas().getGraphicsContext2D();
    Coordinate rectCoords = super.getController()
          .getCanvasCoords(tileId, super.getOffsetWidth(), super.getOffsetHeight());
    double x = rectCoords.getX0();
    double y = rectCoords.getX1();
    gc.setFill(Color.YELLOW);
    gc.fillRect(x, y, super.getTileWidth(), super.getTileHeight());
  }

  private void drawActionTiles() {
    int numberOfTiles = super.getController().getNumberOfTiles();
    for (int id = 1; id <= numberOfTiles; id++) {
      // Java does not support switch cases with instanceof, so we use if-else statements instead
      if (super.getController().checkIfTileAction(id).equals("Ladder")) {

        Coordinate sourceCoords = super.getController()
              .getCanvasCoords(id, super.getOffsetWidth(), super.getOffsetHeight());
        double startX = sourceCoords.getX0();
        double startY = sourceCoords.getX1();

        int destTileId = super.getController().getDestinationTileId(id);
        Coordinate destCoords = super.getController()
              .getCanvasCoords(destTileId, super.getOffsetWidth(), super.getOffsetHeight());
        double endX = destCoords.getX0();
        double endY = destCoords.getX1();

        drawLadders(id, destTileId, startX, startY, endX, endY);
        drawArrowHeads(
              startX + super.getTileWidth() / 2,
              startY + super.getTileHeight() / 2,
              endX + super.getTileWidth() * (3.0 / 8.0),
              endY + getTileHeight() * (3.0 / 8.0)
        );
      } else if (super.getController().checkIfTileAction(id).equals("Prison")) {
        drawPrison(id);
      }
    }
  }

  private void drawPrison(int tileId) {
    GraphicsContext gc = super.getCanvas().getGraphicsContext2D();
    Coordinate rectCoords = super.getController()
          .getCanvasCoords(tileId, super.getOffsetWidth(), super.getOffsetHeight());
    double x = rectCoords.getX0();
    double y = rectCoords.getX1();
    InputStream stream = getClass().getResourceAsStream("/tileIcons/prisonBars.png");
    if (stream == null) {
      System.err.println("Image resource not found!");
      return;
    }
    Image prisonImage = new Image(stream);
    // Draw the image scaled to fit the tile size
    gc.drawImage(prisonImage, x, y, super.getTileWidth(), super.getTileHeight());
  }


  private void drawLadders(int tileId, int destTileId,
                           double startX, double startY, double endX, double endY) {
    GraphicsContext gc = super.getCanvas().getGraphicsContext2D();

    if (destTileId > tileId) {
      gc.setStroke(Color.GREEN);
    } else {
      gc.setStroke(Color.MEDIUMVIOLETRED);
    }
    double scaledDestX = endX - super.getTileWidth() / 8;
    double scaledDestY = endY - super.getTileHeight() / 8;

    double x0 = startX + super.getTileWidth() / 2;
    double y0 = startY + super.getTileHeight() / 2;
    double x1 = scaledDestX + super.getTileWidth() / 2;
    double y1 = scaledDestY + super.getTileHeight() / 2;

    gc.strokeLine(x0, y0, x1, y1);
  }

  private void drawArrowHeads(double x, double y, double destX, double destY) {
    double arrowSize = 10;
    double angle = Math.atan2(destY - y, destX - x);
    double arrowAngle = Math.toRadians(20);

    GraphicsContext gc = super.getCanvas().getGraphicsContext2D();
    // Help from chatGPT
    gc.strokeLine(destX, destY, destX - arrowSize * Math.cos(angle - arrowAngle), destY - arrowSize * Math.sin(angle - arrowAngle));
    gc.strokeLine(destX, destY, destX - arrowSize * Math.cos(angle + arrowAngle), destY - arrowSize * Math.sin(angle + arrowAngle));
  }

  private void numerateTiles(GraphicsContext gc) {
    gc.setStroke(Color.BLACK);
    // Numerate tiles
    int numberOfTiles = super.getController().getNumberOfTiles();
    for (int i = 1; i <= numberOfTiles; i++) {
      Coordinate canvasCoords = super.getController()
            .getCanvasCoords(i, super.getOffsetWidth(), super.getOffsetHeight());
      double x = canvasCoords.getX0();
      double y = canvasCoords.getX1();

      gc.setLineWidth(0.75);
      gc.strokeText(String.valueOf(i), x + super.getTileWidth() / 2, y + super.getTileHeight() / 2);
    }
  }

}
