package main.engine.structures;

import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.observer.Observable;
import main.engine.structures.observer.Observer;

public abstract class TextManager extends GameObject implements Observer
{
    public TextManager(GameObject parent) {
        super(parent);
    }

    public void addText(Text text) {
        spriteList.add(text);
    }

    public void removeText(Text text) {
        spriteList.remove(text);
    }

    public void reloadTexts() {
        removeTexts();
        loadTexts();
    }

    @Override
    public void update(Observable o, Object arg) {
        reloadTexts();
    }

    protected abstract void loadTexts();

    private void removeTexts() {
        spriteList.clear();
    }
}
