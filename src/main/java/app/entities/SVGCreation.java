package app.entities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    public static String generateFasciaBoards(int fasciaBoardLength, int fasciaBoardWidth, int quantityOfFasciaBoards)
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
    }

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
}
