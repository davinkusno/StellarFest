package view.component;

import datastore.UserDatastore;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import view.SFView;
import view.StageManager;
import view.auth.login.LoginView;

public class TopBar {

    private static Pane topBar = null;

    public static Pane getTopBar() {
        if (topBar == null) {
            buildTopBar();
        }

        return topBar;
    }

    private static void buildTopBar() {
        HBox topBarContainer = new HBox(10);
        topBarContainer.setPadding(new Insets(10, 20, 10, 20));
        topBarContainer.setStyle("-fx-background-color: #f0f0f0;");
        topBarContainer.setPrefHeight(50);

        Button backButton = new Button("Back");
        backButton.setPrefWidth(100);
        backButton.setOnMouseClicked(e -> {
            StageManager stageManager = StageManager.getInstance();

            SFView previousView = stageManager.getPreviousView();
            stageManager.switchScene(previousView.getViewName());
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setPrefWidth(100);
        logoutButton.setOnMouseClicked(e -> {
            UserDatastore.getInstance().setCurrentUser(null);
            StageManager.getInstance().switchScene(LoginView.class);
        });
    }

}
