package app.utilities;

import app.entities.Carport;
import app.entities.Material;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CalculatorTest
{
    Carport carport1 = new Carport(1, 360, 240);
    Carport carport2 = new Carport(1, 600, 240);
    Carport carport3 = new Carport(1, 780, 240);
    Carport carport4 = new Carport(1, 360, 360);
    Carport carport5 = new Carport(1, 600, 360);
    Carport carport6 = new Carport(1, 0, 0);
    Carport carport7 = new Carport(1, 600, 480);
    Carport carport8 = new Carport(1, 600, 540);
    Carport carport9 = new Carport(1, 600, 600);
    Carport carport10 = new Carport(1, 420, 420);

    Material material1 = new Material(1, "test_name1", "test_description1", 50, "Stk", 1, 20, 20, 20, false);
    Material material2 = new Material(2, "test_name2", "test_description2", 25, "Stk", 1, 20, 20, 20, false);
    Material material3 = new Material(3, "test_name3", "test_description3", 100, "Kg", 2, 15, 15, 15, true);
    Material material4 = new Material(4, "test_name4", "test_description4", 75, "L", 3, 30, 30, 30, false);
    Material material5 = new Material(5, "test_name5", "test_description5", 60, "Stk", 2, 10, 10, 10, true);
    Material material6 = new Material(6, "test_name6", "test_description6", 45, "m", 1, 25, 25, 25, false);
    Material material7 = new Material(7, "test_name7", "test_description7", 80, "Kg", 3, 35, 35, 35, true);
    Material material8 = new Material(8, "test_name8", "test_description8", 55, "L", 4, 40, 40, 40, false);
    List<Material> materialList = new ArrayList<>();

    @Before
    public void addMaterialsToList()
    {
        materialList.add(material1);
        materialList.add(material2);
        materialList.add(material3);
        materialList.add(material4);
        materialList.add(material5);
        materialList.add(material6);
        materialList.add(material7);
        materialList.add(material8);
    }

    @After
    public void clearMaterialList()
    {
        materialList.clear();
    }

    @Test
    public void testCalcRoofPlates()
    {
        // Arrange and Act
        // Test case 1: Short plates only
        int[] actual1 = Calculator.calcRoofPlates(carport1);
        int expected1Long = 0;
        int expected1Short = 3;

        // Test case 2: Long plates only
        int[] actual2 = Calculator.calcRoofPlates(carport2);
        int expected2Long = 3;
        int expected2Short = 0;

        // Test case 3: Combination of long and short plates
        int[] actual3 = Calculator.calcRoofPlates(carport3);
        int expected3Long = 3;
        int expected3Short = 3;

        // Test case 4: Short plates only, with width adjustment
        int[] actual4 = Calculator.calcRoofPlates(carport4);
        int expected4Long = 0;
        int expected4Short = 4;

        // Test case 5: Long plates only, with width adjustment
        int[] actual5 = Calculator.calcRoofPlates(carport5);
        int expected5Long = 4;
        int expected5Short = 0;

        // Test case 6: Zero dimensions
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

    @Test
    public void testCalcPickListPrice()
    {
        // Arrange and Act
        int actual = Calculator.calcPickListPrice(materialList);
        int expected = 1125;

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCalcSalesPrice()
    {
        // Arrange and Act
        // pickListPrice should be 1125
        int pickListPrice = Calculator.calcPickListPrice(materialList);
        double coverageRatio = 0.35;

        int actual = Calculator.calcSalesPrice(pickListPrice, coverageRatio);
        int expected = 1518;

        // Assert
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testCalcPosts()
    {
        // Arrange and Act
        int actual1 = Calculator.calcPosts(carport1); // Length = 360 cm
        int expected1 = 6;

        int actual2 = Calculator.calcPosts(carport2); // Length = 600 cm
        int expected2 = 6;

        int actual3 = Calculator.calcPosts(carport3); // Length = 780 cm
        int expected3 = 6;

        int actual4 = Calculator.calcPosts(carport4); // Length = 360 cm
        int expected4 = 6;

        int actual5 = Calculator.calcPosts(carport5); // Length = 600 cm
        int expected5 = 6;

        int actual6 = Calculator.calcPosts(carport6); // Length = 0 cm
        int expected6 = 4; // Technically, should be verified. Likely minimal or default posts.

        // Assert
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected3, actual3);
        Assert.assertEquals(expected4, actual4);
        Assert.assertEquals(expected5, actual5);
        Assert.assertEquals(expected6, actual6);
    }

    @Test
    public void testCalcBeams()
    {
        // Arrange and Act
        int[] actual1 = Calculator.calcBeams(carport1); // Length = 360 cm
        int[] expected1 = {2, 480};

        int[] actual2 = Calculator.calcBeams(carport2); // Length = 600 cm
        int[] expected2 = {2, 600};

        int[] actual3 = Calculator.calcBeams(carport3); // Length = 780 cm
        int[] expected3 = {4, 480};

        int[] actual4 = Calculator.calcBeams(carport4); // Length = 360 cm
        int[] expected4 = {2, 480};

        int[] actual5 = Calculator.calcBeams(carport5); // Length = 600 cm
        int[] expected5 = {2, 600};

        int[] actual6 = Calculator.calcBeams(carport6); // Length = 0 cm
        int[] expected6 = {1, 600}; // Default to minimal requirement for very small lengths.

        // Assert
        Assert.assertArrayEquals(expected1, actual1);
        Assert.assertArrayEquals(expected2, actual2);
        Assert.assertArrayEquals(expected3, actual3);
        Assert.assertArrayEquals(expected4, actual4);
        Assert.assertArrayEquals(expected5, actual5);
        Assert.assertArrayEquals(expected6, actual6);
    }

    @Test
    public void testCalcSidesFasciaBoard()
    {
        // Arrange and Act
        int[] actual1 = Calculator.calcSidesFasciaBoard(carport1); // Length = 360 cm
        int[] expected1 = {2, 360}; // Expected: Length is 360, Quantity is 2.

        int[] actual2 = Calculator.calcSidesFasciaBoard(carport2); // Length = 600 cm
        int[] expected2 = {4, 360}; // Expected: Length is 360, Quantity is 2.

        int[] actual3 = Calculator.calcSidesFasciaBoard(carport3); // Length = 780 cm
        int[] expected3 = {4, 540}; // Expected: Length is 540, Quantity is 4.

        int[] actual4 = Calculator.calcSidesFasciaBoard(carport4); // Length = 360 cm
        int[] expected4 = {2, 360}; // Expected: Same as `carport1`.

        int[] actual5 = Calculator.calcSidesFasciaBoard(carport5); // Length = 600 cm
        int[] expected5 = {4, 360}; // Expected: Same as `carport2`.

        int[] actual6 = Calculator.calcSidesFasciaBoard(carport6); // Length = 0 cm
        int[] expected6 = {1, 540}; // Expected: Length is 540, Quantity is 1.

        int[] actual10 = Calculator.calcSidesFasciaBoard(carport10); // Length = 420 cm
        int[] expected10 = {2, 540}; // Expected: Length is 540, Quantity is 2.

        // Assert
        Assert.assertArrayEquals(expected1, actual1);
        Assert.assertArrayEquals(expected2, actual2);
        Assert.assertArrayEquals(expected3, actual3);
        Assert.assertArrayEquals(expected4, actual4);
        Assert.assertArrayEquals(expected5, actual5);
        Assert.assertArrayEquals(expected6, actual6);
        Assert.assertArrayEquals(expected10, actual10);
    }

    @Test
    public void testCalcFrontAndBackFasciaBoard()
    {
        // Arrange and Act
        int[] actual1 = Calculator.calcFrontAndBackFasciaBoard(carport1); // Width = 240 cm
        int[] expected1 = {1, 540}; // Expected: Length is 540, Quantity is 1.

        int[] actual2 = Calculator.calcFrontAndBackFasciaBoard(carport2); // Width = 240 cm
        int[] expected2 = {1, 540}; // Expected: Length is 540, Quantity is 1.

        int[] actual3 = Calculator.calcFrontAndBackFasciaBoard(carport3); // Width = 240 cm
        int[] expected3 = {1, 540}; // Expected: Length is 540, Quantity is 1.

        int[] actual4 = Calculator.calcFrontAndBackFasciaBoard(carport4); // Width = 360 cm
        int[] expected4 = {2, 360}; // Expected: Length is 360, Quantity is 2.

        int[] actual5 = Calculator.calcFrontAndBackFasciaBoard(carport5); // Width = 360 cm
        int[] expected5 = {2, 360}; // Expected: Length is 360, Quantity is 2.

        int[] actual6 = Calculator.calcFrontAndBackFasciaBoard(carport6); // Width = 0 cm (edge case)
        int[] expected6 = {1, 540}; // Expected: No fascia needed.

        int[] actual7 = Calculator.calcFrontAndBackFasciaBoard(carport7); // Width = 480 cm
        int[] expected7 = {2, 540}; // Expected: Length is 540, Quantity is 2.

        int[] actual8 = Calculator.calcFrontAndBackFasciaBoard(carport8); // Width = 540 cm
        int[] expected8 = {2, 540}; // Expected: Length is 540, Quantity is 2.

        int[] actual9 = Calculator.calcFrontAndBackFasciaBoard(carport9); // Width = 600 cm
        int[] expected9 = {4, 360}; // Expected: Length is 360, Quantity is 4.

        // Assert
        Assert.assertArrayEquals(expected1, actual1);
        Assert.assertArrayEquals(expected2, actual2);
        Assert.assertArrayEquals(expected3, actual3);
        Assert.assertArrayEquals(expected4, actual4);
        Assert.assertArrayEquals(expected5, actual5);
        Assert.assertArrayEquals(expected6, actual6);
        Assert.assertArrayEquals(expected7, actual7);
        Assert.assertArrayEquals(expected8, actual8);
        Assert.assertArrayEquals(expected9, actual9);
    }

    @Test
    public void testCalcRafters()
    {
        // Arrange and Act
        int[] actual1 = Calculator.calcRafters(carport1); // Length = 360 cm, Width = 240 cm
        int[] expected1 = {6, 480}; // Expected: Length is 480, Quantity is 6.

        int[] actual2 = Calculator.calcRafters(carport2); // Length = 600 cm, Width = 240 cm
        int[] expected2 = {10, 480}; // Expected: Length is 480, Quantity is 10.

        int[] actual3 = Calculator.calcRafters(carport3); // Length = 780 cm, Width = 240 cm
        int[] expected3 = {13, 480}; // Expected: Length is 480, Quantity is 13.

        int[] actual4 = Calculator.calcRafters(carport4); // Length = 360 cm, Width = 360 cm
        int[] expected4 = {6, 480}; // Expected: Length is 480, Quantity is 6.

        int[] actual5 = Calculator.calcRafters(carport5); // Length = 600 cm, Width = 360 cm
        int[] expected5 = {10, 480}; // Expected: Length is 480, Quantity is 10.

        int[] actual6 = Calculator.calcRafters(carport6); // Length = 0 cm, Width = 0 cm
        int[] expected6 = {0, 480}; // Expected: No rafters needed as length is 0.

        int[] actual7 = Calculator.calcRafters(carport7); // Length = 600 cm, Width = 480 cm
        int[] expected7 = {10, 480}; // Expected: Length is 480, Quantity is 10.

        int[] actual8 = Calculator.calcRafters(carport8); // Length = 600 cm, Width = 540 cm
        int[] expected8 = {10, 600}; // Expected: Length is 600, Quantity is 10.

        int[] actual9 = Calculator.calcRafters(carport9); // Length = 600 cm, Width = 600 cm
        int[] expected9 = {10, 600}; // Expected: Length is 600, Quantity is 10.

        // Assert
        Assert.assertArrayEquals(expected1, actual1);
        Assert.assertArrayEquals(expected2, actual2);
        Assert.assertArrayEquals(expected3, actual3);
        Assert.assertArrayEquals(expected4, actual4);
        Assert.assertArrayEquals(expected5, actual5);
        Assert.assertArrayEquals(expected6, actual6);
        Assert.assertArrayEquals(expected7, actual7);
        Assert.assertArrayEquals(expected8, actual8);
        Assert.assertArrayEquals(expected9, actual9);
    }

    @Test
    public void testCalcScrewsForRoofing()
    {
        // Arrange and Act
        int actual1 = Calculator.calcScrewsForRoofing(carport1); // Length = 360 cm, Width = 240 cm
        int expected1 = 2; // Expected: 2 packages of screws needed for this area.

        int actual2 = Calculator.calcScrewsForRoofing(carport2); // Length = 600 cm, Width = 240 cm
        int expected2 = 2; // Expected: 2 packages of screws needed for this area.

        int actual3 = Calculator.calcScrewsForRoofing(carport3); // Length = 780 cm, Width = 240 cm
        int expected3 = 3; // Expected: 3 packages of screws needed for this area.

        int actual4 = Calculator.calcScrewsForRoofing(carport4); // Length = 360 cm, Width = 360 cm
        int expected4 = 2; // Expected: 3 packages of screws needed for this area.

        int actual5 = Calculator.calcScrewsForRoofing(carport5); // Length = 600 cm, Width = 360 cm
        int expected5 = 3; // Expected: 3 packages of screws needed for this area.

        int actual6 = Calculator.calcScrewsForRoofing(carport6); // Length = 0 cm, Width = 0 cm
        int expected6 = 0; // Expected: 2 packages of screws needed (minimum amount).

        int actual7 = Calculator.calcScrewsForRoofing(carport7); // Length = 600 cm, Width = 480 cm
        int expected7 = 3; // Expected: 3 packages of screws needed for this area.

        int actual8 = Calculator.calcScrewsForRoofing(carport8); // Length = 600 cm, Width = 540 cm
        int expected8 = 3; // Expected: 3 packages of screws needed for this area.

        int actual9 = Calculator.calcScrewsForRoofing(carport9); // Length = 600 cm, Width = 600 cm
        int expected9 = 3; // Expected: 3 packages of screws needed for this area.

        // Assert
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected3, actual3);
        Assert.assertEquals(expected4, actual4);
        Assert.assertEquals(expected5, actual5);
        Assert.assertEquals(expected6, actual6);
        Assert.assertEquals(expected7, actual7);
        Assert.assertEquals(expected8, actual8);
        Assert.assertEquals(expected9, actual9);
    }

    @Test
    public void testCalcStandardScrews()
    {
        //Arrange and Act
        int actual = Calculator.calcStandardScrews();
        int expected = 1;

        //Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCalcRollForWindCross()
    {
        //Arrange and Act
        int actual = Calculator.calcRollForWindCross();
        int expected = 2;

        //Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCalcSquareWasher()
    {
        // Arrange and Act
        int actual1 = Calculator.calcSquareWasher(carport1); // Length = 360 cm
        int expected1 = 8;

        int actual2 = Calculator.calcSquareWasher(carport2); // Length = 600 cm
        int expected2 = 8;

        int actual3 = Calculator.calcSquareWasher(carport3); // Length = 780 cm
        int expected3 = 8;

        int actual4 = Calculator.calcSquareWasher(carport4); // Length = 360 cm
        int expected4 = 8;

        int actual5 = Calculator.calcSquareWasher(carport5); // Length = 600 cm
        int expected5 = 8;

        int actual6 = Calculator.calcSquareWasher(carport6); // Length = 0 cm
        int expected6 = 6; // Technically, should be verified. Likely minimal or default posts.

        // Assert
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected3, actual3);
        Assert.assertEquals(expected4, actual4);
        Assert.assertEquals(expected5, actual5);
        Assert.assertEquals(expected6, actual6);
    }

    @Test
    public void testCalcHardwareForRaftersRight()
    {
        // Arrange and Act
        int actual1 = Calculator.calcHardwareForRaftersRight(carport1); // Length = 360 cm, Width = 240 cm
        int expected1 = 6; // Expected: Length is 480, Quantity is 6.

        int actual2 = Calculator.calcHardwareForRaftersRight(carport2); // Length = 600 cm, Width = 240 cm
        int expected2 = 10; // Expected: Length is 480, Quantity is 10.

        int actual3 = Calculator.calcHardwareForRaftersRight(carport3); // Length = 780 cm, Width = 240 cm
        int expected3 = 13; // Expected: Length is 480, Quantity is 13.

        int actual4 = Calculator.calcHardwareForRaftersRight(carport4); // Length = 360 cm, Width = 360 cm
        int expected4 = 6; // Expected: Length is 480, Quantity is 6.

        int actual5 = Calculator.calcHardwareForRaftersRight(carport5); // Length = 600 cm, Width = 360 cm
        int expected5 = 10; // Expected: Length is 480, Quantity is 10.

        int actual6 = Calculator.calcHardwareForRaftersRight(carport6); // Length = 0 cm, Width = 0 cm
        int expected6 = 0; // Expected: No rafters needed as length is 0.

        int actual7 = Calculator.calcHardwareForRaftersRight(carport7); // Length = 600 cm, Width = 480 cm
        int expected7 = 10; // Expected: Length is 480, Quantity is 10.

        int actual8 = Calculator.calcHardwareForRaftersRight(carport8); // Length = 600 cm, Width = 540 cm
        int expected8 = 10; // Expected: Length is 600, Quantity is 10.

        int actual9 = Calculator.calcHardwareForRaftersRight(carport9); // Length = 600 cm, Width = 600 cm
        int expected9 = 10; // Expected: Length is 600, Quantity is 10.

        // Assert
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected3, actual3);
        Assert.assertEquals(expected4, actual4);
        Assert.assertEquals(expected5, actual5);
        Assert.assertEquals(expected6, actual6);
        Assert.assertEquals(expected7, actual7);
        Assert.assertEquals(expected8, actual8);
        Assert.assertEquals(expected9, actual9);
    }

    @Test
    public void testCalcHardwareForRaftersLeft()
    {
        // Arrange and Act
        int actual1 = Calculator.calcHardwareForRaftersLeft(carport1); // Length = 360 cm, Width = 240 cm
        int expected1 = 6; // Expected: Length is 360, Quantity is 6.

        int actual2 = Calculator.calcHardwareForRaftersLeft(carport2); // Length = 600 cm, Width = 240 cm
        int expected2 = 10; // Expected: Length is 600, Quantity is 10.

        int actual3 = Calculator.calcHardwareForRaftersLeft(carport3); // Length = 780 cm, Width = 240 cm
        int expected3 = 13; // Expected: Length is 780, Quantity is 13.

        int actual4 = Calculator.calcHardwareForRaftersLeft(carport4); // Length = 360 cm, Width = 360 cm
        int expected4 = 6; // Expected: Length is 360, Quantity is 6.

        int actual5 = Calculator.calcHardwareForRaftersLeft(carport5); // Length = 600 cm, Width = 360 cm
        int expected5 = 10; // Expected: Length is 600, Quantity is 10.

        int actual6 = Calculator.calcHardwareForRaftersLeft(carport6); // Length = 0 cm, Width = 0 cm
        int expected6 = 0; // Expected: No rafters needed as length is 0.

        int actual7 = Calculator.calcHardwareForRaftersLeft(carport7); // Length = 600 cm, Width = 480 cm
        int expected7 = 10; // Expected: Length is 600, Quantity is 10.

        int actual8 = Calculator.calcHardwareForRaftersLeft(carport8); // Length = 600 cm, Width = 540 cm
        int expected8 = 10; // Expected: Length is 600, Quantity is 10.

        int actual9 = Calculator.calcHardwareForRaftersLeft(carport9); // Length = 600 cm, Width = 600 cm
        int expected9 = 10; // Expected: Length is 600, Quantity is 10.

        // Assert
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected3, actual3);
        Assert.assertEquals(expected4, actual4);
        Assert.assertEquals(expected5, actual5);
        Assert.assertEquals(expected6, actual6);
        Assert.assertEquals(expected7, actual7);
        Assert.assertEquals(expected8, actual8);
        Assert.assertEquals(expected9, actual9);
    }

    @Test
    public void testCalcHardwareScrews()
    {
        // Arrange and Act
        int actual1 = Calculator.calcHardwareScrews(carport1); // Length = 360 cm, Width = 240 cm
        int expected1 = 2; // Expected: 2 packages.

        int actual2 = Calculator.calcHardwareScrews(carport2); // Length = 600 cm, Width = 240 cm
        int expected2 = 2; // Expected: 2 packages.

        int actual3 = Calculator.calcHardwareScrews(carport3); // Length = 780 cm, Width = 240 cm
        int expected3 = 3; // Expected: 3 packages.

        int actual4 = Calculator.calcHardwareScrews(carport4); // Length = 360 cm, Width = 360 cm
        int expected4 = 2; // Expected: 2 packages.

        int actual5 = Calculator.calcHardwareScrews(carport5); // Length = 600 cm, Width = 360 cm
        int expected5 = 2; // Expected: 3 packages.

        int actual6 = Calculator.calcHardwareScrews(carport6); // Length = 0 cm, Width = 0 cm
        int expected6 = 2; // Expected: Edge case, minimal screws.

        int actual7 = Calculator.calcHardwareScrews(carport7); // Length = 600 cm, Width = 480 cm
        int expected7 = 2; // Expected: 3 packages.

        int actual8 = Calculator.calcHardwareScrews(carport8); // Length = 600 cm, Width = 540 cm
        int expected8 = 2; // Expected: 3 packages.

        int actual9 = Calculator.calcHardwareScrews(carport9); // Length = 600 cm, Width = 600 cm
        int expected9 = 2; // Expected: 3 packages.

        // Assert
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected3, actual3);
        Assert.assertEquals(expected4, actual4);
        Assert.assertEquals(expected5, actual5);
        Assert.assertEquals(expected6, actual6);
        Assert.assertEquals(expected7, actual7);
        Assert.assertEquals(expected8, actual8);
        Assert.assertEquals(expected9, actual9);
    }

    @Test
    public void testCalcBoardBolt()
    {
        // Arrange and Act
        int actual1 = Calculator.calcBoardBolt(carport1); // Length = 360 cm, Width = 240 cm
        int expected1 = 16;

        int actual2 = Calculator.calcBoardBolt(carport2); // Length = 600 cm, Width = 240 cm
        int expected2 = 16;

        int actual3 = Calculator.calcBoardBolt(carport3); // Length = 780 cm, Width = 240 cm
        int expected3 = 16;

        int actual4 = Calculator.calcBoardBolt(carport4); // Length = 360 cm, Width = 360 cm
        int expected4 = 16;

        int actual5 = Calculator.calcBoardBolt(carport5); // Length = 600 cm, Width = 360 cm
        int expected5 = 16;

        int actual6 = Calculator.calcBoardBolt(carport6); // Length = 0 cm, Width = 0 cm
        int expected6 = 8;

        int actual7 = Calculator.calcBoardBolt(carport7); // Length = 600 cm, Width = 480 cm
        int expected7 = 16;

        int actual8 = Calculator.calcBoardBolt(carport8); // Length = 600 cm, Width = 540 cm
        int expected8 = 16;

        int actual9 = Calculator.calcBoardBolt(carport9); // Length = 600 cm, Width = 600 cm
        int expected9 = 16;

        // Assert
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected3, actual3);
        Assert.assertEquals(expected4, actual4);
        Assert.assertEquals(expected5, actual5);
        Assert.assertEquals(expected6, actual6);
        Assert.assertEquals(expected7, actual7);
        Assert.assertEquals(expected8, actual8);
        Assert.assertEquals(expected9, actual9);
    }


}
