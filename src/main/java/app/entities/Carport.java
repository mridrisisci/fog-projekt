package app.entities;

import java.util.List;

public class Carport
{
    private final int HEIGHT;
    private final int WIDTH;
    private final int LENGTH;
    private boolean hasShed;
    private RoofType roofType;
    public List<Material> materialsList;

    public Carport()
    {

    }

    public int calcCarportMaterial()
    {
        return 0;
    }

    public void calcCarportPrice()
    {

    }

    public Material calcPosts(Material material)
    {
        return null;
    }

    public Material calcBeams(Material material)
    {
        return null;
    }

    public Material calcRafters(Material material)
    {
        return null;
    }

    public int getRafters()
    {
        return 0;
    }

    public int getBeams()
    {
        return 0;
    }

    public int getPosts()
    {
        return 0;
    }

}
