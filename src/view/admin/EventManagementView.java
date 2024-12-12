package view.admin;

import controller.view.admin.EventManagementViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;
import model.user.User;
import model.user.impl.EOUser;
import model.user.impl.GuestUser;
import model.user.impl.VendorUser;
import util.DateUtil;
import view.Refreshable;
import view.SFView;
import view.StageManager;
import view.component.EventTable;
import view.component.TopBar;

import java.util.List;
import java.util.stream.Collectors;

public class EventManagementView extends SFView implements Refreshable {

    private final ObservableList<Event> events = FXCollections.observableArrayList();
    private TableView<Event> eventTable;

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

        this.eventTable = EventTable.createEventTable(events);
        borderPane.setCenter(eventTable);

        this.eventTable.setOnMouseClicked(event -> {
            Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                showEventDetailsWindow(selectedEvent);
            }
        });

        Pane topBar = TopBar.getTopBar(AdminHomeView.class);
        borderPane.setTop(topBar);
    }

    @Override
    public void destroyView() {
        events.clear();
    }

    @Override
    public void refreshData() {
        EventManagementViewController.loadEvents(events);
    }

    private void showEventDetailsWindow(Event event) {
        Stage detailsStage = new Stage();
        detailsStage.setTitle("Event Details");

        VBox detailsContainer = new VBox(10);
        detailsContainer.setPadding(new Insets(20));

        detailsContainer.getChildren().addAll(
                createDetailRow("ID:", String.valueOf(event.getId())),
                createDetailRow("Name:", event.getName()),
                createDetailRow("Date:", DateUtil.formatDate(event.getDate(), DateUtil.DATE_FORMAT)),
                createDetailRow("Location:", event.getLocation()),
                createDetailRow("Description:", event.getDescription()),
                createOrganizerDetailRow(event.getOrganizer().getValue()),
                createAttendeeDetailRow(event.getAttendees().getValue())
        );

        Scene detailsScene = new Scene(detailsContainer, 400, 300);
        detailsStage.setScene(detailsScene);

        detailsStage.show();
    }

    private HBox createDetailRow(String labelText, String contentText) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold; -fx-min-width: 100px;");
        Label content = new Label(contentText);
        content.setWrapText(true);

        HBox row = new HBox(10);
        row.getChildren().addAll(label, content);
        return row;
    }

    private HBox createOrganizerDetailRow(EOUser organizer) {
        Label label = new Label("Organizer:");
        label.setStyle("-fx-font-weight: bold; -fx-min-width: 100px;");

        VBox organizerDetails = new VBox(5);
        organizerDetails.getChildren().addAll(
                createDetailRow("ID:", String.valueOf(organizer.getId())),
                createDetailRow("Username:", organizer.getUsername()),
                createDetailRow("Email:", organizer.getEmail())
        );

        HBox row = new HBox(10);
        row.getChildren().addAll(label, organizerDetails);
        return row;
    }

    private HBox createAttendeeDetailRow(List<User> attendees) {
        Label label = new Label("Attendees:");
        label.setStyle("-fx-font-weight: bold; -fx-min-width: 100px;");

        System.out.println("Attendees size: " + attendees.size());
        System.out.println("Attendees: " + attendees);

        VBox vendorDetails = new VBox(5);
        Label vendorLabel = new Label("Vendors:");
        vendorLabel.setStyle("-fx-font-weight: bold; -fx-min-width: 100px;");

        List<User> vendors = attendees.stream()
                .filter(user -> user instanceof VendorUser)
                .collect(Collectors.toList());

        for (User vendor : vendors) {
            vendorDetails.getChildren().addAll(
                    createDetailRow("ID:", String.valueOf(vendor.getId())),
                    createDetailRow("Username:", vendor.getUsername()),
                    createDetailRow("Email:", vendor.getEmail())
            );
        }

        VBox guestDetails = new VBox(5);
        Label guestLabel = new Label("Guests:");
        guestLabel.setStyle("-fx-font-weight: bold; -fx-min-width: 100px;");

        List<User> guests = attendees.stream()
                .filter(user -> user instanceof GuestUser)
                .collect(Collectors.toList());

        for (User guest : guests) {
            guestDetails.getChildren().addAll(
                    createDetailRow("ID:", String.valueOf(guest.getId())),
                    createDetailRow("Username:", guest.getUsername()),
                    createDetailRow("Email:", guest.getEmail())
            );
        }

        HBox row = new HBox(10);
        row.getChildren().addAll(label, vendorDetails, guestDetails);
        return row;
    }
}
