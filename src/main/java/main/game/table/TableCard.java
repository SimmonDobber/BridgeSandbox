package main.game.table;

import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.game.Card;
import main.game.table.card.CardColor;
import main.game.table.card.CardData;
import main.game.table.card.CardFigure;

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
