package view.admin;

import controller.view.admin.EventManagementViewController;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import model.Event;
import view.SFEventTableView;
import view.StageManager;
import view.component.TopBar;

public class EventManagementView extends SFEventTableView {

    public EventManagementView(StageManager stageManager) {
        super(stageManager);
        BorderPane root = new BorderPane();

        this.prepareView(root);

        this.windowTitle = "Manage Events";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        BorderPane borderPane = (BorderPane) root;

        this.eventTable = this.createEventTable(events);
        borderPane.setCenter(eventTable);

        this.eventTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
                if (selectedEvent != null) {
                    showEventDetailsWindow(selectedEvent);
                }
            }
        });

        Pane topBar = TopBar.getTopBar(AdminHomeView.class);
        borderPane.setTop(topBar);
    }

    @Override
    public void refreshData() {
        EventManagementViewController.loadEvents(events);
    }

}
