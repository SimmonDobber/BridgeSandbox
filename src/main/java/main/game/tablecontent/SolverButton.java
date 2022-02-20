package main.game.tablecontent;

import main.engine.display.Window;
import main.engine.structures.Button;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.game.solver.Solver;

import static main.game.GameConstants.*;

public class SolverButton extends Button {
    public static final int DEFAULT_SOLVER_BUTTON_WIDTH = 150;
    public static final int DEFAULT_SOLVER_BUTTON_HEIGHT = 80;
    public static final int DEFAULT_SOLVER_BUTTON_X = Hand.CARD_SPACE;
    public static final int DEFAULT_SOLVER_BUTTON_Y = Window.HEIGHT - Hand.CARD_SPACE - DEFAULT_SOLVER_BUTTON_HEIGHT;
    private Solver solver;

    public SolverButton(GameObject parent, Solver solver) {
        super(DEFAULT_SOLVER_BUTTON_X, DEFAULT_SOLVER_BUTTON_Y,
                DEFAULT_SOLVER_BUTTON_WIDTH, DEFAULT_SOLVER_BUTTON_HEIGHT, parent);
        initializeSpriteList();
        this.solver = solver;
    }

    private void initializeSpriteList()
    {
        spriteList.add(new Rectangle(0, 0, w, h, CYAN, BROWN, 1));
        spriteList.add(new Text("calculate", 16, 22, DEFAULT_FONT_SIZE, GRAY, 1));
    }

    @Override
    public void onClick()
    {
        solver.initialize((Table)(getParent()));
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onHold() {

    }

    @Override
    public void onHover() {
        hovered = true;
    }

    @Override
    public void nonHover() {
        hovered = false;
    }
}
