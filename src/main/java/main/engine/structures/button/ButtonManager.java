package main.engine.structures.button;

import main.engine.structures.gameObject.GameObject;
import main.engine.structures.observer.Observer;

import java.util.LinkedList;
import java.util.List;

public abstract class ButtonManager extends GameObject
{
    private List<Button> buttons;

    public ButtonManager(GameObject parent) {
        super(parent);
        buttons = new LinkedList<>();
    }

    public void addButton(Button button, Observer observer) {
        button.attach(observer);
        buttons.add(button);
        children.add(button);
    }

    public void addButton(Button button) {
        buttons.add(button);
        children.add(button);
    }

    public void removeButton(Button button) {
        for(int i = 0; i < button.getObservers().size(); i++)
            button.detach(button.getObservers().get(i));
        buttons.remove(button);
        children.remove(button);
    }

}
