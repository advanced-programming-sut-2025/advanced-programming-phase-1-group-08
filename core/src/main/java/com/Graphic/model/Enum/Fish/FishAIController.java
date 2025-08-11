package com.Graphic.model.Enum.Fish;

import com.badlogic.gdx.math.Vector2;

public class FishAIController {
    private FishMovementType movementType;
    private Vector2 position;
    private Vector2 velocity = new Vector2(0, 0);

    private float timer = 0;
    private float updateInterval = 0.5f;

    private float minY, maxY;

    public FishAIController(FishMovementType type, Vector2 initialPosition, float minY, float maxY) {
        this.movementType = type;
        this.position = initialPosition;
        this.minY = minY;
        this.maxY = maxY;
    }

    public void update(float delta) {
        timer += delta;
        if (timer >= updateInterval) {
            timer -= updateInterval;
            movementType.updateMovement(this, delta);
        }

        position.y += velocity.y;
        clampPosition();
    }

    private void clampPosition() {
        if (position.y < minY) position.y = minY;
        if (position.y > maxY) position.y = maxY;
    }

    // getters and setters
    public Vector2 getPosition() { return position; }
    public void setPosition(Vector2 pos) { this.position = pos; }

    public Vector2 getVelocity() { return velocity; }
    public void setVelocity(Vector2 velocity) { this.velocity = velocity; }

    public FishMovementType getMovementType() { return movementType; }
    public void setMovementType(FishMovementType movementType) { this.movementType = movementType; }
}
