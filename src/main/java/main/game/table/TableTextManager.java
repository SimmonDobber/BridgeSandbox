package main.game.table;

import main.engine.structures.TextManager;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;

import static main.game.GameConstants.DEFAULT_FONT_SIZE;
import static main.game.GameConstants.GRAY;

public class TableTextManager extends TextManager
{
    protected Table table;
    public TableTextManager(GameObject parent) {
        super(parent);
        table = (Table)(parent);
        loadTexts();
    }

    @Override
    protected void loadTexts() {
        addText(new Text("Contract; ", new Position(10, 25), DEFAULT_FONT_SIZE, GRAY, 1));
        addText(new Text("Current player; " + table.getCurrentPlayer().getAsciiString(), new Position(10, 65), DEFAULT_FONT_SIZE, GRAY, 1));
        addText(new Text("Taken; N/S - " + table.getTaken().x + " | W/E - " + table.getTaken().y, new Position(10, 105),  DEFAULT_FONT_SIZE, GRAY, 1));
    }
}
