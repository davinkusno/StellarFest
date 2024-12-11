package controller.view.common;

import controller.UserController;
import datastore.UserDatastore;
import javafx.scene.control.TextField;
import model.user.User;
import util.AlertUtil;

public class EditProfileViewController {

    public static void handleEditProfile(TextField emailInput, TextField usernameInput, TextField oldPasswordInput, TextField newPasswordInput) {
        User currentUser = UserDatastore.getInstance().getCurrentUser();
        assert currentUser != null;

        String email = emailInput.getText();
        String username = usernameInput.getText();
        String oldPassword = oldPasswordInput.getText();
        String newPassword = newPasswordInput.getText();

        if (!currentUser.getEmail().equals(email)) {
            if (email.isEmpty()) {
                AlertUtil.showError("Email is required", "Please enter your email");
                return;
            }

            if (UserController.isDuplicate("email", email)) {
                AlertUtil.showError("Email is already taken", "Please enter a different email");
                return;
            }
        }

        if (!currentUser.getUsername().equals(username)) {
            if (username.isEmpty()) {
                AlertUtil.showError("Username is required", "Please enter your username");
                return;
            }

            if (UserController.isDuplicate("username", username)) {
                AlertUtil.showError("Username is already taken", "Please enter a different username");
                return;
            }
        }

        if (newPassword.isEmpty()) { // User is not changing password
            updateToDatabase(currentUser, email, username, oldPassword, oldPassword);
            return;
        }

        if (newPassword.length() < 5) {
            AlertUtil.showError("Password is too short", "Please enter a password with at least 5 characters");
            return;
        }

        updateToDatabase(currentUser, email, username, oldPassword, newPassword);
    }

    private static void updateToDatabase(User currentUser, String email, String username, String oldPassword, String newPassword) {
        if (oldPassword.isEmpty()) {
            AlertUtil.showError("Old password is required", "Please enter your old password");
            return;
        }

        if (!isAuthorized(oldPassword, currentUser)) {
            AlertUtil.showError("Old password is incorrect", "Please enter your correct old password");
            return;
        }

        UserController.updateUser(currentUser.getId(), email, username, newPassword);
    }

    private static boolean isAuthorized(String oldPassword, User currentUser) {
        User user = UserController.login(currentUser.getEmail(), oldPassword);
        return user != null;
    }
}
