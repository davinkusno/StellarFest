package view.admin;

import controller.view.admin.UserManagementViewController;
import enums.Role;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.user.User;
import model.user.impl.AdminUser;
import model.user.impl.EOUser;
import model.user.impl.VendorUser;
import view.Refreshable;
import view.SFView;
import view.StageManager;
import view.component.TopBar;

public class UserManagementView extends SFView implements Refreshable {

    private final ObservableList<User> users = FXCollections.observableArrayList();
    private TableView<User> userTable;

    public UserManagementView(StageManager stageManager) {
        super(stageManager);
        BorderPane root = new BorderPane();

        this.prepareView(root);

        this.windowTitle = "Manage Users";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        BorderPane borderPane = (BorderPane) root;

        this.userTable = createUserTable();
        borderPane.setCenter(userTable);

        Button deleteButton = new Button("Delete Selected User");
        deleteButton.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white; -fx-font-size: 14px;");
        deleteButton.setOnAction(e -> UserManagementViewController.handleDeleteSelectedUser(userTable, users));

        HBox buttonContainer = new HBox(deleteButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10));

        borderPane.setBottom(buttonContainer);

        Pane topBar = TopBar.getTopBar(AdminHomeView.class);
        borderPane.setTop(topBar);
    }

    @Override
    public void destroyView() {
        // No cleanup required
    }

    @Override
    public void refreshData() {
        UserManagementViewController.loadUsers(users);
    }

    private TableView<User> createUserTable() {
        TableView<User> tableView = new TableView<>();

        TableColumn<User, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEmail()));

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUsername()));

        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(this.getRole(cellData.getValue())));

        tableView.getColumns().addAll(idColumn, emailColumn, usernameColumn, roleColumn);
        tableView.setItems(users);

        return tableView;
    }

    private String getRole(User user) {
        Role role;
        if (user instanceof AdminUser) {
            role = Role.ADMIN;
        } else if (user instanceof EOUser) {
            role = Role.EVENT_ORGANIZER;
        } else if (user instanceof VendorUser) {
            role = Role.VENDOR;
        } else {
            role = Role.GUEST;
        }

        return role.getRole();
    }
}
