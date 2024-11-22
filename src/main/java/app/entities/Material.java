package app.entities;

public class Material
{
    private int materialID;
    private String materialName;
    private String description;
    private int purchasePrice;
    private String unit;
    private int quantity;
    private int length;
    private int height;
    private int width;
    private boolean hasShedAddons;
    public int getNumbersOfShedAddons;

    public Material(int materialID, String materialName, String description, int purchasePrice, String unit, int quantity, int length, int height, int width, boolean hasShedAddons, int getNumbersOfShedAddons)
    {
        this.materialID = materialID;
        this.materialName = materialName;
        this.description = description;
        this.purchasePrice = purchasePrice;
        this.unit = unit;
        this.quantity = quantity;
        this.length = length;
        this.height = height;
        this.width = width;
        this.hasShedAddons = hasShedAddons;
        this.getNumbersOfShedAddons = getNumbersOfShedAddons;
    }

    public int getMaterialID()
    {
        return materialID;
    }

    public String getMaterialName()
    {
        return materialName;
    }

    public String getDescription()
    {
        return description;
    }

    public int getPurchasePrice()
    {
        return purchasePrice;
    }

    public String getUnit()
    {
        return unit;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public int getLength()
    {
        return length;
    }

    public int getHeight()
    {
        return height;
    }

    public int getWidth()
    {
        return width;
    }

    public boolean isHasShedAddons()
    {
        return hasShedAddons;
    }

    public int getGetNumbersOfShedAddons()
    {
        return getNumbersOfShedAddons;
    }
}
