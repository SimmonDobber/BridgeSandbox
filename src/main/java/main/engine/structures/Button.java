package main.engine.structures;

import lombok.Getter;
import lombok.Setter;
import main.engine.Input;
import main.engine.display.Renderer;
import main.engine.structures.drawable.Drawable;

import java.awt.event.MouseEvent;
import java.util.List;

public abstract class Button extends GameObject implements Clickable
{
    public static final int HOVER_COLOR = 0x220000FF;
    public static final int INACTIVE_COLOR = 0x77333333;
    protected static int clickedId = -1;
    @Getter
    protected int stateCount;
    @Getter
    protected int state;
    @Getter
    @Setter
    protected boolean active;
    @Getter
    @Setter
    protected boolean highlighted;
    @Getter
    @Setter
    private static int[] pOwner;
    @Getter
    @Setter
    private static int screenW;

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
        state = 0;
        this.stateCount = stateCount;
        active = true;
        highlighted = false;
    }

    public void buttonUpdate(Input input, State state)
    {
        if(isOnButton(input, state) && hasMouseInteraction(input))
            buttonActionHandle(input, state);
    }

    private boolean isOnButton(Input input, State state)
    {
        if(inBorders(input, x, y, w, h) && onSurface(input, screenW, id, pOwner) && active)
        {
            onHover(state);
            return true;
        }
        nonHover(state);
        return false;
    }

    private void buttonActionHandle(Input input, State state)
    {
        if(input.isButtonDown(MouseEvent.BUTTON1))
        {
            onClick(state);
            buttonIdUpdate(state);
        }
        if(input.isButtonUp(MouseEvent.BUTTON1))
            onRelease(state);
        if(input.isButton(MouseEvent.BUTTON1))
            onHold(state);
    }

    private boolean hasMouseInteraction(Input input)
    {
        return input.isMouseClicked() || input.isButton(MouseEvent.BUTTON1);
    }

    private void buttonIdUpdate(State state)
    {
        if(Button.clickedId == id)
        {
            onDoubleClick(state);
            Button.clickedId = -1;
        }
        else
            Button.clickedId = id;
    }

    protected void hoveredRender(Renderer r)
    {
        if(highlighted)
            r.drawRectangle(x, y, w, h, HOVER_COLOR, 1, id);
    }

    protected void inactiveRender(Renderer r)
    {
        if(!active)
            r.drawRectangle(x, y, w, h, INACTIVE_COLOR, 1, id);
    }

    public void incState()
    {
        state = (state + 1) % stateCount;
    }
}
