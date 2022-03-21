package main.game.table;

import lombok.Getter;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.game.card.CardColor;
import main.game.card.CardData;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Hand extends GameObject
{
    public static final int CARD_SPACE = 24;
    private static final int[] POS_X = {412, 803, 412, 24};
    private static final int[] POS_Y = {24, 240, 531, 240};
    public static final int[] CENTER_POS_X = {556, 676, 556, 436};
    public static final int[] CENTER_POS_Y = {192, 277, 363, 277};
    @Getter private List<TableCard> cards;

    public Hand(Integer[] id, int cardAmount, int playerId, GameObject parent) {
        super(new Position(POS_X[playerId], POS_Y[playerId]), new Dimensions(), parent);
        initializeCards(id, cardAmount);
    }

    public void attachObserversToCards(Table table) {
        for (TableCard card : cards) {
            card.attach(table.getGameManager());
            card.attach(table.getTextManager());
        }
    }

    public List<CardData> getCardsData(){
        List<CardData> cardsData = new LinkedList<>();
        for(TableCard card : cards)
            cardsData.add(card.getCardData());
        return cardsData;
    }

    public void removeCard(TableCard card) {
        children.remove(card);
        this.cards.remove(card);
        repositionCards();
    }

    public boolean hasColor(CardColor c) {
        for (TableCard card : cards) {
            if (card.getColor() == c)
                return true;
        }
        return false;
    }

    private void initializeCards(Integer[] id, int cardAmount) {
        cards = new ArrayList<>();
        for(int i = 0; i < cardAmount; i++) {
            cards.add(new TableCard(new Position(pos.getX() + i * CARD_SPACE, pos.getY()), this, new CardData(id[i])));
            children.add(cards.get(cards.size() - 1));
        }
    }

    private void repositionCards() {
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).getPos().setX(pos.getX() + i * Hand.CARD_SPACE);
        }
    }
}
