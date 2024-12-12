package view.eventorganizer;

import controller.view.eventogranizer.EOHomeViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.SFHomeView;
import view.StageManager;
import view.component.TopBar;

public class EOHomeView extends SFHomeView {

    public EOHomeView(StageManager stageManager) {
        super(stageManager);
        BorderPane root = new BorderPane();

        this.prepareView(root);

        this.windowTitle = "Home - Event Organizer";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setAlignment(Pos.CENTER);

        formContainer.getChildren().addAll(
                this.createRedirectButton("Create Event", EOHomeViewController::handleCreateEventRedirect),
                this.createRedirectButton("View Organized Events", EOHomeViewController::handleViewOrganizedEventsRedirect),
                this.createRedirectButton("Edit Profile", EOHomeViewController::handleEditProfileRedirect)
        );

        BorderPane borderPane = (BorderPane) root;
        borderPane.setCenter(formContainer);

        Pane topBar = TopBar.getTopBar(null);
        borderPane.setTop(topBar);
    }

}
