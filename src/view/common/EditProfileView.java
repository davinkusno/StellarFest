package view.common;

import controller.view.common.EditProfileViewController;
import datastore.UserDatastore;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.user.User;
import view.Refreshable;
import view.SFView;
import view.StageManager;
import view.component.TopBar;

public class EditProfileView extends SFView implements Refreshable {

    private BorderPane root;
    private TextField emailInput;
    private TextField usernameInput;
    private PasswordField oldPasswordInput;
    private PasswordField newPasswordInput;

    public EditProfileView(StageManager stageManager) {
        super(stageManager);
        this.root = new BorderPane();

        this.prepareView(root);

        this.windowTitle = "Edit Profile";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Edit Profile");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        formContainer.getChildren().add(titleLabel);

        HBox emailRow = this.createEmailInput();
        formContainer.getChildren().add(emailRow);

        HBox usernameRow = this.createUsernameInput();
        formContainer.getChildren().add(usernameRow);

        HBox newPasswordRow = this.createNewPasswordInput();
        formContainer.getChildren().add(newPasswordRow);

        HBox oldPasswordRow = this.createOldPasswordInput();
        formContainer.getChildren().add(oldPasswordRow);

        Button saveButton = this.createSaveButton();
        formContainer.getChildren().add(saveButton);

        BorderPane borderPane = (BorderPane) root;
        borderPane.setCenter(formContainer);
    }

    private Button createSaveButton() {
        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        registerButton.setPrefWidth(200);

        registerButton.setOnMouseClicked(e -> {
            EditProfileViewController.handleEditProfile(this.emailInput, this.usernameInput, this.oldPasswordInput, this.newPasswordInput);
        });

        return registerButton;
    }

    @Override
    public void destroyView() {

    }

    @Override
    public void refreshData() {
        User currentUser = UserDatastore.getInstance().getCurrentUser();
        assert currentUser != null;

        Pane topBar = TopBar.getTopBar(currentUser.getHomeView());
        this.root.setTop(topBar);
    }

    private HBox createEmailInput() {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER);

        Label label = new Label("Email");
        label.setMinWidth(100);
        row.getChildren().add(label);

        this.emailInput = new TextField();
        row.getChildren().add(this.emailInput);

        return row;
    }

    private HBox createUsernameInput() {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER);

        Label label = new Label("Username");
        label.setMinWidth(100);
        row.getChildren().add(label);

        this.usernameInput = new TextField();
        row.getChildren().add(this.usernameInput);

        return row;
    }

    private HBox createNewPasswordInput() {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER);

        Label label = new Label("New Password");
        label.setMinWidth(100);
        row.getChildren().add(label);

        this.newPasswordInput = new PasswordField();
        row.getChildren().add(this.newPasswordInput);

        return row;
    }

    private HBox createOldPasswordInput() {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER);

        Label label = new Label("Old Password");
        label.setMinWidth(100);
        row.getChildren().add(label);

        this.oldPasswordInput = new PasswordField();
        row.getChildren().add(this.oldPasswordInput);

        return row;
    }
}
