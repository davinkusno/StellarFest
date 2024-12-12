package view.common;

import controller.view.common.InvitationsViewController;
import datastore.UserDatastore;
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
import model.Invitation;
import model.user.User;
import util.DateUtil;
import util.StringUtil;
import view.Refreshable;
import view.SFView;
import view.StageManager;
import view.component.TopBar;

public class InvitationsView extends SFView implements Refreshable {

    private final BorderPane root;
    private final ObservableList<Invitation> invitations;
    private TableView<Invitation> invitationsTable;

    public InvitationsView(StageManager stageManager) {
        super(stageManager);
        this.root = new BorderPane();
        this.invitations = FXCollections.observableArrayList();

        this.prepareView(root);

        this.windowTitle = "View Invitations";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        this.invitationsTable = this.createInvitationsTable(invitations);
        this.root.setCenter(invitationsTable);

        HBox buttonContainer = this.createButtonContainer();
        this.root.setBottom(buttonContainer);
    }

    @Override
    public void destroyView() {
        this.invitations.clear();
    }

    @Override
    public void refreshData() {
        User user = UserDatastore.getInstance().getCurrentUser();

        Pane topBar = TopBar.getTopBar(user.getHomeView());
        root.setTop(topBar);

        InvitationsViewController.loadInvitations(invitations, user);
    }

    protected TableView<Invitation> createInvitationsTable(ObservableList<Invitation> events) {
        TableView<Invitation> tableView = new TableView<>();

        TableColumn<Invitation, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

        TableColumn<Invitation, String> nameColumn = new TableColumn<>("Event Name");
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEvent().getValue().getName()));

        TableColumn<Invitation, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(DateUtil.formatDate(cellData.getValue().getEvent().getValue().getDate(), DateUtil.DATE_FORMAT)));

        TableColumn<Invitation, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(StringUtil.truncate(cellData.getValue().getEvent().getValue().getLocation(), 200)));

        TableColumn<Invitation, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(StringUtil.truncate(cellData.getValue().getEvent().getValue().getDescription(), 200)));

        idColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        nameColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        dateColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        locationColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        descriptionColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.4));

        tableView.getColumns().addAll(idColumn, nameColumn, dateColumn, locationColumn, descriptionColumn);
        tableView.setItems(events);

        return tableView;
    }

    private HBox createButtonContainer() {
        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(20));

        Button editNameButton = this.createAcceptButton();
        buttonContainer.getChildren().add(editNameButton);

        Button inviteVendorButton = this.createDeleteButton();
        buttonContainer.getChildren().add(inviteVendorButton);

        return buttonContainer;
    }

    private Button createAcceptButton() {
        Button registerButton = new Button("Accept Invitation");
        registerButton.setPrefWidth(200);
        registerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");

        registerButton.setOnMouseClicked(e -> {
            Invitation selectedEvent = invitationsTable.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                User user = UserDatastore.getInstance().getCurrentUser();
                InvitationsViewController.acceptInvitation(selectedEvent, user);

                this.refreshData();
            }
        });

        return registerButton;
    }

    private Button createDeleteButton() {
        Button registerButton = new Button("Decline Invitation");
        registerButton.setPrefWidth(200);
        registerButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-size: 14px;");

        registerButton.setOnMouseClicked(e -> {
            Invitation selectedItem = invitationsTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                InvitationsViewController.deleteInvitation(selectedItem);

                this.refreshData();
            }
        });

        return registerButton;

    }

}
