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

    public void render(Renderer r) {
        /*r.drawRectangle(x + 2, y + 2, w - 4, h - 4, BROWN, 1, id);
        r.drawRectangle(x, y, w, h, CYAN, 1, id);
        r.drawText("calculate", x + 16, y + 22, GRAY, DEFAULT_FONT_SIZE, 1, id);*/
        hoveredRender(r);
    }

    private void initializeSpriteList()
    {
        spriteList.add(new Rectangle(w, h, CYAN, BROWN, 1));
        spriteList.add(new Text("calculate", DEFAULT_FONT_SIZE, GRAY, 1));
    }

    @Override
    public void onClick(Scene state) {
        ((Table)(state)).getSolver().initialize(((Table)(state)));
    }

    @Override
    public void onDoubleClick(Scene state) {

    }

    @Override
    public void onRelease(Scene state) {

    }

    @Override
    public void onHold(Scene state) {

    }

    @Override
    public void onHover(Scene state) {
        highlighted = true;
    }

    @Override
    public void nonHover(Scene state) {
        highlighted = false;
    }

}
