package main.game.contractChoosePanel;

import lombok.Getter;
import main.engine.structures.button.Button;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observer;
import main.game.card.CardColor;

import java.util.LinkedList;

import static main.game.GameConstants.*;

public class ContractChooseButton extends Button
{
    public final static int DEFAULT_WIDTH = 75;
    public final static int DEFAULT_HEIGHT = 49;
    private LinkedList<Observer> observers;
    @Getter private int contractId;
    public ContractChooseButton(Position pos, GameObject parent, int contractId) {
        super(pos,
                new Dimensions(DEFAULT_WIDTH, DEFAULT_HEIGHT), parent);
        this.contractId = contractId;
        observers = new LinkedList<>();
        initializeSpriteList();
    }

    @Override
    public void onClick()
    {
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) 
            observer.update(this, contractId);
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

    private void initializeSpriteList() {
        spriteList.add(new Rectangle(new Position(), dim, CYAN, BROWN, 1));
        spriteList.add(new Text(getFigureCharacter(contractId), new Position(7, 4),
                DEFAULT_FONT_SIZE, GRAY, 1));
        spriteList.add(new Text(getColorCharacter(contractId), new Position(26, 4),
                DEFAULT_FONT_SIZE, getContractColor(contractId), 1));
    }

    private String getFigureCharacter(int contractId){
        return Character.toString((char)((contractId / (COLOR_COUNT + 1)) + '1'));
    }

    private String getColorCharacter(int contractId){
        return Character.toString((char)((contractId % (COLOR_COUNT + 1)) + '['));
    }

    private int getContractColor(int contractId){
        return CardColor.values()[contractId % 5].getCardColor();
    }
}
