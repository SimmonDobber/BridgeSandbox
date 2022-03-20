package main.game.table.solver;

import main.game.table.PlayerSide;
import main.game.table.card.CardColor;
import main.game.table.card.CardData;

import static main.game.GameConstants.PLAYER_COUNT;

public class TurnState
{
    public static CardColor atu;
    public PlayerSide currentPlayer;
    public PlayerSide lastWinner;
    public CardData[] cardData;

    public TurnState(CardData[] cardData, PlayerSide currentPlayer, PlayerSide lastWinner, CardColor atu){
        TurnState.atu = atu;
        this.currentPlayer = currentPlayer;
        this.lastWinner = lastWinner;
        this.cardData = cardData;
    }

    public TurnState(CardData[] cardData, PlayerSide currentPlayer, PlayerSide lastWinner){
        this.currentPlayer = currentPlayer;
        this.lastWinner = lastWinner;
        this.cardData = cardData;
    }

    public TurnState(PlayerSide lastWinner){
        this.lastWinner = lastWinner;
        this.currentPlayer = lastWinner;
        this.cardData = new CardData[PLAYER_COUNT];
    }

    public TurnState(TurnState turnState){
        this.currentPlayer = turnState.currentPlayer;
        this.lastWinner = turnState.lastWinner;
        this.cardData = new CardData[PLAYER_COUNT];
        for(int i = 0; i < PLAYER_COUNT; i++){
            if(turnState.cardData[i] != null)
                this.cardData[i] = new CardData(turnState.cardData[i]);
        }
    }

    public PlayerSide chooseWinningPlayer() {
        CardData[] currentCardData = getCurrentCardData();
        filterOutNonWinningColorCards();
        PlayerSide winner = chooseBiggestFigure();
        cardData = currentCardData;
        return winner;
    }

    public boolean isTurnFinished(){
        for(int i = 0; i < PLAYER_COUNT; i++) {
            if(cardData[i] == null)
                return false;
        }
        return true;
    }

    public void nextPlayer(){
        currentPlayer = currentPlayer.nextPlayer();
    }

    private void filterOutNonWinningColorCards(){
        if(isAtuPresent())
            filterOutNonAtu();
        else
            filterOutNonFirstCardColor();
    }

    private void filterOutNonFirstCardColor(){
        for(int i = 0; i < PLAYER_COUNT; i++){
            if(cardData[i].color != cardData[lastWinner.ordinal()].color)
                cardData[i] = null;
        }
    }

    private void filterOutNonAtu() {
        for(int i = 0; i < PLAYER_COUNT; i++){
            if(cardData[i].color != atu)
                cardData[i] = null;
        }
    }

    private boolean isAtuPresent(){
        for(CardData card : cardData){
            if(card.color == atu)
                return true;
        }
        return false;
    }

    private PlayerSide chooseBiggestFigure() {
        int biggest = 0;
        PlayerSide biggestOwner = null;
        for(int i = 0; i < PLAYER_COUNT; i++) {
            if(cardData[i] != null && cardData[i].figure.ordinal() >= biggest) {
                biggest = cardData[i].figure.ordinal();
                biggestOwner = PlayerSide.values()[i];
            }
        }
        return biggestOwner;
    }

    private CardData[] getCurrentCardData(){
        CardData[] currentCardData = new CardData[PLAYER_COUNT];
        for(int i = 0; i < PLAYER_COUNT; i++)
            currentCardData[i] = new CardData(cardData[i]);
        return currentCardData;
    }
}
