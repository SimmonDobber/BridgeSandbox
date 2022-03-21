package main.game.table;

import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.game.card.Card;
import main.game.card.CardData;

public class TableCard extends Card
{
    public TableCard(Position pos, GameObject parent, CardData cardData) {
        super(pos, parent, cardData);
    }

    @Override
    public void onClick() {
        notifyObservers();
    }

}
