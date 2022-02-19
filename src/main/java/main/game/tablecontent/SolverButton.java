package main.game.tablecontent;

import main.engine.display.Renderer;
import main.engine.display.Window;
import main.engine.structures.Button;
import main.engine.structures.drawable.Drawable;
import main.engine.structures.GameObject;
import main.engine.structures.Scene;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.game.GameConstants;

import java.util.LinkedList;
import java.util.List;

import static main.game.GameConstants.*;

public class SolverButton extends Button {
    public static final int DEFAULT_SOLVER_BUTTON_WIDTH = 150;
    public static final int DEFAULT_SOLVER_BUTTON_HEIGHT = 80;
    public static final int DEFAULT_SOLVER_BUTTON_X = Hand.CARD_SPACE;
    public static final int DEFAULT_SOLVER_BUTTON_Y = Window.HEIGHT - Hand.CARD_SPACE - DEFAULT_SOLVER_BUTTON_HEIGHT;

    public SolverButton(GameObject parent) {
        super(DEFAULT_SOLVER_BUTTON_X, DEFAULT_SOLVER_BUTTON_Y,
                DEFAULT_SOLVER_BUTTON_WIDTH, DEFAULT_SOLVER_BUTTON_HEIGHT, parent);
        initializeSpriteList();
    }

    private void initializeSpriteList()
    {
        spriteList.add(new Rectangle(0, 0, w, h, CYAN, BROWN, 1));
        spriteList.add(new Text("calculate", 16, 22, DEFAULT_FONT_SIZE, GRAY, 1));
    }

    @Override
    public void onClick()
    {
        toProcess = true;
    }

    @Override
    public void onDoubleClick() {

    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onHold() {

    }

    @Override
    public void onHover() {
        highlighted = true;
    }

    @Override
    public void nonHover() {
        highlighted = false;
    }

}
