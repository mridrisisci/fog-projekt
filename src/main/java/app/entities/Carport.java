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

    //Stolpe
    public int calcPosts(Order order)
    {
        //Antal stolper
        int quantity;
        //150 er en buffer fra starten og slutningen af remmen - 9,7 er bredden på stolpen, og til at starte med har man minimum to stolper per længde
        if (getLENGTH() - 150 - 9.7 - 9.7 <= 480)
        {
            quantity = 4;
        } else
        {
            quantity = 6;
        }

        //Længde på stolperne - sikre vi får fat i den korrekte variant
        List<Material> materialList1 = (List<Material>) MaterialMapper.getMaterialByID();
        //Linjen er castet
        Material material1 = materialList1.get(0);

        return quantity;

    }

    //Remme
    public int calcBeams(Material material)
    {
        //Der skal være minimum 2 af alt, da der på hver side af carporten er en rem.
        //Mangler at kalde materiale der skal bruges'
        //Længderne skal også ændres så det ikke hardcodes men hentes fra køberens valgmuligheder
        int quantity = 2;
        if (getLENGTH() <= 300)
        {
            //material = material.getMaterialID();
            //Woodmaterial length = 600
            quantity = 1;
        } else if (330 < getLENGTH() || getLENGTH() <= 480)
        {
            //Mangler metode/noget der kalder på specifikke MaterialVariant
            //Woodmaterial length = 480
            quantity = 2;
        } else if (480 < getLENGTH() || getLENGTH() <= 600)
        {
            //Mangler metode/noget der kalder på specifikke MaterialVariant
            //Woodmaterial length = 600
            quantity = 2;
        } else if (600 < getLENGTH() || getLENGTH() <= 780)
        {
            //Mangler metode/noget der kalder på specifikke MaterialVariant
            //Woodmaterial length = 480
            quantity = 4;
        }

        return quantity;

    }

    //Stern på remme
    public int calcLengthFasciaBoard(Material material)
    {
        int quantity = 2;
        if (getLENGTH() <= 270)
        {
            //Mangler metode/noget der kalder på specifikke MaterialVariant
            //Woodmaterial length = 540
            quantity = 1;
        } else if (270 < getLENGTH() || getLENGTH() <= 360)
        {
            //Mangler metode/noget der kalder på specifikke MaterialVariant
            //Woodmaterial length = 360
            quantity = 2;
        } else if (360 < getLENGTH() || getLENGTH() <= 540)
        {
            //Mangler metode/noget der kalder på specifikke MaterialVariant
            //Woodmaterial length = 540
            quantity = 2;
        } else if (540 < getLENGTH() || getLENGTH() <= 690)
        {
            //Mangler metode/noget der kalder på specifikke MaterialVariant
            //Woodmaterial length = 360
            quantity = 4;
        } else if (690 < getLENGTH() || getLENGTH() <= 780)
        {
            //Mangler metode/noget der kalder på specifikke MaterialVariant
            //Woodmaterial length = 540
            quantity = 4;
        }

        return quantity;
    }


    //Stern til for- og bagside
    public int calcWidthFasciaBoard(Material material)
    {
        int quantity = 2;
        if (getWIDTH() <= 270)
        {
            //Mangler metode/noget der kalder på specifikke MaterialVariant
            //Woodmaterial length = 540
            quantity = 1;
        } else if (270 < getWIDTH() || getWIDTH() <= 360)
        {
            //Mangler metode/noget der kalder på specifikke MaterialVariant
            //Woodmaterial length = 360
            quantity = 2;
        } else if (360 < getWIDTH() || getWIDTH() <= 540)
        {
            //Mangler metode/noget der kalder på specifikke MaterialVariant
            //Woodmaterial length = 540
            quantity = 2;
        } else if (540 < getWIDTH() || getWIDTH() <= 600)
        {
            //Mangler metode/noget der kalder på specifikke MaterialVariant
            //Woodmaterial length = 360
            quantity = 4;
        }

        return quantity;
    }


    //Lægter - Er ikke med i materialelisten udover til z-et på døren
    /*public Material calcBattern(Material material)
    {
        return material;
    }*/

    //Spær
    public Material calcRafters(Material material)
    {
        Material newMaterial = material;
        for (int i = 0; i < materialList.size(); i++)
        {
            //Tilføj et spær til StykListen
        }
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