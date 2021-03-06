/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 christopher polanco
 */
package ucf.assignments;

import java.text.NumberFormat;

public class checker {

    public boolean isDigit(String value) {
        return ((value != null) && (!value.equals("")) && (value.matches("^[0-9*#+.]+$")));
    }

    public boolean isDigitAlpha(String serial) {
        return ((serial != null) && (!serial.equals("")) && (serial.matches("^[a-zA-Z0-9]+$")));
    }
}
