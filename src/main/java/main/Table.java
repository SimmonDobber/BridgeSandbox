package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Table implements State
{
    public static final int COLOR_COUNT = 4;
    public static final int PLAYER_COUNT = 4;
    public static final int FIGURE_COUNT = 13;
    public static final int DEFAULT_FONT_SIZE = 32;
    public static final int RED = 0xFFB8000A;
    public static final int BLACK = 0xFF000000;
    public static final int SILVER = 0xFFE8E8E8;
    public static final int GRAY = 0xFFB0B0B0;
    public static final char[] PLAYERS = {'N', 'E', 'S', 'W'};
    public static final char[] FIGURES = {'2', '3', '4', '5', '6', '7', '8', '9', ':', 'J', 'Q', 'K', 'A'};
    public static final char[] COLORS  = {'[', '\\', ']', '^', '_'};
    private int width;
    private int height;
    private int contractId;
    private int currentPlayer;
    private int lastWinner;
    private int taken[];
    private Hand[] hand;
    private Card[] choosenCards;

    public Table(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.lastWinner = 0;
        this.currentPlayer = 0;
        taken = new int[2];
        taken[0] = taken[1] = 0;
        hand = new Hand[PLAYER_COUNT];
        choosenCards = new Card[PLAYER_COUNT];
        dealHands(13);
        setContractId(14);
        reenableCards();
    }
    private void dealHands(int cardAmount)
    {
        List<Integer> deck = new ArrayList<>();
        for (int i = 0; i < COLOR_COUNT * FIGURE_COUNT; i++) {
            deck.add(i);
        }
        Collections.shuffle(deck);
        for(int i = 0; i < PLAYER_COUNT; i++)
        {
            int[] temp = new int[cardAmount];
            for(int j = 0; j < cardAmount; j++)
            {
                temp[j] = deck.get(i * cardAmount + j);
            }
            Arrays.sort(temp);
            hand[i] = new Hand(temp, cardAmount, i);
        }
    }
    public void nextTurn()
    {
        for(int i = 0; i < hand[currentPlayer].getCard().size(); i++)
        {
            if((choosenCards[currentPlayer] != null) && (hand[currentPlayer].getCard().get(i).getId() == choosenCards[currentPlayer].getId()))
            {
                removeCard(i);
            }
        }
        nextPlayer();
        if(currentPlayer == lastWinner)
        {
            lastWinner = selectWinner();
            taken[lastWinner % 2]++;
            currentPlayer = lastWinner;
            for(int i = 0; i < PLAYER_COUNT; i++)
                choosenCards[i] = null;
        }
        reenableCards();
    }
    private int selectWinner()
    {
        int atu = contractId % 5;
        int currentWinner = currentPlayer;
        int currentAtu = choosenCards[currentPlayer].getColor();
        System.out.println(currentAtu + " " + currentWinner);
        for(int i = 0; i < PLAYER_COUNT; i++)
        {
            if(i == currentPlayer)
                continue;
            if(choosenCards[currentWinner].getColor() != atu && choosenCards[i].getColor() == atu)
                currentWinner = i;
            else if(choosenCards[i].getColor() != currentAtu);
            else if(choosenCards[currentWinner].getFigure() < choosenCards[i].getFigure())
                currentWinner = i;
            System.out.println(currentAtu + " " + currentWinner);
        }

        return currentWinner;
    }
    private void removeCard(int id)
    {
        hand[currentPlayer].getCard().remove(id);
        for(int i = 0; i < hand[currentPlayer].getCard().size(); i++)
        {
            hand[currentPlayer].getCard().get(i).setX(hand[currentPlayer].getX() + i * Hand.CARD_SPACE);
        }
    }
    private void reenableCards()
    {
        for(int i = 0; i < PLAYER_COUNT; i++)
        {
            for(int j = 0; j < hand[i].getCard().size(); j++)
            {
                if(hand[i].getCard().get(j).getOwner() == currentPlayer)
                    hand[i].getCard().get(j).setActive(true);
                else
                    hand[i].getCard().get(j).setActive(false);
            }
        }
    }

    @Override
    public void update(Window window, Input input, LoopTimer loopTimer)
    {
        for(int i = 0; i < 4; i++)
            hand[i].update(input, this);
    }
    @Override
    public void render(Renderer r)
    {
        r.drawRectangle(0, 0, width, height, 0xFF009900, 1);
        r.drawRectangle(410, 166, 377, 343, 0xFF8B4513, 1);
        r.drawRectangle(412, 168, 373, 339, 0x7700FFFF, 1);
        r.drawText("Contract; " + Integer.toString(contractId / 5 + 1) , 10, 10, GRAY, DEFAULT_FONT_SIZE, 1);
        r.drawText(Character.toString((char)(contractId % 5 + '[')), 176, 10, Card.getColorValue(contractId % 5), DEFAULT_FONT_SIZE, 1);
        r.drawText("Current player; " + Character.toString(PLAYERS[currentPlayer]), 10, 50, GRAY, DEFAULT_FONT_SIZE, 1);
        r.drawText("Taken; N/S - " + Integer.toString(taken[0]) + " | W/E - " + Integer.toString(taken[1]), 10, 90, GRAY, DEFAULT_FONT_SIZE, 1);
        for(int i = 0; i < PLAYER_COUNT; i++)
            hand[i].render(r);
        for(int i = 0; i < PLAYER_COUNT; i++)
        {
            if(choosenCards[i] != null)
                choosenCards[i].render(r);
        }
    }

    public int getContractId() {
        return contractId;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public Hand[] getHand() {
        return hand;
    }

    public Card[] getChoosenCards() {
        return choosenCards;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    public void nextPlayer()
    {
        this.currentPlayer  = (this.currentPlayer + 1) % PLAYER_COUNT;
    }
}
