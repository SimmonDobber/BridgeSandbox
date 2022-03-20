package main.game.cardChoosePanel;

import main.engine.structures.TextManager;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.game.table.GameManager;

import static main.game.GameConstants.DEFAULT_FONT_SIZE;
import static main.game.GameConstants.GRAY;

public class CardChoicePanelTextManager extends TextManager
{
    GameManager gameManager;
    public CardChoicePanelTextManager(GameManager gameManager, GameObject parent) {
        super(parent);
        this.gameManager = gameManager;
        loadTexts();
    }

    @Override
    protected void loadTexts() {
        addText(new Text(getCurrentCardAmountTextString(), new Position(854, 520), DEFAULT_FONT_SIZE, GRAY, 1));
    }

    private String getCurrentCardAmountTextString(){
        return new String("Current card amount; " + gameManager.getCardAmount());
    }

}
