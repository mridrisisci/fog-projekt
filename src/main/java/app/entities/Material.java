package app.entities;

public class Material
{
    private int materialID;
    private String materialName;
    private String description;
    private int price;
    private String unit;
    private int quantity;
    private int length;
    private int height;
    private int width;
    private boolean hasShedAddons;
    private String type;

    public Material(int materialID, String materialName, String description, int price, String unit, int quantity, int length, int height, int width, boolean hasShedAddons)
    {
        this.materialID = materialID;
        this.materialName = materialName;
        this.description = description;
        this.price = price;
        this.unit = unit;
        this.quantity = quantity;
        this.length = length;
        this.height = height;
        this.width = width;
        this.hasShedAddons = hasShedAddons;
    }

    public Material(int materialID, String materialName, String description, int price, String unit, int length, int height, int width, String type)
    {
        this.materialID = materialID;
        this.materialName = materialName;
        this.description = description;
        this.price = price;
        this.unit = unit;
        this.length = length;
        this.height = height;
        this.width = width;
        this.type = type;
        }


    public Material(int materialID, String materialName, String description, int price, String unit, int quantity, int length, String type)
    {
        this.materialID = materialID;
        this.materialName = materialName;
        this.unit = unit;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.length = length;
        this.type = type;
    }

    public Material(int materialID, String materialName, String description, int price, String unit, int quantity, int length)
    {
        this.materialID = materialID;
        this.materialName = materialName;
        this.unit = unit;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.length = length;
    }

    public Material(int materialID, String materialName, String description, int price, String unit, int quantity, String type)
    {
        this.materialID = materialID;
        this.materialName = materialName;
        this.description = description;
        this.price = price;
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

    public int getPrice()
    {
        return price;
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

}
