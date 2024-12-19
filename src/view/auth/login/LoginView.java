package view.auth.login;

import controller.view.auth.LoginViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.SFFormView;
import view.StageManager;

public class LoginView extends SFFormView {

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

        FormRow emailRow = this.createTextField("Email", TextField.class, formContainer);
        this.emailInput = emailRow.getTextField();

        FormRow passwordRow = this.createTextField("Password", PasswordField.class, formContainer);
        this.passwordInput = (PasswordField) passwordRow.getTextField();

        Button registerButton = this.createLoginButton();
        formContainer.getChildren().add(registerButton);

        Hyperlink loginRedirectLink = this.createRegisterRedirectLink();
        formContainer.getChildren().add(loginRedirectLink);

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
    
    private Hyperlink createRegisterRedirectLink() {
        Hyperlink registerRedirectLink = new Hyperlink("Don't have an account? Register");
        registerRedirectLink.setStyle("-fx-text-fill: #2196F3; -fx-font-size: 14px;");

        registerRedirectLink.setOnAction(e -> {
        	LoginViewController.handleRegisterRedirect();
        });

        registerRedirectLink.setOnMouseEntered(e -> {
            registerRedirectLink.setStyle("-fx-text-fill: #1976D2; -fx-font-size: 14px; -fx-underline: true;");
        });

        registerRedirectLink.setOnMouseExited(e -> {
            registerRedirectLink.setStyle("-fx-text-fill: #2196F3; -fx-font-size: 14px;");
        });

        return registerRedirectLink;
    }

}
