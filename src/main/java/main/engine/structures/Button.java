package main.engine.structures;

import main.engine.Input;
import main.engine.display.Image;
import main.game.Table;

import java.awt.event.MouseEvent;

public abstract class Button extends Entity implements Clickable
{
    protected static int buttonCount;
    protected static int clickedId = -1;
    protected int buttonId;
    protected int stateCount;
    protected int state;
    protected boolean active;
    protected boolean highlighted;
    private static int[] pOwner;
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
    public void buttonUpdate(Input input, Table table)
    {

        if(!inborders(input, x, y, w, h) || !onSurface(input, screenW, buttonId, pOwner) || !active)
        {
            nonHover(table);
            return;
        }
        if(!active)
            return;
        onHover(table);
        if(!input.isMouseClicked() && !input.isButton(MouseEvent.BUTTON1))
            return;
        if(input.isButtonDown(MouseEvent.BUTTON1))
        {
            onClick(table);
            if(Button.clickedId == buttonId)
            {
                onDoubleClick(table);
                Button.clickedId = -1;
            }
            else
              Button.clickedId = buttonId;
        }
        if(input.isButtonUp(MouseEvent.BUTTON1))
            onRelease(table);
        if(input.isButton(MouseEvent.BUTTON1))
            onHold(table);
    }

    public int getStateCount() {
        return stateCount;
    }

    public int getstate() {
        return state;
    }

    public void incState()
    {
        state = (state + 1) % stateCount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static void setP(int[] p) {
        Button.pOwner = p;
    }

    public static void setScreenW(int screenW) {
        Button.screenW = screenW;
    }
}
