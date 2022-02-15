package main.engine;

import lombok.Getter;
import main.engine.display.Window;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{
    private final int NUM_KEYS = 256;
    private boolean[] keys = new boolean[NUM_KEYS];
    private boolean[] keysLast = new boolean[NUM_KEYS];

    private final int NUM_BUTTONS = 5;
    private boolean[] buttons = new boolean[NUM_BUTTONS];
    private boolean[] buttonsLast = new  boolean[NUM_BUTTONS];

    @Getter
    private int mouseX;
    @Getter
    private int mouseY;
    @Getter
    private int scroll;
    @Getter
    private boolean mouseClicked;
    @Getter
    private boolean mouseMoved;
    @Getter
    private boolean keyboardClicked;

    Input(Window window)
    {
        mouseX = 0;
        mouseY = 0;
        scroll = 0;
        mouseClicked = false;
        mouseMoved = false;
        keyboardClicked = false;
        window.getCanvas().addKeyListener(this);
        window.getCanvas().addMouseMotionListener(this);
        window.getCanvas().addMouseListener(this);
        window.getCanvas().addMouseWheelListener(this);
    }

    public void update()
    {
        scroll = 0;
        for(int i = 0; i < NUM_KEYS; i++)
        {
            keysLast[i] = keys[i];
        }
        for(int i = 0; i < NUM_BUTTONS; i++)
        {
            buttonsLast[i] = buttons[i];
        }
        mouseClicked = false;
        mouseMoved = false;
        keyboardClicked = false;
    }

    public boolean isKey(int keyCode)
    {
        return keys[keyCode];
    }
    public boolean isKeyUp(int keyCode)
    {
        return !keys[keyCode] && keysLast[keyCode];
    }
    public boolean isKeyDown(int keyCode)
    {
        return keys[keyCode] && !keysLast[keyCode];
    }
    public boolean isButton(int button)
    {
        return buttons[button];
    }
    public boolean isButtonUp(int button)
    {
        return !buttons[button] && buttonsLast[button];
    }
    public boolean isButtonDown(int button)
    {
        return buttons[button] && !buttonsLast[button];
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        keys[e.getKeyCode()] = true;
        keyboardClicked = true;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        keys[e.getKeyCode()] = false;
        keyboardClicked = true;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        buttons[e.getButton()] = true;
        mouseClicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        buttons[e.getButton()] = false;
        mouseClicked = true;
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        mouseX = (int)(e.getX() / Window.SCALE);
        mouseY = (int)(e.getY() / Window.SCALE);
        mouseMoved = true;
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        mouseX = (int)(e.getX() / Window.SCALE);
        mouseY = (int)(e.getY() / Window.SCALE);
        mouseMoved = true;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        scroll = e.getWheelRotation();
    }
}
