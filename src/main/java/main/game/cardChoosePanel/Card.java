package main.game.cardChoosePanel;

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
import main.engine.structures.observer.Observer;
import main.game.GameConstants;
import main.game.table.card.CardColor;
import main.game.table.card.CardFigure;

import java.util.LinkedList;

import static main.game.GameConstants.DEFAULT_FONT_SIZE;
import static main.game.GameConstants.SILVER;

@Getter
public class Card extends GameObject implements Clickable, Activatable, Hoverable
{
    public static final int DEFAULT_WIDTH = 85;
    public static final int DEFAULT_HEIGHT = 120;

    private CardFigure figure;
    private CardColor color;
    @Setter
    private boolean active;
    @Setter
    private boolean hovered;
    private LinkedList<Observer> observers;

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

    private void initializeCard(CardFigure figure, CardColor color)
    {
        this.figure = figure;
        this.color = color;
        this.active = false;
        this.hovered = false;
        Input.getInput().attach(this);
        this.observers = new LinkedList<>();
        initializeSpriteList();
    }

    private void initializeSpriteList()
    {
        spriteList.add(new Rectangle(new Position(), dim, SILVER, color.getCardColor(), 1));
        spriteList.add(new Text(figure.getAsciiString(), new Position(3, 2), DEFAULT_FONT_SIZE, color.getCardColor(), 1));
        spriteList.add(new Text(color.getAsciiString(), new Position(-1, DEFAULT_FONT_SIZE), DEFAULT_FONT_SIZE, color.getCardColor(),1));
        spriteList.add(new Text(figure.getAsciiString(), new Position(dim.getW() - DEFAULT_FONT_SIZE / 2 - 8, dim.getH() - DEFAULT_FONT_SIZE), DEFAULT_FONT_SIZE, color.getCardColor(),1));
        spriteList.add(new Text(color.getAsciiString(), new Position(dim.getW() - DEFAULT_FONT_SIZE / 2 - 11, dim.getH() - DEFAULT_FONT_SIZE * 2 + 6), DEFAULT_FONT_SIZE, color.getCardColor(), 1));
    }

    @Override
    public void update()
    {
        focusUpdate();
    }

    private void focusUpdate()
    {
        if(hasFocus(id) && belongsToCurrentScene())
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
        activeRender(r);
    }

    private void activeRender(Renderer r)
    {
        if(active)
            r.drawRectangle(pos, dim, INACTIVE_COLOR, 1, id);
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
        observers.forEach(Observer::update);
    }

    @Override
    public void onClick() {
        active = !active;
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
}
