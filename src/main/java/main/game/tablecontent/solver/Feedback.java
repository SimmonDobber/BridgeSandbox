package main.game.tablecontent.solver;

import main.engine.structures.IntPair;

import java.util.ArrayList;
import java.util.List;

public class Feedback
{
    public int moves;
    public int amount;
    public List<IntPair> trace;
    public Feedback()
    {
        moves = 0;
        amount = 0;
        trace = new ArrayList<>();
    }
    public Feedback(Feedback f)
    {
        this.moves = f.moves;
        this.amount = f.amount;
        this.trace = new ArrayList<>(f.trace);
    }
}
