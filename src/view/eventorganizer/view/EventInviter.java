package view.eventorganizer.view;

import controller.view.eventogranizer.view.EventInviterController;
import enums.Role;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;
import model.user.User;
import util.Callable;

import java.util.List;
import java.util.stream.Collectors;

public class EventInviter {

    private final Event event;
    private final Role role;
    private final Callable onComplete;

    private final ObservableList<User> users;
    private final Stage stage;

    private TableView<User> tableView;

    public EventInviter(Event event, Role role, Callable onComplete) {
        this.event = event;
        this.role = role;
        this.onComplete = onComplete;

        this.users = FXCollections.observableArrayList();
        this.stage = new Stage();

        this.loadData();
        this.prepareView();
    }

    public void show() {
        stage.show();
    }

    private void loadData() {
        EventInviterController.loadUsers(event, users, role);
    }

    private void prepareView() {
        BorderPane root = new BorderPane();

        VBox container = new VBox(10);
        container.setPadding(new Insets(20));

        Label titleLabel = new Label("Invite " + role.getRole() + "s");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        container.getChildren().add(titleLabel);

        this.tableView = createUsersTable();
        container.getChildren().add(tableView);

        root.setCenter(container);

        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(20));

        Button saveButton = createSaveButton();
        buttonContainer.getChildren().add(saveButton);

        root.setBottom(buttonContainer);

        Scene scene = new Scene(root, 800, 400);
        stage.setScene(scene);
    }

    protected TableView<User> createUsersTable() {
        TableView<User> tableView = new TableView<>();

        TableColumn<User, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEmail()));

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUsername()));

        TableColumn<User, Boolean> checkboxColumn = createTableCheckbox();

        idColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        emailColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
        usernameColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
        checkboxColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));

        tableView.getColumns().addAll(idColumn, emailColumn, usernameColumn, checkboxColumn);
        tableView.setItems(users);

        return tableView;
    }

    private TableColumn<User, Boolean> createTableCheckbox() {
        TableColumn<User, Boolean> checkboxColumn = new TableColumn<>("Invited");
        checkboxColumn.setCellValueFactory(cellData -> {
            boolean isInvited = event.getInvitations().getValue()
                    .stream()
                    .anyMatch(invitation -> invitation.getUser().getId() == cellData.getValue().getId());
            return new ReadOnlyObjectWrapper<>(isInvited);
        });

        checkboxColumn.setCellFactory(column -> new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();

            @Override
            protected void updateItem(Boolean invited, boolean empty) {
                super.updateItem(invited, empty);
                if (empty || invited == null) {
                    setGraphic(null);
                } else {
                    checkBox.setSelected(invited);
                    checkBox.setDisable(true);
                    setGraphic(checkBox);
                }
            }
        });
        return checkboxColumn;
    }

    private Button createSaveButton() {
        Button saveButton = new Button("Invite");
        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        saveButton.setPrefWidth(200);

        saveButton.setOnMouseClicked(e -> {
            List<User> checkedUsers = getCheckedUsers(tableView, event);
            EventInviterController.inviteUsers(event, checkedUsers);

            this.onComplete.call();
            stage.close();
        });

        return saveButton;
    }

    private List<User> getCheckedUsers(TableView<User> tableView, Event event) {
        List<Long> invitedUserIds = event.getInvitations().getValue()
                .stream()
                .map(invitation -> invitation.getUser().getId())
                .collect(Collectors.toList());

        return tableView.getItems()
                .stream()
                .filter(user -> invitedUserIds.contains(user.getId()))
                .collect(Collectors.toList());
    }


}
