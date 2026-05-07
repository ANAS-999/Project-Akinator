package com.ensa.akinator.Models;

public enum AnswerEnum {
    YES(1),
    PROBABLY(2),
    PROBABLY_NOT(3),
    DONT_KNOW(4),
    NO(0);

    private final int value;

    AnswerEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
