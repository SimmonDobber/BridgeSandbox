package main.engine.structures;

import lombok.Getter;
import lombok.Setter;
import main.engine.Input;
import main.engine.structures.features.Clickable;
import main.engine.structures.features.Hoverable;
import main.engine.structures.gameObject.GameObject;

@Getter
@Setter
public abstract class Button extends GameObject implements Clickable, Hoverable
{
    protected int stateCount;
    protected int state;
    protected boolean hovered;

    public Button(int x, int y, int w, int h, GameObject parent) {
        super(x, y, w, h, parent);
        initializeButton(1);
    }

    public Button(int x, int y, int w, int h, GameObject parent, int stateCount) {
        super(x, y, w, h, parent);
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
        if(hasFocus(id))
            clickableUpdate();
    }

    public void incState()
    {
        state = (state + 1) % stateCount;
    }
}
