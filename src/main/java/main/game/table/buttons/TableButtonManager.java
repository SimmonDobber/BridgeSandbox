package main.game.table.buttons;

import main.engine.structures.button.ButtonManager;
import main.engine.structures.gameObject.GameObject;
import main.game.table.Table;

public class TableButtonManager extends ButtonManager
{
    protected Table table;
    public TableButtonManager(GameObject parent) {
        super(parent);
        table = (Table)(parent);
        loadButtons();
    }

    protected void loadButtons() {
        addButton(new SolverButton(table), table.getSolver());
        addButton(new ShuffleButton(table), table.getGameManager());
        addButton(new CardAmountChangeButton(table), table.getGameManager());
        addButton(new ContractButton(table, table.getGameManager().getContractId()));
        addButton(new CardChooseButton(table));
    }


}
