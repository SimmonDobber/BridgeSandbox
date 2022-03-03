package main.engine.structures;

import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.GameObject;

import java.util.LinkedList;
import java.util.List;

public abstract class TextManager extends GameObject
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

    protected abstract void loadTexts();

    private void removeTexts() {
        spriteList.clear();
    }

    public void reloadTexts() {
        removeTexts();
        loadTexts();
    }

}
