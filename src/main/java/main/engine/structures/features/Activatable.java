package main.engine.structures.features;

public interface Activatable
{
    int INACTIVE_COLOR = 0x77777777;
    boolean isActive();
    void setActive(boolean active);
}
