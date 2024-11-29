package app.utilities;
import app.entities.Carport;
import app.utilities.Calculator;
import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest
{
    @Test
    public void testCalcRoofPlates(){

        // Arrange
        Carport carport = new Carport(false, 240, 240);
        int[] actual;
        int expected1 = 0;
        int expected2 = 3;

        // Act
        actual = Calculator.calcRoofPlates(carport);

        // Assert
        Assert.assertEquals(expected1, actual[0]);
        Assert.assertEquals(expected2, actual[1]);

    }
}
