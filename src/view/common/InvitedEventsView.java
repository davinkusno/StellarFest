package view.common;

import controller.view.vendor.view.InvitedEventsViewController;
import datastore.UserDatastore;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import model.Event;
import model.user.User;
import view.SFEventTableView;
import view.StageManager;
import view.component.TopBar;

public class InvitedEventsView extends SFEventTableView {

    private final BorderPane root;

    public InvitedEventsView(StageManager stageManager) {
        super(stageManager);
        this.root = new BorderPane();

        this.prepareView(root);

        this.windowTitle = "View Invited Events";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        BorderPane borderPane = (BorderPane) root;

        this.eventTable = this.createEventTable(events);
        borderPane.setCenter(eventTable);

        this.eventTable.setOnMouseClicked(event -> {
            Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2) {
                if (selectedEvent != null) {
                    showEventDetailsWindow(selectedEvent);
                }
            }
        });
    }

    @Override
    public void refreshData() {
        User currentUser = UserDatastore.getInstance().getCurrentUser();

        Pane topBar = TopBar.getTopBar(currentUser.getHomeView());
        root.setTop(topBar);

        InvitedEventsViewController.loadEvents(events, currentUser);
    }
}
