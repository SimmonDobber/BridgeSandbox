package main.game;

import lombok.Getter;
import lombok.Setter;
import main.engine.Input;
import main.engine.display.renderer.Renderer;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.features.Activatable;
import main.engine.structures.features.Clickable;
import main.engine.structures.features.Hoverable;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observable;
import main.engine.structures.observer.Observer;
import main.game.table.card.CardColor;
import main.game.table.card.CardFigure;

import java.util.LinkedList;

import static main.game.GameConstants.*;

@Getter
public abstract class Card extends GameObject implements Clickable, Activatable, Hoverable
{
    public static final int DEFAULT_WIDTH = 85;
    public static final int DEFAULT_HEIGHT = 120;
    public static final int STATE_AMOUNT = 2;
    protected CardFigure figure;
    protected CardColor color;
    @Setter
    protected boolean active;
    @Setter
    protected boolean hovered;
    protected int stateCount;
    @Setter
    protected int currentState;
    protected LinkedList<Observer> observers;

    public Card(Card card)
    {
        super(card.pos, card.dim, card.parent);
        initializeCard(card.figure, card.color);
    }

    public Card(Position pos, GameObject parent, CardFigure figure, CardColor color) {
        super(pos, new Dimensions(DEFAULT_WIDTH, DEFAULT_HEIGHT), parent);
        initializeCard(figure, color);
    }

    public Card(Position pos, Dimensions dim, GameObject parent, CardFigure figure, CardColor color) {
        super(pos, dim, parent);
        initializeCard(figure, color);
    }

    protected void initializeCard(CardFigure figure, CardColor color)
    {
        this.figure = figure;
        this.color = color;
        this.active = true;
        this.hovered = false;
        this.stateCount = STATE_AMOUNT;
        this.currentState = 0;
        this.observers = new LinkedList<>();
        Input.getInput().attach(this);
        initializeSpriteList();
    }

    protected void initializeSpriteList()
    {
        spriteList.add(new Rectangle(new Position(), dim, SILVER, color.getCardColor(), 1));
        spriteList.add(new Text(figure.getAsciiString(), new Position(3, 2), DEFAULT_FONT_SIZE, color.getCardColor(), 1));
        spriteList.add(new Text(color.getAsciiString(), new Position(-1, DEFAULT_FONT_SIZE), DEFAULT_FONT_SIZE, color.getCardColor(),1));
        spriteList.add(new Text(figure.getAsciiString(), new Position(dim.getW() - DEFAULT_FONT_SIZE / 2 - 8, dim.getH() - DEFAULT_FONT_SIZE), DEFAULT_FONT_SIZE, color.getCardColor(),1));
        spriteList.add(new Text(color.getAsciiString(), new Position(dim.getW() - DEFAULT_FONT_SIZE / 2 - 11, dim.getH() - DEFAULT_FONT_SIZE * 2 + 6), DEFAULT_FONT_SIZE, color.getCardColor(), 1));
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if(canBeChosen(id) && belongsToCurrentScene() && active)
        {
            onHover();
            clickableUpdate();
        }
        else
            nonHover();
    }

    public void render(Renderer r)
    {
        spriteRender(r);
        childrenRender(r);
        hoverRender(r, hovered, id);
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers()
    {
        for(int i = 0; i < observers.size(); i++)
            observers.get(i).update(this, null);
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onHold() {

    }

    @Override
    public void onHover()
    {
        hovered = true;
    }

    @Override
    public void nonHover() {
        hovered = false;
    }

    public int getCardId()
    {
        return color.ordinal() * GameConstants.FIGURE_COUNT + figure.ordinal();
    }

    public void incState()
    {
        currentState = (currentState + 1) % stateCount;
    }
}
