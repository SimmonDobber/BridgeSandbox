package main.engine;

import lombok.Getter;
import main.engine.display.Window;

import java.awt.event.*;
import java.util.Arrays;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{
    private final int NUM_KEYS = 256;
    private final boolean[] keys = new boolean[NUM_KEYS];
    private boolean[] keysLast = new boolean[NUM_KEYS];

    private final int NUM_BUTTONS = 5;
    private final boolean[] buttons = new boolean[NUM_BUTTONS];
    private boolean[] buttonsLast = new boolean[NUM_BUTTONS];

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
        initializeVariables();
        initializeListeners(window);
    }
    private void initializeVariables()
    {
        mouseX = 0;
        mouseY = 0;
        scroll = 0;
        mouseClicked = false;
        mouseMoved = false;
        keyboardClicked = false;
    }
    private void initializeListeners(Window window)
    {
        window.getCanvas().addKeyListener(this);
        window.getCanvas().addMouseMotionListener(this);
        window.getCanvas().addMouseListener(this);
        window.getCanvas().addMouseWheelListener(this);
    }

    public void update()
    {
        updateMouse();
        updateKeyboard();
    }
    private void updateMouse()
    {
        buttonsLast = Arrays.copyOf(buttons, NUM_BUTTONS);
        scroll = 0;
        mouseClicked = false;
        mouseMoved = false;
    }
    private void updateKeyboard()
    {
        keysLast = Arrays.copyOf(keys, NUM_KEYS);
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
