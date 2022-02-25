package main.engine.structures.gameObject;

import lombok.Getter;
import lombok.Setter;
import main.engine.Input;

@Getter
@Setter
public class Position {
    public int x;
    public int y;

    public Position() {
        this.x = 0;
        this.y = 0;
    }

    public Position(Position position) {
        this.x = position.getX();
        this.y = position.getY();
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position cursorPosition() {
        return new Position(Input.getInput().getMouseX(), Input.getInput().getMouseY());
    }

    public static int getPositionId(int x, int y, int width) {
        return x + y * width;
    }

    public int getPositionId(int width) {
        return x + y * width;
    }

    public Position getRescaledPosition(Scale scale) {
        int rescaledX = (int)(x * scale.x);
        int rescaledY = (int)(y * scale.y);
        return new Position(rescaledX, rescaledY);
    }

    public static Position getRescaledPosition(Position pos, Scale scale) {
        int rescaledX = (int)(pos.x * scale.x);
        int rescaledY = (int)(pos.y * scale.y);
        return new Position(rescaledX, rescaledY);
    }

    public void incX(int x) {
        this.x += x;
    }

    public void incY(int y) {
        this.y += y;
    }
}
