package app.entities;

public class Roof
{
    private int roofLength;
    private int roofWidth;
    private final RoofType type;

    public int getRoofLength()
    {
        return roofLength;
    }

    public int getRoofWidth()
    {
        return roofWidth;
    }

    public RoofType getType()
    {
        return type;
    }

    public Roof(RoofType type, int roofWidth, int roofLength)
    {
        this.type = type;
        this.roofWidth = roofWidth;
        this.roofLength = roofLength;
    }
}
