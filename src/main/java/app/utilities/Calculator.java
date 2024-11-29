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

        // Calculate plates needed along the width (no overlap in width)
        int platesAlongWidth = (int) Math.ceil((double) carportWidth / PLATE_WIDTH);

        // If length is less than or equal to a single plate, no overlap needed
        if (carportLength <= SHORT_PLATE_LENGTH) {
            return new int[]{0, platesAlongWidth};
        } else if (carportLength <= LONG_PLATE_LENGTH) {
            return new int[]{platesAlongWidth, 0};
        }

        // Effective lengths considering overlap
        int effectiveLongPlateLength = LONG_PLATE_LENGTH - OVERLAP;
        int effectiveShortPlateLength = SHORT_PLATE_LENGTH - OVERLAP;

        // Calculate how many long plates can fit
        int longPlatesUsed = carportLength / effectiveLongPlateLength;

        // Calculate remaining length to cover
        int remainingLength = carportLength - (longPlatesUsed * effectiveLongPlateLength);

        // Check if the remaining length can be covered by short plates
        int shortPlatesUsed = (remainingLength > 0) ?
                (int) Math.ceil((double) remainingLength / SHORT_PLATE_LENGTH) : 0;

        // Total plates needed
        int totalLongPlates = longPlatesUsed * platesAlongWidth;
        int totalShortPlates = shortPlatesUsed * platesAlongWidth;

        return new int[]{totalLongPlates, totalShortPlates};
    }
}
