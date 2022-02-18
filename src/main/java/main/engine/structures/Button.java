package main.engine.structures;

import lombok.Getter;
import lombok.Setter;
import main.engine.Input;
import main.engine.display.Image;
import main.engine.display.Renderer;

import java.awt.event.MouseEvent;

public abstract class Button extends Entity implements Clickable
{
    public static final int HOVER_COLOR = 0x220000FF;
    public static final int INACTIVE_COLOR = 0x77333333;
    protected static int buttonCount;
    protected static int clickedId = -1;
    @Getter
    protected int buttonId;
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

    public Button(String path, int x, int y, int w, int h, int stateCount, int fixed) {
        super(path, x, y, w, h, fixed);
        initializeButton(stateCount);
    }

    public Button(Image image, int x, int y, int w, int h, int stateCount, int fixed) {
        super(image, x, y, w, h, fixed);
        initializeButton(stateCount);
    }

    public Button(Image image, int x, int y, int stateCount, int fixed) {
        super(image, x, y, fixed);
        initializeButton(state);
    }

    public Button(int x, int y, int w, int h, int stateCount, int fixed) {
        super(x, y, w, h, fixed);
        initializeButton(stateCount);
    }

    private void initializeButton(int stateCount)
    {
        buttonId = ++buttonCount;
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
        if(inBorders(input, x, y, w, h) && onSurface(input, screenW, buttonId, pOwner) && active)
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
        if(Button.clickedId == buttonId)
        {
            onDoubleClick(state);
            Button.clickedId = -1;
        }
        else
            Button.clickedId = buttonId;
    }

    protected void hoveredRender(Renderer r)
    {
        if(highlighted)
            r.drawRectangle(x, y, w, h, HOVER_COLOR, img.getFixed(), buttonId);
    }

    protected void inactiveRender(Renderer r)
    {
        if(!active)
            r.drawRectangle(x, y, w, h, INACTIVE_COLOR, img.getFixed(), buttonId);
    }

    public void incState()
    {
        state = (state + 1) % stateCount;
    }
}
