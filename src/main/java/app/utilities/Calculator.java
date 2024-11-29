package app.utilities;

import app.entities.Carport;
import app.entities.Material;
import app.persistence.ConnectionPool;
import app.persistence.MaterialMapper;

import java.util.ArrayList;
import java.util.List;

public class Calculator
{
    public int calcCarportMaterial()
    {
        return 0;
    }

    //Udregner kostpris
    public static int calcPickListPrice(List<Material> pickList)
    {
        int totalPrice = 0;
        for (Material material : pickList)
        {
            int materialQuantity = material.getQuantity();
            int materialPrice = material.getPurchasePrice();
            totalPrice += materialPrice * materialQuantity;
        }

        return totalPrice;
    }

    public static int calcSalesPrice(int pickListPrice, double coverageRatio)
    {
        double salesPrice = (1 + coverageRatio) * pickListPrice;
        return (int) salesPrice;
    }

    //TODO: Der skal tilføjes mere beregning, hvis der skal tilføjes skur
    //Stolpe
    public static int calcPosts(Carport carport)
    {
        //Antal stolper
        int quantity;
        //Længder er omregnet til mm i stedet for cm, ved at gange med 100
        // 9,7 cm er bredden på stolpen, og til at starte med har man minimum to stolper per længde
        int overhangDefault = 1000; //overhangDefault er en buffer for start og slut, da man ikke placerer stolper for enden af carport
        int widthOfPost = 97; //widthOfPost skal modregnes i hvor langt der er mellem eventuelt er stolper
        int maxSpan = 3100; //maxSpan er spændet der maks. må være melle stolper jf. materialelisten givet
        int totalWidthWithinMaxSpan = 2 * overhangDefault + 2 * widthOfPost + maxSpan;


        //getLength()*100 for at få mm i stedet for cm, så det kan omregnes i int.
        if ((carport.getLENGTH() * 100) - (2 * overhangDefault) - (2 * widthOfPost) <= totalWidthWithinMaxSpan)
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
    public static int[] calcBeams(Carport carport)
    {
        int[] beams = new int[2];
        int quantity = 0;
        int length = 0;

        //TODO: Tallene i if-statement skal ændres så det ikke er hardcoded, men hentes fra køberens valgmuligheder
        if (carport.getLENGTH() <= 300)
        {
            //Woodmaterial length = 600
            length = 600;
            quantity = 1;

        } else if (300 < carport.getLENGTH() || carport.getLENGTH() <= 480)
        {
            //Woodmaterial length = 480
            length = 480;
            quantity = 2;
        } else if (480 < carport.getLENGTH() || carport.getLENGTH() <= 600)
        {
            //Woodmaterial length = 600
            length = 600;
            quantity = 2;
        } else if (600 < carport.getLENGTH() || carport.getLENGTH() <= 780)
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
    public static int[] calcSidesFasciaBoard(Carport carport)
    {

        int[] fascia = new int[2];
        int quantity = 0;
        int length = 0;

        //Antallet af brædder og hvilke bræddelængder der skal bruges, er afhængigt af carportens længde
        //TODO: Tallene i if-statement skal ændres så det ikke er hardcoded, men hentes fra køberens valgmuligheder
        if (carport.getLENGTH() <= 270)
        {
            //Woodmaterial length = 540
            length = 540;
            quantity = 1;
        } else if (270 < carport.getLENGTH() || carport.getLENGTH() <= 360)
        {
            //Woodmaterial length = 360
            length = 360;
            quantity = 2;
        } else if (360 < carport.getLENGTH() || carport.getLENGTH() <= 540)
        {
            //Woodmaterial length = 540
            length = 540;
            quantity = 2;
        } else if (540 < carport.getLENGTH() || carport.getLENGTH() <= 690)
        {
            //Woodmaterial length = 360
            length = 360;
            quantity = 4;
        } else if (690 < carport.getLENGTH() || carport.getLENGTH() <= 780)
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
    public static int[] calcFrontAndBackFasciaBoard(Carport carport)
    {
        int[] fascia = new int[2];
        int quantity = 0;
        int length = 0;

        //Antallet af brædder og hvilke bræddelængder der skal bruges, er afhængigt af carportens bredde
        //TODO: Tallene i if-statement skal ændres så det ikke er hardcoded, men hentes fra køberens valgmuligheder
        if (carport.getWIDTH() <= 270)
        {
            //Woodmaterial length = 540
            length = 540;
            quantity = 1;
        } else if (270 < carport.getWIDTH() || carport.getWIDTH() <= 360)
        {
            //Woodmaterial length = 360
            length = 360;
            quantity = 2;
        } else if (360 < carport.getWIDTH() || carport.getWIDTH() <= 540)
        {
            //Woodmaterial length = 540
            length = 540;
            quantity = 2;
        } else if (540 < carport.getWIDTH() || carport.getWIDTH() <= 600)
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
    public static int calcRafters(Carport carport)
    {
        int lengthMM = carport.getLENGTH();
        //Der er 60 cm mellem hvert spær, og ca. 55 cm fra inderside til inderside af spærene
        int distanceBetweenRafters = 60;

        List<Integer> rafters = new ArrayList<>();

        int totalLength = 0;

        // Brug for-each-løkke til at tilføje spær og beregne længde der er tilbage
        for (int i = 0; totalLength < lengthMM; i++)
        {
            rafters.add(i);
            totalLength += distanceBetweenRafters;
        }

        // Antallet af spær er størrelsen af listen
        return rafters.size();

    }

    public static int[] calcRoofPlates(Carport carport)
    {
        int[] roofPlates = new int[2];
        int numberOf360plates = 0;
        int numberOf600plates = 0;
        //Todo: Mangler calculations til denne del
        roofPlates[0] = numberOf360plates;
        roofPlates[1] = numberOf600plates;

        return roofPlates;
    }

    public static int[] calcScrewsAndHardware(Carport carport)
    {
        int[] screwsAndHardware = new int[8];
        //Todo: Fiks så skruer og hulbånd ikke er hardcoded
        int screws = 1; //Hardcoded til to, så man altid har to bånd af 10 meter.
        int rollForWindCross = 2; //Hardcoded til to, så man altid har to bånd af 10 meter.
        int screwsForRoofing;
        int hardwareForRaftersLeft = calcRafters(carport); //Der skal bruges et beslag per spær
        int hardwareForRaftersRight = calcRafters(carport); //Der skal bruges et beslag per spær
        int hardwarescrews; //Beslagskruer til montering af universalbeslag+hulbånd
        int boardBolt; //Bræddebolt
        int squareWasher; //Firkantskive

        //TODO: Gør så det ikke er hardcoded
        //Udregning af beslagskruer - vi har valgt at give en ekstra pakke med
        if (calcHardwareScrews(carport) <= 249)
        {
            hardwarescrews = 2;
        } else
        {
            hardwarescrews = 3;
        }

        //Udregning for skruer til taget
        //Man skal bruge ca. 12 skruer per kvadratmeter tagplade, der er skruer til 16,6 m^2 i en pakke. Vi runder ned til 16 m^2
        int squareCentimeterPerPackageOfScrews = 1600;
        if ((carport.getWIDTH() * carport.getLENGTH()) / squareCentimeterPerPackageOfScrews < 2)
        {
            screwsForRoofing = 2;
        } else
        {
            screwsForRoofing = 3;
        }

        //Udregning for bræddebolt
        boardBolt = calcBoardBolt(carport);

        //Udregning for firkantskive - De får antallet af stolper plus 2
        squareWasher = calcPosts(carport) + 2;

        screwsAndHardware[0] = screws;
        screwsAndHardware[1] = rollForWindCross;
        screwsAndHardware[2] = screwsForRoofing;
        screwsAndHardware[3] = hardwareForRaftersLeft;
        screwsAndHardware[4] = hardwareForRaftersRight;
        screwsAndHardware[5] = hardwarescrews;
        screwsAndHardware[6] = boardBolt;
        screwsAndHardware[7] = squareWasher;

        return screwsAndHardware;
    }

    public static int calcHardwareScrews(Carport carport)
    {
        int numberOfHardwarescrews;
        int hardwareForRafters = calcRafters(carport) * 2; //Et højre- og et venstrebeslag per spær
        int sidesOnHardwareForRafters = 3; //Jf. Materialelisten fra Fog skal der bruges 3 skruer per flade, og på et beslag er der 3 flade
        int screwsForRaftersHardware = (hardwareForRafters * sidesOnHardwareForRafters) * 3; //Jf. Materialelisten fra Fog skal der bruges 3 skruer per flade, og på et beslag er der 3 flade
        int screwsOnIntersection = 2; //Der skal skrues 1-2 skruer i ved hulbåndskrydsning
        int screwsOnPunchedTape = 4 * 4; //Skruer til fastgørelse af hulbånd, der bliver sat 4 skruer af til hvert hjørne

        numberOfHardwarescrews = hardwareForRafters + screwsForRaftersHardware + screwsOnIntersection + screwsOnPunchedTape;

        return numberOfHardwarescrews;
    }

    public static int calcBoardBolt(Carport carport)
    {
        //Ifølge materialelisten fra fog skal man bruge to bolte per stolpe, med mindre der er en samling af to remme, så skal der bruges fire bolte på de to stolper der er samlinger
        int numberOfBoardBolts = 0;
        int boltsPerPost = 2;
        int extraBoltsPerAssembly = 2;
        int postsWithAssemblyPoint = 2;

        if (calcPosts(carport) == 4)
        {
            numberOfBoardBolts = calcPosts(carport) * 2;

        } else
        {
            numberOfBoardBolts = calcPosts(carport) * boltsPerPost + (postsWithAssemblyPoint * extraBoltsPerAssembly);
        }
        return numberOfBoardBolts;
    }

    /**
     * Calculates the number of long and short roof plates needed for a carport.
     *
     * @param carportLength The length of the carport in cm.
     * @param carportWidth  The width of the carport in cm.
     * @return An array where index 0 is the number of long plates, and index 1 is the number of short plates.
     */
    public static int[] calcRoofPlates(int carportLength, int carportWidth)
    {
        // Plate dimensions in cm
        final int LONG_PLATE_LENGTH = 600;
        final int SHORT_PLATE_LENGTH = 360;
        final int PLATE_WIDTH = 109;
        final int OVERLAP = 20;

        // Calculate plates needed along the width (no overlap in width)
        int platesAlongWidth = (int) Math.ceil((double) carportWidth / PLATE_WIDTH);

        // If length is less than or equal to a single plate, no overlap needed
        if (carportLength <= SHORT_PLATE_LENGTH)
        {
            return new int[]{0, platesAlongWidth};
        } else if (carportLength <= LONG_PLATE_LENGTH)
        {
            return new int[]{platesAlongWidth, 0};
        }

        // Calculate the number of long plates needed without considering excess
        int longPlatesUsed = 1 + (int) Math.floor((double) (carportLength - LONG_PLATE_LENGTH) / (LONG_PLATE_LENGTH - OVERLAP));

        // Calculate the excess length if we only used long plates
        int excessWithLongPlates = (longPlatesUsed * LONG_PLATE_LENGTH - (longPlatesUsed - 1) * OVERLAP) - carportLength;

        // Find the best combination by testing different numbers of long and short plates
        int minWaste = Integer.MAX_VALUE;
        int bestLongPlates = 0;
        int bestShortPlates = 0;

        // Loop to find the optimal combination of long and short plates
        for (int longCount = 0; longCount <= longPlatesUsed; longCount++)
        {
            int lengthCoveredByLongPlates = longCount * LONG_PLATE_LENGTH - (longCount - 1) * OVERLAP;
            int remainingLength = carportLength - lengthCoveredByLongPlates;

            // Calculate how many short plates are needed to cover the remaining length
            int shortPlatesUsed = (remainingLength > 0) ?
                    (int) Math.ceil((double) remainingLength / SHORT_PLATE_LENGTH) : 0;

            // Calculate the total waste for this combination
            int totalWaste = Math.abs((longCount * LONG_PLATE_LENGTH - (longCount - 1) * OVERLAP) + (shortPlatesUsed * SHORT_PLATE_LENGTH) - carportLength);

            // Check if this combination has less waste than the current minimum
            if (totalWaste < minWaste)
            {
                minWaste = totalWaste;
                bestLongPlates = longCount;
                bestShortPlates = shortPlatesUsed;
            }
        }

        // Calculate the total number of plates needed, factoring in the width
        int totalLongPlates = bestLongPlates * platesAlongWidth;
        int totalShortPlates = bestShortPlates * platesAlongWidth;

        return new int[]{totalLongPlates, totalShortPlates};
    }

}
