package app.utilities;

import app.entities.Carport;
import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {

    @Test
    public void testCalcRoofPlates() {

        // Arrange and Act
        // Test case 1: Short plates only
        Carport carport1 = new Carport(1, false, 360, 240);
        int[] actual1 = Calculator.calcRoofPlates(carport1);
        int expected1Long = 0;
        int expected1Short = 3;

        // Test case 2: Long plates only
        Carport carport2 = new Carport(1, false, 600, 240);
        int[] actual2 = Calculator.calcRoofPlates(carport2);
        int expected2Long = 3;
        int expected2Short = 0;

        // Test case 3: Combination of long and short plates
        Carport carport3 = new Carport(1, false, 780, 240);
        int[] actual3 = Calculator.calcRoofPlates(carport3);
        int expected3Long = 3;
        int expected3Short = 3;

        // Test case 4: Short plates only, with width adjustment
        Carport carport4 = new Carport(1, false, 360, 360);
        int[] actual4 = Calculator.calcRoofPlates(carport4);
        int expected4Long = 0;
        int expected4Short = 4;

        // Test case 5: Long plates only, with width adjustment
        Carport carport5 = new Carport(1, false, 600, 360);
        int[] actual5 = Calculator.calcRoofPlates(carport5);
        int expected5Long = 4;
        int expected5Short = 0;

        // Test case 6: Zero dimensions
        Carport carport6 = new Carport(1, false, 0, 0);
        int[] actual6 = Calculator.calcRoofPlates(carport6);
        int expected6Long = 0;
        int expected6Short = 0;

        // Assert
        Assert.assertEquals(expected1Long, actual1[0]);
        Assert.assertEquals(expected1Short, actual1[1]);

        Assert.assertEquals(expected2Long, actual2[0]);
        Assert.assertEquals(expected2Short, actual2[1]);

        Assert.assertEquals(expected3Long, actual3[0]);
        Assert.assertEquals(expected3Short, actual3[1]);

        Assert.assertEquals(expected4Long, actual4[0]);
        Assert.assertEquals(expected4Short, actual4[1]);

        Assert.assertEquals(expected5Long, actual5[0]);
        Assert.assertEquals(expected5Short, actual5[1]);

        Assert.assertEquals(expected6Long, actual6[0]);
        Assert.assertEquals(expected6Short, actual6[1]);
    }
}
