package no.ntnu.idatg2003.mappe10.model.tile;

public class CruiseDock extends Property{
    private String name;
    private int price;
    private int rent;

    public CruiseDock(String name, int price, int rent) {
        super(name, null);
        this.price = price;
        this.rent = rent;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getRent() {
        return rent;
    }



}
