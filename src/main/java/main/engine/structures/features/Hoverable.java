package main.engine.structures.features;

import main.engine.structures.observer.Observer;

public interface Hoverable extends Observer, Measurable
{
    int HIGHLIGHT_COLOR = 0x220000FF;
    void onHover();
    void nonHover();
}
