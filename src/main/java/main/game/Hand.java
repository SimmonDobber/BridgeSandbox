package main.game;

import main.engine.Input;
import main.engine.display.Renderer;

import java.util.ArrayList;
import java.util.List;

public class Hand
{
    public static final int CARD_SPACE = 24;
    public static final int OWNER_X[] = {412, 803, 412, 24};
    public static final int OWNER_Y[] = {24, 277, 531, 277};
    public static final int OWNER_CENTER_X[] = {556, 676, 556, 436};
    public static final int OWNER_CENTER_Y[] = {192, 277, 363, 277};
    private List<Card> card;
    private int x;
    private int y;
    private int owner;
    public Hand(int[] id, int cardAmount, int owner)
    {
        this.owner = owner;
        x = OWNER_X[owner];
        y = OWNER_Y[owner];
        card = new ArrayList<>();
        for(int i = 0; i < cardAmount; i++)
        {
            card.add(new Card(x + i * CARD_SPACE, y, id[i] % 13, id[i] / 13, owner));
        }
    }
    public void update(Input input, Table table)
    {
        for(int i = 0; i < card.size(); i++)
            card.get(i).buttonUpdate(input, table);
    }
    public void render(Renderer r)
    {
        for(int i = 0; i < card.size(); i++)
            card.get(i).render(r);
    }

    public int getX() {
        return x;
    }

    public List<Card> getCard() {
        return card;
    }

    public boolean hasColor(int c)
    {
        for(int i = 0; i < card.size(); i++)
        {
            if(card.get(i).getColor() == c)
                return true;
        }
        return false;
    }
}
