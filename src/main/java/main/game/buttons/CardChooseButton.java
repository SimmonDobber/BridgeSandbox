package main.game.buttons;

import main.engine.ProgramContainer;
import main.engine.structures.button.Button;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observable;
import main.engine.structures.observer.Observer;
import main.game.cardChoosePanel.CardChoosePanel;

import static main.game.GameConstants.*;

public class CardChooseButton extends Button implements Observable
{
    private static final int DEFAULT_CARD_CHOOSE_BUTTON_WIDTH = 150;
    private static final int DEFAULT_CARD_CHOOSE_BUTTON_HEIGHT = 80;
    private static final int DEFAULT_CARD_CHOOSE_BUTTON_X = 198;
    private static final int DEFAULT_CARD_CHOOSE_BUTTON_Y = 442;

    public CardChooseButton(GameObject parent) {
        super(new Position(DEFAULT_CARD_CHOOSE_BUTTON_X, DEFAULT_CARD_CHOOSE_BUTTON_Y),
                new Dimensions(DEFAULT_CARD_CHOOSE_BUTTON_WIDTH, DEFAULT_CARD_CHOOSE_BUTTON_HEIGHT), parent);
        initializeSpriteList();
        attach(ProgramContainer.getProgramContainer());
    }

    @Override
    public void onClick() {
        notifyObservers();
        ((CardChoosePanel)(ProgramContainer.getProgramContainer().getCardChoosePanel())).reloadTextSprites();
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers)
            observer.update(this, null);
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onHold() {

    }

    private void initializeSpriteList()
    {
        spriteList.add(new Rectangle(new Position(), dim, CYAN, BROWN, 1));
        spriteList.add(new Text("choose cards", new Position(8, 22), 28, GRAY, 1));
    }
}
