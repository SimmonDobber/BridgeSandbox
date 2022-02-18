package main.game.tablecontent.card;

import lombok.Getter;
import main.engine.structures.Button;
import main.engine.display.Renderer;
import main.engine.structures.Scene;
import main.game.GameConstants;
import main.game.tablecontent.Hand;
import main.game.tablecontent.Table;

import static main.game.GameConstants.*;

public class Card extends Button
{
    public static final int DEFAULT_WIDTH = 85;
    public static final int DEFAULT_HEIGHT = 120;
    private static final int DEFAULT_STATE_COUNT = 2;
    @Getter
    private CardFigure figure;
    @Getter
    private CardColor color;

    public Card(Card card)
    {
        super(card.x, card.y, card.w, card.h, card.stateCount, 1);
        initializeCard(card.figure, card.color);
    }

    public Card(int x, int y, CardFigure figure, CardColor color) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_STATE_COUNT, 1);
        initializeCard(figure, color);
    }
    public Card(int x, int y, int w, int h, int stateCount, CardFigure figure, CardColor color) {
        super(x, y, w, h, stateCount, 1);
        initializeCard(figure, color);
    }

    public Card(int x, int y, int w, int h, int stateCount, int fixed) {
        super(x, y, w, h, stateCount, fixed);
    }

    private void initializeCard(CardFigure figure, CardColor color)
    {
        this.figure = figure;
        this.color = color;
    }

    @Override
    public void onClick(Scene table) {
        selectCard(((Table)(table)));
        ((Table)(table)).nextTurn();
    }

    @Override
    public void onDoubleClick(Scene table) {

    }

    @Override
    public void onRelease(Scene table) {

    }

    @Override
    public void onHold(Scene table) {

    }

    @Override
    public void onHover(Scene table) {
            highlighted = true;
    }

    @Override
    public void nonHover(Scene table) {
        highlighted = false;
    }

    private void selectCard(Table table)
    {
        for(int i = 0; i < GameConstants.PLAYER_COUNT; i++)
        {
            if(table.getHand()[i].getCard().contains(this))
            {
                moveCardToCanter(table, i);
            }
        }
    }

    private void moveCardToCanter(Table table, int playerId)
    {
        x = Hand.OWNER_CENTER_X[playerId];
        y = Hand.OWNER_CENTER_Y[playerId];
        table.getChosenCards()[playerId] = new Card(this);
    }

    public void render(Renderer r)
    {
        renderBackground(r);
        renderSymbols(r);
        hoveredRender(r);
        inactiveRender(r);
    }

    private void renderBackground(Renderer r)
    {
        r.drawRectangle(x - 2, y - 2, w + 4, h + 4, getColorValue(), img.getFixed(), buttonId);
        r.drawRectangle(x, y, w, h, GameConstants.SILVER, img.getFixed(), buttonId);
    }

    private void renderSymbols(Renderer r)
    {
        r.drawText(figure.getAsciiString(), x + 3, y + 2, color.getCardColor(), DEFAULT_FONT_SIZE, img.getFixed(), buttonId);
        r.drawText(color.getAsciiString(), x - 1, y + DEFAULT_FONT_SIZE - 3, color.getCardColor(), DEFAULT_FONT_SIZE, img.getFixed(), buttonId);
        r.drawText(figure.getAsciiString(), x + w - DEFAULT_FONT_SIZE / 2 - 8, y + h - DEFAULT_FONT_SIZE, color.getCardColor(), DEFAULT_FONT_SIZE, img.getFixed(), buttonId);
        r.drawText(color.getAsciiString(), x + w - DEFAULT_FONT_SIZE / 2 - 11, y + h - DEFAULT_FONT_SIZE * 2 + 6, color.getCardColor(), DEFAULT_FONT_SIZE, img.getFixed(), buttonId);
    }

    public int getId()
    {
        return color.ordinal() * GameConstants.FIGURE_COUNT + figure.ordinal();
    }
}
