package app.utilities;

public class Calculator
{
    /**
     * Calculates the number of long and short roof plates needed for a carport.
     *
     * @param carportLength The length of the carport in cm.
     * @param carportWidth  The width of the carport in cm.
     * @return An array where index 0 is the number of long plates, and index 1 is the number of short plates.
     */
    public static int[] calcRoofPlates(int carportLength, int carportWidth) {
        // Plate dimensions in cm
        final int LONG_PLATE_LENGTH = 600;
        final int SHORT_PLATE_LENGTH = 360;
        final int PLATE_WIDTH = 109;
        final int OVERLAP = 20;

        // Calculate the number of plates needed along the width (no overlap in width)
        int platesAlongWidth = (int) Math.ceil((double) carportWidth / PLATE_WIDTH);

        // Calculate effective lengths considering overlap
        int effectiveLongPlateLength = LONG_PLATE_LENGTH - OVERLAP;
        int effectiveShortPlateLength = SHORT_PLATE_LENGTH - OVERLAP;

        // Try to cover the carport length using long plates first, then fill the remainder with short plates
        int longPlatesNeeded = carportLength / effectiveLongPlateLength;
        int remainingLength = carportLength % effectiveLongPlateLength;

        int shortPlatesNeeded = (remainingLength > 0) ?
                (int) Math.ceil((double) remainingLength / effectiveShortPlateLength) : 0;

        // Total plates needed is the product of platesAlongWidth and platesAlongLength
        longPlatesNeeded *= platesAlongWidth;
        shortPlatesNeeded *= platesAlongWidth;

        return new int[]{longPlatesNeeded, shortPlatesNeeded};
    }
}
