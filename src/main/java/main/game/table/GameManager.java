package main.game.table;

import lombok.Getter;
import lombok.Setter;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observable;
import main.engine.structures.observer.Observer;
import main.game.GameConstants;
import main.game.buttons.CardAmountChangeButton;
import main.game.buttons.ShuffleButton;
import main.game.cardChoosePanel.AcceptChoiceButton;
import main.game.contractChoosePanel.ContractChooseButton;
import main.game.card.CardColor;
import main.game.card.CardData;

import java.util.*;

import static main.game.GameConstants.*;

@Getter
public class GameManager extends GameObject implements Observer
{
    @Setter private int contractId;
    @Setter private PlayerSide currentPlayer;
    private int cardAmount;
    private PlayerSide lastWinner;
    private int taken[];
    private Hand[] hand;
    private TableCard[] chosenCards;
    private Table table;

    public GameManager(GameObject parent) {
        super(parent);
        table = (Table)(parent);
        this.hand = new Hand[PLAYER_COUNT];
        this.taken = new int [PLAYER_COUNT / 2];
        this.cardAmount = 5;
        this.contractId = 0;
        initializeGame();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof TableCard)
            playCard((TableCard)o);
        if(o instanceof ShuffleButton)
            initializeGame();
        if(o instanceof CardAmountChangeButton)
            updateCardAmount((Integer)arg);
        if(o instanceof AcceptChoiceButton)
            initializeGame((List<Integer>)arg);
        if(o instanceof ContractChooseButton)
            updateContract((Integer)arg);
    }

    public String getCurrentPlayerAsciiString() {
        return currentPlayer.getAsciiString();
    }

    public List<CardData> getHandCardData(int handId){
        return hand[handId].getCardsData();
    }

    public CardData[] getChosenCardsData(){
        CardData[] cardData = new CardData[PLAYER_COUNT];
        for(int i = 0; i < PLAYER_COUNT; i++)
            cardData[i] = (chosenCards[i] == null ? null : chosenCards[i].getCardData());
        return cardData;
    }

    public void initializeGame() {
        resetVariables();
        resetBestMoveTable();
        dealRandom();
        manageActivity();
    }

    public CardColor getAtu() {
        return CardColor.values()[contractId % (GameConstants.COLOR_COUNT + 1)];
    }

    private void updateCardAmount(Integer clickValue) {
        if(isCardAmountAbleToModify(clickValue)) {
            cardAmount += clickValue;
            initializeGame();
        }
    }

    private void updateContract(Integer recentlyChosen)
    {
        setContractId(recentlyChosen);
        table.getButtonManager().reloadButtons();
    }

    private void initializeGame(List<Integer> cardsId) {
        resetVariables();
        resetBestMoveTable();
        dealSetCards(cardsId);
        manageActivity();
    }

    private void resetVariables() {
        taken = new int[2];
        chosenCards = new TableCard[PLAYER_COUNT];
        lastWinner = PlayerSide.N;
        currentPlayer = PlayerSide.N;
    }

    private void resetBestMoveTable(){
        ((Table)(parent)).clearBestMovesTable();
    }

    private void dealRandom() {
        detachPreviousDeal();
        List<Integer> deck = createDeck();
        for (int i = 0; i < GameConstants.PLAYER_COUNT; i++) {
            hand[i] = dealToHand(deck, cardAmount, i);
            children.add(hand[i]);
            hand[i].attachObserversToCards(((Table)(parent)));
        }
    }

    private void dealSetCards(List<Integer> cardsId) {
        detachPreviousDeal();
        for (int i = 0; i < GameConstants.PLAYER_COUNT; i++) {
            hand[i] = dealToHand(cardsId, cardAmount, i);
            children.add(hand[i]);
            hand[i].attachObserversToCards(((Table)(parent)));
        }
    }

    private void detachPreviousDeal() {
        for(int i = 0; i < children.size(); i++) {
            if(children.get(i) instanceof Hand || children.get(i) instanceof TableCard)
                children.remove(i--);
        }
    }

    private List<Integer> createDeck() {
        List<Integer> deck = new ArrayList<>();
        for (int i = 0; i < DECK_SIZE; i++)
            deck.add(i);
        Collections.shuffle(deck);
        return deck;
    }

    private Hand dealToHand(List<Integer> deck, int cardAmount, int playerId) {
        Integer[] cardIds = new Integer[cardAmount];
        for (int j = 0; j < cardAmount; j++) {
            cardIds[j] = deck.get(playerId * cardAmount + j);
        }
        Arrays.sort(cardIds, Comparator.comparingInt((Integer a) -> ((a % COLOR_COUNT) * FIGURE_COUNT) + a / COLOR_COUNT));
        return new Hand(cardIds, cardAmount, playerId, this);
    }

    private void nextTurn() {
        hand[currentPlayer.ordinal()].removeCard(getPlayedCardId());
        currentPlayer = currentPlayer.nextPlayer();
        if (isPlayerFirstInTurn())
            summarizeTurn();
        manageActivity();
    }

    private PlayerSide selectWinner() {
        PlayerSide currentWinner = currentPlayer;
        CardColor currentAtu = chosenCards[currentPlayer.ordinal()].getColor();
        return compareCards(currentWinner, currentAtu);
    }

    private PlayerSide compareCards(PlayerSide currentWinner, CardColor currentAtu) {
        for (int i = 0; i < GameConstants.PLAYER_COUNT; i++) {
            if (i == currentPlayer.ordinal()) continue;
            if (isNewWinning(chosenCards[currentWinner.ordinal()], chosenCards[i], currentAtu))
                currentWinner = PlayerSide.values()[i];
        }
        return currentWinner;
    }

    private void manageActivity() {
        for (int i = 0; i < GameConstants.PLAYER_COUNT; i++) {
            manageHandActivity(hand[i]);
        }
    }

    private void playCard(TableCard tableCard) {
        int handId = getCardsHandId(tableCard);
        moveCardToCenter(tableCard, handId);
        chosenCards[handId] = tableCard;
        tableCard.setActive(false);
        children.add(chosenCards[handId]);
        nextTurn();
    }

    private void moveCardToCenter(TableCard tableCard, int handId) {
        tableCard.setPos(new Position(Hand.CENTER_POS_X[handId], Hand.CENTER_POS_Y[handId]));
    }

    private int getCardsHandId(TableCard tableCard) {
        for(int i = 0; i < PLAYER_COUNT; i++) {
            if(hand[i].getCards().contains(tableCard))
                return i;
        }
        return -1;
    }

    private void summarizeTurn() {
        currentPlayer = lastWinner = selectWinner();
        clearTableCenter();
        addPoints();
    }

    private void addPoints() {
        taken[lastWinner.ordinal() % 2]++;
    }

    private boolean isNewWinning(TableCard old, TableCard _new, CardColor currentAtu) {
        if (hasNewAtuAdvantage(old, _new))
            return true;
        if (!hasNewColorAdvantage(old, _new, currentAtu))
            return false;
        return hasNewFigureAdvantage(old, _new);
    }

    private void manageHandActivity(Hand hand) {
        for (int j = 0; j < hand.getCards().size(); j++)
            hand.getCards().get(j).setActive(isCardToActivate(hand.getCards().get(j), !hand.hasColor(getFirstColorInTurn())));
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
        for (int i = 0; i < GameConstants.PLAYER_COUNT; i++) {
            children.remove(chosenCards[i]);
            chosenCards[i] = null;
        }
    }

    private TableCard getPlayedCardId() {
        for (int i = 0; i < hand[currentPlayer.ordinal()].getCards().size(); i++) {
            if (hasAlreadyPlayed(currentPlayer) && isPlayedCardMatchingTable(i))
                return hand[currentPlayer.ordinal()].getCards().get(i);
        }
        return null;
    }

    private CardColor getFirstColorInTurn() {
        if(chosenCards[lastWinner.ordinal()] != null)
            return chosenCards[lastWinner.ordinal()].getColor();
        return CardColor.NO_ATU;
    }

    private boolean isPlayedCardMatchingTable(int cardId) {
        return hand[currentPlayer.ordinal()].getCards().get(cardId).getCardId() == chosenCards[currentPlayer.ordinal()].getCardId();
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

    private boolean isCardOwnedByCurrentPlayer(TableCard tableCard) {
        return hand[currentPlayer.ordinal()].getCards().contains(tableCard);
    }

    private boolean isCardColorMatchingCurrentColor(TableCard tableCard) {
        return tableCard.getColor() == chosenCards[lastWinner.ordinal()].getColor();
    }

    private boolean isCardAmountAbleToModify(int clickValue) {
        return cardAmount + clickValue <= FIGURE_COUNT && cardAmount + clickValue >= 0;
    }

    private boolean hasAlreadyPlayed(PlayerSide p) {
        return chosenCards[p.ordinal()] != null;
    }

    private boolean isPlayerFirstInTurn() {
        return currentPlayer == lastWinner;
    }
}
