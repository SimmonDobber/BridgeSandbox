package main.game.buttons;

import main.engine.structures.button.Button;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observer;

import static main.game.GameConstants.*;
import static main.game.GameConstants.GRAY;

public class ShuffleButton extends Button
{
    private static final int DEFAULT_SHUFFLE_BUTTON_WIDTH = 150;
    private static final int DEFAULT_SHUFFLE_BUTTON_HEIGHT = 80;
    private static final int DEFAULT_SHUFFLE_BUTTON_X = 198;
    private static final int DEFAULT_SHUFFLE_BUTTON_Y = 546;

    public ShuffleButton(GameObject parent) {
        super(new Position(DEFAULT_SHUFFLE_BUTTON_X, DEFAULT_SHUFFLE_BUTTON_Y),
                new Dimensions(DEFAULT_SHUFFLE_BUTTON_WIDTH, DEFAULT_SHUFFLE_BUTTON_HEIGHT), parent);
        initializeSpriteList();
    }

    @Override
    public void onClick() {
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this, true);
        }
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onHold() {

    }

    private void initializeSpriteList() {
        spriteList.add(new Rectangle(new Position(), dim, CYAN, BROWN, 1));
        spriteList.add(new Text("shuffle", new Position(16, 22), DEFAULT_FONT_SIZE, GRAY, 1));
    }
}
