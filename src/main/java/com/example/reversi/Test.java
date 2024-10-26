package com.example.reversi;

public class Test {
    public static <T> void notNull(T obj, String errorMessage) {
        assert obj!=null : errorMessage;
    }

    public static void outOfBounds(int obj, int temp1, int temp2, String errorMessage) {
        assert (temp1 < obj) : "Object " + obj + " is out of bounds of " + temp1 + ". " + errorMessage;
        assert (temp1 != obj) : "Object " + obj + " is equals " + temp1 + ". " + errorMessage;
        assert (temp2 > obj) : "Object " + obj + " is out of bounds of " + temp2 + ". " + errorMessage;
        assert (temp2 != obj) : "Object " + obj + " is equals " + temp2 + ". " + errorMessage;
    }
}
