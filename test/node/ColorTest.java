package node;

import node.exception.InvalidColorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorTest {

    @Test
    void addValue_AddRedAndBlack_Black() {
        //Arrange
        Color c = Color.Red;

        //Act, Assert
        assertEquals(Color.Black, c.addValue(Color.Black), "red+black should be black");
    }

    @Test
    void addValue_AddBlackAndRed_Black() {
        //Arrange
        Color c = Color.Black;

        //Act, Assert
        assertEquals(Color.Black, c.addValue(Color.Red), "black+red should be black");
    }

    @Test
    void addValue_AddRedAndRed_Red() {
        //Arrange
        Color c = Color.Red;

        //Act, Assert
        assertEquals(Color.Red, c.addValue(Color.Red), "red+red should be red");
    }

    @Test
    void addValue_AddBlackAndBlack_DoubleBlack() {
        //Arrange
        Color c = Color.Black;

        //Act, Assert
        assertEquals(Color.DoubleBlack, c.addValue(Color.Black), "black+black should be double black");
    }

    @Test
    void addValue_AddDoubleBlackAndBlack_Throws() {
        //Arrange
        Color c = Color.DoubleBlack;

        //Act, Assert
        assertThrows(InvalidColorException.class, () -> c.addValue(Color.Black));
    }

    @Test
    void nextColor_Red_ReturnsBlack() {
        assertEquals(Color.Black, Color.Red.nextColor());
    }

    @Test
    void nextColor_Black_ReturnsDoubleBlack() {
        assertEquals(Color.DoubleBlack, Color.Black.nextColor());
    }

    @Test
    void prevColor_Black_ReturnsRed() {
        assertEquals(Color.Red, Color.Black.prevColor());
    }

    @Test
    void prevColor_DoubleBlack_ReturnsBlack() {
        assertEquals(Color.Black, Color.DoubleBlack.prevColor());
    }

    @Test
    void of_Value0_Red() {
        assertEquals(Color.Red, Color.of(0));
    }

    @Test
    void of_Value1_Black() {
        assertEquals(Color.Black, Color.of(1));
    }

    @Test
    void of_Value2_DoubleBlack() {
        assertEquals(Color.DoubleBlack, Color.of(2));
    }
}