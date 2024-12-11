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

    public static String generatePosts(List<Material> postsList)
    {
        StringBuilder postsBuilder = new StringBuilder();
        for (int i = 0; i <= postsList.size(); i++)
        {
            int startPosX = postsList.get(i).getSvgPosX() - (postsList.get(i).getLength() / 2);
            int startPosY = postsList.get(i).getSvgPosY() - (postsList.get(i).getWidth() / 2);
            int endPosX = postsList.get(i).getSvgPosX() + (postsList.get(i).getLength() / 2);
            int endPosY = postsList.get(i).getSvgPosY() + (postsList.get(i).getWidth() / 2);
            postsBuilder.append(String.format(
                    "<rect id=\"post%d\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"red\" />\n",
                    i + 1, startPosX, startPosY, endPosX, endPosY
            ));
        }
        return postsBuilder.toString();
    }

    public static String generateRafters(List<Material> raftList)
    {
        StringBuilder raftersBuilder = new StringBuilder();
        for (int i = 0; i < raftList.size(); i++)
        {
            int startPosX = raftList.get(i).getSvgStartX();
            int startPosY = raftList.get(i).getSvgStartY();
            int endPosX = raftList.get(i).getSvgEndX();
            int endPosY = raftList.get(i).getSvgEndY();
            raftersBuilder.append(String.format(
                    "<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"darkgray\" />\n",
                    i + 1, startPosX, startPosY, endPosX, endPosY
            ));
        }
        return raftersBuilder.toString();
    }

    public static String generateBeams(List<Material> remList)
    {
        StringBuilder beamsBuilder = new StringBuilder();
        for (int i = 0; i < remList.size(); i++)
        {
            int startPosX = remList.get(i).getSvgStartX();
            int startPosY = remList.get(i).getSvgStartY();
            int endPosX = remList.get(i).getSvgEndX();
            int endPosY = remList.get(i).getSvgEndY();
            beamsBuilder.append(String.format(
                    "<rect id=\"beam%d\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"gray\" />\n",
                    i + 1, startPosX, startPosY, endPosX, endPosY
            ));
        }
        return beamsBuilder.toString();
    }

    //Vi har valgt at hardcode kanten af carporten da vi får størrelsen sammen med forsprøgslen
    public static String generateFasciaBoards(int carportLength, int carportWidth)
    {
        StringBuilder fasciaBoardsBuilder = new StringBuilder();
        fasciaBoardsBuilder.append(String.format("<rect id=\"facia%d\" x=\"%d\" y=\"%d\" length=\"%d\" width=\"%d\" fill=\"black\" />\n",
                1, 0, 0, carportLength, carportWidth
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

    public static String generateSVGString(List<Material> svgMaterialList, Carport carport)
    {

        List<Material> stolpeList = new ArrayList<>();
        List<Material> remList = new ArrayList<>();
        List<Material> spærList = new ArrayList<>();

        //TODO check at loop ikke giver problemer
        for (Material mat : svgMaterialList)
        {
            switch (mat.getType())
            {
                case "Stolpe":
                    stolpeList.addAll(svgSetXYStople(mat, carport));
                    break;
                case "Rem":
                    remList.addAll(svgSetXYRem(mat, carport));
                    break;
                case "Spær":
                    spærList.addAll(svgSetXYSpær(mat, carport));
                    break;
                /*case "Oversternbrædt":
                    svgMaterialListFinal.addAll(svgSetXY(mat,carport));
                    break;*/
            }
        }
        int carportLength = carport.getLENGTH();
        int carportWidth = carport.getWIDTH();

        String fasciaboardXML = generateFasciaBoards(carportLength, carportWidth);
        String postsXML = generatePosts(stolpeList);
        String beamsXML = generateBeams(remList);
        String raftersXML = generateRafters(spærList);

        String res = fasciaboardXML + postsXML + beamsXML + raftersXML;

        return res;
    }

    private static List<Material> svgSetXYStople(Material stolpe, Carport carport)
    {
        List<Material> svgPosts = new ArrayList<>();
        int quantity = stolpe.getQuantity();
        for (int i = 0; i < quantity; i++)
        {
            int[] posXY = Calculator.calcPostsXY(carport, quantity, i);
            stolpe.setSvgPosX(posXY[0]);
            stolpe.setSvgPosY(posXY[1]);
            svgPosts.add(stolpe);
        }
        return svgPosts;
    }

    private static List<Material> svgSetXYRem(Material rem, Carport carport)
    {
        List<Material> svgPosts = new ArrayList<>();
        int quantity = rem.getQuantity();
        int remLength = rem.getLength();
        for (int i = 0; i < quantity; i++)
        {
            int[] posXY = Calculator.calcBeamsXY(carport, quantity, i, remLength);
            rem.setSvgStartX(posXY[0]);
            rem.setSvgEndX(posXY[1]);
            rem.setSvgStartY(posXY[2]);
            rem.setSvgEndY(posXY[3]);
            svgPosts.add(rem);
        }
        return svgPosts;
    }

    //Vi har valgt ikke at bruge sternbrædder til SVG tegningen
    /*private List<Material> svgSetXYSternbrædtForOgBag(Material stern, Carport carport)
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
    }*/

    private static List<Material> svgSetXYSpær(Material spær, Carport carport)
    {
        List<Material> svgPosts = new ArrayList<>();
        int quantity = spær.getQuantity();
        for (int i = 0; i < quantity; i++)
        {
            int[] posXY = Calculator.calcRaftersXY(carport, quantity, i);
            spær.setSvgStartX(posXY[0]);
            spær.setSvgEndX(posXY[1]);
            spær.setSvgStartY(posXY[2]);
            spær.setSvgEndY(posXY[3]);
            svgPosts.add(spær);
        }
        return svgPosts;
    }
}