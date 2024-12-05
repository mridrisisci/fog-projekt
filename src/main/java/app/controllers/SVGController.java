package app.controllers;

import app.entities.Material;
import app.entities.SVGTestClass;
import io.javalin.http.Context;

import java.util.List;

public class SVGController {

    int postLength;
    int postWidth;
    int beamLength;
    int beamWidth;
    int rafterLength;
    int rafterWidth;
    int fasciaBoardLength;
    int fasciaBoardWidth;
    int crossRafterLength;
    int crossRafterWidth;
    //TODO: Lav en beregningsmetode til at beregne længde på hulbånd til SVG.
    for(Material mat: svgMaterialList){
        if(mat.getType().equals("Stolpe")){
            postLength = mat.getLength();
            postWidth = mat.getLength();
        }
        if(mat.getType().equals("Rem")){
            beamLength = mat.getLength();
            beamWidth = mat.getLength();
        }
        if(mat.getType().equals("Spær")){
            rafterLength = mat.getLength();
            rafterWidth = mat.getLength();
        }
        if(mat.getType().equals("Stern")){
            fasciaBoardLength = mat.getLength();
            fasciaBoardWidth = mat.getLength();
        }
        if(mat.getType().equals("Stern")){
            crossRafterLength = mat.getLength();
            crossRafterWidth = mat.getLength();
        }
    }
    public static void generateSVG(List<Material> svgMaterialList, Context ctx) {
        try {

            String svgContent = SVGTestClass.generateCarportSVG(
                    97,     // postLength
                    97, //postWidth
                    400,    // beamLength
                    20,     // beamWidth
                    400,    // rafterLength
                    10,     // rafterWidth
                    400,    // fasciaBoardLength
                    10,     // fasciaBoardWidth
                    133,    // crossRafterLength,
                    10,     // crossRafterLength,
                    100     // spacing
            );

            // Save to file or send as response
            SVGTestClass.saveSVGToFile("carport.svg", svgContent);

            // Optional: Send as an HTTP response
            ctx.result(svgContent).contentType("image/svg+xml");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Error generating SVG");
        }
    }
}
