package main.game.table.solver;

import main.engine.structures.observer.Observable;
import main.engine.structures.observer.Observer;
import main.game.Card;
import main.game.table.GameManager;
import main.game.table.PlayerSide;
import main.game.table.bestMovesTable.BestMovesTable;
import main.game.table.Table;
import main.game.table.card.CardColor;
import main.game.table.card.CardData;

import java.util.LinkedList;
import java.util.List;

import static main.game.GameConstants.PLAYER_COUNT;


public class Solver implements Observer
{
    private Table table;
    private GameManager gameManager;
    private BestMovesTable bestMovesTable;
    public Solver(Table table, BestMovesTable bestMovesTable){
        this.table = table;
        this.bestMovesTable = bestMovesTable;
        this.gameManager = table.getGameManager();
    }

    @Override
    public void update(Observable o, Object arg) {
        bestMovesTable.clearCardSignatureFields();
        run();
    }

    private void run(){
        GameState initialGameState = new GameState(getCardDataFromTable(), getCurrentTableTurnState(),
                gameManager.getCardAmount(), gameManager.getAtu());
        Feedback outcome = makeMove(initialGameState);
        showFeedback(outcome);
    }

    private void showFeedback(Feedback outcome){
        List<CardData> fullMoveHistory = outcome.getFullMoveHistory(getCurrentTableTurnState());
        List<Integer> lastWinnersIds = outcome.getLastWinnersIds(getCurrentTableTurnState());
        bestMovesTable.updateCardSignatureFields(fullMoveHistory, lastWinnersIds);
    }

    private Feedback makeMove(GameState gameState){
        GameState.totalMoves++;
        if(gameState.playerHands[gameState.getCurrentPlayerId()].isEmpty())
            return new Feedback(gameState.takenNS);
        return getBestMoveOutcome(gameState);
    }

    private Feedback getBestMoveOutcome(GameState gameState) {
        Feedback bestOutcome = new Feedback((gameState.isNSOnMove() ? -1 : GameState.initialCardAmount + 1));
        for(CardData card : gameState.playerHands[gameState.getCurrentPlayerId()]) {
            if(isMoveValid(gameState, card))
                bestOutcome = checkForBestOutcome(bestOutcome, updateAfterMove(gameState, card), gameState.isNSOnMove());
        }
        return bestOutcome;
    }

    private Feedback updateAfterMove(GameState gameState, CardData card){
        TurnState newTurnState = getNewTurnState(gameState, card);
        GameState newGameState = (newTurnState.isTurnFinished() ?
                getNewGameStateAfterTurn(gameState, newTurnState) : getNewGameStateAfterMove(gameState, newTurnState));
        return new Feedback(makeMove(newGameState), card);
    }

    private boolean isMoveValid(GameState gameState, CardData card){
        if(isCurrentPlayerLastWinner(gameState))
            return true;
        CardColor turnColor = gameState.getCurrentTurnColor();
        return card.color == turnColor || gameState.isPlayersColorEmpty(gameState.getCurrentPlayerId(), turnColor);
    }

    private boolean isCurrentPlayerLastWinner(GameState gameState){
        return gameState.getCurrentPlayerId() == gameState.getLastWinnerId();
    }

    private TurnState getNewTurnState(GameState gameState, CardData card){
        TurnState newTurnState = new TurnState(gameState.turnState);
        newTurnState.cardData[gameState.getCurrentPlayerId()] = card;
        newTurnState.nextPlayer();
        return newTurnState;
    }

    private GameState getNewGameStateAfterMove(GameState oldGameState, TurnState newTurnState){
        List<CardData>[] newPlayerHands = oldGameState.getCurrentPlayerHands();
        return new GameState(newPlayerHands, newTurnState, oldGameState.takenNS);
    }

    private GameState getNewGameStateAfterTurn(GameState oldGameState, TurnState newTurnState){
        PlayerSide winner = newTurnState.chooseWinningPlayer();
        List<CardData>[] newPlayerHands = getNewPlayerHandsAfterTurn(oldGameState, newTurnState);
        newTurnState = new TurnState(winner);
        int newTakenNS = getNewTakenNS(oldGameState, winner);
        return new GameState(newPlayerHands, newTurnState, newTakenNS);
    }

    private List<CardData>[] getNewPlayerHandsAfterTurn(GameState gameState, TurnState newTurnState){
        List<CardData>[] newPlayerHands = new List[PLAYER_COUNT];
        for(int i = 0; i < PLAYER_COUNT; i++)
            newPlayerHands[i] = getNewPlayerHandAfterTurn(gameState, newTurnState, i);
        return newPlayerHands;
    }

    private List<CardData> getNewPlayerHandAfterTurn(GameState gameState, TurnState newTurnState, int playerId){
        List<CardData> newPlayerHand = new LinkedList<>();
        for(CardData card : gameState.playerHands[playerId]){
            if(!card.equals(newTurnState.cardData[playerId]))
                newPlayerHand.add(card);
        }
        return newPlayerHand;
    }

    private int getNewTakenNS(GameState gameState, PlayerSide winner){
        if(winner == PlayerSide.N || winner == PlayerSide.S)
            return gameState.takenNS + 1;
        return gameState.takenNS;
    }

    private Feedback checkForBestOutcome(Feedback bestOutcomeSoFar, Feedback moveOutcome, boolean isNSOnMove){
        if(isNSOnMove && moveOutcome.outcome > bestOutcomeSoFar.outcome)
            return moveOutcome;
        else if(!isNSOnMove && moveOutcome.outcome < bestOutcomeSoFar.outcome)
            return moveOutcome;
        else
            return bestOutcomeSoFar;
    }

    private List[] getCardDataFromTable(){
        List[] playerHands = new List[PLAYER_COUNT];
        for(int i = 0; i < PLAYER_COUNT; i++)
            playerHands[i] = gameManager.getHandCardData(i);
        return playerHands;
    }

    private TurnState getCurrentTableTurnState(){
        CardData[] cardData = gameManager.getChosenCardsData();
        PlayerSide currentPlayer = gameManager.getCurrentPlayer();
        PlayerSide lastWinner = gameManager.getLastWinner();
        CardColor atu = gameManager.getAtu();
        return new TurnState(cardData, currentPlayer, lastWinner, atu);
    }
}

