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

public class Table implements State {
    public static final char[] WRITTEN_COLORS = {'C', 'D', 'H', 'S', 'N'};
    private int width;
    private int height;
    @Getter
    @Setter
    private int contractId;
    @Getter
    @Setter
    private PlayerSide currentPlayer;
    @Getter
    private PlayerSide lastWinner;
    @Getter
    private IntPair taken;
    @Getter
    private Hand[] hand;
    @Getter
    private Card[] choosenCards;
    private Solver solver;

    public Table(int width, int height) {
        this.width = width;
        this.height = height;
        initializeTable();
        initializeGame();
        manageActivity();
        solver = new Solver(this);
    }

    private void initializeTable() {

        taken = new IntPair();
        hand = new Hand[GameConstants.PLAYER_COUNT];
        choosenCards = new Card[GameConstants.PLAYER_COUNT];

    }

    private void initializeGame() {
        this.lastWinner = PlayerSide.N;
        this.currentPlayer = PlayerSide.N;
        dealRandom(5);
        setContractId(18);
    }

    private void dealRandom(int cardAmount) {
        List<Integer> deck = createDeck();
        for (int i = 0; i < GameConstants.PLAYER_COUNT; i++) {
            hand[i] = dealToHand(deck, cardAmount, i);
        }
    }

    public void nextTurn() {
        removeCard(getPlayedCardId());
        currentPlayer = currentPlayer.nextPlayer();
        if (isPlayerFirstInTurn())
            summarizeTurn();
        manageActivity();
    }

    private PlayerSide selectWinner() {
        PlayerSide currentWinner = currentPlayer;
        CardColor currentAtu = choosenCards[currentPlayer.ordinal()].getColor();
        return compareCards(currentWinner, currentAtu);
    }

    private PlayerSide compareCards(PlayerSide currentWinner, CardColor currentAtu) {
        for (int i = 0; i < GameConstants.PLAYER_COUNT; i++) {
            if (i == currentPlayer.ordinal()) continue;
            if (isNewWinning(choosenCards[currentWinner.ordinal()], choosenCards[i], currentAtu))
                currentWinner = PlayerSide.values()[i];
        }
        return currentWinner;
    }

    private void manageActivity() {
        for (int i = 0; i < GameConstants.PLAYER_COUNT; i++) {
            manageHandActivity(hand[i]);
        }
    }

    @Override
    public void update(Window window, Input input, LoopTimer loopTimer)
    {
        for(int i = 0; i < GameConstants.PLAYER_COUNT; i++)
            hand[i].update(input, this);
        solver.buttonUpdate(input, this);
    }
    @Override
    public void render(Renderer r)
    {
        renderBackground(r);
        renderGameInfo(r);
        renderChosenCards(r);
        renderHands(r);

        solver.render(r);
    }

    private void renderBackground(Renderer r)
    {
        r.drawRectangle(0, 0, width, height, 0xFF009900, 1);
        r.drawRectangle(410, 166, 377, 343, 0xFF8B4513, 1);
        r.drawRectangle(412, 168, 373, 339, 0x7700FFFF, 1);
    }

    private void renderGameInfo(Renderer r)
    {
        r.drawText("Contract; " + (contractId / 5 + 1) , 10, 10, GameConstants.GRAY, GameConstants.DEFAULT_FONT_SIZE, 1);
        r.drawText(Character.toString((char)(getAtu().ordinal() + '[')), 176, 10, Card.getColorValue(CardColor.values()[contractId % 5]), GameConstants.DEFAULT_FONT_SIZE, 1);
        r.drawText("Current player; " + currentPlayer.getAsciiString(), 10, 50, GameConstants.GRAY, GameConstants.DEFAULT_FONT_SIZE, 1);
        r.drawText("Taken; N/S - " + taken.x + " | W/E - " + taken.y, 10, 90, GameConstants.GRAY, GameConstants.DEFAULT_FONT_SIZE, 1);
    }
    private void renderChosenCards(Renderer r)
    {
        for(int i = 0; i < GameConstants.PLAYER_COUNT; i++)
        {
            if(choosenCards[i] != null)
                choosenCards[i].render(r);
        }
    }
    private void renderHands(Renderer r)
    {
        for(int i = 0; i < GameConstants.PLAYER_COUNT; i++)
            hand[i].render(r);
    }

