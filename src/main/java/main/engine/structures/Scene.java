package main.engine.structures;

import main.engine.display.renderer.Renderer;

public interface Scene {

    void update(double dt);
    void render(Renderer r);
    String getName();
}
