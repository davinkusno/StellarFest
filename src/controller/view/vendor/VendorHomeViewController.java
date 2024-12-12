package controller.view.vendor;

import view.StageManager;
import view.common.EditProfileView;

public class VendorHomeViewController {

    public static void handleViewAcceptedEventsRedirect() {

    }

    public static void handleViewInvitationsRedirect() {

    }

    public static void handleMangeProductsRedirect() {

    }

    public static void handleEditProfileRedirect() {
        StageManager.getInstance().switchScene(EditProfileView.class);
    }
}
