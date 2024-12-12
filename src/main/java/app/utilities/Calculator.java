package app.utilities;

import app.entities.Material;

import java.util.ArrayList;
import java.util.List;

public class Calculator
{

    //Udregner kostpris
    public static int calcPickListPrice(List<Material> pickList)
    {
        int totalPrice = 0;
        for (Material material : pickList)
        {
            int materialQuantity = material.getQuantity();
            int materialPrice = material.getPrice();
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
        //Længder er omregnet til mm i stedet for cm, ved at gange med 10
        // 9,7 cm er bredden på stolpen, og til at starte med har man minimum to stolper per længde
        int overhangDefault; //overhangDefault er en buffer for start og slut, da man ikke placerer stolper for enden af carport
        if ((carport.getLENGTH() / 4) * 10 <= 1000)
        { //Maks overhang er hardcoded
            overhangDefault = (carport.getLENGTH() / 4) * 10;
        } else
        {
            overhangDefault = 1000;
        }

        int widthOfPost = 97; //widthOfPost skal modregnes i hvor langt der er mellem eventuelt er stolper
        int maxSpan = 3100; //maxSpan er spændet der maks. må være melle stolper jf. materialelisten givet
        int totalWidthWithinMaxSpan = 2 * overhangDefault + 2 * widthOfPost + maxSpan;


        //getLength()*100 for at få mm i stedet for cm, så det kan omregnes i int.
        if ((carport.getLENGTH() * 10) <= totalWidthWithinMaxSpan)
        {
            quantity = 4;
        } else
        {
            quantity = 6;
        }
        return quantity;
    }

    //Method for drawing the SVG
    public static int[] calcPostsXY(Carport carport, int quantity, int matNum)
    {
        int[] posXY = new int[2];

        // width is the y-axis
        int width = carport.getWIDTH();
        // length is the x-axis
        int length = carport.getLENGTH();

        int overhang = 15;

        int posX = 0;
        int posY = overhang;

        if (quantity == 4)
        {
            if (matNum == 0)
            {
                posX = length / 4;
            } else if (matNum == 1)
            {
                posX = length / 4;
                posY = width - overhang - 10;
            } else if (matNum == 2)
            {
                posX = (length / 4) * 3;
            } else if (matNum == 3)
            {
                posX = (length / 4) * 3;
                posY = width - overhang - 10;
            }
        } else if (quantity == 6)
        {
            if (matNum == 0)
            {
                posX = (length / 8);
            } else if (matNum == 1)
            {
                posX = (length / 8);
                posY = width - overhang - 10;
            } else if (matNum == 2)
            {
                posX = (length / 8) * 4;
            } else if (matNum == 3)
            {
                posX = (length / 8) * 4;
                posY = width - overhang - 10;
            } else if (matNum == 4)
            {
                posX = (length / 8) * 7;
            } else if (matNum == 5)
            {
                posX = (length / 8) * 7;
                posY = width - overhang - 10;
            }
        }
        posXY[0] = posX;
        posXY[1] = posY;

        return posXY;
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

        } else if (300 > carport.getLENGTH() || carport.getLENGTH() <= 480)
        {
            //Woodmaterial length = 480
            length = 480;
            quantity = 2;
        } else if (480 > carport.getLENGTH() || carport.getLENGTH() <= 600)
        {
            //Woodmaterial length = 600
            length = 600;
            quantity = 2;
        } else if (600 > carport.getLENGTH() || carport.getLENGTH() <= 780)
        {
            //Woodmaterial length = 480
            length = 480;
            quantity = 4;
        }

        beams[0] = quantity;
        beams[1] = length;

        return beams;
    }

    // metode til SVG tegning
    public static int[] calcBeamsXY(Carport carport, int quantity, int matNum, int matLength)
    {
        int[] posXY = new int[4];

        int length = carport.getLENGTH();
        int width = carport.getWIDTH();

        //Udhænget kommer fra materialelisten
        int overhang = 15;

        int startPosX = 0;
        int endPosX = matLength;
        int startPosY = overhang;
        int endPosY = overhang;

        if (matNum == 1)
        {
            startPosY = width - overhang;
            endPosY = width - overhang - 10;
        } else if (quantity == 4)
        {
            if (matNum == 2)
            {
                startPosX = length;
                endPosX = length - matLength;
                startPosY = overhang;
                endPosY = overhang;
            } else if (matNum > 2)
            {
                startPosX = length;
                endPosX = length - matLength;
                startPosY = width - overhang;
                endPosY = width - overhang - 10;
            }
        }

        posXY[0] = startPosX;
        posXY[1] = endPosY;
        posXY[2] = matLength;
        posXY[3] = endPosY;

        return posXY;
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
        } else if (270 < carport.getLENGTH() && carport.getLENGTH() <= 360)
        {
            //Woodmaterial length = 360
            length = 360;
            quantity = 2;
        } else if (360 < carport.getLENGTH() && carport.getLENGTH() <= 540)
        {
            //Woodmaterial length = 540
            length = 540;
            quantity = 2;
        } else if (540 < carport.getLENGTH() && carport.getLENGTH() <= 690)
        {
            //Woodmaterial length = 360
            length = 360;
            quantity = 4;
        } else if (690 < carport.getLENGTH() && carport.getLENGTH() <= 780)
        {
            //Woodmaterial length = 540
            length = 540;
            quantity = 4;
        }

