package main.game.table.buttons;

import main.engine.structures.button.Button;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observer;

import java.util.LinkedList;

import static main.game.GameConstants.*;

public class SolverButton extends Button
{
    private static final int DEFAULT_SOLVER_BUTTON_WIDTH = 150;
    private static final int DEFAULT_SOLVER_BUTTON_HEIGHT = 80;
    private static final int DEFAULT_SOLVER_BUTTON_X = 24;
    private static final int DEFAULT_SOLVER_BUTTON_Y = 546;

    public SolverButton(GameObject parent) {
        super(new Position(DEFAULT_SOLVER_BUTTON_X, DEFAULT_SOLVER_BUTTON_Y),
                new Dimensions(DEFAULT_SOLVER_BUTTON_WIDTH, DEFAULT_SOLVER_BUTTON_HEIGHT), parent);
        initializeSpriteList();
        hovered = false;
    }

    private void initializeSpriteList()
    {
        spriteList.add(new Rectangle(new Position(), dim, CYAN, BROWN, 1));
        spriteList.add(new Text("calculate", new Position(16, 22), DEFAULT_FONT_SIZE, GRAY, 1));
    }

    @Override
    public void notifyObservers()
    {
        for(int i = 0; i < observers.size(); i++)
            observers.get(i).update(null, null);
    }

    @Override
    public void onClick()
    {
        notifyObservers();
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onHold() {

    }
}
