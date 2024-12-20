package view.admin;

import controller.view.admin.EventManagementViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
        
        Button deleteButton = new Button("Delete Selected Event");
        deleteButton.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white; -fx-font-size: 14px;");
        deleteButton.setOnAction(e -> EventManagementViewController.handleDeleteSelectedEvent(eventTable, events));

        HBox buttonContainer = new HBox(deleteButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10));
        
        borderPane.setBottom(buttonContainer);

        Pane topBar = TopBar.getTopBar(AdminHomeView.class);
        borderPane.setTop(topBar);
    }

    @Override
    public void refreshData() {
        EventManagementViewController.loadEvents(events);
    }

}
