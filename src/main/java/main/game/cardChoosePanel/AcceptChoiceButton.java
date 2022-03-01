package main.game.cardChoosePanel;

import main.engine.ProgramContainer;
import main.engine.structures.Button;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observer;

import java.util.LinkedList;

import static main.game.GameConstants.*;
import static main.game.GameConstants.GRAY;

public class AcceptChoiceButton extends Button
{
    private static final int DEFAULT_ACCEPT_CHOICE_BUTTON_WIDTH = 150;
    private static final int DEFAULT_ACCEPT_CHOICE_BUTTON_HEIGHT = 80;
    private static final int DEFAULT_ACCEPT_CHOICE_BUTTON_X = 1026;
    private static final int DEFAULT_ACCEPT_CHOICE_BUTTON_Y = 571;
    private LinkedList<Observer> observers;
    public AcceptChoiceButton(GameObject parent) {
        super(new Position(DEFAULT_ACCEPT_CHOICE_BUTTON_X, DEFAULT_ACCEPT_CHOICE_BUTTON_Y),
                new Dimensions(DEFAULT_ACCEPT_CHOICE_BUTTON_WIDTH, DEFAULT_ACCEPT_CHOICE_BUTTON_HEIGHT), parent);
        observers = new LinkedList<>();
        initializeSpriteList();
    }

    private void initializeSpriteList()
    {
        spriteList.add(new Rectangle(new Position(), dim, CYAN, BROWN, 1));
        spriteList.add(new Text("accept", new Position(36, 22), DEFAULT_FONT_SIZE, GRAY, 1));
    }

    @Override
    public void onClick() {
        notifyObservers();
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onHold() {

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
        for(int i = 0; i < observers.size(); i++)
            observers.get(i).update(this, ((CardChoosePanel)(parent)).groupChosenCards());
    }
}
