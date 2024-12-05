package app.entities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SVGCreation
{

    public static String loadSVGFromFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath))); // Read SVG file content
    }

    public static String generateCarportSVG(
            int postLength,
            int postWidth,
            int beamLength,
            int beamWidth,
            int rafterLength,
            int rafterWidth,
            int fasciaBoardLength,
            int fasciaBoardWidth,
            int crossRafterLength,
            int crossRafterWidth,
            int spacing,
            int quantityOfPosts,
            int quantityOfBeams,
            int quantityOfFaciaBoards) {

        StringBuilder svg = new StringBuilder();
        svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"600\" height=\"600\">\n");

        StringBuilder postBuilder = new StringBuilder();
        for (int i = 1; i <= quantityOfPosts; i++) {
            postBuilder.append(
                    String.format(
                            "<rect id=\"post%d\" x=\"0\" y=\"0\" width=\"%d\" height=\"%d\" fill=\"Red\" />\n",
                            i, postWidth, postLength
                    )
            );
        }
        svg.append(postBuilder.toString());

        StringBuilder beamBuilder = new StringBuilder();
        for (int i = 1; i <= quantityOfBeams; i++) {
            beamBuilder.append(
                    String.format(
                            "<rect id=\"beam%d\" x=\"0\" y=\"0\" width=\"%d\" height=\"%d\" fill=\"gray\" />\n",
                            i, beamWidth, beamLength
                    )
            );
        }
        svg.append(beamBuilder.toString());

        // TODO få kigget på logik bag spær
        StringBuilder raftBuilder = new StringBuilder();
        for (int i = spacing; i < 500 - spacing; i += 50) {
            raftBuilder.append(
                    String.format(
                            "<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"darkgray\" />\n",
                            i, 0, rafterWidth, rafterLength
                    )
            );
        }
        svg.append(raftBuilder.toString());

        StringBuilder faciaBuilder = new StringBuilder();
        for (int i = 1; i <= quantityOfFaciaBoards; i++) {
            faciaBuilder.append(
                    String.format(
                            "<rect id=\"facia%d\" x=\"0\" y=\"0\" width=\"%d\" height=\"%d\" fill=\"black\" />\n",
                            i, fasciaBoardWidth, fasciaBoardLength
                    )
            );
        }
        svg.append(faciaBuilder.toString());

        svg.append("</svg>\n");
        return svg.toString();
    }

    public static void saveSVGToFile(String filePath, String svgContent) throws IOException {
        Files.write(Paths.get(filePath), svgContent.getBytes());
    }
}
