package main.game.tablecontent;

import lombok.Getter;
import main.engine.structures.Button;
import main.engine.display.Renderer;
import main.engine.structures.State;
import main.game.GameConstants;

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
    public void onClick(State table) {
        selectCard(((Table)(table)));
        ((Table)(table)).nextTurn();
    }

    @Override
    public void onDoubleClick(State table) {

    }

    @Override
    public void onRelease(State table) {

    }

    @Override
    public void onHold(State table) {

    }

    @Override
    public void onHover(State table) {
            highlighted = true;
    }

    @Override
    public void nonHover(State table) {
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
        r.drawText(figure.getAsciiString(), x + 3, y + 2, getColorValue(), GameConstants.DEFAULT_FONT_SIZE, img.getFixed(), buttonId);
        r.drawText(color.getAsciiString(), x - 1, y + GameConstants.DEFAULT_FONT_SIZE - 3, getColorValue(), GameConstants.DEFAULT_FONT_SIZE, img.getFixed(), buttonId);
        r.drawText(figure.getAsciiString(), x + w - GameConstants.DEFAULT_FONT_SIZE / 2 - 8, y + h - GameConstants.DEFAULT_FONT_SIZE, getColorValue(), GameConstants.DEFAULT_FONT_SIZE, img.getFixed(), buttonId);
        r.drawText(color.getAsciiString(), x + w - GameConstants.DEFAULT_FONT_SIZE / 2 - 11, y + h - GameConstants.DEFAULT_FONT_SIZE * 2 + 6, getColorValue(), GameConstants.DEFAULT_FONT_SIZE, img.getFixed(), buttonId);
    }

    private void hoveredRender(Renderer r)
    {
        if(highlighted)
            r.drawRectangle(x, y, w, h, 0x220000FF, img.getFixed(), buttonId);
    }

    private void inactiveRender(Renderer r)
    {
        if(!active)
            r.drawRectangle(x, y, w, h, 0x77333333, img.getFixed(), buttonId);
    }

    public int getColorValue()
    {
        return ((color == CardColor.CLUB || color == CardColor.SPADE || color == CardColor.NO_ATU) ? GameConstants.BLACK : GameConstants.RED);
    }

    public static int getColorValue(CardColor c)
    {
        return ((c == CardColor.CLUB || c == CardColor.SPADE || c == CardColor.NO_ATU) ? GameConstants.BLACK : GameConstants.RED);
    }

    public int getId()
    {
        return color.ordinal() * GameConstants.FIGURE_COUNT + figure.ordinal();
    }
}
