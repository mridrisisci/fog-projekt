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
    Carport carport1 = new Carport(1, false, 360, 240);
    Carport carport2 = new Carport(1, false, 600, 240);
    Carport carport3 = new Carport(1, false, 780, 240);
    Carport carport4 = new Carport(1, false, 360, 360);
    Carport carport5 = new Carport(1, false, 600, 360);
    Carport carport6 = new Carport(1, false, 0, 0);
    Material material1 = new Material(1, "test_name1", "test_description1", 50, "Stk", 1, 20, 20, 20, false, 0);
    Material material2 = new Material(2, "test_name2", "test_description2", 25, "Stk", 1, 20, 20, 20, false, 0);
    Material material3 = new Material(3, "test_name3", "test_description3", 100, "Kg", 2, 15, 15, 15, true, 5);
    Material material4 = new Material(4, "test_name4", "test_description4", 75, "L", 3, 30, 30, 30, false, 10);
    Material material5 = new Material(5, "test_name5", "test_description5", 60, "Stk", 2, 10, 10, 10, true, 8);
    Material material6 = new Material(6, "test_name6", "test_description6", 45, "m", 1, 25, 25, 25, false, 12);
    Material material7 = new Material(7, "test_name7", "test_description7", 80, "Kg", 3, 35, 35, 35, true, 0);
    Material material8 = new Material(8, "test_name8", "test_description8", 55, "L", 4, 40, 40, 40, false, 3);
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



}
