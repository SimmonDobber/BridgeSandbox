package main.game.contractChoosePanel;

import lombok.Getter;
import lombok.Setter;
import main.engine.structures.Button;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observer;

import java.util.LinkedList;

import static main.game.GameConstants.COLOR_COUNT;

public class ContractChooseButton extends Button
{
    private final static int DEFAULT_WIDTH = 75;
    private final static int DEFAULT_HEIGHT = 49;
    @Getter
    @Setter
    private static int recentlyChosen = -1;
    private LinkedList<Observer> observers;
    @Getter
    private int id;
    public ContractChooseButton(Position pos, GameObject parent) {
        super(new Position(pos.getX() * DEFAULT_WIDTH, pos.getY() * DEFAULT_HEIGHT),
                new Dimensions(DEFAULT_WIDTH, DEFAULT_HEIGHT), parent);
        this.id = pos.getX() + pos.getY() * (COLOR_COUNT + 1);
        observers = new LinkedList<>();
        initializeSpriteList();
    }

    private void initializeSpriteList()
    {
        spriteList.add(new Rectangle(new Position(), dim, 0xFFFFFFFF, 1));
    }

    @Override
    public void onClick()
    {
        recentlyChosen = id;
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
}
