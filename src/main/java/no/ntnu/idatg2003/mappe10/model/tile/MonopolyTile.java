package no.ntnu.idatg2003.mappe10.model.tile;

public class MonopolyTile extends Tile{
    private Property property;

    /**
     * Creates a new tile with the given id.
     * The next tile is set to null, and the land action is set to null.
     * The coordinates of the tile are pre-set to (0, 0).
     * The next tile, land action and xy coordinates can be set with the setNextTile, setLandAction and set-X/Y-Coordinate method.
     *
     * @param tileId the id of the tile
     */
    public MonopolyTile(int tileId, Property property) {
        super(tileId);
        this.property = property;
    }

    public Property getProperty() {
        return property;
    }
    public Property getPropertyByName(String name) {
        if (property.getName().equals(name)) {
            return property;
        }
        return null;
    }

}
