package app.entities;

import app.persistence.MaterialMapper;

import java.util.ArrayList;
import java.util.List;

public class Carport
{
    private final int HEIGHT;
    private final int WIDTH;
    private final int LENGTH;
    private boolean hasShed;
    private RoofType Rooftype;
    private List<Material> materialList = new ArrayList<>();

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

    //Stople
    public int calcPosts(Order order)
    {
        //Antal stolper
        int quantity;
        //150 er en buffer fra starten og slutningen af remmen - 9,7 er bredden på stolpen, og til at starte med har man minimum to stolper per længde
        if (getLENGTH()-150-9.7-9.7 <= 480)
        {
            quantity = 4;
        } else {
            quantity = 6;
        }

        //Længde på stolperne - sikre vi får fat i den korrekte variant
        List<Material> materialList1 = (List<Material>) MaterialMapper.getMaterialByID();
            //Linjen er castet
        Material material1 = materialList1.get(0);

        return quantity;

    }

    //Remme
    public Material calcBeams(Material material)
    {
    //Der skal være minimum 2 af alt, da der på hver side af carporten er en rem.
        //Mangler at kalde materiale der skal bruges
        int quantity = 2;
        if(getLENGTH() <= 270){
            material = material.getMaterialID();
            //Woodmaterial length = 300
            quantity = 2;
        } else if (getLENGTH() <= 330)
        {
            material.getLength();
            //Woodmaterial length = 360
            quantity = 2;
        } else if (getLENGTH() <= 390)
        {
            material.getLength();
            //Woodmaterial length = 420
            quantity = 2;
        } else if (getLENGTH() <= 450)
        {
            material.getLength();
            //Woodmaterial length = 480
            quantity = 2;
        } else if (getLENGTH() <= 510)
        {
            material.getLength();
            //Woodmaterial length = 540
            quantity = 2;
        } else if (getLENGTH() <= 600)
        {
            material.getLength();
            //Woodmaterial length = 600
            quantity = 2;
        } else if (getLENGTH() <= 690) {
            material.getLength();
            //Woodmaterial length = 360
            quantity = 4;
        } else if (getLENGTH() <= 780){
            material.getLength();
            //Woodmaterial length = 420
            quantity = 4;

        }

        return material;

    }

    //Understern
    public Material calcBottomFasciaBoard(Material material)
    {
        if (getWIDTH() < 300)
        {

        }

        return material;
    }


    //Overstern
    public Material calcTopFasciaBoard(Material material)
    {
        return material;
    }


    //Lægter
    public Material calcBattern(Material material)
    {
        return material;
    }

    //Spær
    public Material calcRafters(Material material)
    {
        Material newMaterial = material;

        //den går igennem restSpær gennem, hvis der er 55, sæt et spær og minus 45

        return newMaterial;
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
