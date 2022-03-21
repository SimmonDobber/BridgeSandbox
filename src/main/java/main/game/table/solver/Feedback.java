package main.game.table.solver;

import main.game.table.PlayerSide;
import main.game.card.CardData;
import java.util.LinkedList;
import java.util.List;

import static main.game.GameConstants.PLAYER_COUNT;

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

    public List<Integer> getLastWinnersIds(TurnState initialTurnState){
        List<Integer> previousWinnersIds = new LinkedList<>();
        List<Integer> winnersIds = getWinnersIds(initialTurnState);
        previousWinnersIds.add(initialTurnState.lastWinner.ordinal());
        previousWinnersIds.addAll(winnersIds);
        return previousWinnersIds;
    }

    public List<Integer> getWinnersIds(TurnState initialTurnState){
        List<Integer> winnersIds = new LinkedList<>();
        List<PlayerSide> winners = getWinners(initialTurnState);
        for(PlayerSide winner : winners)
            winnersIds.add(winner.ordinal());
        return winnersIds;
    }

    public List<PlayerSide> getWinners(TurnState initialTurnState){
        List<PlayerSide> winnerIds = new LinkedList<>();
        List<CardData> fullMoveHistory = getFullMoveHistory(initialTurnState);
        PlayerSide lastWinner = initialTurnState.lastWinner;
        for(int i = 0; i < fullMoveHistory.size(); i += PLAYER_COUNT){
            lastWinner = getProcessedTurnState(fullMoveHistory, i, lastWinner).chooseWinningPlayer();
            winnerIds.add(lastWinner);
        }
        return winnerIds;
    }

    public List<CardData> getFullMoveHistory(TurnState initialTurnState){
        List<CardData> fullMoveHistory = getInitialTurnHistory(initialTurnState);
        fullMoveHistory.addAll(moveHistory);
        return fullMoveHistory;
    }

    private List<CardData> getInitialTurnHistory(TurnState initialTurnState){
        List<CardData> initialTurnHistory = new LinkedList<>();
        int lastWinnerID = initialTurnState.lastWinner.ordinal();
        for(int i = lastWinnerID; i < lastWinnerID + PLAYER_COUNT; i++){
            if(initialTurnState.cardData[i % PLAYER_COUNT] == null)
                break;
            else
                initialTurnHistory.add(initialTurnState.cardData[i % PLAYER_COUNT]);
        }
        return initialTurnHistory;
    }

    private TurnState getProcessedTurnState(List<CardData> fullMoveHistory, int turnIndex, PlayerSide lastWinner){
        CardData[] processedTurnCardData = new CardData[PLAYER_COUNT];
        for(int i = 0; i < PLAYER_COUNT; i++)
            processedTurnCardData[(i + lastWinner.ordinal()) % PLAYER_COUNT] = fullMoveHistory.get(turnIndex + i);
        return new TurnState(processedTurnCardData, lastWinner, lastWinner);
    }
}
