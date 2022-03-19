package main.game.table.solver;

import main.game.table.card.CardData;

import java.util.LinkedList;
import java.util.List;

public class Feedback
{
    public int outcome;
    public List<CardData> moveHistory;

    public Feedback(int bestOutcome) {
        outcome = bestOutcome;
        moveHistory = new LinkedList<>();
    }

    public Feedback(Feedback feedback, CardData playedCard){
        outcome = feedback.outcome;
        moveHistory = new LinkedList<>();
        moveHistory.add(playedCard);
        moveHistory.addAll(feedback.moveHistory);
    }
}
