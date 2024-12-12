package view.vendor;

import controller.view.eventogranizer.EOHomeViewController;
import controller.view.vendor.VendorHomeViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.SFHomeView;
import view.StageManager;
import view.component.TopBar;

public class VendorHomeView extends SFHomeView {

    public VendorHomeView(StageManager stageManager) {
        super(stageManager);
        BorderPane root = new BorderPane();

        this.prepareView(root);

        this.windowTitle = "Home - Vendor";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setAlignment(Pos.CENTER);

        formContainer.getChildren().addAll(
                this.createRedirectButton("View Invitations", VendorHomeViewController::handleViewInvitationsRedirect),
                this.createRedirectButton("View Accepted Events", VendorHomeViewController::handleViewAcceptedEventsRedirect),
                this.createRedirectButton("Manage Vendor Product", VendorHomeViewController::handleMangeProductsRedirect),
                this.createRedirectButton("Edit Profile", VendorHomeViewController::handleEditProfileRedirect)
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
