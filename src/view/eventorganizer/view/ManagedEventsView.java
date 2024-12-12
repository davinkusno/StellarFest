package view.eventorganizer.view;

import controller.view.eventogranizer.view.ManagedEventsViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import model.Event;
import view.SFEventTableView;
import view.StageManager;
import view.component.TopBar;
import view.eventorganizer.EOHomeView;

public class ManagedEventsView extends SFEventTableView {

    private final ObservableList<Event> events = FXCollections.observableArrayList();
    private TableView<Event> eventTable;

    public ManagedEventsView(StageManager stageManager) {
        super(stageManager);
        BorderPane root = new BorderPane();

        this.prepareView(root);

        this.windowTitle = "View Managed Events";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        BorderPane borderPane = (BorderPane) root;

        this.eventTable = this.createEventTable(events);
        borderPane.setCenter(eventTable);

        this.eventTable.setOnMouseClicked(event -> {
            Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                showEventDetailsWindow(selectedEvent);
            }
        });

        Pane topBar = TopBar.getTopBar(EOHomeView.class);
        borderPane.setTop(topBar);
    }

    @Override
    public void refreshData() {
        ManagedEventsViewController.loadEvents(events);
    }
}
