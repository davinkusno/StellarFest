package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.StageManager;
import view.admin.AdminHomeView;
import view.admin.EventManagementView;
import view.admin.UserManagementView;
import view.auth.login.LoginView;
import view.auth.register.RegisterView;
import view.common.EditProfileView;

public class Main extends Application {

    private StageManager stageManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stageManager = new StageManager(stage);

        this.registerViews();

        this.stageManager.switchScene(LoginView.class);
    }

    private void registerViews() {
        this.stageManager.addScene(new LoginView(this.stageManager));
        this.stageManager.addScene(new RegisterView(this.stageManager));
        this.stageManager.addScene(new EditProfileView(this.stageManager));

        this.stageManager.addScene(new AdminHomeView(stageManager));
        this.stageManager.addScene(new UserManagementView(stageManager));
        this.stageManager.addScene(new EventManagementView(stageManager));
    }

}
