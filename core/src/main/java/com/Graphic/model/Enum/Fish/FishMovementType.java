package com.Graphic.model.Enum.Fish;

import com.Graphic.model.HelpersClass.TextureManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public enum FishMovementType {
    Mixed {
        @Override
        public void updateMovement(FishAIController controller, float delta) {
            int choice = MathUtils.random(2); // 0: up, 1: down, 2: stay
            int dy = 0;
            if (choice == 0) dy = 5;
            else if (choice == 1) dy = -5;
            else dy = 0;
            controller.setVelocity(new Vector2(0, dy));
        }
    },
    Smooth {
        @Override
        public void updateMovement(FishAIController controller, float delta) {
            Vector2 prev = controller.getVelocity();
            int choice = MathUtils.random(4);
            int dy;

            if (choice < 3) { // 75% احتمال ادامه‌ی حرکت قبلی
                dy = (int) prev.y;
            } else { // 25% تغییر جهت
                dy = MathUtils.randomSign() * 5;
            }
            controller.setVelocity(new Vector2(0, dy));
        }
    },
    Sinker {
        @Override
        public void updateMovement(FishAIController controller, float delta) {
            int choice = MathUtils.random(4); // 0: up, 1-2: stay, 3: down
            int dy;
            if (choice == 0) dy = 5;
            else if (choice == 3) dy = -10; // سقوط بیشتر
            else dy = 0;
            controller.setVelocity(new Vector2(0, dy));
        }
    },
    Floater {
        @Override
        public void updateMovement(FishAIController controller, float delta) {
            int choice = MathUtils.random(4);
            int dy;
            if (choice == 0) dy = -5;
            else if (choice == 3) dy = 10; // بالا رفتن بیشتر
            else dy = 0;
            controller.setVelocity(new Vector2(0, dy));
        }
    },
    Dart {
        @Override
        public void updateMovement(FishAIController controller, float delta) {
            int choice = MathUtils.random(2);
            int dy = 0;
            if (choice == 0) dy = 9;
            else if (choice == 1) dy = -9;
            controller.setVelocity(new Vector2(0, dy));
        }
    };


    public abstract void updateMovement(FishAIController controller, float delta);
}
