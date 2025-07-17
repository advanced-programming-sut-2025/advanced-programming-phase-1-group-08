package com.Graphic.model.HelpersClass;

public record Result(boolean IsSuccess, String massage) {

    @Override
    public String toString() {
        return massage;
    }

    public String getMessage() {
        return "sa";
    }
}
