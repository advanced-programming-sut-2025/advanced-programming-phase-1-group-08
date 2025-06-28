package com.Graphic.model;

public record Result(boolean IsSuccess, String massage) {

    @Override
    public String toString() {
        return massage;
    }
}