        fascia[0] = quantity;
        fascia[1] = length;

        return fascia;
    }

    // metode til SVG tegning
    public static int[] calcSidesFasciaBoardXY(Carport carport, int quantity, int matNum, int matLength)
    {
        int[] posXY = new int[4];

        int length = carport.getLENGTH();
        int width = carport.getWIDTH();

        int startPosX = 0;
        int endPosX = matLength;
        int startPosY = 0;
        int endPosY = 0;

        if (matNum == 1)
        {
            startPosY = width;
            endPosY = width;
        } else if (quantity == 4)
        {
            if (matNum == 2)
            {
                startPosX = length;
                endPosX = length - matLength;
                startPosY = 0;
                endPosY = 0;
            } else
            {
                startPosX = length;
                endPosX = length - matLength;
                startPosY = width;
                endPosY = width;
            }
        }

        posXY[0] = startPosX;
        posXY[1] = endPosX;
        posXY[2] = startPosY;
        posXY[3] = endPosY;

        return posXY;
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
        } else if (270 < carport.getWIDTH() && carport.getWIDTH() <= 360)
        {
            //Woodmaterial length = 360
            length = 360;
            quantity = 2;
        } else if (360 < carport.getWIDTH() && carport.getWIDTH() <= 540)
        {
            //Woodmaterial length = 540
            length = 540;
            quantity = 2;
        } else if (540 < carport.getWIDTH() && carport.getWIDTH() <= 600)
        {
            //Woodmaterial length = 360
            length = 360;
            quantity = 4;
        }

        fascia[0] = quantity;
        fascia[1] = length;

        return fascia;
    }

    // metode til SVG tegning
    public static int[] calcFrontAndBackFasciaBoardXY(Carport carport, int quantity, int matNum, int matLength)
    {
        // Initialize position array [startPosX, endPosX, startPosY, endPosY].
        int[] posXY = new int[4];

        int width = carport.getWIDTH();
        int length = carport.getLENGTH();

        // Default positions
        int startPosX = 0;
        int endPosX = 0;
        int startPosY = 0;
        int endPosY = width;
        if (quantity == 4)
        {
            switch (matNum)
            {
                case 0: // First board
                    endPosY = matLength;
                    break;
                case 1: // Second board
                    startPosY = width;
                    endPosY = width - matLength;
                    break;
                case 2: // Third board
                    startPosX = length;
                    endPosX = length;
                    endPosY = matLength;
                    break;
                case 3: // Fourth board
                    startPosX = length;
                    endPosX = length;
                    startPosY = width;
                    endPosY = width - matLength;
                    break;

                default:
                    throw new IllegalArgumentException("Invalid matNum for quantity = 4: " + matNum);
            }
        } else if (quantity == 2 && matNum == 1)
        {
            // Special case for two boards, second board
            startPosX = length;
            endPosX = length;
        }
        // Populate the result array
        posXY[0] = startPosX;
        posXY[1] = endPosX;
        posXY[2] = startPosY;
        posXY[3] = endPosY;

        return posXY;
    }

    //Spær
    public static int[] calcRafters(Carport carport)
    {
        int[] rafters = new int[2];
        int quantity = 0;
        int length = 0;


        //Beregning af mængde af spær
        int lengthMM = carport.getLENGTH();
        //Der er 60 cm mellem hvert spær, og ca. 55 cm fra inderside til inderside af spærene
        int distanceBetweenRafters = 60;

        List<Integer> raftersQuantity = new ArrayList<>();

        int totalLength = 0;

        // Brug for-each-løkke til at tilføje spær og beregne længde der er tilbage
        for (int i = 0; totalLength < lengthMM; i++)
        {
            raftersQuantity.add(i);
            totalLength += distanceBetweenRafters;
        }
        // Antallet af spær er størrelsen af listen, plus 1 for enden
        quantity = raftersQuantity.size() + 1;
        rafters[0] = quantity;

        //Beregning af længden på spær

        //Længden på det "korte bredt" er hardcoded ind
        if (carport.getWIDTH() <= 480)
        {
            length = 480;
        } else
        {
            length = 600;
        }

        rafters[1] = length;

        return rafters;

    }

    //Method for drawing the SVG
    public static int[] calcRaftersXY(Carport carport, int quantity, int matNum)
    {
        int[] posXY = new int[4];
        // width is the y-axis
        int width = carport.getWIDTH();
        // length is the x-axis
        int length = carport.getLENGTH();

        // afstanden mellem spærrene er taget fra materialelisten vi fik udleveret
        int spacing = 60;

        int posX = matNum * spacing;

        for (int i = 0; i <= matNum; i++)
        {
            posX = i * spacing;
            if (posX > length)
            {
                posX = length;
            }
        }

        int startPosY = 0;
        int endPosY = width;

        posXY[0] = posX;
        posXY[1] = posX;
        posXY[2] = startPosY;
        posXY[3] = endPosY;

        return posXY;
    }


    public static int calcScrewsForRoofing(Carport carport)
    {
        //Udregning for skruer til taget
        //Man skal bruge ca. 12 skruer per kvadratmeter tagplade, der er skruer til 16,6 m^2 i en pakke. Vi runder ned til 16 m^2
        int squareCentimeterPerPackageOfScrews = 160000;
        double carportRoofInSquareCentimeters = carport.getWIDTH() * carport.getLENGTH();
        //Beregner antal pakker af skruer der skal bruges i decimaltal
        double packagesNeeded = carportRoofInSquareCentimeters / squareCentimeterPerPackageOfScrews;
        //Runder decimal tal op til nærmeste hele tal (Math.ceil)
        int screwsForRoofing = (int) Math.ceil(packagesNeeded);

        //Giver en ekstra pakke skruer man skulle bruge 1 eller 2 pakker
        if (screwsForRoofing < 3 && screwsForRoofing != 0)
        {
            screwsForRoofing += 1;
        }

        return screwsForRoofing;
    }

    public static int calcStandardScrews()
    {
        int packagesOfScrews = 1; //Hardcoded as the biggest carport FOG offers uses one package
        return packagesOfScrews;
    }

    public static int calcRollForWindCross()
    {
        int numberOfRollsForWindCross = 2;
        //Hardcoded til to, så man altid har to bånd af 10 meter.
        return numberOfRollsForWindCross;
    }

    public static int calcSquareWasher(Carport carport)
    {
        int numberOfSquareWasher = calcPosts(carport) + 2;
        //Udregning for firkantskive - De får antallet af stolper plus 2
        return numberOfSquareWasher;
    }

    public static int calcHardwareForRaftersLeft(Carport carport)
    {
        int hardwareForRaftersLeft = calcRafters(carport)[0]; //Der skal bruges et beslag per spær
        return hardwareForRaftersLeft;
    }


    public static int calcHardwareForRaftersRight(Carport carport)
    {
        int hardwareForRaftersRight = calcRafters(carport)[0]; //Der skal bruges et beslag per spær

        return hardwareForRaftersRight;
    }


    public static int calcHardwareScrews(Carport carport)
    {

        int numberOfHardwarescrews;

        int packagesOfHardwarescrews; //Der er 250 skruer i en pakke

        int hardwareForRafters = calcHardwareForRaftersLeft(carport) + calcHardwareForRaftersRight(carport); //Et højre- og et venstrebeslag per spær

        int sidesOnHardwareForRafters = 3; //Jf. Materialelisten fra Fog skal der bruges 3 skruer per flade, og på et beslag er der 3 flade

        int screwsForRaftersHardware = (hardwareForRafters * sidesOnHardwareForRafters) * 3; //Jf. Materialelisten fra Fog skal der bruges 3 skruer per flade, og på et beslag er der 3 flade

        int screwsOnIntersection = 2; //Der skal skrues 1-2 skruer i ved hulbåndskrydsning

        int screwsOnPunchedTape = 4 * 4; //Skruer til fastgørelse af hulbånd, der bliver sat 4 skruer af til hvert hjørne

        //Udregning af hvor mange beslagsskruer der skal bruges
        numberOfHardwarescrews = hardwareForRafters + screwsForRaftersHardware + screwsOnIntersection + screwsOnPunchedTape;


        //TODO: Gør så det ikke er hardcoded
        //Udregning af pakker af beslagskruer - vi har valgt at give en ekstra pakke med
        if (numberOfHardwarescrews <= 249)
        {
            packagesOfHardwarescrews = 2;
        } else
        {
            packagesOfHardwarescrews = 3;
        }

        return packagesOfHardwarescrews;
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

    /*
     * Calculates the number of long and short roof plates needed for a carport.
     *
     * @param carportLength The length of the carport in cm.
     * @param carportWidth  The width of the carport in cm.
     * @return An array where index 0 is the number of long plates, and index 1 is the number of short plates.
     */
    public static int[] calcRoofPlates(Carport carport)
    {
        int carportLength = carport.getLENGTH();
        int carportWidth = carport.getWIDTH();
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
            int shortPlatesUsed = (remainingLength > 0) ? (int) Math.ceil((double) remainingLength / SHORT_PLATE_LENGTH) : 0;

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
