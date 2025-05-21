package no.ntnu.idatg2003.mappe10.ui.view.renderer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import no.ntnu.idatg2003.mappe10.ui.controller.BoardGameController;

import java.io.InputStream;

public class LostDiamondGameRenderer extends Renderer {

  public LostDiamondGameRenderer(BoardGameController controller, Canvas canvas) {
    super(controller, canvas);
  }

  @Override
  public void drawBoard() {
    drawBackground();
    super.drawTiles();
    super.numerateTiles();
    super.drawPlayers(1.4);
  }

  @Override
  public void drawBackground() {
    GraphicsContext gc = super.getCanvas().getGraphicsContext2D();
    InputStream io = getClass().getResourceAsStream("/board/lostDiamondBackground.png");
    Image background = new Image(io);
    gc.drawImage(background,0,0, getCanvasWidth(), getCanvasHeight());
  }

}
