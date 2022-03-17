package main.game.buttons;


import main.engine.Input;
import main.engine.structures.button.Button;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observer;

import java.awt.event.MouseEvent;

import static main.game.GameConstants.*;
import static main.game.GameConstants.GRAY;

public class CardAmountChangeButton extends Button
{
    private static final int DEFAULT_CARD_AMOUNT_CHANGE_BUTTON_WIDTH = 150;
    private static final int DEFAULT_CARD_AMOUNT_CHANGE_BUTTON_HEIGHT = 80;
    private static final int DEFAULT_CARD_AMOUNT_CHANGE_BUTTON_X = 24;
    private static final int DEFAULT_CARD_AMOUNT_CHANGE_BUTTON_Y = 442;

    private Integer mouseButtonId;

    public CardAmountChangeButton(GameObject parent) {
        super(new Position(DEFAULT_CARD_AMOUNT_CHANGE_BUTTON_X, DEFAULT_CARD_AMOUNT_CHANGE_BUTTON_Y),
                new Dimensions(DEFAULT_CARD_AMOUNT_CHANGE_BUTTON_WIDTH, DEFAULT_CARD_AMOUNT_CHANGE_BUTTON_HEIGHT), parent);
        initializeSpriteList();
    }

    public CardAmountChangeButton(Position pos, GameObject parent) {
        super(pos, new Dimensions(DEFAULT_CARD_AMOUNT_CHANGE_BUTTON_WIDTH, DEFAULT_CARD_AMOUNT_CHANGE_BUTTON_HEIGHT), parent);
        initializeSpriteList();
    }

    @Override
    public void onClick() {
        if(Input.getInput().isButtonDown(MouseEvent.BUTTON1))
            mouseButtonId = 1;
        else
            mouseButtonId = -1;
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers)
            observer.update(this, mouseButtonId);
    }

    public void clickableUpdate() {
        if(Input.getInput().isButtonDown(MouseEvent.BUTTON1) || Input.getInput().isButtonDown(MouseEvent.BUTTON3))
            onClick();
        if(Input.getInput().isButtonUp(MouseEvent.BUTTON1))
            onRelease();
        if(Input.getInput().isButton(MouseEvent.BUTTON1))
            onHold();
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onHold() {

    }

    private void initializeSpriteList() {
        spriteList.add(new Rectangle(new Position(), dim, CYAN, BROWN, 1));
        spriteList.add(new Text(getButtonTextString(), new Position(16, 4), 24, GRAY, 1));
    }

    private String getButtonTextString() {
        return "change card\nnumber (+ -)\n(LPM, PPM)";
    }
}
