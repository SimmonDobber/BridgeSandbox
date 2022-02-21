package main.game.tablecontent;

import main.engine.display.Renderer;
import main.engine.structures.Button;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observer;
import main.game.tablecontent.card.CardColor;

import java.util.LinkedList;

import static main.game.GameConstants.*;

public class ContractButton extends Button
{
    public static final int DEFAULT_SOLVER_BUTTON_WIDTH = 55;
    public static final int DEFAULT_SOLVER_BUTTON_HEIGHT = 40;
    public static final int DEFAULT_SOLVER_BUTTON_X = 150;
    public static final int DEFAULT_SOLVER_BUTTON_Y = 22;
    private LinkedList<Observer> observers;

    public ContractButton(GameObject parent, int contractId)
    {
        super(new Position(DEFAULT_SOLVER_BUTTON_X, DEFAULT_SOLVER_BUTTON_Y),
                new Dimensions(DEFAULT_SOLVER_BUTTON_WIDTH, DEFAULT_SOLVER_BUTTON_HEIGHT), parent);
        initializeSpriteList(contractId);
        observers = new LinkedList<>();
    }

    private void initializeSpriteList(int contractId)
    {
        spriteList.add(new Rectangle(new Position(), dim, CYAN, BROWN, 1));
        spriteList.add(new Text(Character.toString((char)((contractId / 5) + '1')), new Position(7, 4), DEFAULT_FONT_SIZE, GRAY, 1));
        spriteList.add(new Text(Character.toString((char)((contractId % 5) + '[')), new Position(26, 4), DEFAULT_FONT_SIZE, CardColor.values()[contractId % 5].getCardColor(), 1));
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observers);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }

    @Override
    public void onClick() {

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
