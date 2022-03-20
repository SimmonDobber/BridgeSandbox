package main.game.cardChoosePanel;

import main.engine.display.renderer.Renderer;
import main.engine.structures.button.Button;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static main.game.GameConstants.*;
import static main.game.GameConstants.GRAY;

public class AcceptChoiceButton extends Button
{
    private static final int DEFAULT_ACCEPT_CHOICE_BUTTON_WIDTH = 150;
    private static final int DEFAULT_ACCEPT_CHOICE_BUTTON_HEIGHT = 80;
    private static final int DEFAULT_ACCEPT_CHOICE_BUTTON_X = 1026;
    private static final int DEFAULT_ACCEPT_CHOICE_BUTTON_Y = 571;
    private boolean active;

    public AcceptChoiceButton(GameObject parent) {
        super(new Position(DEFAULT_ACCEPT_CHOICE_BUTTON_X, DEFAULT_ACCEPT_CHOICE_BUTTON_Y),
                new Dimensions(DEFAULT_ACCEPT_CHOICE_BUTTON_WIDTH, DEFAULT_ACCEPT_CHOICE_BUTTON_HEIGHT), parent);
        initializeSpriteList();
        active = false;
    }

    private void initializeSpriteList() {
        spriteList.add(new Rectangle(new Position(), dim, CYAN, BROWN, 1));
        spriteList.add(new Text("accept", new Position(36, 22), DEFAULT_FONT_SIZE, GRAY, 1));
    }

    @Override
    public void onClick() {
        notifyObservers();
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onHold() {

    }

    @Override
    public void notifyObservers() {
        for(int i = 0; i < observers.size(); i++)
            observers.get(i).update(this, ((CardChoicePanel)(parent)).groupChosenCards());
        ((CardChoicePanel)(parent)).clearCardChoices();
    }

    public void update(Observable o, Object arg)
    {
        active = areCardProperlyChosen();
        if(canBeChosen(id) && belongsToCurrentScene() && active) {
            onHover();
            clickableUpdate();
        }
        else {
            nonHover();
        }
    }

    public void render(Renderer r) {
        spriteRender(r);
        childrenRender(r);
        hoverRender(r, hovered, id);
        inactiveRender(r);
    }

    private void inactiveRender(Renderer r){
        if(!active)
            r.drawRectangle(pos, dim, 0xA0A0A0A0, 1, id);
    }

    private boolean areCardProperlyChosen(){
        LinkedList<Integer>[] playerCards = ((CardChoicePanel)(parent)).getGroupedCardsByPlayer();
        LinkedList<Integer> groupedCards = ((CardChoicePanel)(parent)).groupChosenCards();
        return isCorrectAmountOfCardsChosen(playerCards) && areChosenCardsUnique(groupedCards)
                && areChosenCardsProperlyDistributed(playerCards);
    }

    private boolean isCorrectAmountOfCardsChosen(LinkedList<Integer>[] cards){
        int cardAmount = ((CardChoicePanel)(parent)).getCardAmount();
        return (cardAmount == cards[0].size());
    }

    private boolean areChosenCardsUnique(LinkedList<Integer> cards){
        Set<Integer> set = new HashSet<>(cards);
        return set.size() == cards.size();
    }

    private boolean areChosenCardsProperlyDistributed(LinkedList<Integer>[] cards){
        for(int i = 0; i < PLAYER_COUNT; i++){
            if(cards[i].size() != cards[(i + 1) % PLAYER_COUNT].size())
                return false;
        }
        return true;
    }

}
