package main.engine.structures;

import lombok.Getter;
import lombok.Setter;
import main.engine.Input;
import main.engine.display.renderer.Renderer;
import main.engine.structures.features.Clickable;
import main.engine.structures.features.Hoverable;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;

@Getter
@Setter
public abstract class Button extends GameObject implements Clickable, Hoverable
{
    protected int stateCount;
    protected int state;
    protected boolean hovered;

    public Button(Position pos, Dimensions dim, GameObject parent) {
        super(pos, dim, parent);
        initializeButton(1);
    }

    public Button(Position pos, Dimensions dim, GameObject parent, int stateCount) {
        super(pos, dim, parent);
        initializeButton(stateCount);
    }

    private void initializeButton(int stateCount)
    {
        Input.getInput().attach(this);
        state = 0;
        this.stateCount = stateCount;
        this.hovered = false;
    }

    public void update()
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
    }

    @Override
    public void onHover() {
        hovered = true;
    }

    @Override
    public void nonHover() {
        hovered = false;
    }

    public void incState()
    {
        state = (state + 1) % stateCount;
    }
}
