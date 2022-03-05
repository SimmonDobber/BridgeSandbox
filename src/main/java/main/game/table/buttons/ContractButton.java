package main.game.table.buttons;

import main.engine.ProgramContainer;
import main.engine.structures.button.Button;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observer;
import main.game.table.card.CardColor;

import static main.game.GameConstants.*;

public class ContractButton extends Button
{
    private static final int DEFAULT_CONTRACT_BUTTON_WIDTH = 55;
    private static final int DEFAULT_CONTRACT_BUTTON_HEIGHT = 40;
    private static final int DEFAULT_CONTRACT_BUTTON_X = 150;
    private static final int DEFAULT_CONTRACT_BUTTON_Y = 22;

    public ContractButton(GameObject parent, int contractId)
    {
        super(new Position(DEFAULT_CONTRACT_BUTTON_X, DEFAULT_CONTRACT_BUTTON_Y),
                new Dimensions(DEFAULT_CONTRACT_BUTTON_WIDTH, DEFAULT_CONTRACT_BUTTON_HEIGHT), parent);
        initializeSpriteList(contractId);
        attach(ProgramContainer.getProgramContainer());
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers)
            observer.update(this, null);
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

    private void initializeSpriteList(int contractId) {
        spriteList.add(new Rectangle(new Position(), dim, CYAN, BROWN, 1));
        loadTextSprites(contractId);
    }

    private void loadTextSprites(int contractId) {
        spriteList.add(new Text(getContractFigureCharacter(contractId), new Position(7, 4),
                DEFAULT_FONT_SIZE, GRAY, 1));
        spriteList.add(new Text(getContractColorCharacter(contractId), new Position(26, 4),
                DEFAULT_FONT_SIZE, getContractCardColor(contractId), 1));
    }

    private String getContractFigureCharacter(int contractId) {
        return Character.toString((char)((contractId / 5) + '1'));
    }

    private String getContractColorCharacter(int contractId) {
        return Character.toString((char)((contractId % 5) + '['));
    }

    private int getContractCardColor(int contractId) {
        return CardColor.values()[contractId % 5].getCardColor();
    }

}
