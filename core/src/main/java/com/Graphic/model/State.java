package com.Graphic.model;

import java.util.Objects;

public class State {
    public int x,y;
    public int direction;
    public int Energy;

    public State(int x, int y, int direction, int Energy) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.Energy = Energy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof State)) {
            return false;
        }
        State state = (State) obj;
        return x == state.x && y == state.y && direction == state.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, direction);
    }
}
