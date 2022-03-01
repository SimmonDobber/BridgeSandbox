package main.game.table.buttons;

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

public class ShuffleButton extends Button
{
    private static final int DEFAULT_SHUFFLE_BUTTON_WIDTH = 150;
    private static final int DEFAULT_SHUFFLE_BUTTON_HEIGHT = 80;
    private static final int DEFAULT_SHUFFLE_BUTTON_X = 198;
    private static final int DEFAULT_SHUFFLE_BUTTON_Y = 546;
    private LinkedList<Observer> observers;
    public ShuffleButton(GameObject parent) {
        super(new Position(DEFAULT_SHUFFLE_BUTTON_X, DEFAULT_SHUFFLE_BUTTON_Y),
                new Dimensions(DEFAULT_SHUFFLE_BUTTON_WIDTH, DEFAULT_SHUFFLE_BUTTON_HEIGHT), parent);
        observers = new LinkedList<>();
        initializeSpriteList();
    }

    private void initializeSpriteList()
    {
        spriteList.add(new Rectangle(new Position(), dim, CYAN, BROWN, 1));
        spriteList.add(new Text("shuffle", new Position(16, 22), DEFAULT_FONT_SIZE, GRAY, 1));
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
    public void notifyObservers()
    {
        for(int i = 0; i < observers.size(); i++)
        {
            observers.get(i).update(this, true);
        }
    }
}
