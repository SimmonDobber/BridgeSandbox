package main.game.solver;

import main.engine.display.Renderer;
import main.engine.display.Window;
import main.engine.structures.Button;
import main.engine.structures.State;
import main.game.GameConstants;
import main.game.tablecontent.Hand;
import main.game.tablecontent.Table;

public class SolverButton extends Button
{
    public static final int DEFAULT_SOLVER_BUTTON_WIDTH = 150;
    public static final int DEFAULT_SOLVER_BUTTON_HEIGHT = 80;
    public static final int DEFAULT_SOLVER_BUTTON_X = Hand.CARD_SPACE;
    public static final int DEFAULT_SOLVER_BUTTON_Y = Window.HEIGHT - Hand.CARD_SPACE - DEFAULT_SOLVER_BUTTON_HEIGHT;

    public SolverButton(int x, int y, int w, int h, int stateCount, int fixed) {
        super(x, y, w, h, stateCount, fixed);
    }

    public SolverButton() {
        super(DEFAULT_SOLVER_BUTTON_X, DEFAULT_SOLVER_BUTTON_Y, DEFAULT_SOLVER_BUTTON_WIDTH, DEFAULT_SOLVER_BUTTON_HEIGHT, 1, 1);
    }

    public void render(Renderer r)
    {
        r.drawRectangle(x + 2, y + 2, w - 4, h - 4, GameConstants.BROWN, 1, buttonId);
        r.drawRectangle(x, y, w, h, GameConstants.CYAN, 1, buttonId);
        r.drawText("calculate", x + 16, y + 22, GameConstants.GRAY, GameConstants.DEFAULT_FONT_SIZE, 1, buttonId);
        hoveredRender(r);
    }

    private void hoveredRender(Renderer r)
    {
        if(highlighted)
            r.drawRectangle(x + 2, y + 2, w - 4, h - 4, 0x220000FF, img.getFixed(), buttonId);
    }

    @Override
    public void onClick(State state) {
        ((Table)(state)).getSolver().initialize(((Table)(state)));
    }

    @Override
    public void onDoubleClick(State state) {

    }

    @Override
    public void onRelease(State state) {

    }

    @Override
    public void onHold(State state) {

    }

    @Override
    public void onHover(State state) {
        highlighted = true;
    }

    @Override
    public void nonHover(State state) {
        highlighted = false;
    }

}
