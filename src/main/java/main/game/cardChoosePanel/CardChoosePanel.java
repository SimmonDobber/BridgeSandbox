package main.game.cardChoosePanel;

import main.engine.ProgramContainer;
import main.engine.display.Window;
import main.engine.structures.Scene;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observer;
import main.game.table.Table;
import main.game.table.card.CardColor;
import main.game.table.card.CardFigure;

import java.util.LinkedList;

import static main.game.GameConstants.*;

public class CardChoosePanel extends GameObject implements Scene
{
    private static final int CARD_SPACE = 20;
    private static final int CARD_ROW_OFFSET = 96;
    private static final int PLAYER_SIGNATURE_SIZE = 80;
    private ChoiceCard[][] card;
    private AcceptChoiceButton acceptChoiceButton;

    public CardChoosePanel() {
        super(new Position(), new Dimensions(Window.WIDTH, Window.HEIGHT), null);
        card = new ChoiceCard[PLAYER_COUNT][DECK_SIZE];
        initializeSprites();
        initializeCards();
        initializeButtons();
    }

    private void initializeCards() {
        for(int i = 0; i < PLAYER_COUNT; i++)
            initializePlayerCards(i);
    }

    private void initializeSprites()
    {
        spriteList.add(new Rectangle(new Position(), dim, GREEN, 1));
        spriteList.add(new Text("N;", new Position(16, 16), PLAYER_SIGNATURE_SIZE, GRAY, 1));
        spriteList.add(new Text("E;", new Position(30, 136), PLAYER_SIGNATURE_SIZE, GRAY, 1));
        spriteList.add(new Text("S;", new Position(26, 256), PLAYER_SIGNATURE_SIZE, GRAY, 1));
        spriteList.add(new Text("W;", new Position(6, 376), PLAYER_SIGNATURE_SIZE, GRAY, 1));
    }

    private void initializeButtons()
    {
        acceptChoiceButton = new AcceptChoiceButton(this);
        children.add(acceptChoiceButton);
        acceptChoiceButton.attach(((Table)(ProgramContainer.getProgramContainer().getTable())).getGameManager());
        acceptChoiceButton.attach(ProgramContainer.getProgramContainer());
    }

    public void clearCardChoices()
    {
        for(int i = 0; i < PLAYER_COUNT; i++) {
            for(int j = 0; j < DECK_SIZE; j++) {
                card[i][j].setCurrentState(0);
            }
        }
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

    private void initializePlayerCards(int playerId)
    {
        for(int i = 0; i < COLOR_COUNT; i++) {
            for(int j = 0; j < FIGURE_COUNT; j++) {
                int cardId = i + j * COLOR_COUNT;
                card[playerId][cardId] = initializeCard(playerId, cardId);
            }
        }
    }

    private ChoiceCard initializeCard(int playerId, int cardId)
    {
        CardColor color = CardColor.values()[cardId % 4];
        CardFigure figure = CardFigure.values()[cardId / 4];
        Position cardPos = new Position(getCardXPosInRow(cardId),ChoiceCard.DEFAULT_HEIGHT * playerId);
        ChoiceCard card = new ChoiceCard(cardPos, this, figure, color);
        children.add(card);
        return card;
    }

    private int getCardXPosInRow(int cardId)
    {
        int figureId = cardId / COLOR_COUNT;
        int colorId = cardId % COLOR_COUNT;
        return CARD_ROW_OFFSET + (colorId * FIGURE_COUNT + figureId) * CARD_SPACE;
    }

    @Override
    public String getName() {
        return "CardChoosePanel";
    }
}
