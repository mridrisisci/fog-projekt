package app.entities;

import app.utilities.Calculator;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SVGCreation
{
    public static String loadSVGFromFile(String filePath) throws IOException
    {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static String generatePosts(int postLength, int postWidth, int quantityOfPosts)
    {
        StringBuilder postsBuilder = new StringBuilder();
        for (int i = 1; i <= quantityOfPosts; i++)
        {
            postsBuilder.append(String.format(
                    "<rect id=\"post%d\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"red\" />\n",
                    i, i * 100, 50, postWidth, postLength
            ));
        }
        return postsBuilder.toString();
    }

    public static String generateBeams(int beamLength, int beamWidth, int quantityOfBeams)
    {
        StringBuilder beamsBuilder = new StringBuilder();
        for (int i = 1; i <= quantityOfBeams; i++)
        {
            beamsBuilder.append(String.format(
                    "<rect id=\"beam%d\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"gray\" />\n",
                    i, i * 100, 100, beamWidth, beamLength
            ));
        }
        return beamsBuilder.toString();
    }

    public static String generateRafters(int rafterLength, int rafterWidth, int spacing)
    {
        StringBuilder raftersBuilder = new StringBuilder();
        for (int i = spacing; i < 500 - spacing; i += 50)
        {
            raftersBuilder.append(String.format(
                    "<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"darkgray\" />\n",
                    i, 0, rafterWidth, rafterLength
            ));
        }
        return raftersBuilder.toString();
    }

    public static String generateFasciaBoards(int carportLength, int carportWidth){
        StringBuilder fasciaBoardsBuilder = new StringBuilder();
        fasciaBoardsBuilder.append(String.format( "<rect id=\"facia%d\" x=\"%d\" y=\"%d\" length=\"%d\" width=\"%d\" fill=\"black\" />\n",
                1,0,0,carportLength,carportWidth
                ));
        return fasciaBoardsBuilder.toString();

    }

    //Mangler metoder for sternbrædder derfor vælger vi ikke at bruge metoden.
    /*public static String generateFasciaBoards(int fasciaBoardLength, int fasciaBoardWidth, int quantityOfFasciaBoards)
    {
        StringBuilder fasciaBoardsBuilder = new StringBuilder();
        for (int i = 1; i <= quantityOfFasciaBoards; i++)
        {
            fasciaBoardsBuilder.append(String.format(
                    "<rect id=\"facia%d\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"black\" />\n",
                    i, i * 100, 150, fasciaBoardWidth, fasciaBoardLength
            ));
        }
        return fasciaBoardsBuilder.toString();
    }*/

    public static String generateCarportSVGFromTemplate(
            String template,
            String posts,
            String beams,
            String rafters,
            String fasciaBoards)
    {
        return template
                .replace("{{posts}}", posts)
                .replace("{{beams}}", beams)
                .replace("{{rafters}}", rafters)
                .replace("{{fasciaBoards}}", fasciaBoards);
    }

    public List<Material> svgXYGenerate(List<Material> svgMaterialList, Carport carport)
    {
     List<Material> svgMaterialListFinal = new ArrayList<>();

        for (Material mat : svgMaterialList)
        {
            switch (mat.getType())
            {
                case "Stolpe":
                    svgMaterialListFinal.addAll(svgSetXYStople(mat, carport));
                    break;
                case "Rem":
                    svgMaterialListFinal.addAll(svgSetXYRem(mat,carport));
                    break;
                case "Spær":
                    svgMaterialListFinal.addAll(svgSetXYSpær(mat,carport));
                    break;
                /*case "Oversternbrædt":
                    svgMaterialListFinal.addAll(svgSetXY(mat,carport));
                    break;*/
            }
        }
        return svgMaterialListFinal;
    }

    private List<Material> svgSetXYStople(Material stolpe, Carport carport)
    {
        List<Material> svgPosts = new ArrayList<>();
        int quantity = stolpe.getQuantity();
        for(int i = 0; i <quantity; i++ ){
            int[]posXY = Calculator.calcPostsXY(carport, quantity, i);
            stolpe.setSvgPosX(posXY[0]);
            stolpe.setSvgPosY(posXY[1]);
            svgPosts.add(stolpe);
        }
        return svgPosts;
    }
    private List<Material> svgSetXYRem(Material rem, Carport carport)
    {
        List<Material> svgPosts = new ArrayList<>();
        int quantity = rem.getQuantity();
        int remLength = rem.getLength();
        for(int i = 0; i <quantity; i++ ){
            int[]posXY = Calculator.calcBeamsXY(carport, quantity, i, remLength);
            rem.setSvgPosX(posXY[0]);
            rem.setSvgPosY(posXY[1]);
            svgPosts.add(rem);
        }
        return svgPosts;
    }

    private List<Material> svgSetXYSternbrædtForOgBag(Material stern, Carport carport)
    {
        List<Material> svgPosts = new ArrayList<>();
        int quantity = stern.getQuantity();
        int sternLength = stern.getLength();
        for(int i = 0; i <quantity; i++ ){
            int[]posXY = Calculator.calcFrontAndBackFasciaBoardXY(carport, quantity, i, sternLength);
            stern.setSvgPosX(posXY[0]);
            stern.setSvgPosY(posXY[1]);
            svgPosts.add(stern);
        }
        return svgPosts;
    }

    private List<Material> svgSetXYSternbrædtSider(Material stern, Carport carport)
    {
        List<Material> svgPosts = new ArrayList<>();
        int quantity = stern.getQuantity();
        int sternLength = stern.getLength();
        for (int i = 0; i < quantity; i++)
        {
            int[] posXY = Calculator.calcSidesFasciaBoardXY(carport, quantity, i, sternLength);
            stern.setSvgPosX(posXY[0]);
            stern.setSvgPosY(posXY[1]);
            svgPosts.add(stern);
        }
        return svgPosts;
    }

    private List<Material> svgSetXYSpær(Material spær, Carport carport)
    {
        List<Material> svgPosts = new ArrayList<>();
        int quantity = spær.getQuantity();
        for (int i = 0; i < quantity; i++)
        {
            int[] posXY = Calculator.calcRaftersXY(carport, quantity, i);
            spær.setSvgPosX(posXY[0]);
            spær.setSvgPosY(posXY[1]);
            svgPosts.add(spær);
        }
        return svgPosts;
    }
}