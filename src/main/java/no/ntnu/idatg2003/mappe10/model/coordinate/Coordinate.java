package no.ntnu.idatg2003.mappe10.model.coordinate;

public class Coordinate {
  private double x0;
  private double x1;

  /**
   * Creates a new coordinate with the given x and y values.
   *
   * @param x0 the x0 value of the coordinate
   * @param x1 the x1 value of the coordinate
   */
  public Coordinate(double x0, double x1) {
    this.x0 = x0;
    this.x1 = x1;
  }

  /**
   * Returns the x value of the coordinate.
   *
   * @return the x value of the coordinate
   */
  public double getX0() {
    return x0;
  }

  /**
   * Returns the y value of the coordinate.
   *
   * @return the y value of the coordinate
   */
  public double getX1() {
    return x1;
  }
}
