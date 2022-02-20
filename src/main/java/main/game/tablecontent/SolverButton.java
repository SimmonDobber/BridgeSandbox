package main.game.tablecontent;

import main.engine.display.Renderer;
import main.engine.display.Window;
import main.engine.structures.Button;
import main.engine.structures.features.Hoverable;
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
    public static final int DEFAULT_SOLVER_BUTTON_WIDTH = 150;
    public static final int DEFAULT_SOLVER_BUTTON_HEIGHT = 80;
    public static final int DEFAULT_SOLVER_BUTTON_X = Hand.CARD_SPACE;
    public static final int DEFAULT_SOLVER_BUTTON_Y = Window.HEIGHT - Hand.CARD_SPACE - DEFAULT_SOLVER_BUTTON_HEIGHT;
    private LinkedList<Observer> observers;

    public SolverButton(GameObject parent) {
        super(new Position(DEFAULT_SOLVER_BUTTON_X, DEFAULT_SOLVER_BUTTON_Y),
                new Dimensions(DEFAULT_SOLVER_BUTTON_WIDTH, DEFAULT_SOLVER_BUTTON_HEIGHT), parent);
        initializeSpriteList();
        observers = new LinkedList<>();
        hovered = false;
    }

    private void initializeSpriteList()
    {
        spriteList.add(new Rectangle(new Position(), dim, CYAN, BROWN, 1));
        spriteList.add(new Text("calculate", new Position(16, 22), DEFAULT_FONT_SIZE, GRAY, 1));
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
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

    @Override
    public void onHover() {
        hovered = true;
    }

    @Override
    public void nonHover() {
        hovered = false;
    }
}
