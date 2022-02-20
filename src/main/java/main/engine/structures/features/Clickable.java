package main.engine.structures.features;

import main.engine.Input;
import main.engine.structures.observer.Observable;
import main.engine.structures.observer.Observer;

import java.awt.event.MouseEvent;

public interface Clickable extends Measurable, Serializable, Observer, Observable
{
    void onClick();
    void onRelease();
    void onHold();

    default void clickableUpdate()
    {
        if(Input.getInput().isButtonDown(MouseEvent.BUTTON1))
            onClick();
        if(Input.getInput().isButtonUp(MouseEvent.BUTTON1))
            onRelease();
        if(Input.getInput().isButton(MouseEvent.BUTTON1))
            onHold();
    }

}
