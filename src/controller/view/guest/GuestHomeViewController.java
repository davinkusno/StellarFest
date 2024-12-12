package controller.view.guest;

import view.StageManager;
import view.common.EditProfileView;
import view.common.InvitedEventsView;

public class GuestHomeViewController {

    public static void handleViewAcceptedEventsRedirect() {
        StageManager.getInstance().switchScene(InvitedEventsView.class);
    }

    public static void handleViewInvitationsRedirect() {

    }

    public static void handleEditProfileRedirect() {
        StageManager.getInstance().switchScene(EditProfileView.class);
    }
}
