package main.game.contractChoosePanel;

import lombok.Getter;
import lombok.Setter;
import main.engine.structures.Button;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observer;
import main.game.table.card.CardColor;

import java.util.LinkedList;

import static main.game.GameConstants.*;

public class ContractChooseButton extends Button
{
    public final static int DEFAULT_WIDTH = 75;
    public final static int DEFAULT_HEIGHT = 49;
    @Setter
    private static Integer RECENTLY_CHOSEN = null;
    private LinkedList<Observer> observers;
    @Getter
    private int contractId;
    public ContractChooseButton(Position pos, GameObject parent, int contractId) {
        super(pos,
                new Dimensions(DEFAULT_WIDTH, DEFAULT_HEIGHT), parent);
        this.contractId = contractId;
        observers = new LinkedList<>();
        initializeSpriteList();
    }

    private void initializeSpriteList() {
        spriteList.add(new Rectangle(new Position(), dim, CYAN, BROWN, 1));
        spriteList.add(new Text(Character.toString((char)((contractId / (COLOR_COUNT + 1)) + '1')), new Position(7, 4), DEFAULT_FONT_SIZE, GRAY, 1));
        spriteList.add(new Text(Character.toString((char)((contractId % (COLOR_COUNT + 1)) + '[')), new Position(26, 4), DEFAULT_FONT_SIZE, CardColor.values()[contractId % 5].getCardColor(), 1));
    }

    @Override
    public void onClick()
    {
        RECENTLY_CHOSEN = contractId;
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

    public static Integer getRecentlyChosen()
    {
        Integer recentlyChosen = RECENTLY_CHOSEN;
        RECENTLY_CHOSEN = null;
        return recentlyChosen;
    }
}
