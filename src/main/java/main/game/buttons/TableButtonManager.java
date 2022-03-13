package main.game.buttons;

import main.engine.structures.button.ButtonManager;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.observer.Observer;
import main.game.table.Table;

public class TableButtonManager extends ButtonManager
{
    private Table table;

    public TableButtonManager(GameObject parent) {
        super(parent);
        table = (Table)(parent);
        loadButtons();
    }

    protected void loadButtons() {
        addButton(new SolverButton(table), table.getSolver());
        addButton(new ShuffleButton(table), new Observer[]{table.getGameManager(), table.getTextManager()});
        addButton(new CardAmountChangeButton(table), new Observer[]{table.getGameManager(), table.getTextManager()});
        addButton(new ContractButton(table, table.getContractId()), table.getTextManager());
        addButton(new CardChooseButton(table), table.getTextManager());
    }

}
