package main.game.table;

import lombok.Getter;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.game.Card;
import main.game.GameConstants;
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
    private List<TableCard> cards;

    public Hand(int[] cardIDs, int cardAmount, int playerId, GameObject parent) {
        super(new Position(OWNER_X[playerId], OWNER_Y[playerId]), new Dimensions(), parent);
        initializeCards(cardIDs, cardAmount);
    }

    public void attachObserversToCards(GameManager gameManager) {
        for (TableCard card : cards) {
            card.attach(gameManager);
        }
    }

    public void removeCard(TableCard tableCard) {
        children.remove(tableCard);
        this.cards.remove(tableCard);
        repositionCards();
    }

    public boolean hasColor(CardColor c) {
        for (TableCard card : cards) {
            if (card.getColor() == c)
                return true;
        }
        return false;
    }

    public int getSize(){
        return cards.size();
    }

    private void initializeCards(int[] cardIDs, int cardAmount) {
        cards = new ArrayList<>();
        for(int i = 0; i < cardAmount; i++) {
            cards.add(new TableCard(getCardPosition(i), this,
                    Card.getCardFigureFromId(cardIDs[i]), Card.getCardColorFromId(cardIDs[i])));
            children.add(cards.get(cards.size() - 1));
        }
    }

    private void repositionCards() {
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).getPos().setX(getIthCardXPosition(i));
        }
    }

    private Position getCardPosition(int cardNumber){
        return new Position(getIthCardXPosition(cardNumber), pos.getY());
    }

    private int getIthCardXPosition(int i){
        return pos.getX() + i * CARD_SPACE;
    }
}
