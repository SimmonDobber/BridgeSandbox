package main.game.table;

import lombok.Getter;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.game.GameConstants;
import main.game.table.card.CardColor;
import main.game.table.card.CardFigure;

import java.util.ArrayList;
import java.util.List;

public class Hand extends GameObject
{
    public static final int CARD_SPACE = 24;
    private static final int[] POS_X = {412, 803, 412, 24};
    private static final int[] POS_Y = {24, 240, 531, 240};
    public static final int[] CENTER_POS_X = {556, 676, 556, 436};
    public static final int[] CENTER_POS_Y = {192, 277, 363, 277};
    @Getter
    private List<TableCard> cards;

    public Hand(int[] id, int cardAmount, int playerId, GameObject parent)
    {
        super(new Position(POS_X[playerId], POS_Y[playerId]), new Dimensions(), parent);
        initializeCards(id, cardAmount);
    }

    private void initializeCards(int[] id, int cardAmount)
    {
        cards = new ArrayList<>();
        for(int i = 0; i < cardAmount; i++)
        {
            CardFigure cardFigure = CardFigure.values()[id[i] % GameConstants.FIGURE_COUNT];
            CardColor cardColor = CardColor.values()[id[i] / GameConstants.FIGURE_COUNT];
            cards.add(new TableCard(new Position(pos.getX() + i * CARD_SPACE, pos.getY()), this, cardFigure, cardColor));
            children.add(cards.get(cards.size() - 1));
        }
    }
    public void attachObserversToCards(Table table)
    {
        for(int i = 0; i < cards.size(); i++)
        {
            cards.get(i).attach(table.getGameManager());
            cards.get(i).attach(table.getTextManager());
        }
    }

    public void removeCard(TableCard card) {
        children.remove(card);
        this.cards.remove(card);
        repositionCards();
    }

    private void repositionCards() {
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).getPos().setX(pos.getX() + i * Hand.CARD_SPACE);
        }
    }

    public boolean hasColor(CardColor c)
    {
        for(int i = 0; i < cards.size(); i++)
        {
            if(cards.get(i).getColor() == c)
                return true;
        }
        return false;
    }
}
