package view.guest;

import controller.view.guest.GuestHomeViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.SFHomeView;
import view.StageManager;
import view.component.TopBar;

public class GuestHomeView extends SFHomeView {

    public GuestHomeView(StageManager stageManager) {
        super(stageManager);
        BorderPane root = new BorderPane();

        this.prepareView(root);

        this.windowTitle = "Home - Guest";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setAlignment(Pos.CENTER);

        formContainer.getChildren().addAll(
                this.createRedirectButton("View Invitations", GuestHomeViewController::handleViewInvitationsRedirect),
                this.createRedirectButton("View Accepted Events", GuestHomeViewController::handleViewAcceptedEventsRedirect),
                this.createRedirectButton("Edit Profile", GuestHomeViewController::handleEditProfileRedirect)
        );

        BorderPane borderPane = (BorderPane) root;
        borderPane.setCenter(formContainer);

        Pane topBar = TopBar.getTopBar(null);
        borderPane.setTop(topBar);
    }

    @Override
    public void destroyView() {

    }

}
