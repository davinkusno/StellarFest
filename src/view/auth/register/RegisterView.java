package view.auth.register;

import controller.view.auth.RegisterViewController;
import enums.Role;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.SFFormView;
import view.StageManager;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RegisterView extends SFFormView {

    private TextField emailInput;
    private TextField usernameInput;
    private PasswordField passwordInput;
    private ComboBox<String> roleSelector;

    public RegisterView(StageManager stageManager) {
        super(stageManager);
        BorderPane root = new BorderPane();

        this.prepareView(root);

        this.windowTitle = "Register";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Register");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        formContainer.getChildren().add(titleLabel);

        FormRow emailRow = this.createTextField("Email", TextField.class, formContainer);
        this.emailInput = emailRow.getTextField();

        FormRow usernameRow = this.createTextField("Username", TextField.class, formContainer);
        this.usernameInput = usernameRow.getTextField();

        FormRow passwordRow = this.createTextField("Password", PasswordField.class, formContainer);
        this.passwordInput = (PasswordField) passwordRow.getTextField();

        HBox roleRow = this.createRoleSelector();
        formContainer.getChildren().add(roleRow);

        Button registerButton = this.createRegisterButton();
        formContainer.getChildren().add(registerButton);

        Hyperlink loginRedirectLink = this.createLoginRedirectLink();
        formContainer.getChildren().add(loginRedirectLink);

        BorderPane borderPane = (BorderPane) root;
        borderPane.setCenter(formContainer);
    }

    @Override
    public void destroyView() {
        this.emailInput.clear();
        this.usernameInput.clear();
        this.passwordInput.clear();
        this.roleSelector.getSelectionModel().clearSelection();
    }

    private Button createRegisterButton() {
        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        registerButton.setPrefWidth(200);

        registerButton.setOnMouseClicked(e -> {
            RegisterViewController.handleRegister(this.emailInput, this.usernameInput, this.passwordInput, this.roleSelector);
        });

        return registerButton;
    }

    private HBox createRoleSelector() {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER);

        Label label = new Label("Role");
        label.setMinWidth(100);
        row.getChildren().add(label);

        this.roleSelector = new ComboBox<>();
        this.roleSelector.getItems().addAll(Stream.of(Role.values()).map(Role::getRole).collect(Collectors.toList()));
        this.roleSelector.setPromptText("Select a role");
        row.getChildren().add(this.roleSelector);

        return row;
    }

    private Hyperlink createLoginRedirectLink() {
        Hyperlink loginRedirectLink = new Hyperlink("Already have an account? Login");
        loginRedirectLink.setStyle("-fx-text-fill: #2196F3; -fx-font-size: 14px;");

        loginRedirectLink.setOnAction(e -> {
            RegisterViewController.handleLoginRedirect();
        });

        loginRedirectLink.setOnMouseEntered(e -> {
            loginRedirectLink.setStyle("-fx-text-fill: #1976D2; -fx-font-size: 14px; -fx-underline: true;");
        });

        loginRedirectLink.setOnMouseExited(e -> {
            loginRedirectLink.setStyle("-fx-text-fill: #2196F3; -fx-font-size: 14px;");
        });

        return loginRedirectLink;
    }

}
