package app.entities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SVGTestClass {

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
            int spacing) {
        StringBuilder svg = new StringBuilder();

        svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"600\" height=\"600\">\n");

        // Add posts (4 posts in a rectangular layout)
        svg.append(String.format("<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"Red\" />\n", spacing, spacing, postLength, postWidth));
        svg.append(String.format("<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"Red\" />\n", 500 - spacing, spacing, postLength, postWidth));
        svg.append(String.format("<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"Red\" />\n", spacing, 500 - spacing, postLength, postWidth));
        svg.append(String.format("<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"Red\" />\n", 500 - spacing, 500 - spacing, postLength, postWidth));

        // Add beams (2 beams opposite each other along the top and bottom)
        svg.append(String.format("<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"gray\" />\n", spacing, spacing - beamWidth, beamLength, beamWidth));
        svg.append(String.format("<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"gray\" />\n", spacing, 500 - spacing, beamLength, beamWidth));

        // Add rafters (several rafters connecting the beams)
        for (int i = spacing; i < 500 - spacing; i += 50) {
            svg.append(String.format("<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"darkgray\" />\n", spacing, i, rafterLength, rafterWidth));
        }

        // Add fascia boards (2 fascia boards opposite each other along the sides)
        svg.append(String.format("<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"black\" />\n", spacing - fasciaBoardWidth, spacing, fasciaBoardWidth, fasciaBoardLength));
        svg.append(String.format("<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"black\" />\n", 500, spacing, fasciaBoardWidth, fasciaBoardLength));

        svg.append("</svg>\n");

        return svg.toString();
    }

    public static void saveSVGToFile(String filePath, String svgContent) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(svgContent);
        }
    }
}
