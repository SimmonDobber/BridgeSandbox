package main.game.table.solver;

import main.engine.structures.IntPair;
import main.game.GameConstants;
import main.game.table.Table;

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
        currPlayer = table.getCurrentPlayer().ordinal();
        lastWinner = table.getLastWinner().ordinal();
        currColor = (table.getLastWinner() == table.getCurrentPlayer() ? -1 : table.getChosenCards()[table.getLastWinner().ordinal()].getColor().ordinal());
        taken = new IntPair(table.getTaken());
        cards = new List[GameConstants.PLAYER_COUNT];
        for(int i = 0; i < GameConstants.PLAYER_COUNT; i++)
        {
            cards[i] = new ArrayList<>();
            for(int j = 0; j < table.getHand()[i].getCard().size(); j++)
                cards[i].add(new IntPair(table.getHand()[i].getCard().get(j).getColor().ordinal(), table.getHand()[i].getCard().get(j).getFigure().ordinal()));
        }
        trace = new ArrayList<>();
        for(int i = 0; i < GameConstants.PLAYER_COUNT; i++)
        {
            if(table.getChosenCards()[(table.getCurrentPlayer().ordinal() + i) % GameConstants.PLAYER_COUNT] != null)
              trace.add(new IntPair(table.getChosenCards()[(table.getCurrentPlayer().ordinal() + i) % GameConstants.PLAYER_COUNT].getColor().ordinal(), table.getChosenCards()[(table.getCurrentPlayer().ordinal() + i) % GameConstants.PLAYER_COUNT].getFigure().ordinal()));
        }
    }
    public GameState(GameState g)
    {
        currPlayer = g.currPlayer;
        lastWinner = g.lastWinner;
        currColor = g.currColor;
        taken = new IntPair(g.taken);
        cards = new List[GameConstants.PLAYER_COUNT];
        for(int i = 0; i < GameConstants.PLAYER_COUNT; i++)
        {
            cards[i] = new ArrayList<>(g.cards[i]);
        }
        trace = new ArrayList<>(g.trace);
    }

}
