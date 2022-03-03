package main.game.table;

import lombok.Getter;
import lombok.Setter;
import main.engine.display.Window;
import main.engine.structures.IntPair;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.Scene;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observable;
import main.engine.structures.observer.Observer;
import main.game.GameConstants;
import main.game.cardChoosePanel.AcceptChoiceButton;
import main.game.contractChoosePanel.ContractChooseButton;
import main.game.table.buttons.*;
import main.game.table.solver.Solver;
import main.game.table.card.CardColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static main.game.GameConstants.*;

@Getter
public class Table extends GameObject implements Scene, Observer
{
    @Getter
    private static int cardAmount;
    @Setter
    private int contractId;
    @Setter
    private PlayerSide currentPlayer;
    private PlayerSide lastWinner;
    private IntPair taken;
    private Hand[] hand;
    private TableCard[] chosenTableCards;
    private Solver solver;
    private TableButtonManager buttonManager;

    public Table()
    {
        super(new Position(), new Dimensions(Window.WIDTH, Window.HEIGHT), null);
        initializeTable();
        initializeRandomGame();
        initializeSpriteList();
        solver = new Solver(this);
        buttonManager = new TableButtonManager(this);
        children.add(buttonManager);
    }

    private void initializeTable() {
        hand = new Hand[PLAYER_COUNT];
        this.cardAmount = 5;
        this.contractId = 0;
    }

    private void initializeRandomGame() {
        resetGame();
        dealRandom();
        manageActivity();
        reLoadTextSprites();
    }

    private void initializeSetGame(List<Integer> cardsId) {
        resetGame();
        dealSetCards(cardsId);
        manageActivity();
        reLoadTextSprites();
    }

    private void resetGame() {
        taken = new IntPair();
        chosenTableCards = new TableCard[PLAYER_COUNT];
        this.lastWinner = PlayerSide.N;
        this.currentPlayer = PlayerSide.N;
    }

    private void initializeSpriteList()
    {
        spriteList.add(new Rectangle(new Position(), dim, GREEN, 1));
        spriteList.add(new Rectangle(new Position(411, 166), new Dimensions(375, 343), CYAN, BROWN, 1));
        loadTextSprites();
    }

    private void loadTextSprites()
    {
        spriteList.add(new Text("Contract; ", new Position(10, 25), DEFAULT_FONT_SIZE, GRAY, 1));
        spriteList.add(new Text("Current player; " + currentPlayer.getAsciiString(), new Position(10, 65), DEFAULT_FONT_SIZE, GRAY, 1));
        spriteList.add(new Text("Taken; N/S - " + taken.x + " | W/E - " + taken.y, new Position(10, 105),  DEFAULT_FONT_SIZE, GRAY, 1));
    }

    private void removeTextSprites()
    {
        for(int i = 0; i < spriteList.size(); i++)
        {
            if(spriteList.get(i).getClass().equals(Text.class))
                spriteList.remove(i--);
        }
    }

    private void reLoadTextSprites()
    {
        removeTextSprites();
        loadTextSprites();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if(o instanceof TableCard)
            updateCards((TableCard)o);
        if(o instanceof ContractChooseButton)
        updateContractButton((Integer)arg);
        if(o instanceof ShuffleButton)
            updateShuffleButton();
        if(o instanceof CardAmountChangeButton)
            updateCardAmountChangeButton((Integer)arg);
        if(o instanceof AcceptChoiceButton)
            updateCardChoose((List<Integer>)arg);
    }

    private void updateCardChoose(List<Integer> chosenCards)
    {
        initializeSetGame(chosenCards);
    }

    private void updateCards(TableCard playedTableCard)
    {
        playCard(playedTableCard);
    }

    private void updateContractButton(Integer recentlyChosen)
    {
        setContractId(recentlyChosen);
        //contractButton.reLoadTextSprites(contractId);
    }

    private void updateShuffleButton()
    {
        initializeRandomGame();
    }

    private void updateCardAmountChangeButton(Integer clickValue)
    {
        if(isCardAmountAbleToModify(clickValue))
        {
            cardAmount += clickValue;
            initializeRandomGame();
        }
    }

    private boolean isCardAmountAbleToModify(int clickValue)
    {
        return cardAmount + clickValue <= FIGURE_COUNT && cardAmount + clickValue >= 0;
    }

    private void dealRandom() {
        detachPreviousDeal();
        List<Integer> deck = createDeck();
        for (int i = 0; i < GameConstants.PLAYER_COUNT; i++) {
            hand[i] = dealToHand(deck, cardAmount, i);
            children.add(hand[i]);
            hand[i].attachObserversToCards(this);
        }
    }

    private void dealSetCards(List<Integer> cardsId)
    {
        detachPreviousDeal();
        for (int i = 0; i < GameConstants.PLAYER_COUNT; i++) {
            hand[i] = dealToHand(cardsId, cardAmount, i);
            children.add(hand[i]);
            hand[i].attachObserversToCards(this);
        }
    }

    private void detachPreviousDeal()
    {
        for(int i = 0; i < children.size(); i++)
        {
            if(children.get(i).getClass() == Hand.class || children.get(i).getClass() == TableCard.class)
                children.remove(i--);
        }
    }

    public void nextTurn() {
        hand[currentPlayer.ordinal()].removeCard(getPlayedCardId());
        currentPlayer = currentPlayer.nextPlayer();
        reLoadTextSprites();
        if (isPlayerFirstInTurn())
            summarizeTurn();
        manageActivity();
    }

    private PlayerSide selectWinner() {
        PlayerSide currentWinner = currentPlayer;
        CardColor currentAtu = chosenTableCards[currentPlayer.ordinal()].getColor();
        return compareCards(currentWinner, currentAtu);
    }

