package app.entities;

public class Roof
{
    private int roofLength;
    private int roofWidth;
    private  RoofType roofType;

    public Roof(RoofType roofType, int roofWidth, int roofLength)
    {
        this.roofType = roofType;
        this.roofWidth = roofWidth;
        this.roofLength = roofLength;
    }

    public Roof(RoofType roofType)
    {
        this.roofType = roofType;
    }

    public int getRoofLength()
    {
        return roofLength;
    }

    public int getRoofWidth()
    {
        return roofWidth;
    }

    public RoofType getRoofType()
    {
        return roofType;
    }
}
