package controller.view.eventogranizer;

import view.StageManager;
import view.common.EditProfileView;

public class EOHomeViewController {

    public static void handleCreateEventRedirect() {

    }

    public static void handleViewOrganizedEventsRedirect() {

    }

    public static void handleEditProfileRedirect() {
        StageManager.getInstance().switchScene(EditProfileView.class);
    }


}
