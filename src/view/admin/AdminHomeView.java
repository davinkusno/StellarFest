package view.admin;

import controller.view.admin.AdminHomeViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.SFHomeView;
import view.StageManager;
import view.component.TopBar;

public class AdminHomeView extends SFHomeView {

    public AdminHomeView(StageManager stageManager) {
        super(stageManager);
        BorderPane root = new BorderPane();

        this.prepareView(root);

        this.windowTitle = "Home - Admin";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setAlignment(Pos.CENTER);

        formContainer.getChildren().addAll(
                this.createRedirectButton("Manage Events", AdminHomeViewController::handleManageEventRedirect),
                this.createRedirectButton("Manage Users", AdminHomeViewController::handleManageUserRedirect),
                this.createRedirectButton("Edit Profile", AdminHomeViewController::handleEditProfile)
        );

        BorderPane borderPane = (BorderPane) root;
        borderPane.setCenter(formContainer);

        Pane topBar = TopBar.getTopBar(null);
        borderPane.setTop(topBar);
    }

    @Override
    public void destroyView() {
        // No cleanup required
    }

}
