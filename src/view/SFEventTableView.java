package view;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;
import model.user.User;
import model.user.impl.EOUser;
import model.user.impl.GuestUser;
import model.user.impl.VendorUser;
import util.DateUtil;
import util.StringUtil;

import java.util.List;
import java.util.stream.Collectors;

public abstract class SFEventTableView extends SFView implements Refreshable {

    protected final ObservableList<Event> events;
    protected TableView<Event> eventTable;

    public SFEventTableView(StageManager stageManager) {
        super(stageManager);
        events = FXCollections.observableArrayList();
    }

    protected TableView<Event> createEventTable(ObservableList<Event> events) {
        TableView<Event> tableView = new TableView<>();

        TableColumn<Event, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

        TableColumn<Event, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));

        TableColumn<Event, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(DateUtil.formatDate(cellData.getValue().getDate(), DateUtil.DATE_FORMAT)));

        TableColumn<Event, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(StringUtil.truncate(cellData.getValue().getLocation(), 200)));

        TableColumn<Event, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(StringUtil.truncate(cellData.getValue().getDescription(), 200)));

        idColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        nameColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        dateColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        locationColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        descriptionColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.4));

        tableView.getColumns().addAll(idColumn, nameColumn, dateColumn, locationColumn, descriptionColumn);
        tableView.setItems(events);

        return tableView;
    }

    @Override
    public void destroyView() {
        events.clear();
    }

    protected void showEventDetailsWindow(Event event) {
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

        Scene detailsScene = new Scene(detailsContainer, 800, 400);
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
        organizerDetails.setPadding(new Insets(0, 0, 0, 20));
        organizerDetails.setStyle("-fx-border-color: #000000; -fx-border-width: 1px;");

        HBox row = new HBox(10);
        row.getChildren().addAll(label, organizerDetails);
        return row;
    }

    private HBox createAttendeeDetailRow(List<User> attendees) {
        Label label = new Label("Attendees:");
        label.setStyle("-fx-font-weight: bold; -fx-min-width: 100px;");

        VBox vendorDetails = new VBox(5);
        Label vendorLabel = new Label("Vendors:");
        vendorLabel.setStyle("-fx-font-weight: bold; -fx-min-width: 100px;");
        vendorDetails.getChildren().add(vendorLabel);

        List<User> vendors = attendees.stream()
                .filter(user -> user instanceof VendorUser)
                .collect(Collectors.toList());

        for (User vendor : vendors) {
            VBox vendorDetail = new VBox(5);
            vendorDetail.setPadding(new Insets(0, 0, 0, 20));
            vendorDetail.setStyle("-fx-border-color: #000000; -fx-border-width: 1px;");

            vendorDetail.getChildren().addAll(
                    createDetailRow("ID:", String.valueOf(vendor.getId())),
                    createDetailRow("Username:", vendor.getUsername()),
                    createDetailRow("Email:", vendor.getEmail())
            );

            vendorDetails.getChildren().add(vendorDetail);
        }

        VBox guestDetails = new VBox(5);
        Label guestLabel = new Label("Guests:");
        guestLabel.setStyle("-fx-font-weight: bold; -fx-min-width: 100px;");
        guestDetails.getChildren().add(guestLabel);

        List<User> guests = attendees.stream()
                .filter(user -> user instanceof GuestUser)
                .collect(Collectors.toList());

        for (User guest : guests) {
            VBox guestDetail = new VBox(5);
            guestDetail.setPadding(new Insets(0, 0, 0, 20));
            guestDetail.setStyle("-fx-border-color: #000000; -fx-border-width: 1px;");

            guestDetails.getChildren().addAll(
                    createDetailRow("ID:", String.valueOf(guest.getId())),
                    createDetailRow("Username:", guest.getUsername()),
                    createDetailRow("Email:", guest.getEmail())
            );

            guestDetails.getChildren().add(guestDetail);
        }

        HBox row = new HBox(10);
        row.getChildren().addAll(label, vendorDetails, guestDetails);
        return row;
    }
}
