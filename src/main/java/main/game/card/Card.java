package main.game.card;

import main.engine.Input;
import main.engine.LoopTimer;
import main.engine.display.Renderer;
import main.engine.display.Window;
import main.engine.structures.State;

public class Card implements State {
    CardRenderer renderer = new CardRenderer();
    CardLogicHandler handler = new CardLogicHandler();


    @Override
    public void update(Window window, Input input, LoopTimer loopTimer) {
        handler.update();
    }

    @Override
    public void render(Renderer r) {
        renderer.render();
    }
}
