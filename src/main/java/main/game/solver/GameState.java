package main.game.solver;

import main.engine.IntPair;
import main.game.Table;

import java.util.ArrayList;
import java.util.List;

public class GameState
{
    public int currPlayer;
    public int lastWinner;
    public int currColor;
    public IntPair taken;
    public List<IntPair>[] cards;
    public List<IntPair> trace;
    public GameState(Table table)
    {
        currPlayer = table.getCurrentPlayer();
        lastWinner = table.getLastWinner();
        currColor = (table.getLastWinner() == table.getCurrentPlayer() ? -1 : table.getChoosenCards()[currPlayer].getColor());
        taken = new IntPair(table.getTaken());
        cards = new List[Table.PLAYER_COUNT];
        for(int i = 0; i < Table.PLAYER_COUNT; i++)
        {
            cards[i] = new ArrayList<>();
            for(int j = 0; j < table.getHand()[i].getCard().size(); j++)
                cards[i].add(new IntPair(table.getHand()[i].getCard().get(j).getColor(), table.getHand()[i].getCard().get(j).getFigure()));
        }
        trace = new ArrayList<>();
    }
    public GameState(GameState g)
    {
        currPlayer = g.currPlayer;
        lastWinner = g.lastWinner;
        currColor = g.currColor;
        taken = new IntPair(g.taken);
        cards = new List[Table.PLAYER_COUNT];
        for(int i = 0; i < Table.PLAYER_COUNT; i++)
        {
            cards[i] = new ArrayList<>(g.cards[i]);
        }
        trace = new ArrayList<>(g.trace);
    }

}
