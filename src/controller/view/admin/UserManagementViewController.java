package controller.view.admin;

import controller.UserController;
import driver.Connect;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.user.User;
import util.AlertUtil;

public class UserManagementViewController {

    public static void loadUsers(ObservableList<User> users) {
        users.clear();
        users.addAll(UserController.getAll());
    }

    public static void handleDeleteSelectedUser(TableView<User> userTable, ObservableList<User> users) {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            AlertUtil.showError("No user selected", "Please select a user to delete");
            return;
        }

        try {
            int update = Connect.getInstance().executeUpdate("DELETE FROM users WHERE id = " + selectedUser.getId());
            if (update <= 0) {
                AlertUtil.showError("Error deleting user", "No user was deleted");
                return;
            }

            users.remove(selectedUser);
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Error deleting user", "An error occurred while deleting the user");
        }
    }
}
