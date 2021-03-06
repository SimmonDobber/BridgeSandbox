package main.engine;

import lombok.Getter;
import main.engine.display.Window;
import main.engine.structures.features.Clickable;
import main.engine.structures.observer.Observable;
import main.engine.structures.observer.Observer;

import java.awt.event.*;
import java.util.LinkedList;

@Getter
public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, Observable
{
    private static Input INPUT = null;

    private final int NUM_KEYS = 256;
    private final boolean[] keys = new boolean[NUM_KEYS];
    private boolean[] keysLast = new boolean[NUM_KEYS];

    private final int NUM_BUTTONS = 5;
    private final boolean[] buttons = new boolean[NUM_BUTTONS];
    private boolean[] buttonsLast = new boolean[NUM_BUTTONS];

    private int mouseX;
    private int mouseY;
    private int scroll;

    private boolean mouseClicked;
    private boolean mouseMoved;
    private boolean mouseHeld;
    private boolean keyboardClicked;
    private boolean keyboardHeld;

    private LinkedList<Observer> observers;


    private Input(Window window)
    {
        initializeMouse();
        initializeKeyboard();
        initializeListeners(window);
        observers = new LinkedList<>();
    }

    private void initializeMouse()
    {
        mouseX = 0;
        mouseY = 0;
        scroll = 0;
        mouseMoved = false;
        mouseHeld = false;
    }

    private void initializeKeyboard()
    {
        keyboardClicked = false;
        keyboardHeld = false;
    }

    private void initializeListeners(Window window)
    {
        window.getCanvas().addKeyListener(this);
        window.getCanvas().addMouseMotionListener(this);
        window.getCanvas().addMouseListener(this);
        window.getCanvas().addMouseWheelListener(this);
    }

    void update()
    {
        if(isActionOngoing())
            notifyObservers();
        updateMouse();
        updateKeyboard();
    }

    private void updateMouse()
    {
        mouseClicked = false;
        mouseMoved = false;
        mouseHeld = false;
        for(int i = 0; i < NUM_BUTTONS; i++)
        {
            buttonsLast[i] = buttons[i];
            mouseHeld |= buttons[i];
        }
        scroll = 0;
    }

    private void updateKeyboard()
    {
        keyboardClicked = false;
        keyboardHeld = false;
        for(int i = 0; i < NUM_BUTTONS; i++)
        {
            keysLast[i] = keys[i];
            keyboardHeld |= keys[i];
        }
    }

    public boolean isActionOngoing()
    {
        return mouseClicked | mouseMoved | mouseHeld | keyboardClicked | keyboardHeld;
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
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(int i = 0; i < observers.size(); i++) {
            observers.get(i).update(null, null);
        }
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
        mouseX = (int)(e.getX() / Window.widthScale);
        mouseY = (int)(e.getY() / Window.heightScale);
        mouseMoved = true;
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        mouseX = (int)(e.getX() / Window.widthScale);
        mouseY = (int)(e.getY() / Window.heightScale);
        mouseMoved = true;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        scroll = e.getWheelRotation();
    }

    public static Input getInput() {
        if(INPUT == null)
            INPUT = new Input(Window.getWindow());
        return INPUT;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int giveId() {
        return Observable.super.giveId();
    }
}
