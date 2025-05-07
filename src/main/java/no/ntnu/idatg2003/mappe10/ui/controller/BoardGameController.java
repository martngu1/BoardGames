package no.ntnu.idatg2003.mappe10.ui.controller;

import no.ntnu.idatg2003.mappe10.ui.view.BoardGameView;

public class BoardGameController {
  BoardGameView boardGameView;

  /**
   * Creates a new BoardGameController with the given view.
   *
   * @param view the view to control
   */
  public BoardGameController(BoardGameView view) {
    this.boardGameView = view;
  }
}