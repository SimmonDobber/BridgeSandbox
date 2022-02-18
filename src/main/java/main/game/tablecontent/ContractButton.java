package main.game.tablecontent;

import main.engine.display.Renderer;
import main.engine.structures.Button;
import main.engine.structures.GameObject;
import main.engine.structures.Scene;
import main.game.GameConstants;
import main.game.tablecontent.card.Card;
import main.game.tablecontent.card.CardColor;

public class ContractButton extends Button
{
    public static final int DEFAULT_SOLVER_BUTTON_WIDTH = 55;
    public static final int DEFAULT_SOLVER_BUTTON_HEIGHT = 40;
    public static final int DEFAULT_SOLVER_BUTTON_X = 150;
    public static final int DEFAULT_SOLVER_BUTTON_Y = Hand.CARD_SPACE - 4;

    public ContractButton(GameObject parent)
    {
        super(DEFAULT_SOLVER_BUTTON_X, DEFAULT_SOLVER_BUTTON_Y, DEFAULT_SOLVER_BUTTON_WIDTH, DEFAULT_SOLVER_BUTTON_HEIGHT, parent);
    }

    public void render(Renderer r, int contractId, int atuId)
    {
        r.drawRectangle(x + 2, y + 2, w - 4, h - 4, GameConstants.BROWN, 1, buttonId);
        r.drawRectangle(x, y, w, h, GameConstants.CYAN, 1, buttonId);
        r.drawText("Contract; " + (contractId / 5 + 1) , 10, 25, GameConstants.GRAY, GameConstants.DEFAULT_FONT_SIZE, 1);
        r.drawText(Character.toString((char)(atuId + '[')), 176, 25, CardColor.values()[contractId % 5].getCardColor(), GameConstants.DEFAULT_FONT_SIZE, 1);
        hoveredRender(r);
    }

    @Override
    public void onClick(Scene state) {

    }

    @Override
    public void onDoubleClick(Scene state) {

    }

    @Override
    public void onRelease(Scene state) {

    }

    @Override
    public void onHold(Scene state) {

    }

    @Override
    public void onHover(Scene state) {
        highlighted = true;
    }

    @Override
    public void nonHover(Scene state) {
        highlighted = false;
    }
}
