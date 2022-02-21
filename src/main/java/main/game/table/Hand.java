package main.game.table;

import lombok.Getter;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.game.GameConstants;
import main.game.table.card.Card;
import main.game.table.card.CardColor;
import main.game.table.card.CardFigure;

import java.util.ArrayList;
import java.util.List;

public class Hand extends GameObject
{
    public static final int CARD_SPACE = 24;
    private static final int[] OWNER_X = {412, 803, 412, 24};
    private static final int[] OWNER_Y = {24, 277, 531, 277};
    public static final int[] OWNER_CENTER_X = {556, 676, 556, 436};
    public static final int[] OWNER_CENTER_Y = {192, 277, 363, 277};
    @Getter
    private List<Card> card;

    public Hand(int[] id, int cardAmount, int playerId, GameObject parent)
    {
        super(new Position(OWNER_X[playerId], OWNER_Y[playerId]), new Dimensions(), parent);
        initializeCards(id, cardAmount);
    }

    private void initializeCards(int[] id, int cardAmount)
    {
        card = new ArrayList<>();
        for(int i = 0; i < cardAmount; i++)
        {
            CardFigure cardFigure = CardFigure.values()[id[i] % GameConstants.FIGURE_COUNT];
            CardColor cardColor = CardColor.values()[id[i] / GameConstants.FIGURE_COUNT];
            card.add(new Card(new Position(pos.getX() + i * CARD_SPACE, pos.getY()), this, cardFigure, cardColor));
            children.add(card.get(card.size() - 1));
        }
    }
    public void attachObserversToCards(Table table)
    {
        for(int i = 0; i < card.size(); i++)
        {
            card.get(i).attach(table);
        }
    }

    public void removeCard(Card card) {
        children.remove(card);
        this.card.remove(card);
        repositionCards();
    }

    private void repositionCards() {
        for (int i = 0; i < card.size(); i++) {
            card.get(i).getPos().setX(pos.getX() + i * Hand.CARD_SPACE);
        }
    }

    public boolean hasColor(CardColor c)
    {
        for(int i = 0; i < card.size(); i++)
        {
            if(card.get(i).getColor() == c)
                return true;
        }
        return false;
    }
}
