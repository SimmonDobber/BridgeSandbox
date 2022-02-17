package main.engine.structures;

import lombok.Getter;
import lombok.Setter;
import main.engine.Input;
import main.engine.display.Image;
import main.engine.display.Renderer;
import main.game.Table;

import java.awt.event.MouseEvent;

public abstract class Button extends Entity implements Clickable
{
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
        buttonId = ++buttonCount;
        state = 0;
        this.stateCount = stateCount;
        active = true;
        highlighted = false;
    }

    public Button(Image image, int x, int y, int w, int h, int stateCount, int fixed) {
        super(image, x, y, w, h, fixed);
        buttonId = ++buttonCount;
        state = 0;
        this.stateCount = stateCount;
        active = true;
        highlighted = false;
    }

    public Button(Image image, int x, int y, int stateCount, int fixed) {
        super(image, x, y, fixed);
        buttonId = ++buttonCount;
        state = 0;
        this.stateCount = stateCount;
        active = true;
    }

    public Button(int x, int y, int w, int h, int stateCount, int fixed) {
        super(x, y, w, h, fixed);
        buttonId = ++buttonCount;
        state = 0;
        this.stateCount = stateCount;
        active = true;
    }
    public void buttonUpdate(Input input, State state)
    {
        if(!inborders(input, x, y, w, h) || !onSurface(input, screenW, buttonId, pOwner) || !active)
        {
            nonHover(state);
            return;
        }
        onHover(state);
        if(!input.isMouseClicked() && !input.isButton(MouseEvent.BUTTON1))
            return;
        if(input.isButtonDown(MouseEvent.BUTTON1))
        {
            onClick(state);
            if(Button.clickedId == buttonId)
            {
                onDoubleClick(state);
                Button.clickedId = -1;
            }
            else
              Button.clickedId = buttonId;
        }
        if(input.isButtonUp(MouseEvent.BUTTON1))
            onRelease(state);
        if(input.isButton(MouseEvent.BUTTON1))
            onHold(state);
    }
    public void incState()
    {
        state = (state + 1) % stateCount;
    }
}
