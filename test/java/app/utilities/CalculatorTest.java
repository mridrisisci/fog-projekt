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
    Carport carport11 = new Carport(1, 330, 360);

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
        int expected1 = 4;

        int actual2 = Calculator.calcPosts(carport2); // Length = 600 cm
        int expected2 = 6;

        int actual3 = Calculator.calcPosts(carport3); // Length = 780 cm
        int expected3 = 6;

        int actual4 = Calculator.calcPosts(carport4); // Length = 360 cm
        int expected4 = 4;

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
        int[] expected1 = {7, 480}; // Expected: Length is 480, Quantity is 7.

        int[] actual2 = Calculator.calcRafters(carport2); // Length = 600 cm, Width = 240 cm
        int[] expected2 = {11, 480}; // Expected: Length is 480, Quantity is 11.

        int[] actual3 = Calculator.calcRafters(carport3); // Length = 780 cm, Width = 240 cm
        int[] expected3 = {14, 480}; // Expected: Length is 480, Quantity is 14.

        int[] actual4 = Calculator.calcRafters(carport4); // Length = 360 cm, Width = 360 cm
        int[] expected4 = {7, 480}; // Expected: Length is 480, Quantity is 7.

        int[] actual5 = Calculator.calcRafters(carport5); // Length = 600 cm, Width = 360 cm
        int[] expected5 = {11, 480}; // Expected: Length is 480, Quantity is 11.

        int[] actual6 = Calculator.calcRafters(carport6); // Length = 0 cm, Width = 0 cm
        int[] expected6 = {1, 480}; // Expected: No rafters needed as length is 1.

        int[] actual7 = Calculator.calcRafters(carport7); // Length = 600 cm, Width = 480 cm
        int[] expected7 = {11, 480}; // Expected: Length is 480, Quantity is 11.

        int[] actual8 = Calculator.calcRafters(carport8); // Length = 600 cm, Width = 540 cm
        int[] expected8 = {11, 600}; // Expected: Length is 600, Quantity is 11.

        int[] actual9 = Calculator.calcRafters(carport9); // Length = 600 cm, Width = 600 cm
        int[] expected9 = {11, 600}; // Expected: Length is 600, Quantity is 11.

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
        int expected1 = 6;

        int actual2 = Calculator.calcSquareWasher(carport2); // Length = 600 cm
        int expected2 = 8;

        int actual3 = Calculator.calcSquareWasher(carport3); // Length = 780 cm
        int expected3 = 8;

        int actual4 = Calculator.calcSquareWasher(carport4); // Length = 360 cm
        int expected4 = 6;

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
        int expected1 = 7; // Expected: Length is 480, Quantity is 7.

        int actual2 = Calculator.calcHardwareForRaftersRight(carport2); // Length = 600 cm, Width = 240 cm
        int expected2 = 11; // Expected: Length is 480, Quantity is 11.

        int actual3 = Calculator.calcHardwareForRaftersRight(carport3); // Length = 780 cm, Width = 240 cm
        int expected3 = 14; // Expected: Length is 480, Quantity is 14.

        int actual4 = Calculator.calcHardwareForRaftersRight(carport4); // Length = 360 cm, Width = 360 cm
        int expected4 = 7; // Expected: Length is 480, Quantity is 7.

        int actual5 = Calculator.calcHardwareForRaftersRight(carport5); // Length = 600 cm, Width = 360 cm
        int expected5 = 11; // Expected: Length is 480, Quantity is 11.

        int actual6 = Calculator.calcHardwareForRaftersRight(carport6); // Length = 0 cm, Width = 0 cm
        int expected6 = 1; // Expected: No rafters needed as length is 1.

        int actual7 = Calculator.calcHardwareForRaftersRight(carport7); // Length = 600 cm, Width = 480 cm
        int expected7 = 11; // Expected: Length is 480, Quantity is 11.

        int actual8 = Calculator.calcHardwareForRaftersRight(carport8); // Length = 600 cm, Width = 540 cm
        int expected8 = 11; // Expected: Length is 600, Quantity is 11.

        int actual9 = Calculator.calcHardwareForRaftersRight(carport9); // Length = 600 cm, Width = 600 cm
        int expected9 = 11; // Expected: Length is 600, Quantity is 11.

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
        int expected1 = 7; // Expected: Length is 360, Quantity is 7.

        int actual2 = Calculator.calcHardwareForRaftersLeft(carport2); // Length = 600 cm, Width = 240 cm
        int expected2 = 11; // Expected: Length is 600, Quantity is 11.

        int actual3 = Calculator.calcHardwareForRaftersLeft(carport3); // Length = 780 cm, Width = 240 cm
        int expected3 = 14; // Expected: Length is 780, Quantity is 14.

        int actual4 = Calculator.calcHardwareForRaftersLeft(carport4); // Length = 360 cm, Width = 360 cm
        int expected4 = 7; // Expected: Length is 360, Quantity is 7.

        int actual5 = Calculator.calcHardwareForRaftersLeft(carport5); // Length = 600 cm, Width = 360 cm
        int expected5 = 11; // Expected: Length is 600, Quantity is 11.

        int actual6 = Calculator.calcHardwareForRaftersLeft(carport6); // Length = 0 cm, Width = 0 cm
        int expected6 = 1; // Expected: No rafters needed as length is 1.

        int actual7 = Calculator.calcHardwareForRaftersLeft(carport7); // Length = 600 cm, Width = 480 cm
        int expected7 = 11; // Expected: Length is 600, Quantity is 11.

        int actual8 = Calculator.calcHardwareForRaftersLeft(carport8); // Length = 600 cm, Width = 540 cm
        int expected8 = 11; // Expected: Length is 600, Quantity is 11.

        int actual9 = Calculator.calcHardwareForRaftersLeft(carport9); // Length = 600 cm, Width = 600 cm
        int expected9 = 11; // Expected: Length is 600, Quantity is 11.

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
        int expected1 = 8;

        int actual2 = Calculator.calcBoardBolt(carport2); // Length = 600 cm, Width = 240 cm
        int expected2 = 12;

        int actual3 = Calculator.calcBoardBolt(carport3); // Length = 780 cm, Width = 240 cm
        int expected3 = 16;

        int actual4 = Calculator.calcBoardBolt(carport4); // Length = 360 cm, Width = 360 cm
        int expected4 = 8;

        int actual5 = Calculator.calcBoardBolt(carport5); // Length = 600 cm, Width = 360 cm
        int expected5 = 12;

        int actual6 = Calculator.calcBoardBolt(carport6); // Length = 0 cm, Width = 0 cm
        int expected6 = 8;

        int actual7 = Calculator.calcBoardBolt(carport7); // Length = 600 cm, Width = 480 cm
        int expected7 = 12;

        int actual8 = Calculator.calcBoardBolt(carport8); // Length = 600 cm, Width = 540 cm
        int expected8 = 12;

        int actual9 = Calculator.calcBoardBolt(carport9); // Length = 600 cm, Width = 600 cm
        int expected9 = 12;

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

    // carport med 4 stolper
    @Test
    public void testCalcPostsXYCarport1()
    {
        // Arrange
        int quantity = Calculator.calcPosts(carport1);

        // post 1
        int expectedX1 = 90;
        int expectedY1 = 15;

        // post 2
        int expectedX2 = 90;
        int expectedY2 = 215;

        // post 3
        int expectedX3 = 270;
        int expectedY3 = 15;

        // post 4
        int expectedX4 = 270;
        int expectedY4 = 215;

        // Act
        int[] actual1 = Calculator.calcPostsXY(carport1, quantity, 0);
        int actualX1 = actual1[0];
        int actualY1 = actual1[1];

        int[] actual2 = Calculator.calcPostsXY(carport1, quantity, 1);
        int actualX2 = actual2[0];
        int actualY2 = actual2[1];

        int[] actual3 = Calculator.calcPostsXY(carport1, quantity, 2);
        int actualX3 = actual3[0];
        int actualY3 = actual3[1];

        int[] actual4 = Calculator.calcPostsXY(carport1, quantity, 3);
        int actualX4 = actual4[0];
        int actualY4 = actual4[1];

        // Assert
        Assert.assertEquals(expectedX1, actualX1);
        Assert.assertEquals(expectedY1, actualY1);
        Assert.assertEquals(expectedX2, actualX2);
        Assert.assertEquals(expectedY2, actualY2);
        Assert.assertEquals(expectedX3, actualX3);
        Assert.assertEquals(expectedY3, actualY3);
        Assert.assertEquals(expectedX4, actualX4);
        Assert.assertEquals(expectedY4, actualY4);

    }

    // carport med 6 stolper
    @Test
    public void testCalcPostsXYCarport2()
    {
        // Arrange
        int quantity = Calculator.calcPosts(carport2);

        // post 1
        int expectedX1 = 75;
        int expectedY1 = 15;

        // post 2
        int expectedX2 = 75;
        int expectedY2 = 215;

        // post 3
        int expectedX3 = 300;
        int expectedY3 = 15;

        // post 4
        int expectedX4 = 300;
        int expectedY4 = 215;

        // post 5
        int expectedX5 = 525;
        int expectedY5 = 15;

        // post 6
        int expectedX6 = 525;
        int expectedY6 = 215;

        // Act
        int[] actual1 = Calculator.calcPostsXY(carport2, quantity, 0);
        int actualX1 = actual1[0];
        int actualY1 = actual1[1];

        int[] actual2 = Calculator.calcPostsXY(carport2, quantity, 1);
        int actualX2 = actual2[0];
        int actualY2 = actual2[1];

        int[] actual3 = Calculator.calcPostsXY(carport2, quantity, 2);
        int actualX3 = actual3[0];
        int actualY3 = actual3[1];

        int[] actual4 = Calculator.calcPostsXY(carport2, quantity, 3);
        int actualX4 = actual4[0];
        int actualY4 = actual4[1];

        int[] actual5 = Calculator.calcPostsXY(carport2, quantity, 4);
        int actualX5 = actual5[0];
        int actualY5 = actual5[1];

        int[] actual6 = Calculator.calcPostsXY(carport2, quantity, 5);
        int actualX6 = actual6[0];
        int actualY6 = actual6[1];

        // Assert
        Assert.assertEquals(expectedX1, actualX1);
        Assert.assertEquals(expectedY1, actualY1);
        Assert.assertEquals(expectedX2, actualX2);
        Assert.assertEquals(expectedY2, actualY2);
        Assert.assertEquals(expectedX3, actualX3);
        Assert.assertEquals(expectedY3, actualY3);
        Assert.assertEquals(expectedX4, actualX4);
        Assert.assertEquals(expectedY4, actualY4);
        Assert.assertEquals(expectedX5, actualX5);
        Assert.assertEquals(expectedY5, actualY5);
        Assert.assertEquals(expectedX6, actualX6);
        Assert.assertEquals(expectedY6, actualY6);

    }

    // Carport som ikke rykker sidste spær ind
    @Test
    public void testCalcRaftersXY1()
    {
        //Arrange
        int expectedR1X1 = 0;
        int expectedR1X2 = 0;
        int expectedR2X1 = 60;
        int expectedR2X2 = 60;
        int expectedR3X1 = 120;
        int expectedR3X2 = 120;
        int expectedR4X1 = 180;
        int expectedR4X2 = 180;
        int expectedR5X1 = 240;
        int expectedR5X2 = 240;


        int expectedR1Y1 = 0;
        int expectedR1Y2 = 240;
        int expectedR2Y1 = 0;
        int expectedR2Y2 = 240;
        int expectedR3Y1 = 0;
        int expectedR3Y2 = 240;
        int expectedR4Y1 = 0;
        int expectedR4Y2 = 240;
        int expectedR5Y1 = 0;
        int expectedR5Y2 = 240;

        // Act

        ArrayList<Integer> actual = new ArrayList<>();

        for (int i = 0; i < 5; i++)
        {
            int[] actualRafters = Calculator.calcRaftersXY(carport1, 5, i);
            for (int j : actualRafters)
            {
                actual.add(j);
            }
        }

        // Assert
        int[] actualArray = actual.stream().mapToInt(Integer::intValue).toArray();

        Assert.assertEquals(expectedR1X1, actualArray[0]);
        Assert.assertEquals(expectedR1X2, actualArray[1]);
        Assert.assertEquals(expectedR1Y1, actualArray[2]);
        Assert.assertEquals(expectedR1Y2, actualArray[3]);

        Assert.assertEquals(expectedR2X1, actualArray[4]);
        Assert.assertEquals(expectedR2X2, actualArray[5]);
        Assert.assertEquals(expectedR2Y1, actualArray[6]);
        Assert.assertEquals(expectedR2Y2, actualArray[7]);

        Assert.assertEquals(expectedR3X1, actualArray[8]);
        Assert.assertEquals(expectedR3X2, actualArray[9]);
        Assert.assertEquals(expectedR3Y1, actualArray[10]);
        Assert.assertEquals(expectedR3Y2, actualArray[11]);

        Assert.assertEquals(expectedR4X1, actualArray[12]);
        Assert.assertEquals(expectedR4X2, actualArray[13]);
        Assert.assertEquals(expectedR4Y1, actualArray[14]);
        Assert.assertEquals(expectedR4Y2, actualArray[15]);

        Assert.assertEquals(expectedR5X1, actualArray[16]);
        Assert.assertEquals(expectedR5X2, actualArray[17]);
        Assert.assertEquals(expectedR5Y1, actualArray[18]);
        Assert.assertEquals(expectedR5Y2, actualArray[19]);
    }

    //Test for at det sidste spær bliver rykket
    @Test
    public void testCalcRaftersXY2()
    {
        //Arrange
        int expectedR1X1 = 0;
        int expectedR1X2 = 0;
        int expectedR2X1 = 60;
        int expectedR2X2 = 60;
        int expectedR3X1 = 120;
        int expectedR3X2 = 120;
        int expectedR4X1 = 180;
        int expectedR4X2 = 180;
        int expectedR5X1 = 240;
        int expectedR5X2 = 240;
        int expectedR6X1 = 300;
        int expectedR6X2 = 300;
        int expectedR7X1 = 330;
        int expectedR7X2 = 330;

        int expectedR1Y1 = 0;
        int expectedR1Y2 = 360;
        int expectedR2Y1 = 0;
        int expectedR2Y2 = 360;
        int expectedR3Y1 = 0;
        int expectedR3Y2 = 360;
        int expectedR4Y1 = 0;
        int expectedR4Y2 = 360;
        int expectedR5Y1 = 0;
        int expectedR5Y2 = 360;
        int expectedR6Y1 = 0;
        int expectedR6Y2 = 360;
        int expectedR7Y1 = 0;
        int expectedR7Y2 = 360;

        // Act

        ArrayList<Integer> actual = new ArrayList<>();

        for (int i = 0; i < 7; i++)
        {
            int[] actualRafters = Calculator.calcRaftersXY(carport11, 7, i);
            for (int j : actualRafters)
            {
                actual.add(j);
            }
        }

        // Assert
        int[] actualArray = actual.stream().mapToInt(Integer::intValue).toArray();

        Assert.assertEquals(expectedR1X1, actualArray[0]);
        Assert.assertEquals(expectedR1X2, actualArray[1]);
        Assert.assertEquals(expectedR1Y1, actualArray[2]);
        Assert.assertEquals(expectedR1Y2, actualArray[3]);

        Assert.assertEquals(expectedR2X1, actualArray[4]);
        Assert.assertEquals(expectedR2X2, actualArray[5]);
        Assert.assertEquals(expectedR2Y1, actualArray[6]);
        Assert.assertEquals(expectedR2Y2, actualArray[7]);

        Assert.assertEquals(expectedR3X1, actualArray[8]);
        Assert.assertEquals(expectedR3X2, actualArray[9]);
        Assert.assertEquals(expectedR3Y1, actualArray[10]);
        Assert.assertEquals(expectedR3Y2, actualArray[11]);

        Assert.assertEquals(expectedR4X1, actualArray[12]);
        Assert.assertEquals(expectedR4X2, actualArray[13]);
        Assert.assertEquals(expectedR4Y1, actualArray[14]);
        Assert.assertEquals(expectedR4Y2, actualArray[15]);

        Assert.assertEquals(expectedR5X1, actualArray[16]);
        Assert.assertEquals(expectedR5X2, actualArray[17]);
        Assert.assertEquals(expectedR5Y1, actualArray[18]);
        Assert.assertEquals(expectedR5Y2, actualArray[19]);

        Assert.assertEquals(expectedR6X1, actualArray[20]);
        Assert.assertEquals(expectedR6X2, actualArray[21]);
        Assert.assertEquals(expectedR6Y1, actualArray[22]);
        Assert.assertEquals(expectedR6Y2, actualArray[23]);

        Assert.assertEquals(expectedR7X1, actualArray[24]);
        Assert.assertEquals(expectedR7X2, actualArray[25]);
        Assert.assertEquals(expectedR7Y1, actualArray[26]);
        Assert.assertEquals(expectedR7Y2, actualArray[27]);
    }

    //To remme
    @Test
    public void testCalcBeamsXY1()
    {
        //Arrange

        int quantity = Calculator.calcBeams(carport11)[0];
        int beamLength = Calculator.calcBeams(carport11)[1];
        int matLength = Calculator.calcBeamsXY(carport11, quantity,0, beamLength)[2];

        int expectedB1X1 = 0;
        int expectedB1Y1 = 15;
        int expectedB1X2 = 330;
        int expectedB1Y2 = 15;

        int expectedB2X1 = 0;
        int expectedB2Y1 = 335;
        int expectedB2X2 = 330;
        int expectedB2Y2 = 335;

        //Act
        int[] actualB1 = Calculator.calcBeamsXY(carport11, quantity, 0, matLength);
        int actualB1X1 = actualB1[0];
        int actualB1Y1 = actualB1[1];
        int actualB1X2 = actualB1[2];
        int actualB1Y2 = actualB1[3];

        int[] actualB2 = Calculator.calcBeamsXY(carport11, quantity, 1, matLength);
        int actualB2X1 = actualB2[0];
        int actualB2Y1 = actualB2[1];
        int actualB2X2 = actualB2[2];
        int actualB2Y2 = actualB2[3];

        //Assert

        Assert.assertEquals(expectedB1X1, actualB1X1);
        Assert.assertEquals(expectedB1X2, actualB1X2);
        Assert.assertEquals(expectedB1Y1, actualB1Y1);
        Assert.assertEquals(expectedB1Y2, actualB1Y2);

        Assert.assertEquals(expectedB2X1, actualB2X1);
        Assert.assertEquals(expectedB2X2, actualB2X2);
        Assert.assertEquals(expectedB2Y1, actualB2Y1);
        Assert.assertEquals(expectedB2Y2, actualB2Y2);

    }

    // Stor carport, hvor 4 lang rem bliver skåret over
    @Test
    public void testCalcBeamsXY2()
    {
        //Arrange

        int quantity = Calculator.calcBeams(carport3)[0];
        int beamLength = Calculator.calcBeams(carport3)[1];
        int matLength = Calculator.calcBeamsXY(carport3, quantity,0, beamLength)[2];

        int expectedB1X1 = 0;
        int expectedB1Y1 = 15; //Y aksen er hvor på bredden den ligger
        int expectedB1X2 = 330;
        int expectedB1Y2 = 15;

        int expectedB2X1 = 0;
        int expectedB2X2 = 330;
        int expectedB2Y1 = 335; //Y aksen er hvor på bredden den ligger
        int expectedB2Y2 = 335;

        //Act
        int[] actualB1 = Calculator.calcBeamsXY(carport3, quantity, 0, matLength);
        int actualB1X1 = actualB1[0];
        int actualB1Y1 = actualB1[1];
        int actualB1X2 = actualB1[2];
        int actualB1Y2 = actualB1[3];

        int[] actualB2 = Calculator.calcBeamsXY(carport3, quantity, 1, matLength);
        int actualB2X1 = actualB2[0];
        int actualB2Y1 = actualB2[1];
        int actualB2X2 = actualB2[2];
        int actualB2Y2 = actualB2[3];

        //Assert

        Assert.assertEquals(expectedB1X1, actualB1X1);
        Assert.assertEquals(expectedB1X2, actualB1X2);
        Assert.assertEquals(expectedB1Y1, actualB1Y1);
        Assert.assertEquals(expectedB1Y2, actualB1Y2);

        Assert.assertEquals(expectedB2X1, actualB2X1);
        Assert.assertEquals(expectedB2X2, actualB2X2);
        Assert.assertEquals(expectedB2Y1, actualB2Y1);
        Assert.assertEquals(expectedB2Y2, actualB2Y2);

    }

}
