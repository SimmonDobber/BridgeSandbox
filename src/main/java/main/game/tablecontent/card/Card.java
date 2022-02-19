package main.game.tablecontent.card;

import lombok.Getter;
import lombok.Setter;
import main.engine.Input;
import main.engine.LoopTimer;
import main.engine.display.Window;
import main.engine.structures.Button;
import main.engine.display.Renderer;
import main.engine.structures.GameObject;
import main.engine.structures.Scene;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.game.GameConstants;
import main.game.tablecontent.Hand;
import main.game.tablecontent.Table;

import static main.game.GameConstants.*;

@Getter
public class Card extends Button
{
    public static final int DEFAULT_WIDTH = 85;
    public static final int DEFAULT_HEIGHT = 120;
    private CardFigure figure;
    private CardColor color;

    public Card(Card card)
    {
        super(card.x, card.y, card.w, card.h, card.parent);
        initializeCard(card.figure, card.color);
    }

    public Card(int x, int y, GameObject parent, CardFigure figure, CardColor color) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, parent);
        initializeCard(figure, color);
    }

    public Card(int x, int y, int w, int h, GameObject parent, CardFigure figure, CardColor color) {
        super(x, y, w, h, parent);
        initializeCard(figure, color);
    }

    private void initializeCard(CardFigure figure, CardColor color)
    {
        this.figure = figure;
        this.color = color;
        initializeSpriteList();
    }

    private void initializeSpriteList()
    {
        spriteList.add(new Rectangle(0, 0, w, h, SILVER, color.getCardColor(), 1));
        spriteList.add(new Text(figure.getAsciiString(), 3, 2, DEFAULT_FONT_SIZE, color.getCardColor(), 1));
        spriteList.add(new Text(color.getAsciiString(), -1, DEFAULT_FONT_SIZE, DEFAULT_FONT_SIZE, color.getCardColor(),1));
        spriteList.add(new Text(figure.getAsciiString(), w - DEFAULT_FONT_SIZE / 2 - 8, h - DEFAULT_FONT_SIZE, DEFAULT_FONT_SIZE, color.getCardColor(),1));
        spriteList.add(new Text(color.getAsciiString(), w - DEFAULT_FONT_SIZE / 2 - 11, h - DEFAULT_FONT_SIZE * 2 + 6, DEFAULT_FONT_SIZE, color.getCardColor(), 1));
    }

    @Override
    public void onClick() {
        toProcess = true;
    }

    @Override
    public void onDoubleClick() {

    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onHold() {

    }

    @Override
    public void onHover() {
            highlighted = true;
    }

    @Override
    public void nonHover() {
        highlighted = false;
    }

    public int getId()
    {
        return color.ordinal() * GameConstants.FIGURE_COUNT + figure.ordinal();
    }
}
