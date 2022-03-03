package main.game.table.buttons;

import main.engine.structures.button.ButtonManager;
import main.engine.structures.gameObject.GameObject;
import main.game.table.Table;

public class TableButtonManager extends ButtonManager
{
    private Table table;
    public TableButtonManager(GameObject parent) {
        super(parent);
        table = (Table)(parent);
        initializeButtons();
    }

    private void initializeButtons() {
        addButton(new SolverButton(table), table.getSolver());
        addButton(new ShuffleButton(table), table);
        addButton(new CardAmountChangeButton(table), table);
        addButton(new ContractButton(table, table.getContractId()));
        addButton(new CardChooseButton(table));
    }
}
