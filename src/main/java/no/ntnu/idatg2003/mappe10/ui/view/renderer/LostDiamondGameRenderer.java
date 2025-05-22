package no.ntnu.idatg2003.mappe10.ui.view.renderer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import no.ntnu.idatg2003.mappe10.model.coordinate.Coordinate;
import no.ntnu.idatg2003.mappe10.ui.controller.BoardGameController;

import java.io.InputStream;

public class LostDiamondGameRenderer extends Renderer {

  private final GraphicsContext gc = super.getCanvas().getGraphicsContext2D();

  private final double diameter = 30;
  private final double radius = diameter / 2;

  public LostDiamondGameRenderer(BoardGameController controller, Canvas canvas) {
    super(controller, canvas);
  }

  @Override
  public void drawBoard() {
    drawBackground();

    drawTiles();

    drawPlayers();
  }

  @Override
  public void drawBackground() {
    gc.clearRect(0, 0, getCanvasWidth(), getCanvasHeight());
    InputStream io = getClass().getResourceAsStream("/board/lostDiamondBackground.png");
    Image background = new Image(io, getCanvasWidth(), getCanvasHeight(), true, true);
    gc.drawImage(background, 0, 0, getCanvasWidth(), getCanvasHeight());
  }

  @Override
  public void drawTiles() {
    int numberOfTiles = super.getController().getNumberOfTiles();
    for (int i = 1; i <= numberOfTiles; i++) {
      Coordinate canvasCoords = super.getController().getCanvasCoords(i, getOffsetWidth(), getOffsetHeight());
      double x = canvasCoords.getX0();
      double y = canvasCoords.getX1();

      gc.setFill(Color.YELLOW);

      gc.fillOval(x - radius, y - radius, diameter, diameter);
    }
  }

  @Override
  public void drawPlayers() {
    super.getController().getPlayerListIterator().forEachRemaining(player -> {
      InputStream inputStream = getClass().getResourceAsStream("/playingPieces/" + player.getPlayingPiece() + ".png");
      if (inputStream == null) {
        System.out.println("Image not found: " + player.getPlayingPiece());
        return;
      }

      Image image = new Image(inputStream, 70, 70, true, true);
      double scale = 0.67 * Math.min(diameter / image.getWidth(), diameter / image.getHeight());
      double scaledWidth = image.getWidth() * scale;
      double scaledHeight = image.getHeight() * scale;

      int currentTileID = player.getCurrentTile().getTileId();
      Coordinate canvasCoords = super.getController()
            .getCanvasCoords(currentTileID, super.getOffsetWidth(), super.getOffsetHeight());
      double x = canvasCoords.getX0();
      double y = canvasCoords.getX1();

      double centeredX = x - scaledWidth / 2;
      double centeredY = y - scaledHeight / 2;

      gc.drawImage(image, centeredX, centeredY, scaledWidth, scaledHeight);
    });
  }

}