    private List<Integer> createDeck() {
        List<Integer> deck = new ArrayList<>();
        for (int i = 0; i < GameConstants.COLOR_COUNT * GameConstants.FIGURE_COUNT; i++) {
            deck.add(i);
        }
        Collections.shuffle(deck);
        return deck;
    }

    private Hand dealToHand(List<Integer> deck, int cardAmount, int player) {
        int[] temp = new int[cardAmount];
        for (int j = 0; j < cardAmount; j++) {
            temp[j] = deck.get(player * cardAmount + j);
        }
        Arrays.sort(temp);
        return new Hand(temp, cardAmount, player);
    }

    private void removeCard(int id) {
        if (id < 0) return;
        hand[currentPlayer.ordinal()].getCard().remove(id);
        repositionCards();
    }

    private void summarizeTurn() {
        currentPlayer = lastWinner = selectWinner();
        clearTableCenter();
        addPoints();
    }

    private void addPoints() {
        if (lastWinner == PlayerSide.N || lastWinner == PlayerSide.S)
            taken.x++;
        else
            taken.y++;
    }

    private void repositionCards() {
        for (int i = 0; i < hand[currentPlayer.ordinal()].getCard().size(); i++) {
            hand[currentPlayer.ordinal()].getCard().get(i).setX(hand[currentPlayer.ordinal()].getX() + i * Hand.CARD_SPACE);
        }
    }

    private boolean isNewWinning(Card old, Card _new, CardColor currentAtu) {
        if (hasNewAtuAdvantage(old, _new))
            return true;
        else if (!hasNewColorAdvantage(old, _new, currentAtu))
            return false;
        else if (hasNewFigureAdvantage(old, _new))
            return true;
        return false;
    }

    private void manageHandActivity(Hand hand) {
        for (int j = 0; j < hand.getCard().size(); j++)
        {
            hand.getCard().get(j).setActive(isCardToActivate(hand.getCard().get(j), !hand.hasColor(getFirstColorInTurn())));
        }
    }

    private boolean isCardToActivate(Card card, boolean hasVoid) {
        if (!isCardOwnedByCurrentPlayer(card))
            return false;
        if (isPlayerFirstInTurn())
            return true;
        if(isCardColorMatchingCurrentColor(card))
            return true;
        if(hasVoid)
            return true;
        return false;
    }

    private void clearTableCenter() {
        for (int i = 0; i < GameConstants.PLAYER_COUNT; i++)
            choosenCards[i] = null;
    }

    private int getPlayedCardId() {
        for (int i = 0; i < hand[currentPlayer.ordinal()].getCard().size(); i++) {
            if (hasAlreadyPlayed(currentPlayer) && isPlayedCardMatchingTable(i))
                return i;
        }
        return -1;
    }

    private boolean isPlayedCardMatchingTable(int cardId) {
        return hand[currentPlayer.ordinal()].getCard().get(cardId).getId() == choosenCards[currentPlayer.ordinal()].getId();
    }

    private boolean hasNewAtuAdvantage(Card old, Card _new) {
        return old.getColor() != getAtu() && _new.getColor() == getAtu();
    }

    private boolean hasNewColorAdvantage(Card old, Card _new, CardColor currentAtu) {
        return ((_new.getColor() == currentAtu) && (_new.getColor() == getAtu() || old.getColor() != getAtu()));
    }

    private boolean hasNewFigureAdvantage(Card old, Card _new) {
        return old.getFigure().ordinal() < _new.getFigure().ordinal();
    }

    private boolean isCardOwnedByCurrentPlayer(Card card)
    {
        return card.getOwner() == currentPlayer.ordinal();
    }

    private boolean isCardColorMatchingCurrentColor(Card card)
    {
        return card.getColor() == choosenCards[lastWinner.ordinal()].getColor();
    }

    private CardColor getFirstColorInTurn()
    {
        if(choosenCards[lastWinner.ordinal()] != null)
            return choosenCards[lastWinner.ordinal()].getColor();
        return CardColor.NO_ATU;
    }

    private boolean hasAlreadyPlayed(PlayerSide p)
    {
        return choosenCards[p.ordinal()] != null;
    }

    private boolean isPlayerFirstInTurn()
    {
        return currentPlayer == lastWinner;
    }

    private CardColor getAtu()
    {
        return CardColor.values()[contractId % (GameConstants.COLOR_COUNT + 1)];
    }
}
