package app.entities;

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
        //postsPrice = materialMapper.getType("stolpe").getPrice() * calcPosts[].get(0);

        //beamPrice = materialMapper.getLength(calcBeams[].get(1).getPrice() * calcBeams[].get(0);
    }

    /*Der skal laves noget hvor der er metoder der tager kostpris ud fra hvormange beams,posts,rafters
     * så skal der være en metode der beregner salgspris
     * en der beregner dækningspris*/

    //TODO: Der skal tilføjes mere beregning, hvis der skal tilføjes skur
    //Stolpe
    public int calcPosts()
    {
        //Antal stolper
        int quantity;
        //Længder er omregnet til mm i stedet for cm, ved at gange med 100
        // 9,7 cm er bredden på stolpen, og til at starte med har man minimum to stolper per længde
        int overhangDefault = 1000; //overhangDefault er en buffer for start og slut, da man ikke placerer stolper for enden af carport
        int widthOfPost = 97; //widthOfPost skal modregnes i hvor langt der er mellem eventuelt er stolper
        int maxSpan = 3100; //maxSpan er spændet der maks. må være melle stolper jf. materialelisten givet
        int totalWidthWithinMaxSpan = 2*overhangDefault + 2*widthOfPost + maxSpan;


        //getLength()*100 for at få mm i stedet for cm, så det kan omregnes i int.
        if ((getLENGTH()*100) - (2*overhangDefault) - (2*widthOfPost) <= totalWidthWithinMaxSpan)
        {
            quantity = 4;
        } else
        {
            quantity = 6;
        }
        return quantity;
    }

    //TODO: Der skal tilføjes mere beregning, hvis der skal tilføjes skur
    //Remme
    public int[] calcBeams()
    {
        int[] beams = new int[2];
        int quantity = 0;
        int length = 0;

        //TODO: Tallene i if-statement skal ændres så det ikke er hardcoded, men hentes fra køberens valgmuligheder
        if (getLENGTH() <= 300)
        {
            //Woodmaterial length = 600
            length = 600;
            quantity = 1;

        } else if (300 < getLENGTH() || getLENGTH() <= 480)
        {
            //Woodmaterial length = 480
            length = 480;
            quantity = 2;
        } else if (480 < getLENGTH() || getLENGTH() <= 600)
        {
            //Woodmaterial length = 600
            length = 600;
            quantity = 2;
        } else if (600 < getLENGTH() || getLENGTH() <= 780)
        {
            //Woodmaterial length = 480
            length = 480;
            quantity = 4;
        }

        beams[0] = quantity;
        beams[1] = length;

        return beams;
    }

    //TODO: Der skal tilføjes mere beregning, hvis der skal tilføjes skur
    //Stern på remme
    public int[] calcSidesFasciaBoard()
    {

        int[] fascia = new int[2];
        int quantity = 0;
        int length = 0;

        //Antallet af brædder og hvilke bræddelængder der skal bruges, er afhængigt af carportens længde
        //TODO: Tallene i if-statement skal ændres så det ikke er hardcoded, men hentes fra køberens valgmuligheder
        if (getLENGTH() <= 270)
        {
            //Woodmaterial length = 540
            length = 540;
            quantity = 1;
        } else if (270 < getLENGTH() || getLENGTH() <= 360)
        {
            //Woodmaterial length = 360
            length = 360;
            quantity = 2;
        } else if (360 < getLENGTH() || getLENGTH() <= 540)
        {
            //Woodmaterial length = 540
            length = 540;
            quantity = 2;
        } else if (540 < getLENGTH() || getLENGTH() <= 690)
        {
            //Woodmaterial length = 360
            length = 360;
            quantity = 4;
        } else if (690 < getLENGTH() || getLENGTH() <= 780)
        {
            //Woodmaterial length = 540
            length = 540;
            quantity = 4;
        }

        fascia[0] = quantity;
        fascia[1] = length;

        return fascia;
    }

    //TODO: Der skal tilføjes mere beregning, hvis der skal tilføjes skur
    //Stern til for- og bagside
    public int[] calcFrontAndBackFasciaBoard()
    {
        int[] fascia = new int[2];
        int quantity = 0;
        int length = 0;

        //Antallet af brædder og hvilke bræddelængder der skal bruges, er afhængigt af carportens bredde
        //TODO: Tallene i if-statement skal ændres så det ikke er hardcoded, men hentes fra køberens valgmuligheder
        if (getWIDTH() <= 270)
        {
            //Woodmaterial length = 540
            length = 540;
            quantity = 1;
        } else if (270 < getWIDTH() || getWIDTH() <= 360)
        {
            //Woodmaterial length = 360
            length = 360;
            quantity = 2;
        } else if (360 < getWIDTH() || getWIDTH() <= 540)
        {
            //Woodmaterial length = 540
            length = 540;
            quantity = 2;
        } else if (540 < getWIDTH() || getWIDTH() <= 600)
        {
            //Woodmaterial length = 360
            length = 360;
            quantity = 4;
        }

        fascia[0] = quantity;
        fascia[1] = length;

        return fascia;
    }

    //Spær
    public int calcRafters()
    {
        int lengthMM = getLENGTH();
        //Der er 60 cm mellem hvert spær, og ca. 55 cm fra inderside til inderside af spærene
        int distanceBetweenRafters = 60;

        List<Integer> rafters = new ArrayList<>();

        int totalLength = 0;

        // Brug for-each-løkke til at tilføje spær og beregne længde der er tilbage
        for (int i = 0; totalLength < lengthMM; i++) {
            rafters.add(i);
            totalLength += distanceBetweenRafters;
        }

        // Antallet af spær er størrelsen af listen
        return rafters.size();

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
