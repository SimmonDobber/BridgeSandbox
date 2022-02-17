package main.game;

import lombok.Getter;
import main.engine.Input;
import main.engine.display.Renderer;

import java.util.ArrayList;
import java.util.List;

public class Hand
{
    public static final int CARD_SPACE = 24;
    public static final int[] OWNER_X = {412, 803, 412, 24};
    public static final int[] OWNER_Y = {24, 277, 531, 277};
    public static final int[] OWNER_CENTER_X = {556, 676, 556, 436};
    public static final int[] OWNER_CENTER_Y = {192, 277, 363, 277};
    @Getter
    private List<Card> card;
    @Getter
    private final int x;
    @Getter
    private final int y;
    public Hand(int[] id, int cardAmount, int playerId)
    {
        x = OWNER_X[playerId];
        y = OWNER_Y[playerId];
        initializeCards(id, cardAmount);
    }
    private void initializeCards(int[] id, int cardAmount)
    {
        card = new ArrayList<>();
        for(int i = 0; i < cardAmount; i++)
        {
            card.add(new Card(x + i * CARD_SPACE, y, CardFigure.values()[id[i] % GameConstants.FIGURE_COUNT], CardColor.values()[id[i] / GameConstants.FIGURE_COUNT]));
        }
    }
    public void update(Input input, Table table)
    {
        for (Card value : card)
            value.buttonUpdate(input, table);
    }
    public void render(Renderer r)
    {
        for (Card value : card)
            value.render(r);
    }

    public boolean hasColor(CardColor c)
    {
        for (Card value : card)
        {
            if (value.getColor() == c)
                return true;
        }
        return false;
    }
}
