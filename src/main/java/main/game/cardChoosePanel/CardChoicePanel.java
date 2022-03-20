package main.game.cardChoosePanel;

import lombok.Getter;
import main.engine.ProgramContainer;
import main.engine.display.Window;
import main.engine.structures.Scene;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.game.buttons.CardAmountChangeButton;
import main.game.table.GameManager;
import main.game.table.card.CardData;

import java.util.LinkedList;

import static main.game.GameConstants.*;

public class CardChoicePanel extends GameObject implements Scene
{
    private static final int CARD_SPACE = 20;
    private static final int CARD_ROW_OFFSET = 96;
    private static final int PLAYER_SIGNATURE_SIZE = 80;
    @Getter
    private ChoiceCard[][] card;
    private AcceptChoiceButton acceptChoiceButton;
    private CardAmountChangeButton cardAmountChangeButton;
    @Getter
    private CardChoicePanelTextManager cardChoosePanelTextManager;
    private GameManager gameManager;

    public CardChoicePanel(GameManager gameManager) {
        super(new Position(), new Dimensions(Window.WIDTH, Window.HEIGHT), null);
        this.gameManager = gameManager;
        initializeTextManager();
        initializeSprites();
        initializeCards();
        initializeButtons();
    }

    private void initializeCards() {
        card = new ChoiceCard[PLAYER_COUNT][DECK_SIZE];
        for(int i = 0; i < PLAYER_COUNT; i++)
            initializePlayerCards(i);
    }

    private void initializeTextManager() {
        this.cardChoosePanelTextManager = new CardChoicePanelTextManager(gameManager, this);
        children.add(cardChoosePanelTextManager);
    }

    private void initializeSprites() {
        spriteList.add(new Rectangle(new Position(), dim, GREEN, 1));
        spriteList.add(new Text("N;", new Position(16, 16), PLAYER_SIGNATURE_SIZE, GRAY, 1));
        spriteList.add(new Text("E;", new Position(30, 136), PLAYER_SIGNATURE_SIZE, GRAY, 1));
        spriteList.add(new Text("S;", new Position(26, 256), PLAYER_SIGNATURE_SIZE, GRAY, 1));
        spriteList.add(new Text("W;", new Position(6, 376), PLAYER_SIGNATURE_SIZE, GRAY, 1));
    }

    private void initializeButtons() {
        initializeAcceptChoiceButton();
        initializeCardAmountChangeButton();
    }

    private void initializeAcceptChoiceButton(){
        acceptChoiceButton = new AcceptChoiceButton(this);
        children.add(acceptChoiceButton);
        acceptChoiceButton.attach(gameManager);
        acceptChoiceButton.attach(ProgramContainer.getProgramContainer());
    }

    private void initializeCardAmountChangeButton(){
        cardAmountChangeButton = new CardAmountChangeButton(new Position(852, 571), this);
        children.add(cardAmountChangeButton);
        cardAmountChangeButton.attach(gameManager);
        cardAmountChangeButton.attach(cardChoosePanelTextManager);
    }

    public void clearCardChoices() {
        for(int i = 0; i < PLAYER_COUNT; i++) {
            for(int j = 0; j < DECK_SIZE; j++) {
                card[i][j].setCurrentState(0);
                card[i][j].attach(acceptChoiceButton);
            }
        }
    }

    public void reloadTextSprites(){
        cardChoosePanelTextManager.reloadTexts();
    }

    public LinkedList<Integer> groupChosenCards() {
        LinkedList<Integer> cards = new LinkedList<>();
        for(int i = 0; i < PLAYER_COUNT; i++) {
            for(int j = 0; j < DECK_SIZE; j++) {
                if(card[i][j].getCurrentState() == 1)
                    cards.add(card[i][j].getCardId());
            }
        }
        return cards;
    }

    public LinkedList<Integer>[] getGroupedCardsByPlayer() {
        LinkedList[] cards = new LinkedList[PLAYER_COUNT];
        for(int i = 0; i < PLAYER_COUNT; i++)
            cards[i] = getPlayersChosenCards(i);
        return cards;
    }

    private LinkedList<Integer> getPlayersChosenCards(int playerId){
        LinkedList<Integer> cards = new LinkedList<>();
        for(int j = 0; j < DECK_SIZE; j++) {
            if(card[playerId][j].getCurrentState() == 1)
                cards.add(card[playerId][j].getCardId());
        }
        return cards;
    }

    public int getCardAmount(){
        return gameManager.getCardAmount();
    }

    private void initializePlayerCards(int playerId) {
        for(int i = 0; i < COLOR_COUNT; i++) {
            for(int j = 0; j < FIGURE_COUNT; j++) {
                int cardId = i + j * COLOR_COUNT;
                card[playerId][cardId] = initializeCard(playerId, cardId);
            }
        }
    }

    private ChoiceCard initializeCard(int playerId, int cardId) {
        Position cardPos = new Position(getCardXPosInRow(cardId),ChoiceCard.DEFAULT_HEIGHT * playerId);
        ChoiceCard card = new ChoiceCard(cardPos, this, new CardData(cardId));
        children.add(card);
        return card;
    }

    private int getCardXPosInRow(int cardId) {
        int figureId = cardId / COLOR_COUNT;
        int colorId = cardId % COLOR_COUNT;
        return CARD_ROW_OFFSET + (colorId * FIGURE_COUNT + figureId) * CARD_SPACE;
    }

    @Override
    public String getName() {
        return "CardChoosePanel";
    }
}
