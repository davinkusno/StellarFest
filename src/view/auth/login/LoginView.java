package view.auth.login;

import controller.view.auth.LoginViewController;
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
import view.SFView;
import view.StageManager;

public class LoginView extends SFView {

    private TextField emailInput;
    private PasswordField passwordInput;

    public LoginView(StageManager stageManager) {
        super(stageManager);
        BorderPane root = new BorderPane();

        this.prepareView(root);

        this.windowTitle = "Login";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        formContainer.getChildren().add(titleLabel);

        HBox emailRow = this.createEmailInput();
        formContainer.getChildren().add(emailRow);

        HBox passwordRow = this.createPasswordInput();
        formContainer.getChildren().add(passwordRow);

        Button registerButton = this.createLoginButton();
        formContainer.getChildren().add(registerButton);

        Label loginRedirectLabel = this.createRegisterRedirectLabel();
        formContainer.getChildren().add(loginRedirectLabel);

        BorderPane borderPane = (BorderPane) root;
        borderPane.setCenter(formContainer);
    }

    @Override
    public void destroyView() {
        this.emailInput.clear();
        this.passwordInput.clear();
    }

    private Button createLoginButton() {
        Button registerButton = new Button("Login");
        registerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        registerButton.setPrefWidth(200);

        registerButton.setOnMouseClicked(e -> {
            LoginViewController.handleLogin(this.emailInput, this.passwordInput);
        });

        return registerButton;
    }

    private Label createRegisterRedirectLabel() {
        Label registerRedirectLabel = new Label("Don't have an account? Register");
        registerRedirectLabel.setStyle("-fx-text-fill: #2196F3; -fx-font-size: 14px;");

        registerRedirectLabel.setOnMouseClicked(e -> {
            LoginViewController.handleRegisterRedirect();
        });

        registerRedirectLabel.setOnMouseEntered(e -> {
            registerRedirectLabel.setStyle("-fx-text-fill: #1976D2; -fx-font-size: 14px; -fx-underline: true;");
        });

        return registerRedirectLabel;
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

    private HBox createPasswordInput() {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER);

        Label label = new Label("Password");
        label.setMinWidth(100);
        row.getChildren().add(label);

        this.passwordInput = new PasswordField();
        row.getChildren().add(this.passwordInput);

        return row;
    }

}
