package main.engine.structures.button;

import lombok.Getter;
import lombok.Setter;
import main.engine.Input;
import main.engine.display.renderer.Renderer;
import main.engine.structures.features.Clickable;
import main.engine.structures.features.Hoverable;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observable;
import main.engine.structures.observer.Observer;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public abstract class Button extends GameObject implements Clickable, Hoverable
{
    private static final int DEFAULT_STATE_COUNT = 1;
    protected int stateCount;
    protected int state;
    protected boolean hovered;
    protected List<Observer> observers;

    public Button(Position pos, Dimensions dim, GameObject parent) {
        super(pos, dim, parent);
        initializeButton(DEFAULT_STATE_COUNT);
        observers = new LinkedList<>();
    }

    public Button(Position pos, Dimensions dim, GameObject parent, int stateCount) {
        super(pos, dim, parent);
        initializeButton(stateCount);
    }

    private void initializeButton(int stateCount)
    {
        Input.getInput().attach(this);
        this.state = 0;
        this.stateCount = stateCount;
        this.hovered = false;
    }

    public void update(Observable o, Object arg)
    {
        if(canBeChosen(id) && belongsToCurrentScene())
        {
            onHover();
            clickableUpdate();
        }
        else
        {
            nonHover();
        }
    }

    public void render(Renderer r)
    {
        spriteRender(r);
        childrenRender(r);
        hoverRender(r, hovered, id);
    }

    @Override
    public void onHover() {
        hovered = true;
    }

    @Override
    public void nonHover() {
        hovered = false;
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void incState()
    {
        state = (state + 1) % stateCount;
    }
}
