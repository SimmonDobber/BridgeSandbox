package main.game;

import lombok.Getter;
import lombok.Setter;
import main.engine.*;
import main.engine.display.Renderer;
import main.engine.display.Window;
import main.engine.structures.State;
import main.game.solver.Solver;

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
    public static final char[] WRITTEN_COLORS  = {'C', 'D', 'H', 'S', 'N'};
    private int width;
    private int height;
    @Getter
    @Setter
    private int contractId;
    @Getter
    @Setter
    private int currentPlayer;
    @Getter
    private int lastWinner;
    @Getter
    private IntPair taken;
    @Getter
    private Hand[] hand;
    @Getter
    private Card[] choosenCards;
    private Solver solver;

    public Table(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.lastWinner = 0;
        this.currentPlayer = 0;
        taken = new IntPair();
        hand = new Hand[PLAYER_COUNT];
        choosenCards = new Card[PLAYER_COUNT];
        dealHands(5);
        setContractId(18);
        reenableCards();
        solver = new Solver(this);
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
            if(lastWinner % 2 == 0)
                taken.x++;
            else
                taken.y++;
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
        for(int i = 0; i < PLAYER_COUNT; i++)
        {
            if(i == currentPlayer)
                continue;
            if(choosenCards[currentWinner].getColor() != atu && choosenCards[i].getColor() == atu)
                currentWinner = i;
            else if((choosenCards[i].getColor() != currentAtu) || (choosenCards[i].getColor() != atu && choosenCards[currentWinner].getColor() == atu));
            else if(choosenCards[currentWinner].getFigure() < choosenCards[i].getFigure())
                currentWinner = i;
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
                if(hand[i].getCard().get(j).getOwner() == currentPlayer && (currentPlayer == lastWinner || (hand[i].getCard().get(j).getColor() == choosenCards[lastWinner].getColor() || !hand[i].hasColor(choosenCards[lastWinner].getColor()))))
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
        solver.buttonUpdate(input, this);
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
        r.drawText("Taken; N/S - " + Integer.toString(taken.x) + " | W/E - " + Integer.toString(taken.y), 10, 90, GRAY, DEFAULT_FONT_SIZE, 1);
        for(int i = 0; i < PLAYER_COUNT; i++)
            hand[i].render(r);
        for(int i = 0; i < PLAYER_COUNT; i++)
        {
            if(choosenCards[i] != null)
                choosenCards[i].render(r);
        }
        solver.render(r);
    }
    public void nextPlayer()
    {
        this.currentPlayer  = (this.currentPlayer + 1) % PLAYER_COUNT;
    }
}
