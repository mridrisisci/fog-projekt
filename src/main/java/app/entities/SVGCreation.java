package app.entities;

import app.utilities.Calculator;

import java.util.ArrayList;
import java.util.List;

public class SVGCreation
{

    public static String generatePosts(List<Material> postList)
    {
        StringBuilder postsBuilder = new StringBuilder();
        for (int i = 0; i < postList.size(); i++)
        {
            int startPosX = postList.get(i).getSvgStartPosX() - (postList.get(i).getLength() / 2);
            int startPosY = postList.get(i).getSvgStartPosY() - (postList.get(i).getWidth() / 2);
            int width = postList.get(i).getSvgWidth();
            int height = postList.get(i).getSvgHeight();
            postsBuilder.append(String.format(
                    "<rect id=\"post%d\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" stroke=\"black\" fill-opacity=\"0.0\" />\n",
                    i + 1, startPosX, startPosY, width, height
            ));
        }
        return postsBuilder.toString();
    }

    public static String generateRafters(List<Material> raftersList)
    {
        StringBuilder raftersBuilder = new StringBuilder();
        for (int i = 0; i < raftersList.size(); i++)
        {
            int startPosX = raftersList.get(i).getSvgStartPosX();
            int endPosX = raftersList.get(i).getSvgEndPosX();
            int startPosY = raftersList.get(i).getSvgStartPosY();
            int endPosY = raftersList.get(i).getSvgEndPosY();
            raftersBuilder.append(String.format(
                    "<line id=\"rafter%d\" x1=\"%d\" x2=\"%d\" y1=\"%d\" y2=\"%d\" stroke=\"darkgray\" stroke-width=\"5\" />\n",
                    i + 1, startPosX, endPosX, startPosY, endPosY
            ));
        }
        return raftersBuilder.toString();
    }

    public static String generateBeams(List<Material> beamList)
    {
        StringBuilder beamsBuilder = new StringBuilder();
        for (int i = 0; i < beamList.size(); i++)
        {
            int posX = beamList.get(i).getSvgStartPosX();
            int posY = beamList.get(i).getSvgStartPosY();
            int width = beamList.get(i).getSvgWidth();
            int height = beamList.get(i).getSvgHeight();
            beamsBuilder.append(String.format(
                    "<rect id=\"beam%d\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" stroke=\"black\" fill-opacity=\"0.0\" />\n",
                    i + 1, posX, posY, width, height
            ));
        }
        return beamsBuilder.toString();
    }

    //Vi har valgt at hardcode kanten af carporten da vi får størrelsen sammen med foresprøgslen
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
                    stolpeList.addAll(svgSetXYPosts(mat, carport));
                    break;
                case "Rem":
                    remList.addAll(svgSetXYBeam(mat, carport));
                    break;
                case "Spær":
                    spærList.addAll(svgSetXYRafters(mat, carport));
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

    private static List<Material> svgSetXYPosts(Material post, Carport carport)
    {
        List<Material> svgPosts = new ArrayList<>();
        int quantity = post.getQuantity();
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

    private static List<Material> svgSetXYRafters(Material rafter, Carport carport)
    {
        List<Material> svgRafters = new ArrayList<>();
        int quantity = rafter.getQuantity();
        for (int i = 0; i < quantity; i++)
        {
            int[] posXY = Calculator.calcRaftersXY(carport, quantity, i);
            int startXPos = posXY[0];
            int endXPos = posXY[1];
            int startYPos = posXY[2];
            int endYPos = posXY[3];
            Material newRafter = new Material(startXPos, endXPos, startYPos, endYPos, true);
            svgRafters.add(newRafter);
        }
        return svgRafters;
    }

    private static List<Material> svgSetXYBeam(Material beam, Carport carport)
    {
        List<Material> svgBeams = new ArrayList<>();

        int quantity = beam.getQuantity();
        int remLength = beam.getLength();
        int carportLength = carport.getLENGTH();

        // hvis man kun har 1 beam i styklisten skal den saves over i 2
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
            svgBeams.add(newBeam);
        }
        return svgBeams;
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