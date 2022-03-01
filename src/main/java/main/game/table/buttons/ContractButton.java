package main.game.table.buttons;

import main.engine.ProgramContainer;
import main.engine.structures.Button;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observer;
import main.game.table.card.CardColor;

import java.util.LinkedList;

import static main.game.GameConstants.*;

public class ContractButton extends Button
{
    private static final int DEFAULT_CONTRACT_BUTTON_WIDTH = 55;
    private static final int DEFAULT_CONTRACT_BUTTON_HEIGHT = 40;
    private static final int DEFAULT_CONTRACT_BUTTON_X = 150;
    private static final int DEFAULT_CONTRACT_BUTTON_Y = 22;
    private LinkedList<Observer> observers;

    public ContractButton(GameObject parent, int contractId)
    {
        super(new Position(DEFAULT_CONTRACT_BUTTON_X, DEFAULT_CONTRACT_BUTTON_Y),
                new Dimensions(DEFAULT_CONTRACT_BUTTON_WIDTH, DEFAULT_CONTRACT_BUTTON_HEIGHT), parent);
        initializeSpriteList(contractId);
        observers = new LinkedList<>();
        attach(ProgramContainer.getProgramContainer());
    }

    private void initializeSpriteList(int contractId)
    {
        spriteList.add(new Rectangle(new Position(), dim, CYAN, BROWN, 1));
        loadTextSprites(contractId);
    }

    private void loadTextSprites(int contractId)
    {
        spriteList.add(new Text(Character.toString((char)((contractId / 5) + '1')), new Position(7, 4), DEFAULT_FONT_SIZE, GRAY, 1));
        spriteList.add(new Text(Character.toString((char)((contractId % 5) + '[')), new Position(26, 4), DEFAULT_FONT_SIZE, CardColor.values()[contractId % 5].getCardColor(), 1));
    }

    private void removeTextSprites()
    {
        for(int i = 0; i < spriteList.size(); i++)
        {
            if(spriteList.get(i).getClass().equals(Text.class))
                spriteList.remove(i--);
        }
    }

    public void reLoadTextSprites(int contractId)
    {
        removeTextSprites();
        loadTextSprites(contractId);
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
            observers.get(i).update(this, null);
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
}
