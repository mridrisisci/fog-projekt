package app.entities;

import app.utilities.Calculator;

import java.io.IOException;
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
        for (int i = 0; i < postsList.size(); i++)
        {
            int startPosX = postsList.get(i).getSvgStartPosX() - (postsList.get(i).getLength() / 2);
            int startPosY = postsList.get(i).getSvgStartPosY() - (postsList.get(i).getWidth() / 2);
            int width = postsList.get(i).getSvgWidth();
            int height = postsList.get(i).getSvgHeight();
            postsBuilder.append(String.format(
                    "<rect id=\"post%d\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" stroke=\"black\" fill-opacity=\"0.0\" />\n",
                    i + 1, startPosX, startPosY, width, height
            ));
        }
        return postsBuilder.toString();
    }

    public static String generateRafters(List<Material> raftList)
    {
        StringBuilder raftersBuilder = new StringBuilder();
        for (int i = 0; i < raftList.size(); i++)
        {
            int startPosX = raftList.get(i).getSvgStartPosX();
            int endPosX = raftList.get(i).getSvgEndPosX();
            int startPosY = raftList.get(i).getSvgStartPosY();
            int endPosY = raftList.get(i).getSvgEndPosY();
            raftersBuilder.append(String.format(
                    "<line id=\"rafter%d\" x1=\"%d\" x2=\"%d\" y1=\"%d\" y2=\"%d\" stroke=\"darkgray\" stroke-width=\"5\" />\n",
                    i + 1, startPosX, endPosX, startPosY, endPosY
            ));
        }
        return raftersBuilder.toString();
    }

    public static String generateBeams(List<Material> remList)
    {
        StringBuilder beamsBuilder = new StringBuilder();
        for (int i = 0; i < remList.size(); i++)
        {
            int posX = remList.get(i).getSvgStartPosX();
            int posY = remList.get(i).getSvgStartPosY();
            int width = remList.get(i).getSvgWidth();
            int height = remList.get(i).getSvgHeight();
            beamsBuilder.append(String.format(
                    "<rect id=\"beam%d\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" stroke=\"black\" fill-opacity=\"0.0\" />\n",
                    i + 1, posX, posY, width, height
            ));
        }
        return beamsBuilder.toString();
    }

    //Vi har valgt at hardcode kanten af carporten da vi får størrelsen sammen med forsprøgslen
    public static String generateFasciaBoards(int carportLength, int carportWidth)
    {
        StringBuilder fasciaBoardsBuilder = new StringBuilder();
        fasciaBoardsBuilder.append(String.format("<rect id=\"facia%d\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" stroke=\"black\" fill-opacity=\"0.0\" stroke-width=\"2\" />\n",
                1, 0, 0, carportLength, carportWidth
        ));
        return fasciaBoardsBuilder.toString();

    }

    public static String generateCarportSVGFromTemplate(String template, String svg)
    {
        template = getSVGTemplate();
        return template.replace("{{svg}}", svg);
    }

    public static String generateSVGString(List<Material> svgMaterialList, Carport carport)
    {

        List<Material> stolpeList = new ArrayList<>();
        List<Material> remList = new ArrayList<>();
        List<Material> spærList = new ArrayList<>();

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
            }
        }
        int carportLength = carport.getLENGTH();
        int carportWidth = carport.getWIDTH();

        String fasciaboardXML = generateFasciaBoards(carportLength, carportWidth);
        String postsXML = generatePosts(stolpeList);
        String beamsXML = generateBeams(remList);
        String raftersXML = generateRafters(spærList);

        String res = fasciaboardXML + beamsXML + postsXML + raftersXML;

        return res;
    }

    private static List<Material> svgSetXYStople(Material stolpe, Carport carport)
    {
        List<Material> svgPosts = new ArrayList<>();
        int quantity = stolpe.getQuantity();
        for (int i = 0; i < quantity; i++)
        {
            int[] posXY = Calculator.calcPostsXY(carport, quantity, i);
            int xPos = posXY[0];
            int yPos = posXY[1];
            int width = 10;
            int height = 10;
            Material newPost = new Material(xPos, yPos, width, height);
            svgPosts.add(newPost);
        }
        return svgPosts;
    }

    //TODO lav tykkelse på remmene så det ikke bliver en streg
    private static List<Material> svgSetXYRem(Material rem, Carport carport)
    {
        List<Material> svgPosts = new ArrayList<>();

        int quantity = rem.getQuantity();
        int remLength = rem.getLength();
        int carportLength = carport.getLENGTH();

        // hvis man kun har 1 rem i styklisten skal den saves over i 2
        if (quantity == 1)
        {
            quantity += 1;
            remLength = carportLength;
        }

        for (int i = 0; i < quantity; i++)
        {
            int[] posXY = Calculator.calcBeamsXY(carport, quantity, i, remLength);
            int xPos = posXY[0];
            int yPos = posXY[1];
            int width = posXY[2];
            // hardcoded til at have samme højde som vores stolper
            int height = 10;
            Material newBeam = new Material(xPos, yPos, width, height);
            svgPosts.add(newBeam);
        }
        return svgPosts;
    }

    private static List<Material> svgSetXYSpær(Material spær, Carport carport)
    {
        List<Material> svgPosts = new ArrayList<>();
        int quantity = spær.getQuantity();
        for (int i = 0; i < quantity; i++)
        {
            int[] posXY = Calculator.calcRaftersXY(carport, quantity, i);
            int startXPos = posXY[0];
            int endXPos = posXY[1];
            int startYPos = posXY[2];
            int endYPos = posXY[3];
            Material newRafter = new Material(startXPos, endXPos, startYPos, endYPos, true);
            svgPosts.add(newRafter);
        }
        return svgPosts;
    }

    private static String getSVGTemplate()
    {
        return "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"850\" height=\"850\">\n" +
                "    <g transform=\"translate(10,10)\">\n" +
                "    {{svg}}\n" +
                "    </g>\n" +
                "</svg>";
    }
}