package view.eventorganizer.view;

import controller.view.eventogranizer.view.ManagedEventsViewController;
import enums.Role;
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
import view.eventorganizer.EOHomeView;

public class ManagedEventsView extends SFEventTableView {

    private Button editEventNameButton;
    private Button inviteVendorButton;
    private Button inviteGuestButton;

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
            if (event.getClickCount() == 2) {
                if (selectedEvent != null) {
                    showEventDetailsWindow(selectedEvent);
                }
            }

            if (selectedEvent != null) {
                editEventNameButton.setDisable(false);
                inviteVendorButton.setDisable(false);
                inviteGuestButton.setDisable(false);
            }
        });

        HBox buttonContainer = this.createButtonContainer();
        borderPane.setBottom(buttonContainer);

        Pane topBar = TopBar.getTopBar(EOHomeView.class);
        borderPane.setTop(topBar);
    }

    @Override
    public void refreshData() {
        ManagedEventsViewController.loadEvents(events);
        editEventNameButton.setDisable(true);
        inviteVendorButton.setDisable(true);
        inviteGuestButton.setDisable(true);
    }

    private HBox createButtonContainer() {
        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(20));

        Button editNameButton = this.createEditNameButton();
        buttonContainer.getChildren().add(editNameButton);

        Button inviteVendorButton = this.createInviteVendorButton();
        buttonContainer.getChildren().add(inviteVendorButton);

        Button inviteGuestButton = this.createInviteGuestButton();
        buttonContainer.getChildren().add(inviteGuestButton);

        return buttonContainer;
    }

    private Button createEditNameButton() {
        this.editEventNameButton = new Button("Edit Event Name");
        editEventNameButton.setPrefWidth(200);
        editEventNameButton.setDisable(true);

        editEventNameButton.setOnMouseClicked(e -> {
            Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                new EditEventName(selectedEvent, this::refreshData).show();
            }
        });

        return editEventNameButton;
    }

    private Button createInviteVendorButton() {
        this.inviteVendorButton = new Button("Invite Vendors");
        inviteVendorButton.setPrefWidth(200);
        inviteVendorButton.setDisable(true);

        inviteVendorButton.setOnMouseClicked(e -> {
            Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                new EventInviter(selectedEvent, Role.VENDOR, this::refreshData).show();
            }
        });

        return inviteVendorButton;
    }

    private Button createInviteGuestButton() {
        this.inviteGuestButton = new Button("Invite Guests");
        inviteGuestButton.setPrefWidth(200);
        inviteGuestButton.setDisable(true);

        inviteGuestButton.setOnMouseClicked(e -> {
            Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                new EventInviter(selectedEvent, Role.GUEST, this::refreshData).show();
            }
        });

        return inviteGuestButton;
    }
}
