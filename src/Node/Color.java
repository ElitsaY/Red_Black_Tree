package Node;

import Node.exception.InvalidColorException;

public enum Color {
    Red(0),
    Black(1),
    DoubleBlack(2);

    private int value;
    Color(int value) {
        this.value = value;
    }

    private static Color of(int value) {
        for (Color c : values()) {
            if (c.value == value) {
                return c;
            }
        }
        throw new InvalidColorException("invalid value " + value);
    }
    public Color addValue(Color other) {
        return of(value + other.value);
    }
}
