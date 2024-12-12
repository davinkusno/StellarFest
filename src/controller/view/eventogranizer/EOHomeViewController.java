package controller.view.eventogranizer;

import view.StageManager;
import view.common.EditProfileView;
import view.eventorganizer.create.CreateEventView;
import view.eventorganizer.view.ManagedEventsView;

public class EOHomeViewController {

    public static void handleCreateEventRedirect() {
        StageManager.getInstance().switchScene(CreateEventView.class);
    }

    public static void handleViewOrganizedEventsRedirect() {
        StageManager.getInstance().switchScene(ManagedEventsView.class);
    }

    public static void handleEditProfileRedirect() {
        StageManager.getInstance().switchScene(EditProfileView.class);
    }

}
