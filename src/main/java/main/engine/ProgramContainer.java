package main.engine;

import lombok.Getter;
import main.engine.structures.Scene;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observable;
import main.engine.structures.observer.Observer;
import main.game.cardChoosePanel.AcceptChoiceButton;
import main.game.cardChoosePanel.CardChoicePanel;
import main.game.contractChoosePanel.ContractChooseButton;
import main.game.contractChoosePanel.ContractChoosePanel;
import main.game.table.Table;
import main.game.buttons.CardChooseButton;
import main.game.buttons.ContractButton;

public class ProgramContainer implements Observer
{
    private static ProgramContainer PROGRAM_CONTAINER = null;
    private MainLoop mainLoop;
    @Getter private Scene table;
    @Getter private Scene contractChoosePanel;
    @Getter private Scene cardChoosePanel;
    private ProgramContainer() {
        mainLoop = MainLoop.getMainLoop();
    }

    public void initializeScenes() {
        table = new Table();
        contractChoosePanel = new ContractChoosePanel(((Table)(table)).getGameManager(), new Position(409, 164));
        cardChoosePanel = new CardChoicePanel(((Table)(table)).getGameManager());
        switchSceneToTable();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof ContractButton)
            switchSceneToContractChoosePanel();
        if(o instanceof CardChooseButton)
            switchSceneToCardChoosePanel();
        if(o instanceof AcceptChoiceButton || o instanceof ContractChooseButton)
            switchSceneToTable();
    }

    private void switchSceneToTable() {
        mainLoop.setCurrentScene(table);
    }

    private void switchSceneToContractChoosePanel() {
        mainLoop.setCurrentScene(contractChoosePanel);
    }

    private void switchSceneToCardChoosePanel() {
        mainLoop.setCurrentScene(cardChoosePanel);
    }

    public static ProgramContainer getProgramContainer() {
        if(PROGRAM_CONTAINER == null)
            PROGRAM_CONTAINER = new ProgramContainer();
        return PROGRAM_CONTAINER;
    }
}
