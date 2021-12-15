package com.nempyxa.mathtr.model.math;

import androidx.annotation.NonNull;

public enum Operation {
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/");

    private final String stringValue;

    Operation(String s) {
        stringValue = s;
    }

    public static Operation of(String string) {
        return valueOf(string);
    }

    public String getStringValue() {
        return stringValue;
    }
}
