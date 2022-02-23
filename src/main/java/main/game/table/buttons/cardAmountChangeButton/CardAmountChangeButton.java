package main.game.table.buttons.cardAmountChangeButton;


import main.engine.Input;
import main.engine.display.Window;
import main.engine.structures.Button;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observer;
import main.game.table.Hand;

import java.awt.event.MouseEvent;
import java.util.LinkedList;

import static main.game.GameConstants.*;
import static main.game.GameConstants.GRAY;

public class CardAmountChangeButton extends Button
{
    private static final int DEFAULT_CARD_AMOUNT_CHANGE_BUTTON_WIDTH = 150;
    private static final int DEFAULT_CARD_AMOUNT_CHANGE_BUTTON_HEIGHT = 80;
    private static final int DEFAULT_CARD_AMOUNT_CHANGE_BUTTON_X = 24;
    private static final int DEFAULT_CARD_AMOUNT_CHANGE_BUTTON_Y = 442;
    private static Integer CLICK_VALUE = null;
    private LinkedList<Observer> observers;

    public CardAmountChangeButton(GameObject parent)
    {
        super(new Position(DEFAULT_CARD_AMOUNT_CHANGE_BUTTON_X, DEFAULT_CARD_AMOUNT_CHANGE_BUTTON_Y),
                new Dimensions(DEFAULT_CARD_AMOUNT_CHANGE_BUTTON_WIDTH, DEFAULT_CARD_AMOUNT_CHANGE_BUTTON_HEIGHT), parent);
        initializeSpriteList();
        observers = new LinkedList<>();
    }

    private void initializeSpriteList()
    {
        spriteList.add(new Rectangle(new Position(), dim, CYAN, BROWN, 1));
        spriteList.add(new Text("change card\namount (+ -)\n(LPM, PPM)", new Position(16, 4), 24, GRAY, 1));
    }

    public void clickableUpdate()
    {
        if(Input.getInput().isButtonDown(MouseEvent.BUTTON1) || Input.getInput().isButtonDown(MouseEvent.BUTTON3))
            onClick();
        if(Input.getInput().isButtonUp(MouseEvent.BUTTON1))
            onRelease();
        if(Input.getInput().isButton(MouseEvent.BUTTON1))
            onHold();
    }

    @Override
    public void onClick() {
        if(Input.getInput().isButtonDown(MouseEvent.BUTTON1))
            CLICK_VALUE = 1;
        else
            CLICK_VALUE = -1;
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
        observers.forEach(Observer::update);
    }

    public static Integer getClickValue()
    {
        Integer clickValue = CLICK_VALUE;
        CLICK_VALUE = null;
        return clickValue;
    }
}