    private PlayerSide compareCards(PlayerSide currentWinner, CardColor currentAtu) {
        for (int i = 0; i < GameConstants.PLAYER_COUNT; i++) {
            if (i == currentPlayer.ordinal()) continue;
            if (isNewWinning(chosenTableCards[currentWinner.ordinal()], chosenTableCards[i], currentAtu))
                currentWinner = PlayerSide.values()[i];
        }
        return currentWinner;
    }

    private void manageActivity() {
        for (int i = 0; i < GameConstants.PLAYER_COUNT; i++) {
            manageHandActivity(hand[i]);
        }
    }

    private List<Integer> createDeck() {
        List<Integer> deck = new ArrayList<>();
        for (int i = 0; i < GameConstants.COLOR_COUNT * GameConstants.FIGURE_COUNT; i++) {
            deck.add(i);
        }
        Collections.shuffle(deck);
        return deck;
    }

    private Hand dealToHand(List<Integer> deck, int cardAmount, int playerId) {
        int[] temp = new int[cardAmount];
        for (int j = 0; j < cardAmount; j++) {
            temp[j] = deck.get(playerId * cardAmount + j);
        }
        Arrays.sort(temp);
        return new Hand(temp, cardAmount, playerId, this);
    }

    public void playCard(TableCard tableCard)
    {
        int handId = getCardsHandId(tableCard);
        moveCardToCenter(tableCard, handId);
        chosenTableCards[handId] = tableCard;
        tableCard.setActive(false);
        children.add(chosenTableCards[handId]);
        nextTurn();
    }

    private void moveCardToCenter(TableCard tableCard, int handId)
    {
        tableCard.setPos(new Position(Hand.OWNER_CENTER_X[handId], Hand.OWNER_CENTER_Y[handId]));
    }

    private int getCardsHandId(TableCard tableCard)
    {
        for(int i = 0; i < PLAYER_COUNT; i++)
        {
            if(hand[i].getTableCard().contains(tableCard))
                return i;
        }
        return -1;
    }

    private void summarizeTurn() {
        currentPlayer = lastWinner = selectWinner();
        reLoadTextSprites();
        clearTableCenter();
        addPoints();
    }

    private void addPoints() {
        if (lastWinner == PlayerSide.N || lastWinner == PlayerSide.S)
            taken.x++;
        else
            taken.y++;
        reLoadTextSprites();
    }

    private boolean isNewWinning(TableCard old, TableCard _new, CardColor currentAtu) {
        if (hasNewAtuAdvantage(old, _new))
            return true;
        if (!hasNewColorAdvantage(old, _new, currentAtu))
            return false;
        return hasNewFigureAdvantage(old, _new);
    }

    private void manageHandActivity(Hand hand) {
        for (int j = 0; j < hand.getTableCard().size(); j++)
        {
            hand.getTableCard().get(j).setActive(isCardToActivate(hand.getTableCard().get(j), !hand.hasColor(getFirstColorInTurn())));
        }
    }

    private boolean isCardToActivate(TableCard tableCard, boolean hasVoid) {
        if (!isCardOwnedByCurrentPlayer(tableCard))
            return false;
        if (isPlayerFirstInTurn())
            return true;
        if(isCardColorMatchingCurrentColor(tableCard))
            return true;
        return hasVoid;
    }

    private void clearTableCenter() {
        for (int i = 0; i < GameConstants.PLAYER_COUNT; i++)
        {
            children.remove(chosenTableCards[i]);
            chosenTableCards[i] = null;
        }
    }

    private TableCard getPlayedCardId() {
        for (int i = 0; i < hand[currentPlayer.ordinal()].getTableCard().size(); i++) {
            if (hasAlreadyPlayed(currentPlayer) && isPlayedCardMatchingTable(i))
                return hand[currentPlayer.ordinal()].getTableCard().get(i);
        }
        return null;
    }

    private boolean isPlayedCardMatchingTable(int cardId) {
        return hand[currentPlayer.ordinal()].getTableCard().get(cardId).getCardId() == chosenTableCards[currentPlayer.ordinal()].getCardId();
    }

    private boolean hasNewAtuAdvantage(TableCard old, TableCard _new) {
        return old.getColor() != getAtu() && _new.getColor() == getAtu();
    }

    private boolean hasNewColorAdvantage(TableCard old, TableCard _new, CardColor currentAtu) {
        return ((_new.getColor() == getAtu()) || (_new.getColor() == currentAtu && old.getColor() != getAtu()));
    }

    private boolean hasNewFigureAdvantage(TableCard old, TableCard _new) {
        return old.getFigure().ordinal() < _new.getFigure().ordinal();
    }

    private boolean isCardOwnedByCurrentPlayer(TableCard tableCard)
    {
        return hand[currentPlayer.ordinal()].getTableCard().contains(tableCard);
    }

    private boolean isCardColorMatchingCurrentColor(TableCard tableCard)
    {
        return tableCard.getColor() == chosenTableCards[lastWinner.ordinal()].getColor();
    }

    private CardColor getFirstColorInTurn()
    {
        if(chosenTableCards[lastWinner.ordinal()] != null)
            return chosenTableCards[lastWinner.ordinal()].getColor();
        return CardColor.NO_ATU;
    }

    private boolean hasAlreadyPlayed(PlayerSide p)
    {
        return chosenTableCards[p.ordinal()] != null;
    }

    private boolean isPlayerFirstInTurn()
    {
        return currentPlayer == lastWinner;
    }

    private CardColor getAtu()
    {
        return CardColor.values()[contractId % (GameConstants.COLOR_COUNT + 1)];
    }

    public String getName()
    {
        return "Table";
    }
}
