package com.Graphic.model.HelpersClass;

public enum EffectType {


    DEATH("death/death", 6, 0.5f),; // این مثاله اینشکلی اد کنین     اول ادرسه که یه پوشه براش میزنم بعدی هم تعداد عکسا و بعدیشم سرعت

    public final String basePath;
    public final int frameCount;
    public final float speed;

    EffectType(String basePath, int frameCount, float speed) {
        this.basePath = basePath;
        this.frameCount = frameCount;
        this.speed = speed;
    }
}
