package main.game.table.bestMovesTable;

import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.game.card.CardColor;
import main.game.card.CardFigure;

import static main.game.GameConstants.*;

public class BestMovesTableField extends GameObject
{
    public static final int FIELD_WIDTH = 48;
    public static final int FIELD_HEIGHT = 40;

    public BestMovesTableField(Position pos, GameObject parent) {
        super(pos, new Dimensions(FIELD_WIDTH, FIELD_HEIGHT), parent);
    }

    public BestMovesTableField(Position pos, GameObject parent, String text) {
        super(pos, new Dimensions(FIELD_WIDTH, FIELD_HEIGHT), parent);
        spriteReload(text);
    }

    public void spriteReload(String text){
        removeSprites();
        if(text == null)
            return;
        spriteList.add(new Rectangle(new Position(), dim, CYAN, BROWN, 1));
        spriteList.add(new Text(text, new Position(2, 2), DEFAULT_FONT_SIZE, GRAY, 1));
    }

    public void spriteReload(int cardId, boolean winner){
        removeSprites();
        addFieldBackgroundSprite(winner);
        addCardSignatureTexts(cardId);
    }

    public void removeSprites(){
        spriteList.clear();
    }

    private void addFieldBackgroundSprite(boolean winner){
        spriteList.add(new Rectangle(new Position(), dim, (winner ? TEAL : CYAN), BROWN, 1));
    }

    private void addCardSignatureTexts(int cardId){
        addCardSignatureFigure(cardId);
        addCardSignatureColor(cardId);
    }

    private void addCardSignatureFigure(int cardId){
        CardFigure figure = CardFigure.values()[cardId / COLOR_COUNT];
        spriteList.add(new Text(figure.getAsciiString(), new Position(4, 4), DEFAULT_FONT_SIZE, GRAY, 1));
    }

    private void addCardSignatureColor(int cardId){
        CardColor color = CardColor.values()[cardId % COLOR_COUNT];
        spriteList.add(new Text(color.getAsciiString(), new Position(22, 4), DEFAULT_FONT_SIZE, color.getCardColor(), 1));
    }
}
