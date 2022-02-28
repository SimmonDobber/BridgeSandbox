package main.game.table.solver;

import main.engine.structures.IntPair;
import main.engine.structures.observer.Observer;
import main.game.table.card.CardColor;
import main.game.table.card.CardFigure;
import main.game.GameConstants;
import main.game.table.Table;

public class Solver implements Observer
{
    private final Table table;
    private int atu;
    private GameState initialState;
    private Feedback feedback;
    public Solver(Table table)
    {
        this.table = table;
    }
    @Override
    public void update() {
        initialize();
    }
    private int chooseWinner(GameState g)
    {
        IntPair[] lastCards = new IntPair[GameConstants.PLAYER_COUNT];
        for(int i = 0; i < GameConstants.PLAYER_COUNT; i++)
        {
            lastCards[(i + g.lastWinner) % GameConstants.PLAYER_COUNT] = new IntPair(g.trace.get(g.trace.size() - 4 + i));
        }
        int currentWinner = g.lastWinner;
        int currentAtu = lastCards[g.lastWinner].x;
        for(int i = 0; i < GameConstants.PLAYER_COUNT; i++)
        {
            if(i == g.lastWinner)
                continue;
            if(lastCards[currentWinner].x != atu && lastCards[i].x == atu)
            {
                currentWinner = i;
            }
            else if((lastCards[i].x != atu) && ((lastCards[i].x != currentAtu) || (lastCards[currentWinner].x == atu)))
            {
                ;
            }
            else if(lastCards[currentWinner].y < lastCards[i].y)
            {
                currentWinner = i;
            }

        }
        return currentWinner;
    }
    private boolean valid(GameState g, int id)
    {
        if(g.cards[g.currPlayer].get(id).x == g.currColor || g.currColor == -1)
            return true;
        for(int i = 0; i < g.cards[g.currPlayer].size(); i++)
        {
            if(g.cards[g.currPlayer].get(i).x == g.currColor)
                return false;
        }
        return true;
    }
    private Feedback move(GameState g, IntPair card, int id)
    {
        g.trace.add(card);
        g.cards[g.currPlayer].remove(id);
        if((g.currPlayer + 1) % GameConstants.PLAYER_COUNT == g.lastWinner)
        {
            g.currPlayer = chooseWinner(g);
            g.lastWinner = g.currPlayer;
            if(g.currPlayer % 2 == 0)
                g.taken.x++;
            else
                g.taken.y++;
        }
        else
        {
            if(g.currPlayer == g.lastWinner)
                g.currColor = card.x;
            g.currPlayer = (g.currPlayer + 1) % GameConstants.PLAYER_COUNT;
        }
        Feedback f;
        Feedback feedback = new Feedback();
        int moves = 0;
        feedback.amount = 1000 * (g.currPlayer % 2 == 1 ? 1 : 0) - 1;
        if(g.cards[g.currPlayer].isEmpty())
        {
            feedback.amount = g.taken.x;
            feedback.trace = g.trace;
            feedback.moves = 1;
            return feedback;
        }
        for(int i = 0; i < g.cards[g.currPlayer].size(); i++)
        {
            if(!valid(g, i))
                continue;
            f = move(new GameState(g), new IntPair(g.cards[g.currPlayer].get(i)), i);
            moves += f.moves;
            if((g.currPlayer % 2 == 1 && feedback.amount > f.amount) || (g.currPlayer % 2 == 0 && feedback.amount < f.amount))
            {
                feedback = new Feedback(f);
            }
        }
        feedback.moves = moves + 1;
        return feedback;
    }
    public void initialize()
    {
        atu = table.getContractId() % 5;
        initialState = new GameState(table);
        feedback = initialMove(initialState);
        for(int i = 0; i < feedback.trace.size(); i++)
        {
            System.out.println(CardFigure.values()[feedback.trace.get(i).y] + " " + CardColor.values()[feedback.trace.get(i).x]);
        }
        System.out.println(feedback.moves + " " + feedback.amount);
    }
    public Feedback initialMove(GameState g)
    {
        Feedback f;
        Feedback feedback = new Feedback();
        int moves = 0;
        feedback.amount = 1000 * (g.currPlayer % 2 == 1 ? 1 : 0) - 1;
        for(int i = 0; i < g.cards[g.currPlayer].size(); i++)
        {
            if(!valid(g, i))
                continue;
            f = move(new GameState(g), new IntPair(g.cards[g.currPlayer].get(i)), i);
            moves += f.moves;
            if((g.currPlayer % 2 == 1 && feedback.amount > f.amount) || (g.currPlayer % 2 == 0 && feedback.amount < f.amount))
            {
                feedback = new Feedback(f);
            }
        }
        feedback.moves = moves + 1;
        return feedback;
    }
}
