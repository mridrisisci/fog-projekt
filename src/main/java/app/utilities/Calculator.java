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

        // Calculate the number of long plates needed without considering excess
        int longPlatesUsed = 1 + (int) Math.floor((double) (carportLength - LONG_PLATE_LENGTH) / (LONG_PLATE_LENGTH - OVERLAP));

        // Calculate the excess length if we only used long plates
        int excessWithLongPlates = (longPlatesUsed * LONG_PLATE_LENGTH - (longPlatesUsed - 1) * OVERLAP) - carportLength;

        // Find the best combination by testing different numbers of long and short plates
        int minWaste = Integer.MAX_VALUE;
        int bestLongPlates = 0;
        int bestShortPlates = 0;

        // Loop to find the optimal combination of long and short plates
        for (int longCount = 0; longCount <= longPlatesUsed; longCount++) {
            int lengthCoveredByLongPlates = longCount * LONG_PLATE_LENGTH - (longCount - 1) * OVERLAP;
            int remainingLength = carportLength - lengthCoveredByLongPlates;

            // Calculate how many short plates are needed to cover the remaining length
            int shortPlatesUsed = (remainingLength > 0) ?
                    (int) Math.ceil((double) remainingLength / SHORT_PLATE_LENGTH) : 0;

            // Calculate the total waste for this combination
            int totalWaste = Math.abs((longCount * LONG_PLATE_LENGTH - (longCount - 1) * OVERLAP) + (shortPlatesUsed * SHORT_PLATE_LENGTH) - carportLength);

            // Check if this combination has less waste than the current minimum
            if (totalWaste < minWaste) {
                minWaste = totalWaste;
                bestLongPlates = longCount;
                bestShortPlates = shortPlatesUsed;
            }
        }

        // Calculate the total number of plates needed, factoring in the width
        int totalLongPlates = bestLongPlates * platesAlongWidth;
        int totalShortPlates = bestShortPlates * platesAlongWidth;

        return new int[]{totalLongPlates, totalShortPlates};
    }
}
