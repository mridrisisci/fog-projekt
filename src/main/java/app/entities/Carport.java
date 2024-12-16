package app.entities;

import java.util.ArrayList;
import java.util.List;

public class Carport
{
    private final int WIDTH;
    private final int LENGTH;
    private int orderID;
    private boolean hasShed;
    private RoofType roofType;
    private int price;

    private List<Material> materialList = new ArrayList<>();

    public Carport(List<Material> materialList, RoofType roofType, boolean hasShed, int LENGTH, int WIDTH, int price)
    {
        this.materialList = materialList;
        this.roofType = roofType;
        this.hasShed = hasShed;
        this.LENGTH = LENGTH;
        this.WIDTH = WIDTH;
        this.price = price;
    }

    public Carport(int orderID, int LENGTH, int WIDTH)
    {
        this.orderID = orderID;
        this.LENGTH = LENGTH;
        this.WIDTH = WIDTH;
    }

    public int getOrderID()
    {
        return orderID;
    }

    public int getWIDTH()
    {
        return WIDTH;
    }

    public int getLENGTH()
    {
        return LENGTH;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public void setMaterialList(List<Material> materialList)
    {
        this.materialList = materialList;
    }

    public List<Material> getMaterialList()
    {
        return materialList;
    }
}
