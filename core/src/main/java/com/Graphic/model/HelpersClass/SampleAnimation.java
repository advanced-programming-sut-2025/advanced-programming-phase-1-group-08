package com.Graphic.model.HelpersClass;

public enum SampleAnimation {

    Bat ("Bat/main",13,10,4),
    Star ("Star/Star",13,13,4),
    Heart ("Heart/Heart",9,9,6),
    Pillow ("Pillow/Pillow",11,12,6),
    Crystal ("Crystal/Crystal",13,13,4),
    Pyramid ("Pyramid/Pyramid",13,13,4),
    Skeleton ("Skeleton/Skeleton",13,13,4),
    QuestionMark ("QuestionMark/QuestionMark",7,13,6),
    Rain ("rain.png", 16, 16, 4)


    ;



    private final String path;
    private final int width;
    private final int height;
    private final int frameNumber;

    SampleAnimation(String path, int width, int height, int frameNumber) {
        this.path = path;
        this.width = width;
        this.height = height;
        this.frameNumber = frameNumber;
    }



    public String getPath() {
        return "Erfan/Beauty/" + path + ".png";
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getFrameNumber() {
        return frameNumber;
    }
}
