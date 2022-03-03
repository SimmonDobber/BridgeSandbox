package main.game.table.solver;

import main.engine.structures.IntPair;
import main.game.GameConstants;
import main.game.table.GameManager;
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
    public GameState(GameManager game)
    {
        currPlayer = game.getCurrentPlayer().ordinal();
        lastWinner = game.getLastWinner().ordinal();
        currColor = (game.getLastWinner() == game.getCurrentPlayer() ? -1 : game.getChosenTableCards()[game.getLastWinner().ordinal()].getColor().ordinal());
        taken = new IntPair(game.getTaken());
        cards = new List[GameConstants.PLAYER_COUNT];
        for(int i = 0; i < GameConstants.PLAYER_COUNT; i++)
        {
            cards[i] = new ArrayList<>();
            for(int j = 0; j < game.getHand()[i].getTableCard().size(); j++)
                cards[i].add(new IntPair(game.getHand()[i].getTableCard().get(j).getColor().ordinal(), game.getHand()[i].getTableCard().get(j).getFigure().ordinal()));
        }
        trace = new ArrayList<>();
        for(int i = 0; i < GameConstants.PLAYER_COUNT; i++)
        {
            if(game.getChosenTableCards()[(game.getCurrentPlayer().ordinal() + i) % GameConstants.PLAYER_COUNT] != null)
              trace.add(new IntPair(game.getChosenTableCards()[(game.getCurrentPlayer().ordinal() + i) % GameConstants.PLAYER_COUNT].getColor().ordinal(), game.getChosenTableCards()[(game.getCurrentPlayer().ordinal() + i) % GameConstants.PLAYER_COUNT].getFigure().ordinal()));
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
