package main.game.tablecontent;

import main.engine.display.Renderer;
import main.engine.structures.Button;
import main.engine.structures.State;
import main.game.GameConstants;
import main.game.tablecontent.card.Card;
import main.game.tablecontent.card.CardColor;

public class ContractButton extends Button
{
    public static final int DEFAULT_SOLVER_BUTTON_WIDTH = 55;
    public static final int DEFAULT_SOLVER_BUTTON_HEIGHT = 40;
    public static final int DEFAULT_SOLVER_BUTTON_X = 150;
    public static final int DEFAULT_SOLVER_BUTTON_Y = Hand.CARD_SPACE - 4;

    public ContractButton()
    {
        super(DEFAULT_SOLVER_BUTTON_X, DEFAULT_SOLVER_BUTTON_Y, DEFAULT_SOLVER_BUTTON_WIDTH, DEFAULT_SOLVER_BUTTON_HEIGHT, 1, 1);
    }

    public void render(Renderer r, int contractId, int atuId)
    {
        r.drawRectangle(x + 2, y + 2, w - 4, h - 4, GameConstants.BROWN, 1, buttonId);
        r.drawRectangle(x, y, w, h, GameConstants.CYAN, 1, buttonId);
        r.drawText("Contract; " + (contractId / 5 + 1) , 10, 25, GameConstants.GRAY, GameConstants.DEFAULT_FONT_SIZE, 1);
        r.drawText(Character.toString((char)(atuId + '[')), 176, 25, Card.getColorValue(CardColor.values()[contractId % 5]), GameConstants.DEFAULT_FONT_SIZE, 1);
        hoveredRender(r);
    }

    @Override
    public void onClick(State state) {

    }

    @Override
    public void onDoubleClick(State state) {

    }

    @Override
    public void onRelease(State state) {

    }

    @Override
    public void onHold(State state) {

    }

    @Override
    public void onHover(State state) {
        highlighted = true;
    }

    @Override
    public void nonHover(State state) {
        highlighted = false;
    }
}
