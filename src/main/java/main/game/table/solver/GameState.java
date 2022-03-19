package main.game.table.solver;

import main.game.table.PlayerSide;
import main.game.table.card.CardColor;
import main.game.table.card.CardData;

import java.util.LinkedList;
import java.util.List;

import static main.game.GameConstants.PLAYER_COUNT;


public class GameState
{
    public static CardColor atu;
    public static int initialCardAmount;
    public static int totalMoves;
    public TurnState turnState;
    public List<CardData>[] playerHands;
    public int takenNS;

    public GameState(List<CardData>[] playerHands, TurnState turnState, int initialCardAmount, CardColor atu){
        GameState.initialCardAmount = initialCardAmount;
        GameState.atu = atu;
        GameState.totalMoves = 0;
        this.turnState = turnState;
        this.playerHands = playerHands;
        this.takenNS = 0;
    }

    public GameState(List<CardData>[] playerHands, TurnState turnState, int takenNS){
        this.playerHands = playerHands;
        this.turnState = turnState;
        this.takenNS = takenNS;
    }

    public int getCurrentPlayerId(){
        return turnState.currentPlayer.ordinal();
    }

    public int getLastWinnerId(){
        return turnState.lastWinner.ordinal();
    }

    public boolean isNSOnMove(){
        return turnState.currentPlayer == PlayerSide.N || turnState.currentPlayer == PlayerSide.S;
    }

    public List<CardData>[] getCurrentPlayerHands(){
        List<CardData>[] currentPlayerHands = new List[PLAYER_COUNT];
        for(int i = 0; i < PLAYER_COUNT; i++)
            currentPlayerHands[i] = getCurrentPlayerHand(i);
        return currentPlayerHands;
    }

    public CardColor getCurrentTurnColor(){
        return turnState.cardData[turnState.lastWinner.ordinal()].color;
    }

    public boolean isPlayersColorEmpty(int playerId, CardColor color){
        for(CardData card : playerHands[playerId]){
            if(card.color == color)
                return false;
        }
        return true;
    }

    private List<CardData> getCurrentPlayerHand(int playerId){
        List<CardData> currentPlayerHand = new LinkedList<>();
        for(CardData card : playerHands[playerId])
            currentPlayerHand.add(new CardData(card));
        return currentPlayerHand;
    }
}
