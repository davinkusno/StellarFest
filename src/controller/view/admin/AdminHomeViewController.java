package controller.view.admin;

import view.StageManager;
import view.admin.EventManagementView;
import view.admin.UserManagementView;
import view.common.EditProfileView;

public class AdminHomeViewController {

    public static void handleManageEventRedirect() {
        StageManager.getInstance().switchScene(EventManagementView.class);
    }

    public static void handleManageUserRedirect() {
        StageManager.getInstance().switchScene(UserManagementView.class);
    }

    public static void handleEditProfile() {
        StageManager.getInstance().switchScene(EditProfileView.class);
    }

}
