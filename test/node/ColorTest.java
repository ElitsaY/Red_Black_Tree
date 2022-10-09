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

}