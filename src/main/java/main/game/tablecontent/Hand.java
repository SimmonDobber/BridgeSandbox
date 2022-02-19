package main.game.tablecontent;

import lombok.Getter;
import main.engine.Input;
import main.engine.LoopTimer;
import main.engine.display.Renderer;
import main.engine.display.Window;
import main.engine.structures.GameObject;
import main.game.GameConstants;
import main.game.tablecontent.card.Card;
import main.game.tablecontent.card.CardColor;
import main.game.tablecontent.card.CardFigure;

import java.util.ArrayList;
import java.util.List;

public class Hand extends GameObject
{
    public static final int CARD_SPACE = 24;
    public static final int[] OWNER_X = {412, 803, 412, 24};
    public static final int[] OWNER_Y = {24, 277, 531, 277};
    public static final int[] OWNER_CENTER_X = {556, 676, 556, 436};
    public static final int[] OWNER_CENTER_Y = {192, 277, 363, 277};
    @Getter
    private List<Card> card;
    public Hand(int[] id, int cardAmount, int playerId, GameObject parent)
    {
        super(OWNER_X[playerId], OWNER_Y[playerId], 0, 0, parent);
        initializeCards(id, cardAmount);
    }
    private void initializeCards(int[] id, int cardAmount)
    {
        card = new ArrayList<>();
        for(int i = 0; i < cardAmount; i++)
        {
            card.add(new Card(x + i * CARD_SPACE, y, this, CardFigure.values()[id[i] % GameConstants.FIGURE_COUNT], CardColor.values()[id[i] / GameConstants.FIGURE_COUNT]));
            children.add(card.get(card.size() - 1));
        }
    }

    public void update(Window window, Input input, LoopTimer loopTimer)
    {
        updateChildren(window, input, loopTimer);
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
