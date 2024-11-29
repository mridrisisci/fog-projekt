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
    private String type;

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

    public Material(int materialID, String materialName, String description, int purchasePrice, String unit, int quantity, int length)
    {
        this.materialID = materialID;
        this.materialName = materialName;
        this.unit = unit;
        this.description = description;
        this.purchasePrice = purchasePrice;
        this.length = length;
    }

    public Material(int materialID, String materialName, String description, int purchasePrice, String unit, int quantity, String type)
    {
        this.materialID = materialID;
        this.materialName = materialName;
        this.description = description;
        this.purchasePrice = purchasePrice;
        this.unit = unit;
        this.quantity = quantity;
        this.type = type;
    }



    public Material(String materialName, String unit, String description)
    {
        this.materialName = materialName;
        this.unit = unit;
        this.description = description;
    }

    public Material(String name, String unit, String description, String type, int quantity)
    {
        this.materialName = name;
        this.unit = unit;
        this.description = description;
        this.type = type;
        this.quantity = quantity;
    }

    public Material(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
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
