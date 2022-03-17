package main.game.buttons;

import main.engine.ProgramContainer;
import main.engine.display.renderer.Renderer;
import main.engine.structures.button.Button;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observable;
import main.engine.structures.observer.Observer;
import main.game.table.Table;

import static main.game.GameConstants.*;

public class SolverButton extends Button
{
    private static final int DEFAULT_SOLVER_BUTTON_WIDTH = 150;
    private static final int DEFAULT_SOLVER_BUTTON_HEIGHT = 80;
    private static final int DEFAULT_SOLVER_BUTTON_X = 24;
    private static final int DEFAULT_SOLVER_BUTTON_Y = 546;
    private boolean active;

    public SolverButton(GameObject parent) {
        super(new Position(DEFAULT_SOLVER_BUTTON_X, DEFAULT_SOLVER_BUTTON_Y),
                new Dimensions(DEFAULT_SOLVER_BUTTON_WIDTH, DEFAULT_SOLVER_BUTTON_HEIGHT), parent);
        initializeSpriteList();
        active = false;
    }

    private void initializeSpriteList() {
        spriteList.add(new Rectangle(new Position(), dim, CYAN, BROWN, 1));
        spriteList.add(new Text("calculate", new Position(16, 22), DEFAULT_FONT_SIZE, GRAY, 1));
    }

    public void update(Observable o, Object arg)
    {
        active = ((Table)(ProgramContainer.getProgramContainer().getTable())).getCardAmount() <= 6;
        if(canBeChosen(id) && belongsToCurrentScene() && active) {
            onHover();
            clickableUpdate();
        }
        else {
            nonHover();
        }
    }

    public void render(Renderer r) {
        spriteRender(r);
        childrenRender(r);
        hoverRender(r, hovered, id);
        inactiveRender(r);
    }

    @Override
    public void onClick() {
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers)
            observer.update(null, null);
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onHold() {

    }

    private void inactiveRender(Renderer r){
        if(!active)
            r.drawRectangle(pos, dim, 0xA0A0A0A0, 1, id);
    }
}
