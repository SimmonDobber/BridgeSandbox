package main.game.tablecontent.card;

import lombok.Getter;
import lombok.Setter;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.features.Activable;
import main.engine.structures.features.Clickable;
import main.engine.structures.features.Hoverable;
import main.engine.structures.gameObject.GameObject;
import main.game.GameConstants;
import main.game.tablecontent.Table;

import static main.game.GameConstants.*;

@Getter
public class Card extends GameObject implements Clickable, Activable, Hoverable
{
    public static final int DEFAULT_WIDTH = 85;
    public static final int DEFAULT_HEIGHT = 120;
    private CardFigure figure;
    private CardColor color;
    @Setter
    private boolean active;
    @Setter
    private boolean hovered;

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
        this.active = true;
        this.hovered = false;
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
    public void update()
    {
        ((Table)(getParent().getParent())).playCard(this);
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onHold() {

    }

    @Override
    public void onHover()
    {
        hovered = true;
    }

    @Override
    public void nonHover() {
        hovered = false;
    }

    public int getId()
    {
        return color.ordinal() * GameConstants.FIGURE_COUNT + figure.ordinal();
    }
}
