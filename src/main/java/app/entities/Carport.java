package app.entities;

import java.util.List;

public class Carport
{
    private final int HEIGHT;
    private final int WIDTH;
    private final int LENGTH;
    private boolean hasShed;
    private RoofType Rooftype;
    public List<Material> materialList;

    public Carport(List<Material> materialList, RoofType rooftype, boolean hasShed, int LENGTH, int WIDTH, int HEIGHT)
    {
        this.materialList = materialList;
        Rooftype = rooftype;
        this.hasShed = hasShed;
        this.LENGTH = LENGTH;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    public int calcCarportMaterial()
    {
        return 0;
    }

    public void calcCarportPrice()
    {

    }

    /*Der skal laves noget hvor der er metoder der tager kostpris ud fra hvormange beams,posts,rafters
     * så skal der være en metode der beregner salgspris
     * en der beregner dækningspris*/

    public Material calcPosts(Material material)
    {
        if (WIDTH <= 300 && LENGTH <= 300)
        {
            int posts = 3;
        } else if (WIDTH <= 300 && LENGTH > 300)
        {
         int posts = 5;
        }

        return material;

    }

    public Material calcBeams(Material material)
    {
        return material;

    }

    public Material calcRafters(Material material)
    {
        return material;
    }

    public int getHEIGHT()
    {
        return HEIGHT;
    }

    public int getWIDTH()
    {
        return WIDTH;
    }

    public int getLENGTH()
    {
        return LENGTH;
    }

    public boolean isHasShed()
    {
        return hasShed;
    }

    public RoofType getRooftype()
    {
        return Rooftype;
    }

    public List<Material> getMaterialList()
    {
        return materialList;
    }
}
